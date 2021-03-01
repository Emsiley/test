package suiviDesRepas.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import suiviDesRepas.BusinessException;
import suiviDesRepas.bo.Aliment;
import suiviDesRepas.bo.Repas;

public class RepasDAOJdbcImpl implements RepasDAO {

	private static final String INSERT_REPAS="insert into repas(date_repas, heure_repas) values(?,?)";
	private static final String INSERT_ALIMENT="insert into aliments(nom, id_repas) values(?,?)";
	private static final String SELECT_ALL=" SELECT " + 
											"	r.id as id_repas," + 
											"	r.date_repas," + 
											"	r.heure_repas," + 
											"	a.id as id_aliment," + 
											"	a.nom" + 
											" FROM" + 
											"	REPAS r" + 
											"	INNER JOIN ALIMENTS a ON r.id=a.id_repas" +
											"	ORDER BY r.date_repas desc, r.heure_repas desc";
	private static final String SELECT_UNE_DATE=" SELECT " + 
											"	r.id as id_repas," + 
											"	r.date_repas," + 
											"	r.heure_repas," + 
											"	a.id as id_aliment," + 
											"	a.nom" + 
											" FROM" + 
											"	REPAS r" + 
											"	INNER JOIN ALIMENTS a ON r.id=a.id_repas" +
											" WHERE r.date_repas=?"+
											"	ORDER BY r.date_repas desc, r.heure_repas desc";
	
	@Override
	public void insert(Repas repas) throws BusinessException {
		if(repas==null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			try
			{
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_REPAS, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setDate(1, java.sql.Date.valueOf(repas.getDate_repas()));
				pstmt.setTime(2, java.sql.Time.valueOf(repas.getHeure_repas()));
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					repas.setId(rs.getInt(1));
				}
				rs.close();
				pstmt.close();
				pstmt = cnx.prepareStatement(INSERT_ALIMENT, PreparedStatement.RETURN_GENERATED_KEYS);
				for (Aliment aliment : repas.getListeAliments()) {
					pstmt.setString(1, aliment.getNom());
					pstmt.setInt(2, repas.getId());
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if(rs.next())
					{
						aliment.setId(rs.getInt(1));
					}
					rs.close();
				}
				pstmt.close();
				cnx.commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}

	}

	@Override
	public List<Repas> select() throws BusinessException {
		List<Repas> listeRepas = new ArrayList<Repas>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			Repas repasCourant=new Repas();
			while(rs.next())
			{
				if(rs.getInt("id_repas")!=repasCourant.getId())
				{
					repasCourant = repasBuilder(rs);
					listeRepas.add(repasCourant);
				}
				Aliment aliment = alimentBuilder(rs);
				repasCourant.getListeAliments().add(aliment);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_REPAS_ECHEC);
			throw businessException;
		}
		return listeRepas;
	}

	@Override
	public List<Repas> select(LocalDate dateRecherchee) throws BusinessException {
		List<Repas> listeRepas = new ArrayList<Repas>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_UNE_DATE);
			pstmt.setDate(1, java.sql.Date.valueOf(dateRecherchee));
			ResultSet rs = pstmt.executeQuery();
			Repas repasCourant=new Repas();
			while(rs.next())
			{
				if(rs.getInt("id_repas")!=repasCourant.getId())
				{
					repasCourant = repasBuilder(rs);
					listeRepas.add(repasCourant);
				}
				Aliment aliment = alimentBuilder(rs);
				repasCourant.getListeAliments().add(aliment);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_REPAS_ECHEC);
			throw businessException;
		}
		return listeRepas;
	}

	
	@Override
	public List<Repas> select(int page) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Aliment alimentBuilder(ResultSet rs) throws SQLException {
		Aliment aliment = new Aliment(rs.getInt("id_aliment"), rs.getString("nom"));
		return aliment;
	}

	private Repas repasBuilder(ResultSet rs) throws SQLException {
		Repas repasCourant;
		repasCourant=new Repas();
		repasCourant.setId(rs.getInt("id_repas"));
		repasCourant.setDate_repas(rs.getDate("date_repas").toLocalDate());
		repasCourant.setHeure_repas(rs.getTime("heure_repas").toLocalTime());
		return repasCourant;
	}


}

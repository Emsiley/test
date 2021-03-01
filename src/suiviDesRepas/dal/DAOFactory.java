package suiviDesRepas.dal;

public abstract class DAOFactory {
	
	public static RepasDAO getRepasDAO()
	{
		return new RepasDAOJdbcImpl();
	}
}
	
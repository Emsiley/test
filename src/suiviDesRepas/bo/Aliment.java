package suiviDesRepas.bo;

public class Aliment {
	int id; //identity(1,1) CONSTRAINT PK_ALIMENTS PRIMARY KEY,
	String nom ;//varchar(50) NOT NULL,
	int id_repas ; //NOT NULL
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getId_repas() {
		return id_repas;
	}
	public void setId_repas(int id_repas) {
		this.id_repas = id_repas;
	}
	public Aliment() {	}	

	public Aliment(String nom) {
		super();
		this.nom = nom;
	}	
	public Aliment(int identifiant, String nom) {
		this(nom);
		this.id = identifiant;
	}
	@Override
	public String toString() {
		return "Aliment [identifiant=" + id + ", nom=" + nom + "]";
	}
	
	
}

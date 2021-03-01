package suiviDesRepas.bo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Repas {
	int id; //int identity(1,1) CONSTRAINT PK_REPAS PRIMARY KEY,
	LocalDate date_repas; // date NOT NULL ,
	LocalTime heure_repas; //time NOT NULL
	ArrayList <Aliment> listeAliments;

	public Repas() {
		listeAliments = new ArrayList <Aliment>(); 
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDate_repas() {
		return date_repas;
	}
	public void setDate_repas(LocalDate date_repas) {
		this.date_repas = date_repas;
	}
	public LocalTime getHeure_repas() {
		return heure_repas;
	}
	public void setHeure_repas(LocalTime heure_repas) {
		this.heure_repas = heure_repas;
	}
	public ArrayList<Aliment> getListeAliments() {
		return listeAliments;
	}
	public void setListeAliments(ArrayList<Aliment> listeAliments) {
		this.listeAliments = listeAliments;
	}
}
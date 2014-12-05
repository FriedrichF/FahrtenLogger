package com.friedrichf.fahrtenlogger;

import java.util.Date;

/**
 * @author Friedrich
 *
 */
public class Fahrt {
	private String Bezeichnung;
	private int id;
	private double km;
	private Date datum;
	private String Kategorie;
	
	
	/**
	 * @param Bezeichnung
	 * @param km
	 * @param datum wenn null übergeben wird, wird aktuelles Datum verwendet
	 */
	public Fahrt(int id, String Bezeichnung, double km, Date datum, String Kategorie){
		this.id = id;
		this.Kategorie = Kategorie;
		this.Bezeichnung = Bezeichnung;
		this.km = km;
		
		if(datum == null){
			Date dateTmp = new Date();
			this.datum = dateTmp;
		}
		else{
			this.datum = datum;
		}
	}
	
	public Fahrt(){
		this(-1, "Dummy", 100, null, "");
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getKm() {
		return km;
	}

	public void setKm(double km) {
		this.km = km;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getBezeichnung(){
		return Bezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung){
		this.Bezeichnung = bezeichnung;
	}

	public String getKategorie() {
		return Kategorie;
	}

	public void setKategorie(String kategorie) {
		Kategorie = kategorie;
	}

}

package com.friedrichf.fahrtenlogger;

public class Kategorie {
	
	private String KategorieName;
	
	public Kategorie(String KategorieName){
		this.setKategorieName(KategorieName);
	}

	public String getKategorieName() {
		return KategorieName;
	}

	public void setKategorieName(String kategorieName) {
		KategorieName = kategorieName;
	}

}

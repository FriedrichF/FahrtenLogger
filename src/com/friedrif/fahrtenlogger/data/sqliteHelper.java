package com.friedrif.fahrtenlogger.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.friedrichf.fahrtenlogger.Fahrt;

public class sqliteHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_APP_NAME = "FahrtenLogger";
	
	private static final String DATABASE_NAME_FAHRTEN = "Fahrten";
	
	private static final String FAHRTEN_ID = "id";
	private static final String FAHRTEN_BEZEICHNUNG = "Bezeichnung";
	private static final String FAHRTEN_KATEGORIE = "Kategorie";
	private static final String FAHRTEN_KM = "KM";
	private static final String FAHRTEN_DATUM = "Datum";
	
	private static final String DATABASE_NAME_KATEGORIE = "Kategorie";
	
	private static final String KATEGORIE_ID = "id";
	private static final String KATEGORIE_NAME = "Kategorie_Name";
	

	public sqliteHelper(Context context) {
		super(context, DATABASE_APP_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_FAHRTEN_TABLE = "CREATE TABLE " + DATABASE_NAME_FAHRTEN + "("
                + FAHRTEN_ID + " INTEGER PRIMARY KEY NOT NULL," + FAHRTEN_BEZEICHNUNG + " TEXT,"
                + FAHRTEN_KATEGORIE + " TEXT,"+ FAHRTEN_KM + " REAL, " + FAHRTEN_DATUM + " DATE)";
		db.execSQL(CREATE_FAHRTEN_TABLE);
		
		String CREATE_KATEGORIE_TABLE = "CREATE TABLE " + DATABASE_NAME_KATEGORIE + "("
                + KATEGORIE_ID + " INTEGER PRIMARY KEY NOT NULL," + KATEGORIE_NAME + " TEXT)";
		db.execSQL(CREATE_KATEGORIE_TABLE);
	}
	
	public void delDB(){
		SQLiteDatabase db = this.getWritableDatabase();
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME_FAHRTEN);
        
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME_KATEGORIE);
 
        // Create tables again
        onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME_FAHRTEN);
 
        // Create tables again
        onCreate(db);
	}
	
	public void addFahrt(Fahrt fahrt){
		SQLiteDatabase db = this.getWritableDatabase();
		
		final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		ContentValues values = new ContentValues();
		values.put(FAHRTEN_BEZEICHNUNG, fahrt.getBezeichnung());
		values.put(FAHRTEN_KM, fahrt.getKm());
		values.put(FAHRTEN_DATUM, parser.format(fahrt.getDatum()));
		values.put(FAHRTEN_KATEGORIE, fahrt.getKategorie());
		
		db.insert(DATABASE_NAME_FAHRTEN, null, values);
		db.close();
	}
	
	public ArrayList<Fahrt> getAllFahrten(){
		ArrayList<Fahrt> alleFahrten = new ArrayList<Fahrt>();
		
		String SELECT_ALL = "SELECT * FROM " + DATABASE_NAME_FAHRTEN;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SELECT_ALL, null);
		
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		if(cursor.moveToFirst()){
			do{
				try {
					date = sdf1.parse(cursor.getString(4).toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Fahrt fahrt = new Fahrt(Integer.parseInt(cursor.getString(0)), cursor.getString(1), 
						Double.parseDouble(cursor.getString(3)), date, cursor.getString(2));
				alleFahrten.add(fahrt);
			}while(cursor.moveToNext());
		}
		
		return alleFahrten;
		
	}
	
	public boolean deleteById(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(DATABASE_NAME_FAHRTEN, FAHRTEN_ID + "=" + id, null) > 0;
	}
	
	
	//** AB Hier Methoden für Kategorie Tabelle **//
	
	public ArrayList<String> getAllKategorien(){
		ArrayList<String> kategorien = new ArrayList<String>();
		
		String SELECT_ALL_KATEGORIE = "SELECT * FROM " + DATABASE_NAME_KATEGORIE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SELECT_ALL_KATEGORIE, null);
		
		if(cursor.moveToFirst()){
			do{
				kategorien.add(cursor.getString(1));
			}while(cursor.moveToNext());
		}
		
		return kategorien;
	}
	
	public void addKategorie(String Kategorie){
		SQLiteDatabase db = this.getWritableDatabase();
				
		ContentValues values = new ContentValues();
		values.put(KATEGORIE_NAME, Kategorie);
		
		db.insert(DATABASE_NAME_KATEGORIE, null, values);
		db.close();
	}
	

}

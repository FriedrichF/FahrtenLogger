package com.friedrichf.fahrtenlogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.friedrif.fahrtenlogger.data.sqliteHelper;

public class NeueFahrt extends Activity {
	
	private int year;
	private int month;
	private int day;
	
	private TextView dateText;
	private Button dateButton, sendButton;
	private EditText bezeichnungText, kmText;
	private AutoCompleteTextView kategorieAutoComplete;
	
	private sqliteHelper db;
	
	static final int DATE_DIALOG_ID = 999;
	private ArrayList<String> alleKategorien;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neue_fahrt);
		
		dateText = (TextView) findViewById(R.id.datumText);
		bezeichnungText = (EditText) findViewById(R.id.bezeichnungInput);
		kmText = (EditText) findViewById(R.id.kmInput);
		
		dateButton = (Button) findViewById(R.id.datumButton);
		sendButton = (Button) findViewById(R.id.neueFahrtButton);
		
		kategorieAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteKategorie);
		
		db = new sqliteHelper(this);
		
		alleKategorien = db.getAllKategorien();
		
		for(String kat: alleKategorien){
			Log.d("Kategorie", kat);
		}
		
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,  
				android.R.layout.simple_dropdown_item_1line, 
				alleKategorien);
		
		kategorieAutoComplete.setAdapter(adapter_state);
		kategorieAutoComplete.setThreshold(0);
		
		dateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		sendButton.setOnClickListener(new View.OnClickListener() {
			Date date;
			@Override
			public void onClick(View v) {
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
				try {
					date = sdf1.parse(dateText.getText().toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				String neueKategorie = kategorieAutoComplete.getText().toString();
				
				if(alleKategorien.indexOf(neueKategorie) == -1){
					db.addKategorie(neueKategorie);
				}
				
				db.addFahrt(new Fahrt(-1, bezeichnungText.getText().toString(), 
						Double.parseDouble(kmText.getText().toString()), date, 
						neueKategorie));
				finish();
			}
		});

		setCurrentDateOnView();
	}
	
	// display current date
	public void setCurrentDateOnView() { 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		dateText.setText(month+"-"+day+"-"+year);
	}
 
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			dateText.setText(month+"-"+day+"-"+year);
		}
	};
}

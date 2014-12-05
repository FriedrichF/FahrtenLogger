package com.friedrichf.fahrtenlogger;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.friedrif.fahrtenlogger.data.sqliteHelper;

public class Uebersicht extends ActionBarActivity {
	
	private Button addButton, deleteDB;
	sqliteHelper db;
	ListView fahrtenList;
	
	FahrtenAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uebersicht);
		
		db = new sqliteHelper(this);
		
		fahrtenList = (ListView) findViewById(R.id.listView);
		
		ArrayList<Fahrt> alleFahrten = new ArrayList<Fahrt>();
		
		alleFahrten = db.getAllFahrten();
		
		adapter = new FahrtenAdapter(this, alleFahrten, db);
		final Intent intent = new Intent(this, NeueFahrt.class);
		
		fahrtenList.setAdapter(adapter);
		fahrtenList.setOnItemClickListener(adapter);
		
		addButton = (Button) findViewById(R.id.addFahrt);
		deleteDB = (Button) findViewById(R.id.deleteAll);
		
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(intent);
				
				adapter.updateList(db.getAllFahrten());
				adapter.notifyDataSetChanged();
			}
		});
		
		deleteDB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.delDB();
				adapter.updateList(db.getAllFahrten());
				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uebersicht, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		adapter.updateList(db.getAllFahrten());
		adapter.notifyDataSetChanged();
	}
}

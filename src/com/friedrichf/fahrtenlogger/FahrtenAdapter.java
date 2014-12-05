package com.friedrichf.fahrtenlogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.friedrif.fahrtenlogger.data.sqliteHelper;

public class FahrtenAdapter extends BaseAdapter implements OnItemClickListener {
	private final LayoutInflater mInflater;
	private ArrayList<Fahrt> fahrtList = new ArrayList<Fahrt>();
	private Context context;
	private sqliteHelper db;
	
	public FahrtenAdapter(Context context, ArrayList<Fahrt> fahrtList, sqliteHelper db){
		this.db = db;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
		this.fahrtList = fahrtList;
	}
	
	@Override
	public int getCount() {
		return fahrtList.size();
	}

	@Override
	public Object getItem(int pos) {
		return fahrtList.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyViewHolder mViewHolder;
		SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
        
        if(convertView == null) {
                convertView = mInflater.inflate(R.layout.all_fahrten_list_view, null);
                mViewHolder = new MyViewHolder();
                convertView.setTag(mViewHolder);
        } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
        }
		
        TextView datumTv = (TextView) convertView.findViewById(R.id.Datum);
        
        
        datumTv.setText(datumFormat.format(fahrtList.get(position).getDatum()));
        mViewHolder.datumTv = datumTv;
        TextView kmTv = (TextView) convertView.findViewById(R.id.KM);
        kmTv.setText(String.valueOf(fahrtList.get(position).getKm()));
        mViewHolder.kmTv = kmTv;
        TextView bezTv = (TextView) convertView.findViewById(R.id.Bezeichnung);
        bezTv.setText(fahrtList.get(position).getBezeichnung());
        mViewHolder.bezTv = bezTv;

		return convertView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		final FahrtenAdapter adapter = this;
		AlertDialog.Builder adb=new AlertDialog.Builder(context);
        adb.setTitle("Löschen?");
        adb.setMessage("Löschen von " + fahrtList.get(position).getBezeichnung());
        final int positionToRemove = position;
        adb.setNegativeButton("Abbrechen", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            @Override
        	public void onClick(DialogInterface dialog, int which) {
                db.deleteById(fahrtList.get(positionToRemove).getId());
            	fahrtList.remove(positionToRemove);
                adapter.notifyDataSetChanged();
            }
        });
        adb.show();
    }
	
	public void updateList(ArrayList<Fahrt> fahrtList){
		this.fahrtList = fahrtList;
	}
	
	private class MyViewHolder {
        TextView datumTv, kmTv, bezTv;
	}

}

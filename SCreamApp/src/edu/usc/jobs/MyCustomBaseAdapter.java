package edu.usc.jobs;

import java.util.ArrayList;

import edu.usc.R;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	 private static ArrayList<SearchResults> searchArrayList;
	 
	 private LayoutInflater mInflater;

	 public MyCustomBaseAdapter(Context context, ArrayList<SearchResults> results) {
	  searchArrayList = results;
	  mInflater = LayoutInflater.from(context);
	 }

	 public int getCount() {
	  return searchArrayList.size();
	 }

	 public Object getItem(int position) {
	  return searchArrayList.get(position);
	 }

	 public long getItemId(int position) {
	  return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	  ViewHolder holder;
	  if (convertView == null) {
	   convertView = mInflater.inflate(R.layout.custom_row_view, null);
	   holder = new ViewHolder();
	   holder.txtName = (TextView) convertView.findViewById(R.id.name);
	   holder.txtCityState = (TextView) convertView.findViewById(R.id.cityState);
	   holder.txtPhone = (TextView) convertView.findViewById(R.id.phone);
      
	   holder.txtlink = (TextView) convertView.findViewById(R.id.link);
       holder.txtlink.setMovementMethod(LinkMovementMethod.getInstance());
       
	   convertView.setTag(holder);
	  } else {
	   holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.txtName.setText(searchArrayList.get(position).getName());
	  holder.txtCityState.setText(searchArrayList.get(position).getCityState());
	  holder.txtPhone.setText(searchArrayList.get(position).getPhone());
	  
	  String linkval = searchArrayList.get(position).getLink();
	  holder.txtlink.setText(Html.fromHtml(linkval));
	  
	  return convertView;
	 }

	 static class ViewHolder {
	  TextView txtName;
	  TextView txtCityState;
	  TextView txtPhone;
	  TextView txtlink;
	 }
	}
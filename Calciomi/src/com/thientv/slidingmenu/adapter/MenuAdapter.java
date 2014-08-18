package com.thientv.slidingmenu.adapter;

import java.util.ArrayList;
import java.util.List;

import com.thientv.calciomino.MainActivity;
import com.thientv.calciomino.R;
import com.thientv.slidingmenu.bean.MenuItem;
import com.thientv.slidingmenu.utils.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<MenuItem> {
	
	Context context;
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	

	public MenuAdapter(Context context, ArrayList<MenuItem> menuItems) {
		super(context, R.layout.item_menu, menuItems);
		this.context = context;
		this.menuItems = menuItems;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public MenuItem getItem(int position) {
		return menuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		Holder holder = null;
		if (v==null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.item_menu, parent, false);
			holder = new Holder();

			
			holder.txtName = (TextView) v.findViewById(R.id.txt_name);
			
			
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}
		
		holder.txtName.setText(menuItems.get(position).getName());
		holder.txtName.setTextColor(getContext().getResources().getColor(menuItems.get(position).getColor()));
		
		if (position == MainActivity.mCurrent){
			holder.txtName.setTypeface(Utils.getTypefaceBold(context));
		} else {
			holder.txtName.setTypeface(Utils.getTypefaceRegular(context));
		}
		
		return v;
	}
	
	class Holder{
		TextView txtName;
	}
	
	

}

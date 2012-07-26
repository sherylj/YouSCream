package edu.usc.apartment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

public class ApartmentExpandList extends BaseExpandableListAdapter {

	private Context mContext;
	private String[][] mContents;
	private String[] mTitles;

	public ApartmentExpandList(Context context, String[] titles,
			String[][] contents) {
		super();
		if (titles.length != contents.length) {
			throw new IllegalArgumentException(
					"Titles and Contents must be the same size.");
		}
		
		mContext = context;
		mContents = contents;
		mTitles = titles;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mContents[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView row = (TextView) convertView;
		if (row == null) {
			row = new TextView(mContext);
		}
		row.setText(mContents[groupPosition][childPosition]);
		row.setTextSize(20);
		row.setPadding(0, 10, 0, 10);
		return row;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mContents[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mContents[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mContents.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView row = (TextView) convertView;
		if (row == null) {
			row = new TextView(mContext);
		}
		row.setTypeface(Typeface.DEFAULT_BOLD);
		row.setText(mTitles[groupPosition]);
		row.setTextSize(30);
		row.setPadding(0, 30, 0, 30);
		return row;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean onContextItemSelected(MenuItem item) {
		System.out.println("Insidded onContextItemSelected");

		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();

		String title = ((TextView) info.targetView).getText().toString();
		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);

		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView
					.getPackedPositionChild(info.packedPosition);
			Toast.makeText(mContext,title + ": Child " + childPos + " clicked in group "+ groupPos, Toast.LENGTH_SHORT).show();
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			Toast.makeText(mContext, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

}

package edu.usc.apartment;

import android.app.ActionBar.OnNavigationListener;
import android.content.Context;

public class ListListener extends BaseListener implements OnNavigationListener {
	public ListListener(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		this.mReportTo.reportBack("list listener","ItemPostion:" + itemPosition);
		
		return true;
	}
}
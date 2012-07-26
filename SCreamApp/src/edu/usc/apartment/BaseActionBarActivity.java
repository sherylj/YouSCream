package edu.usc.apartment;

import edu.usc.R;
import edu.usc.Tutorial.*;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public abstract class BaseActionBarActivity extends DebugActivity {
	private String tag = null;

	public BaseActionBarActivity(String inTag) {
		super(R.menu.filters, R.layout.main, R.id.textView1, inTag);
		tag = inTag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = this.getTextView();
		tv.setText(tag);
	}

	protected boolean onMenuItemSelected(MenuItem item) {
		// Responding to Home Icon
		if (item.getItemId() == android.R.id.home) {
			this.reportBack(tag, "Home Pressed");
			return true;
		}
		// Common behavior to invoke sibling activities
		/*
		 * if (item.getItemId() == R.id.menu_invoke_tabnav){ if (getNavMode() ==
		 * ActionBar.NAVIGATION_MODE_TABS) { this.reportBack(tag,
		 * "You are already in tab nav"); } else { this.invokeTabNav(); } return
		 * true; }
		 */
		/*if (item.getItemId() == R.id.menu_invoke_listnav) {
			if (getNavMode() == ActionBar.NAVIGATION_MODE_LIST) {
				this.reportBack(tag, "You are already in list nav");
			} else {
				this.invokeListNav();
			}
			return true;
		}
		if (item.getItemId() == R.id.menu_invoke_standardnav) {
			if (getNavMode() == ActionBar.NAVIGATION_MODE_STANDARD) {
				this.reportBack(tag, "You are already in standard nav");
			} else {
				this.invokeStandardNav();
			}
			return true;
		}*/
		return false;
	}

	private int getNavMode() {
		ActionBar bar = getActionBar();
		return bar.getNavigationMode();
	}

	private void invokeTabNav() {
		// Intent i = new Intent(this,TabNavigationActionBarActivity.class);
		// startActivity(i);
	}

	// Uncomment the following method bodies
	// as you implement these additional activities
	private void invokeListNav() {
		Intent i = new Intent(this, ListNavigationActionBarActivity.class);
		startActivity(i);
	}

	private void invokeStandardNav() {
		// Intent i = new Intent(this,
		// StandardNavigationActionBarActivity.class);
		// startActivity(i);
	}
}// eof-class

package edu.usc.Tutorial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.usc.R;
import edu.usc.apartment.YouSCreamActivity;
import edu.usc.events.EventsActivity;
import edu.usc.jobs.MultiListActivity;

public class Start extends Activity {
    /** Called when the activity is first created. */
	
	public Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        setContentView(R.layout.main);
        //if(dialog.isShowing())
        	
        double lat2 = 34.029571;
	    double lng2 = -118.283476;
        double lat1= 34.0210643;
		double lng1= -118.28575;
		mContext = this;
	    
	    //for(int i = 0; i < 3; i++)
	    	//Toast.makeText(getBaseContext(), "Android Distance Forumula " + A.distanceTo(B) / 1609.344 , Toast.LENGTH_SHORT).show();
        
        Button StartGameButton = (Button)findViewById(R.id.StartGame);
        StartGameButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent StartGameIntent = new Intent(Start.this,EventsActivity.class);
        		startActivity(StartGameIntent);
        		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        	}
        });
        
        Button HelpButton = (Button)findViewById(R.id.Help);
        HelpButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		
        		Intent HelpIntent = new Intent(Start.this,MultiListActivity.class);
        		startActivity(HelpIntent);
        		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        	}
        });
        
        Button OptionsButton = (Button)findViewById(R.id.Options);
        OptionsButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent OptionsIntent = new Intent(Start.this,YouSCreamActivity.class);
        		startActivity(OptionsIntent);
        		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        	}
        });
        
    }
    
   
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		//SubMenu menu = (SubMenu) findViewById(R.id.submenu2);
		switch (item.getItemId()) {

		
		case R.id.menu_currentLoc:
			final AlertDialog.Builder builder=new AlertDialog.Builder(Start.this);
            builder.setTitle("Start Page");
            builder.setMessage("YouSCream – No More! is a central platform app that provides real-time information services regarding apartments, jobs and events in and around the campus. " +
            		"Providing quick access to essential academic, professional and social resources, students need not resort to multiple services gain information. " +
            		"YouSCream will provide all this information in a unified location. " +
            		"Simply click one of the preferred icons and go! " +
            		"\n\n" +
            		"What do you want to find? Click one of the icons!");
            
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                      }
                    });
            AlertDialog alert = builder.create();
            alert.show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onGroupItemClick(MenuItem item) {
	    // One of the group items (using the onClick attribute) was clicked
	    // The item parameter passed here indicates which item it is
	    // All other menu item clicks are handled by onOptionsItemSelected()
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    getActionBar().setSubtitle("Start Page");
	    return true;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		/*getMenuInflater().inflate(R.menu.search, menu);
		//SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		// Set appropriate listeners for searchView
		searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget;
		MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
        mSearchView.setRight(100);
        mSearchView.setMinimumWidth(1000);
        mSearchView.setSaveEnabled(true);*/
		getMenuInflater().inflate(R.menu.help_item, menu);
		return true;
		
	}
   
    
   
}
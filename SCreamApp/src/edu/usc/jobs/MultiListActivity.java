package edu.usc.jobs;

import java.util.ArrayList;

import android.app.ActionBar;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import edu.usc.R;
import edu.usc.events.EventsActivity;

public class MultiListActivity extends Activity {
	/** Called when the activity is first created. */

	public ArrayList<SearchResults> results;

	public String city = "";
	public String title = "";
	
	public Context mContext;
	public ListView lv1;

	public void onCreate(Bundle savedInstanceState) {

		results = GetSearchResults();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobs);

		lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(new MyCustomBaseAdapter(this, results));
		final Button handle = (Button) findViewById(R.id.handleout);
		final Button handlein = (Button) findViewById(R.id.handlein);
		handle.setOnClickListener(handleListener);
		handlein.setOnClickListener(handleListener);
		mContext = this;
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		/*Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// get value of city and job title
				String city = "CA";
				String title = "Software";
				ArrayList<SearchResults> FilteredResults = new ArrayList<SearchResults>();
				// GetSearchResults(city,title);
				SearchResults sr2 = new SearchResults();

				for (int i = 0; i < results.size(); i++) {
					sr2 = results.get(i);
					if ((sr2.getCityState().contains(city))
							&& (sr2.getName().contains(title)))
						FilteredResults.add(sr2);
					// return results;
				}
				ListView lv1 = (ListView) findViewById(R.id.ListView01);
				lv1.setAdapter(new MyCustomBaseAdapter(MultiListActivity.this,
						FilteredResults));

			}

		});*/

	}

	private ArrayList<SearchResults> GetSearchResults() {
		ArrayList<SearchResults> results = new ArrayList<SearchResults>();

		SearchResults sr1 = new SearchResults();
		sr1.setName("Software Engineer, Machine Learning");
		sr1.setCityState("Company: Ooyala, Mountain View, California");
		sr1.setPhone("Posted on : 26-November-11");
	   sr1.setLink("<a href='http://pobyfy.com/jobs/517'>Apply Here..</a>");
   		results.add(sr1);

	     sr1 = new SearchResults();
		sr1.setName("C# Software Engineer - SkyPay");
		sr1.setCityState("Company: Accenture, Minneapolis");
		sr1.setPhone("Posted on : 17-November-11");
		   sr1.setLink("<a href='http://pobyfy.com/jobs/493'>Apply Here..</a>");

		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("System Administrator - Unix");
		sr1.setCityState("Company: Skype, Palo Alto, California");
		sr1.setPhone("Posted on : 16-November-11");
		   sr1.setLink("<a href='http://pobyfy.com/jobs/490'>Apply Here..</a>");

		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Server Engineer");
		sr1.setCityState("Company: StackMob, San Francisco, California");
		sr1.setPhone("Posted on : 29-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/479'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Production Database/Web Support Engineer");
		sr1.setCityState("Company: RingCentral, San Mateo, California");
		sr1.setPhone("Posted on : 30-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/486'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Web Developer");
		sr1.setCityState("Company: Barracuda , Ann Harbor, MI");
		sr1.setPhone("Posted on : 30-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/499'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Software Engineer, Ruby on Rails");
		sr1.setCityState("Company: Ooyala, Mountain View, California");
		sr1.setPhone("Posted on : 25-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/516'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Mobile Developer (iOS or Android)");
		sr1.setCityState("Company: eHarmony, Santa Monica, California");
		sr1.setPhone("Posted on : 25-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/507'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Technical Support Engineer");
		sr1.setCityState("Company: Dropbox, San Francisco, California");
		sr1.setPhone("Posted on : 26-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/503'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Software Architect");
		sr1.setCityState("Company: Truaxis, RedWood City, California");
		sr1.setPhone("Posted on : 26-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/501'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Data Scientist");
		sr1.setCityState("Company: IMVU, Mountain View, California");
		sr1.setPhone("Posted on : 27-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/511'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Front End Developer");
		sr1.setCityState("Company: eHarmony , Santa Monica, California");
		sr1.setPhone("Posted on : 28-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/504'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("Web Designer");
		sr1.setCityState("Company: Tradeshift,San Francisco, California");
		sr1.setPhone("Posted on : 28-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/471'>Apply Here..</a>");
		results.add(sr1);

		sr1 = new SearchResults();
		sr1.setName("QA Engineer II");
		sr1.setCityState("Company: Zynga, Cambridge, MA" );
		sr1.setPhone("Posted on : 28-November-11");
		sr1.setLink("<a href='http://pobyfy.com/jobs/477'>Apply Here..</a>");
		results.add(sr1);
		
		sr1 = new SearchResults();
		sr1.setName(" Data Engineer, Analytics Intern");
		sr1.setCityState("Company: Meebo, Mountain View, California" );
		sr1.setPhone("Posted on : 29-November-11");
		sr1.setLink("<a href='http://pobyfy.com/internships/390'>Apply Here..</a>");
		results.add(sr1);
		
		sr1 = new SearchResults();
		sr1.setName(" Intern in Software Development");
		sr1.setCityState("Company: Fog Creek, New York , NY" );
		sr1.setPhone("Posted on : 29-November-11");
		sr1.setLink("<a href='http://pobyfy.com/internships/382'>Apply Here..</a>");
		results.add(sr1);
		
		sr1 = new SearchResults();
		sr1.setName(" Software Engineer");
		sr1.setCityState("Company: Facebook, New York , NY" );
		sr1.setPhone("Posted on : 29-November-11");
		sr1.setLink("<a href='http://pobyfy.com/internships/376'>Apply Here..</a>");
		results.add(sr1);
		
		sr1 = new SearchResults();
		sr1.setName("Java Developer");
		sr1.setCityState("Company: DIRECTV, Los Angeles , California" );
		sr1.setPhone("Posted on : 29-November-11");
		sr1.setLink("<a href='http://www.viadeo.com/jobs/offer/?jobOfferId=0021i7cjhdwz677n&nav=8&navContext=00224byj1uupflbd'>Apply Here..</a>");
		results.add(sr1);
		
		sr1 = new SearchResults();
		sr1.setName(" Software Engineer");
		sr1.setCityState("Company: Openx, Pasadena , California" );
		sr1.setPhone("Posted on : 29-November-11");
		sr1.setLink("<a href='http://www.openx.com/about/jobs/software-engineer-test-automation-pasadena-ca'>Apply Here..</a>");
		results.add(sr1);
		
		sr1 = new SearchResults();
		sr1.setName(" Java Developer ");
		sr1.setCityState("Company: Film Funds Inc, Santa Monica , California" );
		sr1.setPhone("Posted on : 30-November-11");
		sr1.setLink("<a href='http://www.jobhost.org/jobs/viewjob/java-developer-a9846afc99b58683?source=indeed&medium=organic'>Apply Here..</a>");
		results.add(sr1);
		
		return results;
	}

	@Override
	protected void onStart() {
		super.onStart();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		getActionBar().setSubtitle("Jobs");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		// SubMenu menu = (SubMenu) findViewById(R.id.submenu2);
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent intent = new Intent(MultiListActivity.this,
					edu.usc.Tutorial.Start.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("dialog", "stop");
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			return true;
			
		case R.id.menu_currentLoc:
			final AlertDialog.Builder builder=new AlertDialog.Builder(MultiListActivity.this);
            builder.setTitle("Jobs Help");
            builder.setMessage("Once again YouScream provides a simple yet powerful search engine that enables students " +
            		"retrieve real time information on jobs." +
            		"\n\n" +
            		"*** Scroll through the currently populated data based" +
            		"\n\n" +
            		"*** Expand the side panel and input location information as well as filters needed and hunt!" + 
            		"\n\n" +
            		"*** External link to job source.");
            
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                      }
                    });
            builder.setIcon(R.drawable.question);
            AlertDialog alert = builder.create();
            alert.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
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
		//getMenuInflater().inflate(R.menu.current_location_impl, menu);

		getMenuInflater().inflate(R.menu.help_item, menu);
		return true;
		
	}
	
	View.OnClickListener handleListener = new View.OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub

		// }

		public void onClick(View v) {
			// TODO Auto-generated method stub
			final Button handle = (Button) findViewById(R.id.handleout);
			final Button handlein = (Button) findViewById(R.id.handlein);
			final RelativeLayout panel = (RelativeLayout) findViewById(R.id.panel);
			/*
			 * TranslateAnimation animPanel = new TranslateAnimation (-1, 0, 0,
			 * 0); TranslateAnimation animHandle = new TranslateAnimation (-1,
			 * 0, 0, 0);
			 * 
			 * animPanel.setFillAfter(true); animPanel.setDuration(1000);
			 * animHandle.setFillAfter(true); animHandle.setDuration(1000);
			 * 
			 * handle.setAnimation(animHandle); panel.setAnimation(animPanel);
			 */
			//float handlePos = handle.getX();
			if (handle.getId() == ((Button) v).getId()) {
				AnimationSet setHandle = new AnimationSet(true);
				Animation animHandle = new TranslateAnimation(
						Animation.RELATIVE_TO_PARENT, -1.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
				animHandle.setDuration(1000);
				// animation.setRepeatMode(Animation.RESTART);

				AnimationSet setPanel = new AnimationSet(true);
				Animation animPanel = new TranslateAnimation(
						Animation.RELATIVE_TO_PARENT, -1.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
				animPanel.setDuration(1000);
				// animation.setRepeatMode(Animation.RESTART);

				long time = AnimationUtils.currentAnimationTimeMillis();
				animHandle.setStartTime(time);
				animPanel.setStartTime(time);

				setHandle.addAnimation(animHandle);
				setPanel.addAnimation(animPanel);

				panel.setVisibility(View.VISIBLE);
				handle.startAnimation(setHandle);
				handlein.startAnimation(setHandle);
				panel.startAnimation(setPanel);

				handle.setVisibility(View.INVISIBLE);
				handlein.setVisibility(View.VISIBLE);
				final EditText job = (EditText) findViewById(R.id.jobField);
				final EditText location = (EditText) findViewById(R.id.locationField);
				final Button hunt = (Button) findViewById(R.id.find);
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.hideSoftInputFromWindow(job.getWindowToken(), 0);

				hunt.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub

						AnimationSet Close = new AnimationSet(true);
						Animation animHandleClose = new TranslateAnimation(
								Animation.RELATIVE_TO_PARENT, handle.getX(),
								Animation.RELATIVE_TO_PARENT,
								-panel.getWidth(),
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f);
						animHandleClose.setDuration(2000);
						animHandleClose.setFillAfter(true);
						// animation.setRepeatMode(Animation.RESTART);

						// AnimationSet setPanelClose = new AnimationSet (true);
						Animation animPanelClose = new TranslateAnimation(
								Animation.RELATIVE_TO_PARENT, panel.getX(),
								Animation.RELATIVE_TO_PARENT,
								-panel.getWidth(),
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f);
						animPanelClose.setDuration(2000);
						animPanelClose.setFillEnabled(true);
						animPanelClose.setFillAfter(true);
						// animation.setRepeatMode(Animation.RESTART);

						long timeClose = AnimationUtils
								.currentAnimationTimeMillis();
						animHandleClose.setStartTime(timeClose);
						animPanelClose.setStartTime(timeClose);

						Close.addAnimation(animPanelClose);
						Close.addAnimation(animHandleClose);
						panel.startAnimation(Close);
						handlein.startAnimation(Close);
						handle.startAnimation(Close);
						panel.setVisibility(View.GONE);
						handlein.setVisibility(View.INVISIBLE);
						handle.setVisibility(View.VISIBLE);

						setResults(job.getText().toString(), location.getText().toString());
						job.setText("");
						location.setText("");

					}
				});
			}

			if (handlein.getId() == ((Button) v).getId()) {
				// handlein.setOnClickListener(new View.OnClickListener() {
				// TODO Auto-generated method stub
				AnimationSet Close = new AnimationSet(true);
				Animation animHandleClose = new TranslateAnimation(
						Animation.RELATIVE_TO_PARENT, handle.getX(),
						Animation.RELATIVE_TO_PARENT, -panel.getWidth(),
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
				animHandleClose.setDuration(2000);
				animHandleClose.setFillAfter(true);
				animHandleClose.setFillEnabled(true);
				// animation.setRepeatMode(Animation.RESTART);

				// AnimationSet setPanelClose = new AnimationSet (true);
				Animation animPanelClose = new TranslateAnimation(
						Animation.RELATIVE_TO_PARENT, panel.getX(),
						Animation.RELATIVE_TO_PARENT, -panel.getWidth(),
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
				animPanelClose.setDuration(2000);
				animPanelClose.setFillAfter(true);
				// animation.setRepeatMode(Animation.RESTART);

				long timeClose = AnimationUtils.currentAnimationTimeMillis();
				animHandleClose.setStartTime(timeClose);
				animPanelClose.setStartTime(timeClose);
				animPanelClose.setFillAfter(true);
				animPanelClose.setFillEnabled(true);
				Close.addAnimation(animPanelClose);
				Close.addAnimation(animHandleClose);

				panel.startAnimation(Close);
				handlein.startAnimation(Close);
				panel.setVisibility(View.GONE);
				handlein.setVisibility(View.INVISIBLE);
				handle.setVisibility(View.VISIBLE);
				// open = false;
				// remove this
			}
		}
	};// ///// ------ close Listener here
	
	public void setResults(String title, String city) {
		ArrayList<SearchResults> FilteredResults = new ArrayList<SearchResults>();
		// GetSearchResults(city,title);

		for (int i = 0; i < results.size(); i++) {
			SearchResults sr2 = results.get(i);
			if ((sr2.getCityState().toLowerCase().contains(city.toLowerCase())) && (sr2.getName().toLowerCase().contains(title.toLowerCase())))
				FilteredResults.add(sr2);
			// return results;
		}
		lv1.setAdapter(new MyCustomBaseAdapter(MultiListActivity.this,FilteredResults));
	}
}
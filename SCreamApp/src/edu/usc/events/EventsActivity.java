package edu.usc.events;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import edu.usc.R;
import edu.usc.apartment.YouSCreamActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsActivity extends ListActivity {
	/** Called when the activity is first created. */
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	private View expanded = null;
	private Button mCal;
	RowData rd;
	public Context mContext;
	static final int DATE_DIALOG_ID = 0;
	static final String[] title = new String[] {
			"Dancing Through an Arabian Night",
			"Fragile Minds: Neural Scaffolding and Neuropathology in the Aging Brain",
			"Facade Tectonics No. 7",
			"Advanced Placement French Language and Culture: Strategizing for Success",
			"Tensions in Multi-Sector Collaborations To Combat Human Trafficking",
			"Developing NIH Grant Applications",
			"Examining the Health-Information-Seeking Behaviors of Korean American Immigrants",
			"L.A. Philharmonic: Dudamel Conducts Mahler 4",
			"Communicating Today's Complexities",
			"Tom Stoppard's Rock 'n' Roll",
			"The Republic of the Imagination: An Evening with Azar Nafisi" };

	static final String[] detail = new String[] {
			"Take a magic carpet ride with the School of Theatre’s Repertory Dance Company as it interprets the classic tale of Aladdin. Featuring contemporary choreography, the RDC puts its signature modern twist on this timeless tale.\nDirected by Angeliki Papadakis",
			"A lecture by Denise Park, Ph.D., director of the Center for Vital Longevity, professor and Regents’ Research Scholar at the University of Texas.",
			"A conference on issues in the design, delivery and operation of modern building envelopes. Facade Tectonics #7, held at the USC School of Architecture, will feature 15 presentations by architects, engineers, contractors and owners. Facade Tectonics conferences are held twice each year. We strive to include participation and presentations by a broad range of contractors, architects, engineers, manufacturers, building owners, academics and others committed to the design, delivery and use of building enclosures. Facade Tectonics #7 is structured in a single-track format in one large auditorium space.",
			"California Polytechnic State University’s Brian Kennelly leads a workshop introducing AP teachers to the new course and examination in French Language and Culture. The USC Francophone Research and Resource Center is pleased to present this new workshop. Participants will develop familiarity with the curriculum framework, review a practice examination (item types, scoring guidelines, preparation strategies), receive guidance in the principles of instructional design and assessment, and prepare their syllabi for the course audit.",
			"Join Annenberg students and faculty for a presentation by Kirsten Foot, associate professor of communication at the University of Washington.",
			" A class on locating funding opportunities within NIH, preparing successful proposals, and working with program officers. The course, led by Steve Moldin, executive director of the USC Washington, D.C., Office of Research Advancement, is designed for new and established USC investigators who wish to submit a grant application to the National Institutes of Health (NIH). The workshop will instruct participants in how to identify which NIH institute to target, determine institute funding priorities, evaluate which funding mechanisms are most appropriate, prepare applications for different NIH solicitations, prepare for the NIH review process, build relationships with funding agencies, and advocate in the most effective manner for funding of the application.",
			"Join Annenberg students and faculty for a talk by Gary Kreps, University Distinguished Professor and chair of the Department of Communication at George Mason University. Kreps is also director of the Center for Health and Risk Communication at George Mason University.",
			"The dynamic and distinguished Gustavo Dudamel conducts the L.A. Philharmonic as it performs the last part of Mahler’s “Wunderhorn” symphonies. The unique sound of flutes and sleigh bells make the beginning of Gustav Mahler’s Fourth Symphony unforgettable. Built around a single song, Das Himmlische Leben, Mahler’s Fourth moves through the viewpoints of different characters and settings — a skeleton playing a death dance on a fiddle, a somber processional march, and a child’s vision of heaven— making for a concert performance that will amaze audiences.",
			"Pulitzer Prize winner George Will, one of the nation’s foremost political commentators, is this year’s recipient of the Holt Distinguished Lecture Award. Will will deliver the Holt Lecture, which is designed to illuminate the intersection of public policy and communication.",
			"Academy Award and Tony Award winner Tom Stoppard (Shakespeare in Love,Rosencrantz & Guildenstern Are Dead) is one of the most internationally acclaimed dramatists of his generation. In all of his work, Stoppard reveals an exceptional literary talent for exploring the complexities of relationships with quick intellect and heartfelt emotion. Rock ’n’ Roll, called “Stoppard’s finest play” by The New York Times, follows the passions and politics of a Marxist professor in Cambridge as well as his Rolling Stones–obsessed protege, who is fighting for freedom in Soviet-dominated Prague.",
			"The renowned author of Reading Lolita in Tehran: A Memoir in Books discusses the implications of literature and culture, and the human rights of Iranian women and girls. Join Visions and Voices for an evening with Azar Nafisi. Her Reading Lolita in Tehranelectrified its readers with a compassionate and often harrowing portrait of the Islamic revolution in Iran, and won numerous awards, including the Prix du Meilleur Livre Etranger, the Non-Fiction Book of the Year Award from Book Sense, and the Frederic W. Ness Book Award. It was also named one of the 100 Best Books of the Decade by The Times (U.K.). Nafisi is a visiting professor at the Foreign Policy Institute at Johns Hopkins University’s School of Advanced International Studies." };

	static final String[] venue = new String[] {
			"University Park Campus\nBing Theatre",
			"University Park Campus\nAndrus Gerontology Center Auditorium",
			"University Park Campus\nUSC Gin D. Wong, FAIA Conference Center\nRoom 101",
			"University Park Campus\nLeavey Library Auditorium",
			"University Park Campus\nAnnenberg School for Communication & Journalism\nGeoffrey Cowan Forum, Room 207",
			"Health Sciences Campus\nNorris Medical Library East Conference Room",
			"University Park Campus\nAnnenberg School for Communication & Journalism\nGeoffrey Cowan Forum, Room 207",
			"Walt Disney Concert Hall\n111 South Grand Avenue\nLos Angeles, CA 90012",
			"University Park Campus\nTown and Gown",
			"University Park Campus\nBovard Auditorium",
			"University Park Campus\nBovard Auditorium" };

	private Integer[] imgid = { R.drawable.event1, R.drawable.event2,
			R.drawable.event3, R.drawable.event4, R.drawable.event5,
			R.drawable.event6, R.drawable.event7, R.drawable.event8,
			R.drawable.event9, R.drawable.event10, R.drawable.event11 };

	private String[] datetime = new String[] { "7 pm ",
			"12:00pm to 1:15pm",
			"9 am",
			"8:30am to 4:00pm",
			"12:00pm to 1:00pm",
			"2:00pm to 4:00pm",
			"12:00pm to 1:00pm",
			"6:00pm to 11:00pm",
			"5:00pm to 6:30pm",
			"7:00pm",
			"7:00pm" };

	private String[] rsvp = new String[] {
			"http://ev10.evenue.net/cgi-bin/ncommerce3/SEGetEventList?groupCode=D37&linkID=usc&shopperContext=&caller=&appCode=",
			"mailto:lindah@usc.edu",
			"https://store.uscarchitecture.com/fall-2011/facade-tectonics-7.html",
			"mailto:frc@usc.edu",
			"mailto:ascevent@usc.edu",
			"mailto:usccer@usc.edu",
			"mailto: ascevent@usc.edu",
			"http://www.usc.edu/dept/pubrel/visionsandvoices/RSVP/reserve.php?RSVPEvtCode=191",
			"http://www.usc.edu/schools/sppd/events/georgewill",
			"http://www.usc.edu/dept/pubrel/visionsandvoices/RSVP/reserve.php?RSVPEvtCode=192",
			"http://www.usc.edu/dept/pubrel/visionsandvoices/RSVP/reserve.php?RSVPEvtCode=194" };

	// private String[] eday = new String[] {
	// "19 \n Dec, '12 ","19 \n Dec, '12 ","19 \n Dec, '12","19 \n Dec, '12","19 \n Dec, '12","19 \n Dec, '12","19 \n Dec, '12","19 \n Dec, '12"};
	private String[] starttime = new String[] { "2011-12-01 19:00:00.0",
			"2011-12-01 12:00:00.0", "2011-12-02 09:00:00.0",
			"2011-12-03 08:30:00.0", "2011-12-05 12:00:00.0",
			"2011-12-07 14:00:00.0", "2012-01-09 12:00:00.0",
			"2012-01-14 18:00:00.0", "2012-01-18 17:00:00.0",
			"2012-01-19 19:00:00.0", "2012-01-31 19:00:00.0" };
	private String[] endtime = new String[] { "2011-12-01 20:00:00.0",
			"2011-12-01 13:15:00.0", "2011-12-02 11:00:00.0",
			"2011-12-03 16:00:00.0", "2011-12-05 13:00:00.0",
			"2011-12-07 16:00:00.0", "2011-01-09 13:00:00.0",
			"2012-01-14 23:00:00.0", "2012-01-18 18:30:00.0",
			"2012-01-19 20:00:00.0", "2012-01-31 20:00:00.0" };
	private Integer[] flags = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventsmain);
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		mContext = this;

	    getActionBar().setSubtitle("Events");
		// mCal = (Button) findViewById(R.id.addcal);

		// add a click listener to the button
		/*
		 * mCal.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { Toast.makeText(EventsActivity.this,
		 * " Student Housing ", Toast.LENGTH_SHORT).show(); } });
		 */
		for (int i = 0; i < title.length; i++) {
			try {
				rd = new RowData(i, title[i], venue[i], datetime[i], detail[i],
						rsvp[i], imgid[i], starttime[i], endtime[i], flags[i]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.add(rd);
		}
		CustomAdapter adapter = new CustomAdapter(this, R.layout.list,
				R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}

	public void myClickHandler(View v) {

		Toast.makeText(getApplicationContext(), "You have selectedth item",
				Toast.LENGTH_SHORT).show();
		// get the row the clicked button is in
		/*
		 * LinearLayout vwParentRow = (LinearLayout)v.getParent();
		 * 
		 * TextView child = (TextView)vwParentRow.getChildAt(0); Button btnChild
		 * = (Button)vwParentRow.getChildAt(1);
		 * btnChild.setText(child.getText());
		 * btnChild.setText("I've been clicked!");
		 * 
		 * int c = Color.CYAN;
		 * 
		 * vwParentRow.setBackgroundColor(c);
		 * vwParentRow.refreshDrawableState();
		 */

	}

	public void onListItemClick(ListView parent, View v, final int position,
			long id) {
		/*
		 * Toast.makeText(getApplicationContext(), "You have selected "
		 * +(position+1)+"th item", Toast.LENGTH_SHORT).show();
		 */

		View extend = v.findViewById(R.id.extend);
		// mCal = (Button) findViewById(R.id.addcal);
		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(extend, 500);

		// Start the animation on the listitem extension
		extend.startAnimation(expandAni);

		// add a click listener to the button

	}

	private class RowData {
		protected int mId;
		protected String mTitle;
		protected String mDetail;
		protected String mVenue;
		protected String mDate;
		protected String mRsvp;
		protected String mEday;
		protected Button mCal;
		protected Integer mImg;
		protected Integer mFlag;
		protected String mStart;
		protected String mEnd;

		RowData(int id, String title, String venue, String datetime,
				String detail, String rsvp, Integer imgid, String start,
				String end, int flag) {
			mId = id;
			mTitle = title;
			mDetail = detail;
			mVenue = venue;
			mDate = datetime;
			mRsvp = rsvp;
			// mEday = eday;
			mStart = start;
			mEnd = end;
			mImg = imgid;
			mFlag = flag;
		}

		@Override
		public String toString() {
			return mId + " " + mTitle + " " + mVenue + " " + mDate + " "
					+ mDetail + " " + mRsvp;
		}
	}

	private class CustomAdapter extends ArrayAdapter<RowData> {
		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			TextView title = null;
			TextView detail = null;
			TextView venue = null;
			TextView date = null;
			// TextView rsvp = null;
			Button rsvp;
			ImageView i11 = null;
			TextView eday = null;
			final RowData rowData = getItem(position);

			if (null == convertView) {
				convertView = mInflater.inflate(R.layout.list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);

			}
			if ((position % 2) == 0)
				convertView.setBackgroundResource(R.drawable.customshape);
			else
				convertView.setBackgroundResource(R.drawable.customshapegold);

			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);
			venue = holder.getvenue();
			venue.setText(rowData.mVenue);
			date = holder.getdate();
			date.setText(rowData.mDate);
			// eday = holder.gettext();
			// eday.setText(rowData.mEday);
			detail = holder.getdetail();
			detail.setText(rowData.mDetail);

			i11 = holder.getImage();
			i11.setImageResource(imgid[rowData.mId]);

			rsvp = holder.getrsvp();
			rsvp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW, Uri
							.parse(rowData.mRsvp));
					startActivity(i);
				}
			});
			rsvp.setFocusable(false);
			// rsvp.setText(rowData.mRsvp);
			// rsvp.setMovementMethod(LinkMovementMethod.getInstance());
			// rsvp.setText(Html.fromHtml(rowData.mRsvp));
			// rsvp.setFocusable(false);
			// rsvp.setAutoLinkMask(Linkify.WEB_URLS);
			// Resets the toolbar to be closed

			mCal = holder.getcal();

			mCal.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Toast.makeText(getApplicationContext(),
					// "You have selectedth item", Toast.LENGTH_SHORT).show();
					if (rowData.mFlag == 0) {
						String[] projection = new String[] { "_id", "name" };
						Uri calendars = Uri
								.parse("content://com.android.calendar/calendars");

						Cursor managedCursor = managedQuery(calendars,
								projection, null, null, null);
						String calName;
						String calId = null;
						if (managedCursor.moveToFirst()) {

							int nameColumn = managedCursor
									.getColumnIndex("name");
							int idColumn = managedCursor.getColumnIndex("_id");
							do {
								calName = managedCursor.getString(nameColumn);
								calId = managedCursor.getString(idColumn);
							} while (managedCursor.moveToNext());
						}

						Calendar cal = Calendar.getInstance();
						Intent intent = new Intent();
						intent.setType("vnd.android.cursor.item/event");
						ContentValues event = new ContentValues();
						event.put("calendar_id", calId);
						event.put("title", rowData.mTitle);
						event.put("description", rowData.mDetail);
						event.put("eventLocation", rowData.mVenue);
						java.sql.Timestamp ts1 = java.sql.Timestamp
								.valueOf(rowData.mStart);
						java.sql.Timestamp ts2 = java.sql.Timestamp
								.valueOf(rowData.mEnd);

						long tsTime1 = ts1.getTime();
						long tsTime2 = ts2.getTime();

						event.put("dtstart", tsTime1);
						event.put("dtend", tsTime2);

						Uri eventsUri = Uri
								.parse("content://com.android.calendar/events");
						Uri url = getContentResolver().insert(eventsUri, event);

						ComponentName cn = new ComponentName(
								"com.google.android.calendar",
								"com.android.calendar.LaunchActivity");

						intent.setComponent(cn);
						startActivity(intent);
						rowData.mFlag = 1;
					} else {
						// Toast.makeText(getApplicationContext(),
						// "Event already added. Go to Calendar to delete the event.",
						// Toast.LENGTH_SHORT).show();
						final AlertDialog.Builder builder = new AlertDialog.Builder(
								EventsActivity.this);
						builder.setTitle("Aww Snap!");
						builder.setMessage("Event already added to Calendar.");

						builder.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});
						AlertDialog alert = builder.create();
						alert.show();
					}

				}
			});

			View extend = convertView.findViewById(R.id.extend);
			((LinearLayout.LayoutParams) extend.getLayoutParams()).bottomMargin = -100;
			extend.setVisibility(View.GONE);
			return convertView;
		}

		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private TextView detail = null;
			private TextView venue = null;
			private TextView date = null;
			private ImageView i11 = null;
			private TextView eday = null;
			// private TextView rsvp=null;
			private Button rsvp = null;
			private Button mcal = null;

			public ViewHolder(View row) {
				mRow = row;
			}

			public TextView gettitle() {
				if (null == title) {
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			public TextView getdetail() {
				if (null == detail) {
					detail = (TextView) mRow.findViewById(R.id.detail);
				}
				return detail;
			}

			public TextView getvenue() {
				if (null == venue) {
					venue = (TextView) mRow.findViewById(R.id.venue);
				}
				return venue;
			}

			public TextView getdate() {
				if (null == date) {
					date = (TextView) mRow.findViewById(R.id.datetime);
				}
				return date;
			}

			public Button getrsvp() {
				if (null == rsvp) {
					rsvp = (Button) mRow.findViewById(R.id.rsvp);

				}
				return rsvp;
			}

			/*
			 * public TextView gettext() { if(null == eday){ eday = (TextView)
			 * mRow.findViewById(R.id.eday); } return eday; }
			 */
			public ImageView getImage() {
				if (null == i11) {
					i11 = (ImageView) mRow.findViewById(R.id.img);
				}
				return i11;
			}

			public Button getcal() {
				if (null == mcal) {
					mcal = (Button) mRow.findViewById(R.id.addcal);

				}
				return mcal;
			}
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		// SubMenu menu = (SubMenu) findViewById(R.id.submenu2);
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent intent = new Intent(EventsActivity.this,
					edu.usc.Tutorial.Start.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("dialog", "stop");
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			return true;

			/*
			 * case R.id.menu_currentLoc: if(myLocationOverlay != null &&
			 * mapView.getOverlays().indexOf(myLocationOverlay) != -1) {
			 * mapView.
			 * getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay
			 * )); mapView.invalidate();
			 * 
			 * } myLocationManager.requestLocationUpdates(LocationManager.
			 * NETWORK_PROVIDER, 0, 0, myLocationListener); myLocationOverlay =
			 * null;
			 */

		case R.id.menu_currentLoc:
			final AlertDialog.Builder builder=new AlertDialog.Builder(EventsActivity.this);
            builder.setTitle("Events Help");
            builder.setMessage("YouSCream provides a real time news feed for all events in and around the campus. " +
            		"Whether it is social, academic or cultural, students will have access to what is going on at any given time." +
            		"\n\n" +
            		"*** Scroll through the newsfeed in order to access information on events." +
            		"\n\n" +
            		"*** Expand an event to find out more information" + 
            		"\n\n" +
            		"*** Add to personal calendar");
            
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

	@Override
	protected void onStart() {
		super.onStart();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	// pauses listener while app is inactive
	@Override
	public void onPause() {
		super.onPause();
	}

	// reactivates listener when app is resumed
	@Override
	public void onResume() {
		super.onResume();
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
}
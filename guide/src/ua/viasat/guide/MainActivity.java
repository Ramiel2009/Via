package ua.viasat.guide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public static String lname;
	public static String ltime;
	public static String lchannel;
	public static String lsynopse;
	ProgressDialog pd; // Loading dialog
	static TextView tvd;
	String Url = "http://ru.viasat.ua/contents";
	public static int i; // titles counter
	public static Document doc;
	public static String[] st = new String[150]; // array for titles name
	public static String[] time = new String[150]; // array for time
	public static String[] channel = new String[150]; // array for channel
	public static String[] synopsis = new String[150]; // array for synopsis
	public static int clickID; // get clicked title id
	public static String[] contentUrl;

	private DrawerLayout myDrawerLayout;
	private ListView myDrawerList;
	private ActionBarDrawerToggle myDrawerToggle;
	private CharSequence myDrawerTitle; // navigation drawer title
	private CharSequence myTitle;// used to store app title
	private String[] viewsNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn2 = (Button) findViewById(R.id.btnRefresh);
		btn2.setOnClickListener(this);
		Context dRequest = new Context();
		dRequest.execute();

		/////////NAVIGATION DRAWER//////////

		myTitle = getTitle();
		myDrawerTitle = getResources().getString(R.string.menu);

		// load slide menu items
		viewsNames = getResources().getStringArray(R.array.views_array);
		myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		myDrawerList = (ListView) findViewById(R.id.left_drawer);

		myDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, viewsNames));

		// enabling action bar app icon and behaving it as toggle button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
		
		myDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(myTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(myDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		myDrawerLayout.setDrawerListener(myDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

		myDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	private void displayView(int position) {
		
		// update the main content by replacing fragments
		Fragment fragment = null;
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
		Button btn2 = (Button)findViewById(R.id.btnRefresh);
		switch (position) {
		case 0:		
			TVCreator();
			rl.setVisibility(View.VISIBLE);
			btn2.setVisibility(View.VISIBLE);
			fragment = new FirstFragment();
			break;
		case 1:
			rl.setVisibility(View.GONE);
			btn2.setVisibility(View.GONE);
			fragment = new SecondFragment();
			break;
		case 2:
			rl.setVisibility(View.GONE);
			btn2.setVisibility(View.GONE);
			fragment = new ThirdFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			android.app.FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			myDrawerList.setItemChecked(position, true);
			myDrawerList.setSelection(position);
			setTitle(viewsNames[position]);
			myDrawerLayout.closeDrawer(myDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (myDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if navigation drawer is opened, hide the action items
		boolean drawerOpen = myDrawerLayout.isDrawerOpen(myDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		myTitle = title;
		getActionBar().setTitle(myTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		myDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		myDrawerToggle.onConfigurationChanged(newConfig);
	}

	// NAVIGATION DRAWER

	public void TVCreator() {
		for (int a = i; a > 0; a--) {
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView tvd = new TextView(this);
			tvd.setId(a);
			tvd.setOnClickListener(this);
			params.addRule(RelativeLayout.BELOW, tvd.getId() - 1);
			rl.addView(tvd, params);
			tvd.setText("");
			tvd.setText(Html.fromHtml("<b>" + st[i - a] + "</b>" + "<br>"
					+ time[i - a] + "   " + "<font color=\"grey\">"
					+ channel[i - a] + "</font>" + "<br>"));
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnRefresh) {
			Context dRequest = new Context();
			dRequest.execute();
		} else {
			Intent synIntent = new Intent(this, Synopsis.class);
			clickID = v.getId() - 1;
			startActivity(synIntent);
		}
	}

	private class Context extends AsyncTask<Void, Void, Void> {

		@Override
		public void onPreExecute() {
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("Getting Data");
			pd.setMessage("Loading...");
			pd.show();
		}

		public Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect(Url).timeout(60000).get();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					if (link.attr("href").contains("contents/")) {
						i++;
					}
				}
				i = i / 2;
				for (int a = i; a > 0; a--) {
					Element tnames = doc.select("a.title").get(i - a);
					lname = tnames.text();
					st[a - 1] = lname;
					Element ttime = doc.select("span.time").get(i - a);
					ltime = ttime.text();
					time[a - 1] = ltime;
					Element tchannel = doc.select("span.channel").get(i - a);
					lchannel = tchannel.text();
					channel[a - 1] = lchannel;
					Element tsynopse = doc.select("div.text").get(i - a);
					lsynopse = tsynopse.text();
					synopsis[a - 1] = lsynopse;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			pd.dismiss();
			TVCreator();
		}
	}

}

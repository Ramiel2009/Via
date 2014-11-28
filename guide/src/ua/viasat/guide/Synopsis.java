package ua.viasat.guide;

import java.io.IOException;



import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.TextView;

public class Synopsis extends Activity {

	public static String synopsis;
	private ProgressDialog pd;
	public TextView sname;
	public TextView ssynopsis;
	public String text="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.synopsis);
		sname = (TextView) findViewById(R.id.sname);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#3399FF")));
		sname.setTypeface(null, Typeface.BOLD);
		ssynopsis = (TextView) findViewById(R.id.ssyn);
		ssynopsis.setText("");
		sname.setText("");
		

		MainActivity.flagRefreshed = true;
		// try {
		String id = Parser.id.get(MainActivity.clickID - 1); // get item id
		String name = Parser.title.get(MainActivity.clickID - 1);

		sname.setText(name);
		
		System.out.println("id: " + id + ",  name: " + name);
		Context dRequest = new Context();
		dRequest.execute();
		

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This is called when the Home (Up) button is pressed in the action
			// bar.
			// Create a simple intent that starts the hierarchical parent
			// activity and
			// use NavUtils in the Support Package to ensure proper handling of
			// Up.
			Intent upIntent = new Intent(this, MainActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				// This activity is not part of the application's task, so
				// create a new task
				// with a synthesized back stack.
				TaskStackBuilder.from(this)
				// If there are ancestor activities, they should be added here.
						.addNextIntent(upIntent).startActivities();
				finish();
			} else {
				// This activity is part of the application's task, so simply
				// navigate up to the hierarchical parent activity.
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	private class Context extends AsyncTask<Void, Void, Void> {

		@Override
		public void onPreExecute() {
			pd = new ProgressDialog(Synopsis.this);
			pd.setTitle("Getting Data");
			pd.setMessage("Loading...");
			pd.show();
		}

		public Void doInBackground(Void... params) {
			try {
					
				TextView ss2 = (TextView) findViewById(R.id.ssyn);
					System.out.println(Parser.id.get(MainActivity.clickID - 1));
					System.out.println(MainActivity.clickID - 1);					
					text = (String) Parser.getItemInfo(Parser.id.get(MainActivity.clickID - 1)).get(0);
					System.out.println(text);
					ss2.setText(text+"");
					
								
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			pd.dismiss();
		}
	}

}

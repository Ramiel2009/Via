package ua.viasat.guide;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Synopsis extends Activity {

	public static String synopsis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synopsis);
		TextView sname = (TextView) findViewById(R.id.sname);
		sname.setTypeface(null, Typeface.BOLD);
		TextView ssynopsis = (TextView) findViewById(R.id.ssyn);
		sname.setText("");
		ssynopsis.setText("");
		int x = MainActivity.i - MainActivity.clickID - 1; // clicked TextView
		
		sname.setText(MainActivity.st[x]);
		ssynopsis.setText(MainActivity.synopsis[x]);
	}
}

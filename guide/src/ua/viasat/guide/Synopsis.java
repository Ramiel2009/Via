package ua.viasat.guide;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Synopsis extends Activity{
	@Override
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synopsis);
		TextView sname = (TextView)findViewById(R.id.sname);
		sname.setTypeface(null, Typeface.BOLD);
		TextView ssynopsis = (TextView)findViewById(R.id.ssyn);
		//sname.setText(MainActivity.name);	
		sname.setText("");
		MainActivity.desc.replaceAll("\\bСмотреть на Viaplay!\\b", "");
		//ssynopsis.setText(MainActivity.desc);
		ssynopsis.setText("");
		switch (MainActivity.clicked){
		case 1:
			//sname.setText(MainActivity.st[3]);
			Toast.makeText(this, MainActivity.clicked, Toast.LENGTH_SHORT);
			break;
		case 2:
			Toast.makeText(this, MainActivity.clicked, Toast.LENGTH_SHORT);
			//sname.setText(MainActivity.st[MainActivity.clicked]);
			break;
		case 3:
			Toast.makeText(this, MainActivity.clicked, Toast.LENGTH_SHORT);
			break;
		}
	}
}

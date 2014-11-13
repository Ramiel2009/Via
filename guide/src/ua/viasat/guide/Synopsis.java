package ua.viasat.guide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Synopsis extends Activity{
	@Override
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synopsis);
		TextView sname = (TextView)findViewById(R.id.sname);
		TextView stitle = (TextView)findViewById(R.id.ssyn);
		String title = MainActivity.name;
		sname.setText(title);	
	}
}

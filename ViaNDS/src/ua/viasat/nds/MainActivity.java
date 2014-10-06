package ua.viasat.nds;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	Button btn1;
	EditText et1;
	TextView tv1;
	TextView tv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TextView tv1 = (TextView)findViewById (R.id.tv1);
		Button btn1 = (Button)findViewById (R.id.button1);
		EditText et1 = (EditText)findViewById (R.id.editText1);
		btn1.setOnClickListener(this);
		TextView tv2 = (TextView)findViewById(R.id.textView1);
	}
	
	@Override
	public void onClick (View v){
		switch (v.getId()){
		case R.id.button1:
			String text  = et1.getText().toString();
			tv1.setText(text);
			break;
		}
	}
}

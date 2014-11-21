package ua.viasat.guide;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondFragment extends Fragment implements OnClickListener{
	
	public TextView tv_f2;
	public Button btn_f2;

	public SecondFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_second, container,
				false);	
		return rootView;
	}
	@Override 
	public void onClick (View v){
		btn_f2 = (Button)btn_f2.findViewById(R.id.btn_f2);
		btn_f2.setOnClickListener(this);
		tv_f2 = (TextView)tv_f2.findViewById(R.id.tv_f2);
		tv_f2.setText("Hmmmmmm");
		switch (v.getId()){
		case R.id.btn_f2:
			tv_f2.setText(""+"Hey!");
			break;
		}
	}
}

package ua.viasat.guide;

import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public static String name;
	public static String desc;
	public static String fulldesc;
	ProgressDialog pd;
	public static String title;
	TextView tv1;
	TextView tv2;
	TextView tvt;
	String contUrl = "http://viasat.ua/contents";
	public static int i;
	public static String[] st;
	public static String tt = "";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setText("Title");
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setText("Synopsis");
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		tv2.setOnClickListener(this);
		TextView tvt = (TextView) findViewById(R.id.tvt);
		tvt.setText("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn2:
			Context dRequest = new Context();
			dRequest.execute();
			Toast.makeText(this, "in development", Toast.LENGTH_SHORT);
			break;

		case R.id.btn1:
			TextView tv2 = (TextView) findViewById(R.id.tv2);
			if (tv2.getText().equals("Synopsis")) {
				Toast.makeText(this, "Alert", Toast.LENGTH_LONG).show();
				Stringg();
				
			} else {
			//	Intent intent = new Intent(this, Synopsis.class);
			//	startActivity(intent);
				Stringg();
			}
			break;
		default:
			Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
		}
	}

	private class Context extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("First Title");
			pd.setMessage("Loading...");
			pd.show();
		}

		protected Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect(contUrl).timeout(60000).get();// Get first title name
				Element tName = doc.select("a.title").first();
				name = tName.text();
				Element description = doc.select("div.text").first();// Get first title description
				desc = description.text();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					if (link.attr("href").contains("contents/")) {
						i++;
						System.out.println("\n" + link.attr("href"));
					}	
				}
				i = i / 2;
				for(int n=0; n!=i; n++){
					Element tt = doc.select("a.title").get(n);
					System.out.println(tt);
					st[n]=tt.toString();
				}	
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			TextView tv1 = (TextView) findViewById(R.id.tv1);
			tv1.setTypeface(null, Typeface.BOLD);
			tv1.setTextSize(20);
			tv1.setText(name);
			TextView tv2 = (TextView) findViewById(R.id.tv2);
			tv2.setText(desc);
			System.out.println(i);
		}
	}

	public void Stringg() {
		System.out.println(i);
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setText("");
		TextView tv1 = (TextView)findViewById(R.id.tv1);
		tv1.setText("");
		for (int a = i; a > 0; a--) {
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView tvd = new TextView(this);
			tvd.setId(i);
				params.addRule(RelativeLayout.BELOW, tvd.getId() - 1);
			i--;
			rl.addView(tvd, params);
			tvd.setText(""+Arrays.st[5]);
		}
	}
}

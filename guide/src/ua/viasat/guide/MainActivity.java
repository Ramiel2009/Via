package ua.viasat.guide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public static String name;
	public static String lname;
	public static String ltime;
	public static String lchannel;
	public static String lsynopse;
	public static String contUrl="http://ru.viasat.ua/";
	ProgressDialog pd;
	public static String title;
	TextView tv1;
	TextView tv2;
	TextView tvd;
	String Url = "http://ru.viasat.ua/contents";
	public static int i; // titles counter
	public static Element tname;
	public static Document doc;
	public static String[] st = new String[150]; // array for titles name
	public static String[] time = new String[150]; // array for time
	public static String[] channel = new String[150]; // array for channel
	public static String[] synopse = new String [150]; // array for synopse
	public static int clicked= 0; //get clicked title id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setText("");
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setText("");
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		Button btn2 = (Button) findViewById(R.id.btn2);
		btn1.setVisibility(View.GONE);
		btn2.setOnClickListener(this);
		// tv2.setOnClickListener(this);
		Context dRequest = new Context();
		
		
		dRequest.execute();
		Stringg();
		
	}

	public void Stringg() {
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
			// tvd.setTypeface(null, Typeface.BOLD);

			// tvd.setTextSize(16);
			tvd.setText(Html.fromHtml("<b>" + st[i - a] + "</b>" + "<br>"
					+ time[i - a] + "   " + "<font color=\"grey\">"
					+ channel[i - a] + "</font>" + "<br>"));
		}
	}

	@Override
	public void onClick(View v) {
		Intent synIntent = new Intent (this, Synopsis.class);
		Integer.toString(i);
		
		//TODO FIX THIS SHIT!!!!
		switch (v.getId()) {
		case R.id.btn2:
			Context dRequest = new Context();
			dRequest.execute();
			break;
		case 1:
			System.out.println("1");
			clicked = 0;
			startActivity(synIntent);
			break;
		case 2:
			System.out.println("2");
			clicked = 1;
			startActivity(synIntent);
			break;
		case 3:
			System.out.println("3");
			clicked = 2;
			startActivity(synIntent);
			break;
		case 4:
			System.out.println("1");
			clicked = 3;
			startActivity(synIntent);
			break;
		case 5:
			System.out.println("2");
			clicked = 4;
			startActivity(synIntent);
			break;
		case 6:
			System.out.println("3");
			clicked = 5;
			startActivity(synIntent);
			break;
		case 7:
			System.out.println("1");
			clicked = 6;
			startActivity(synIntent);
			break;
		case 8:
			System.out.println("2");
			clicked = 7;
			startActivity(synIntent);
			break;
		case 9:
			System.out.println("3");
			clicked = 8;
			startActivity(synIntent);
			break;
		default:
			Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
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
				// getting info for contents in the list view
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
					synopse [a-1]=lsynopse;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			pd.dismiss();
			Stringg();

		}
	}
}

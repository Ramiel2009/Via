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

public class MainActivity extends Activity implements OnClickListener {

	public static String lname;
	public static String ltime;
	public static String lchannel;
	public static String lsynopse;
	ProgressDialog pd; // Loading dialog
	TextView tvd;
	String Url = "http://ru.viasat.ua/contents";
	public static int i; // titles counter
	public static Document doc;
	public static String[] st = new String[150]; // array for titles name
	public static String[] time = new String[150]; // array for time
	public static String[] channel = new String[150]; // array for channel
	public static String[] synopsis = new String[150]; // array for synopsis
	public static int clickID; // get clicked title id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn2 = (Button) findViewById(R.id.btnRefresh);
		btn2.setOnClickListener(this);
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
			tvd.setText(Html.fromHtml("<b>" + st[i - a] + "</b>" + "<br>"
					+ time[i - a] + "   " + "<font color=\"grey\">"
					+ channel[i - a] + "</font>" + "<br>"));
		}
	}

	@Override
	public void onClick(View v) {
		Intent synIntent = new Intent(this, Synopsis.class);
		Integer.toString(i);

		clickID = v.getId() - 1;
		switch (v.getId()) {
		case R.id.btnRefresh:
			Context dRequest = new Context();
			dRequest.execute();
			break;
		}
		startActivity(synIntent);
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
			Stringg();
		}
	}
}

package ua.viasat.guide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
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
	public static String lname;
	public static String desc;
	public static String fulldesc;
	ProgressDialog pd;
	public static String title;
	TextView tv1;
	TextView tv2;
	String contUrl = "http://viasat.ua/contents";
	public static int i;
	public static Element tname;
	public static Document doc;
	public static String[] st = new String[150];

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
		Context dRequest = new Context();
		dRequest.execute();
	}

	public void Stringg() {
		for (int a = i; a > 0; a--) {
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView tvd = new TextView(this);
			tvd.setId(a);
			params.addRule(RelativeLayout.BELOW, tvd.getId() - 1);
			rl.addView(tvd, params);
			tvd.setText(st[i - a]);
		}
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
			} else {
				// Intent intent = new Intent(this, Synopsis.class);
				// startActivity(intent);
				TextView tv1 = (TextView) findViewById(R.id.tv1);
				tv1.setText("");
				tv2.setText("");
				Stringg();
			}
			break;
		default:
			Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
		}
	}

	private class Context extends AsyncTask<Void, Void, Void> {

		@Override
		public void onPreExecute() {
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("First Title");
			pd.setMessage("Loading...");
			pd.show();
		}

		public Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect(contUrl).timeout(60000).get();
				// Get first title name
				Element tName = doc.select("a.title").first();
				name = tName.text();
				// Get first title synopsis
				Element description = doc.select("div.text").first();
				desc = description.text();
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
					st[i - a] = lname;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			pd.dismiss();
			TextView tv1 = (TextView) findViewById(R.id.tv1);
			tv1.setTypeface(null, Typeface.BOLD);
			tv1.setTextSize(20);
			tv1.setText(name);
			TextView tv2 = (TextView) findViewById(R.id.tv2);
			tv2.setText(desc);
		}
	}
}

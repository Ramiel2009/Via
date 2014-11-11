package ua.viasat.guide;

import java.io.InputStream;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	ProgressDialog pd;
	String title;
	TextView tv1;
	TextView tv2;
	TextView tvt;
	String contUrl = "http://viasat.ua/contents";
	Bitmap bmp;
	//ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setText("");
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setText("");
		//ImageView img = (ImageView)findViewById(R.id.img1);
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);
		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		tv2.setOnClickListener(this);
		TextView tvt = (TextView)findViewById(R.id.tvt);
		tvt.setText("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			Title tRequest = new Title();
			tRequest.execute();
			break;
		case R.id.btn2:
			Context dRequest = new Context();
			dRequest.execute();
			break;
		case R.id.btn3:
			/*Image iRequest = new Image();
			iRequest.execute();*/
			Toast.makeText(this, "in development", Toast.LENGTH_SHORT);
			break;
		case R.id.tv2:
			Toast.makeText(this, "Alert", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, ChannelsList.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
		}

	}

	
	//Title
	private class Title extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("Jsoup test");
			pd.setMessage("Loading...");
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect("http://viasat.ua").get();
				title = doc.title();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			TextView tv1 = (TextView) findViewById(R.id.tv1);
			tv1.setText(title);
			pd.dismiss();
			
		}
	}


	
	// Contents
	private class Context extends AsyncTask<Void, Void, Void> {
		String desc;
		String name;
		
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("First Title");
			pd.setMessage("Loading...");
			pd.show();
		}

		protected Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect(contUrl).timeout(60000).get();
				
				// Get first title name
				
				Element tName = doc.select("a.title").first();				
				name = tName.text();
				
				// Get first title description
				Element description = doc.select("div.text").first();
				desc = description.text();
				
				
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					TextView tvt =(TextView)findViewById(R.id.tvt);
					System.out.println("\n"+link.attr("href"));
					
					//tvt.setText("\nlink : " + link.select("div.title"));
					
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
			tv2.setText(desc + "\n");
			
			TextView tvt=(TextView)findViewById(R.id.tvt);
			//tvt.setText(Arrays.toString(s));
			
		}
	}

	
	
	
	// Image
	/*protected class Image extends AsyncTask<Void, Void, Void> {
		String img;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("With image");
			pd.setMessage("Loading...");
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect(contUrl).get();
				Elements img = doc.select("img");
				String imgSrc = img.attr("src");
				InputStream input = new java.net.URL(imgSrc).openStream();
				bmp=BitmapFactory.decodeStream(input);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ImageView img = (ImageView) findViewById(R.id.img1);
            img.setImageBitmap(bmp);
			pd.dismiss();
		}
	}*/
}

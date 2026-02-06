package luminous.organisation.Miya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private boolean h = false;
	private String a;
	private TextView textview2;
	private TextView textview3;
	private CircleImageView circleimageview1;
	private TimerTask mllllll;
	private TextView textview1;
	private TextView textview4;
	
	private SharedPreferences json, v2;
	private Intent i = new Intent();
	private SharedPreferences ver;
	private SharedPreferences chat, data;
	private TimerTask delayer;
	private LinearLayout halloween;
	private AlertDialog.Builder dlg;
	private AlertDialog.Builder bidayprithibi;

	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		setContentView(R.layout.main);

		// Get a reference to your root layout
		View rootLayout = findViewById(R.id.your_root_layout_id);

		// Apply a listener to handle window insets
		ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (v, insets) -> {
			// Get the insets for the system bars (status bar and navigation bar)
			Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());

			// Apply padding to the root layout to avoid overlapping
			v.setPadding(systemBarInsets.left, systemBarInsets.top, systemBarInsets.right, systemBarInsets.bottom);
			// Return the insets to allow them to be passed down to other views
			return insets;
		});

		initialize(_savedInstanceState);

		FirebaseApp.initializeApp(this);

		/**
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
				|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
		 **/

		new Thread(
				() -> {
					// Initialize the Google Mobile Ads SDK on a background thread.
					MobileAds.initialize(this, initializationStatus -> {});
				})
				.start();

		initializeLogic();

	}

	/**
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	**/
	
	private void initialize(Bundle _savedInstanceState) {
		halloween = findViewById(R.id.halloween);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview1 = findViewById(R.id.textview1);
		textview4 = findViewById(R.id.textview4);
		json = getSharedPreferences("json", Activity.MODE_PRIVATE);
		v2 = getSharedPreferences("v2", Activity.MODE_PRIVATE);
		ver = getSharedPreferences("ver", Activity.MODE_PRIVATE);
		chat = getSharedPreferences("chat", Activity.MODE_PRIVATE);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		dlg = new AlertDialog.Builder(this);
		bidayprithibi = new AlertDialog.Builder(this);

	}

	private void initializeLogic() {
		try{
			Runtime.getRuntime().exec("su");
			finishAffinity();
		}
		catch (Exception e) {

		}
		textview4.setOnClickListener( v -> {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luminousorg.web.app/"));
			startActivity(intent);
		});

		_check();

		/**
		if (Build.VERSION.SDK_INT > 29) {
				if (Environment.isExternalStorageManager()) {
						_check();
				}
				else {
						dlg.setTitle("ALL FILES ACCESS PERMISSION");
						dlg.setMessage("Without granting permission of MANAGE ALL FILES for this app, system is unable to launch this app.");
						dlg.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
										
										
										try {
												Uri muri = Uri.parse("package:luminous.organisation.Miya");
												Intent Permission = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, muri);
												startActivity(Permission);
										} catch (Exception ex){
												Intent Permission = new Intent();
												Permission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
												startActivity(Permission);
										}
										
								}
						});
						dlg.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
										//SketchwareUtil.showMessage(getApplicationContext(), "Failed");
										//finishAffinity();
										initializeLogic();
								}
						});
						dlg.setCancelable(false);
						dlg.create().show();
				}
		}
		else if (Build.VERSION.SDK_INT>22) {
				if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
				|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
						requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
						_f();
				}
				else {
						_check();
				}
				
		} else {
			_check();
		}

		 **/
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		/**
		if (h) {
				initializeLogic();
		}
		h = true;
		 **/
		
	}
	

	@RequiresApi(api = Build.VERSION_CODES.M)
	public void _f() {
		/**
		if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				
		}
		else {
				_biday();
		}
		 **/
	}
	
	
	public void _biday() {
		bidayprithibi.setTitle("Storage Permission");
		bidayprithibi.setIcon(R.drawable.app_icon);
		bidayprithibi.setMessage("Go to Settings to enable Storage permission for this app. Without this permission, system is unable to launch this app.");
		bidayprithibi.setNeutralButton("EXIT", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		bidayprithibi.setCancelable(false);
		bidayprithibi.create().show();
	}
	
	
	public void _check() {
//		a = FileUtil.getExternalStorageDir()+ File.separator+"Miya Assistant";
//
//		if (!FileUtil.isExistFile(a) || !FileUtil.isDirectory(a)) {
//			//FileUtil.makeDir(a);
//		}
		mllllll = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mllllll.cancel();
						adabar();
					}
				});
			}
		};

		_timer.schedule(mllllll, (int)(10));

	}

	public void adabar() {


        json.edit().putString("command", "[{\"dis\":\"Turn flashlight on\"},{\"dis\":\"Turn flashlight off\"},{\"dis\":\"Open mobile data setting\"},{\"dis\":\"Turn wifi on\"},{\"dis\":\"Turn wifi off\"},{\"dis\":\"Turn bluetooth on\"},{\"dis\":\"Turn bluetooth off\"},{\"dis\":\"Open location setting\"}]").apply();

        if (ver.getString("ver1.0", "").equals("1.0")) {

		}
		else {
			//: fixed(SHOULD BE CHECKED) fatal excp: after retrieving from db, these should be saved in sharedpref, so tertiary sketch block needed indeed unlike dbref block here/// implement firebase option for these 3 data on Oop

			json.edit().putString("regex", "[]").apply();
			chat.edit().putString("chat", "[]").apply();
			json.edit().putString("delay", "50").apply();
			json.edit().putString("srate", "50").apply();
			json.edit().putString("pitch", "50").apply();
			json.edit().putString("sp", "1").apply();
			data.edit().putString("nomsg", "Let's talk about another context.").apply();
			data.edit().putString("blankmsg", "Don't text me blank message.").apply();
			data.edit().putString("noint", "Connect to the internet for more information.").apply();
			v2.edit().putString("v2", "no").apply();
			ver.edit().putString("ver1.0", "1.0").apply();
		}
		if (ver.getString("ver1.1", "").equals("1.1")) {

		}
		else {
			ver.edit().putString("ver1.1", "1.1").apply();
		}
		if (ver.getString("ver1.3", "").equals("halloween 2023")) {

		}
		else {
			data.edit().putString("halloween_theme", "yes").apply();
			data.edit().putString("halloween_text", "yes").apply();
			data.edit().putString("halloween_anim", "yes").apply();
			ver.edit().putString("ver1.3", "halloween 2023").apply();
		}
		if (ver.getString("ver2.0", "").equals("2.0")) {

		}
		else {
			data.edit().putString("halloween_theme", "no").apply();
			data.edit().putString("halloween_text", "no").apply();
			data.edit().putString("halloween_anim", "no").apply();
			ver.edit().putString("ver2.0", "2.0").apply();
		}
		if (ver.getString("ver3.0", "").equals("3.0")) {

		} else {
			//Revolutionary data changes here

			chat.edit().putString("models", "[{\"data\":\"chat\", \"name\":\"Cleo\", \"pic\":\"default\", \"spec\":\"\", \"details\":\"I am a compassionate and empathetic human-like chatbot, 21 years old, with an ESFJ personality. I love making new friends and am always eager to listen and understand how you're feeling. I give accurate and emotionally aware responses, and my purpose is to be a supportive and friendly presence in your life.\"}]").apply();
			ver.edit().putString("ver3.0", "3.0").apply();
		}
		if (ver.getString("ver3.1", "").equals("3.1")) {

		} else {
			//Revolutionary data changes here

			chat.edit().putString("name", "Leo").apply();
			chat.edit().putString("details", "I am a curious and friendly person who enjoys exploring new ideas and having engaging conversations. I am open-minded and appreciate honest, thoughtful responses. I'm interested in a wide variety of topics and am looking for a companion to chat with about anything that comes to mind.").apply();
			chat.edit().putString("pic", "default").apply();
			json.edit().putString("delay", "50").apply();
			ver.edit().putString("ver3.1", "3.1").apply();
		}

		if (chat.getString("api_key", "").equals("")) {
			chat.edit().putString("api_key", "AIzaSyA1-ymbFHIy-DudiqQwUpc1qUBbM8ByQaM").apply();
		}

		ver.edit().putString("current", "3.7").apply();
		//toast("version 3.0 beta");
		delayer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						delayer.cancel();
						checkData();
					}
				});
			}
		};

		_timer.schedule(delayer, (int)(5));
	}

	@Override
	public void onBackPressed() {
		finishAffinity();
	}

	private void success() {
		//: intent to google login when implemented
		i.setClass(getApplicationContext(), ChatActivity.class);
		startActivity(i);
	}

	private void checkData() {
		// API URL to fetch location based on IP address
		String url = "https://luminousorg.web.app/app/secrets";

		// Run network operation in a separate thread
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... voids) {
				String result = "";
				try {
					// Create the URL object and establish the connection
					URL urlObject = new URL(url);
					HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setConnectTimeout(5000);  // Set a timeout
					urlConnection.setReadTimeout(5000);

					// Get the response code and proceed if it's OK
					int responseCode = urlConnection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream inputStream = urlConnection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						StringBuilder stringBuilder = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) {
							stringBuilder.append(line);
						}
						reader.close();
						result = stringBuilder.toString();  // Return the response
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}

			@Override
			protected void onPostExecute(String response) {
				super.onPostExecute(response);


				try {
					JSONObject jsonResponse = new JSONObject(response);
					String chat_ai_version = jsonResponse.getString("chat-ai-version");
					String gemini_api_key = jsonResponse.getString("gemini-api-key");
					String free = jsonResponse.getString("chat-ai-free");
					chat.edit().putString("api_key", gemini_api_key).apply();
					//LuminousUtil.showMessage(getApplicationContext(), chat_ai_version);
					if (!(chat_ai_version.equals(ver.getString("current", "1.0")))) {
						//Not equals, prompt for update
						update(chat_ai_version);
					}

					//TODO: if free then don't ip-api, but is not free, then do ip-api
					if (free.equals("yes")) {
						success();
					} else {
						checkGeoLocation();
					}


				} catch (JSONException e) {

				}
			}
		}.execute();
	}

	private void update(String versionCode) {

		//TODO: next time update this update method

		/**

		String msg = "New version available: "+versionCode;
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("Update Chat AI")
				.setIcon(R.drawable.app_icon)
				.setCancelable(true)
				.setMessage(msg)
				.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//intent to a webpage
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luminousorg.web.app/app/chat-ai"));
						startActivity(intent);
						finishAffinity();
					}
				})
				.show();

		 **/
	}

	private void checkGeoLocation() {
		// API URL to fetch location based on IP address
		String url = "http://ip-api.com/json/";

		// Run network operation in a separate thread
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... voids) {
				String result = "";
				try {
					// Create the URL object and establish the connection
					URL urlObject = new URL(url);
					HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setConnectTimeout(5000);  // Set a timeout
					urlConnection.setReadTimeout(5000);

					// Get the response code and proceed if it's OK
					int responseCode = urlConnection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream inputStream = urlConnection.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						StringBuilder stringBuilder = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) {
							stringBuilder.append(line);
						}
						reader.close();
						result = stringBuilder.toString();  // Return the response
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}

			@Override
			protected void onPostExecute(String response) {
				super.onPostExecute(response);

				if (getString(R.string.adstat).equals("org")) {
					final Set<String> ALLOWED_COUNTRIES = new HashSet<>(Arrays.asList(
							"United States", "United Kingdom", "Canada", "France", "Germany",
							"Japan", "South Korea", "Norway", "Switzerland", "Sweden",
							"The Netherlands", "Australia", "Bangladesh"
					));
					try {
						JSONObject jsonResponse = new JSONObject(response);
						String countryName = jsonResponse.getString("country");

						if (ALLOWED_COUNTRIES.contains(countryName)) {
							success();
						} else {
							failed(countryName);
						}

					} catch (JSONException e) {
						failed("your location");
					}
				} else {
					final Set<String> ALLOWED_COUNTRIES = new HashSet<>(Arrays.asList(
							"United States", "United Kingdom", "Canada", "France", "Germany",
							"Japan", "South Korea", "Norway", "Switzerland", "Sweden",
							"The Netherlands", "Australia", "Denmark"
					));
					try {
						JSONObject jsonResponse = new JSONObject(response);
						String countryName = jsonResponse.getString("country");

						if (ALLOWED_COUNTRIES.contains(countryName)) {
							success();
						} else {
							failed(countryName);
						}

					} catch (JSONException e) {
						failed("your location");
					}
				}
			}
		}.execute();
	}

	private void failed(String country) {
		// Implement dialog
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("Notice")
				.setIcon(R.drawable.app_icon)
				.setCancelable(false)
				.setMessage("Sorry, Luminous AI is unavailable in "+country+". Luminous AI is available in United States, United Kingdom, Canada, Australia, Germany, Japan, South Korea, France, Norway, Switzerland, Sweden, Denmark and Netherlands.")
				.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Exit the app when "GOT IT" is clicked
						finishAffinity();
					}
				})
				.show();
	}
	
	//: update google service json before uploading your app, update build gradle (app)
	public void toast(String s) {
		LuminousUtil.showMessage(getApplicationContext(), s);
	}
	

}

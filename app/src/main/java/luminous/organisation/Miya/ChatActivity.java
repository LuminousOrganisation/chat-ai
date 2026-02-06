package luminous.organisation.Miya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


	private Timer _timer = new Timer();
	private Camera camera;
	private String finalError;

	// Gemini API configuration
	// Array of Gemini model names to try in order

	private static final String[] GEMINI_MODELS = {
            "gemini-3-pro-preview",
			"gemini-2.5-pro",
			"gemini-2.5-pro-flash",
			"gemini-2.5-pro-flash-lite",
			"gemini-2.0-flash",
			"gemini-2.0-flash-lite",
            "gemini-2.0-flash-live",
			"gemma-3n-e2b-it",
			"gemma-3n-e4b-it",
			"gemma-3-1b-it",
			"gemma-3-4b-it",
			"gemma-3-12b-it",
			"gemma-3-27b-it",
	};

	String API_KEY;


	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	// These variables will store the active model's data
	private String activeModelName;
	private String activeModelDetails;
	private String activeModelPicPath;
	private String activeModelDataKey; // This stores the key for the current chat history (e.g., "chat", "chat_model_1")

	private HashMap<String, Object> vmap = new HashMap<>();
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lmap_admob = new ArrayList<>();
	private String stringifY = "";
	private String ans = "";
	private boolean ableToIn = false;
	private String ping = "";
	private boolean speaking = false;
	private double ppitch = 0;
	private double ssrate = 0;
	private double RDD;

	private String doggy = "";
	private String uid;
	private HashMap<String, Object> dutturiChai = new HashMap<>();
	private String who;
	private double signify = 0;
	private double dig = 0;
	private String SIGNIFY = "";
	private String SIGNIFY_DOGGY = "";
	private String ser = "";
	private String out = "";
	private String str = "";
	private String tag;
	private boolean spcl = false;
	private String inputValue = "";
	private Uri fileUri;
	private Integer helper;
	private FileProvider fileprovider;

	private ArrayList<HashMap<String, Object>> CHAT = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> JSON = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> REGEX = new ArrayList<>();
	private ArrayList<String> words = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> COMMAND = new ArrayList<>();



	private LinearLayout linear1;
	private ListView listview1;
	private LinearLayout linear6;
	private LinearLayout linear2;
	private CircleImageView circleimageview5;
	private TextView textview2;
	private TextView textview3;
	private CircleImageView circleimageview4;
	private CircleImageView circleimageview3;
	private TextView textview1;
	private CircleImageView dovoiceon;
	private CircleImageView dovoiceoff;
	private CircleImageView commandimageview;
	private Spinner spinner1;
	private AutoCompleteTextView edittext1;
	private CircleImageView circleimageview2;
	private ArrayList<HashMap<String, Object>> modelsList; // For reading the list of models from SharedPreferences

	private SpeechRecognizer stt;
	private Intent i = new Intent();
	private SharedPreferences chat,
			data,
			ver;
	private SharedPreferences json;
	private TextToSpeech tts;
	private TimerTask t;
	private Intent ic = new Intent();
	private Calendar cal = Calendar.getInstance();
	private LinearLayout bannerContainer;

	private InterstitialAd interstitialAd;
	private ImageView imageView, delChat;
	private ArrayList<String> suggestionsList = new ArrayList<>();
	boolean isLoading;
	private RewardedAd rewardedAd;
	private TextView coinView;
	private int currentIndex = 0;
	private ProgressDialog pd;
	private AlertDialog.Builder dd;
	private AlertDialog.Builder dd2;
	private AlertDialog.Builder xjxitdiyf;

	private boolean fini = false;
	private Boolean speechable = false;

	private Boolean reload = true;
	private Boolean reloadInt = true;

	private RewardedInterstitialAd rewardedInterstitialAd;


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		setContentView(R.layout.chat);

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

		initializeLogic();

	}

	private void initialize(Bundle _savedInstanceState) {
		xjxitdiyf = new AlertDialog.Builder(this);



		linear1 = findViewById(R.id.linear1);
		listview1 = findViewById(R.id.listview1);
		linear6 = findViewById(R.id.linear6);
		linear2 = findViewById(R.id.linear2);
		circleimageview5 = findViewById(R.id.circleimageview5);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		circleimageview4 = findViewById(R.id.circleimageview4);
		circleimageview3 = findViewById(R.id.circleimageview3);
		textview1 = findViewById(R.id.textview1);
		dovoiceon = findViewById(R.id.dovoiceon);
		dovoiceoff = findViewById(R.id.dovoiceoff);
		commandimageview = findViewById(R.id.commandimageview);
		spinner1 = findViewById(R.id.spinner1);
		edittext1 = findViewById(R.id.edittext1);
		circleimageview2 = findViewById(R.id.circleimageview2);

		stt = SpeechRecognizer.createSpeechRecognizer(this);
		chat = getSharedPreferences("chat", Activity.MODE_PRIVATE);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		json = getSharedPreferences("json", Activity.MODE_PRIVATE);
		ver = getSharedPreferences("ver", Activity.MODE_PRIVATE);
		tts = new TextToSpeech(getApplicationContext(), null);
		bannerContainer = findViewById(R.id.bannerContainer);
		imageView = findViewById(R.id.imageView);


		uid = data.getString("uid", "");
		coinView = findViewById(R.id.coinView);
		dd = new AlertDialog.Builder(this);
		dd2 = new AlertDialog.Builder(this);
		delChat = findViewById(R.id.delChat);


		circleimageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				showIntAd();
			}
		});

		dovoiceon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

				if (ableToIn) {
					ableToIn = false;
					Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
					_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
					stt.startListening(_intent);
					dovoiceon.setVisibility(View.GONE);
					dovoiceoff.setVisibility(View.VISIBLE);
				}

			}
		});

		dovoiceoff.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				stt.stopListening();
				dovoiceoff.setVisibility(View.GONE);
				dovoiceon.setVisibility(View.VISIBLE);
				ableToIn = true;

			}
		});

		commandimageview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				release();
			}
		});

		circleimageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (ableToIn) {
					if (isChatLimitReached()) {
						ableToIn = false;
						vmap = new HashMap<>();
						vmap.put("who", "you");
						vmap.put("you", edittext1.getText().toString());
						CHAT.add(vmap);
						listview1.setAdapter(new Listview1Adapter(CHAT));
						((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
						linear6.setVisibility(View.VISIBLE); // Show typing indicator
						textview1.setText("..."); // Set initial typing message
						ping = edittext1.getText().toString();
						edittext1.setText("");
						_doShe(ping);
					}  else {
						Toast.makeText(getApplicationContext(), "Chat limit reached.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		delChat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				del();
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});




		stt.setRecognitionListener(new RecognitionListener() {
			@Override
			public void onReadyForSpeech(Bundle _param1) {
			}

			@Override
			public void onBeginningOfSpeech() {
			}

			@Override
			public void onRmsChanged(float _param1) {
			}

			@Override
			public void onBufferReceived(byte[] _param1) {
			}

			@Override
			public void onEndOfSpeech() {
			}

			@Override
			public void onPartialResults(Bundle _param1) {
			}

			@Override
			public void onEvent(int _param1, Bundle _param2) {
			}

			@Override
			public void onResults(Bundle _param1) {
				final ArrayList<String> _results = _param1.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				final String _result = _results.get(0);

				if (ableToIn) {
					if (isChatLimitReached()) {
						ableToIn = false;
						dovoiceon.setVisibility(View.VISIBLE);
						dovoiceoff.setVisibility(View.GONE);
						ableToIn = false;
						vmap = new HashMap<>();
						vmap.put("who", "you");
						vmap.put("you", _result);
						CHAT.add(vmap);
						listview1.setAdapter(new Listview1Adapter(CHAT));
						((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
						linear6.setVisibility(View.VISIBLE); // Show typing indicator
						textview1.setText("..."); // Set initial typing message
						ping = _result;
						_doShe(ping);
					} else {
						Toast.makeText(getApplicationContext(), "Chat limit reached.", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onError(int _param1) {
				final String _errorMessage;
				switch (_param1) {
					case SpeechRecognizer.ERROR_AUDIO:
						_errorMessage = "Audio error";
						break;

					case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
						_errorMessage = "Speech timeout";
						break;

					case SpeechRecognizer.ERROR_NO_MATCH:
						_errorMessage = "Speech no match";
						break;

					case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
						_errorMessage = "Recognizer busy";
						break;

					case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
						_errorMessage = "Recognizer insufficient permissions";
						break;

					default:
						_errorMessage = "Recognizer other error";
						break;
				}

				dovoiceon.setVisibility(View.VISIBLE);
				dovoiceoff.setVisibility(View.GONE);
				ableToIn = false;
				vmap = new HashMap<>();
				vmap.put("who", "she");
				vmap.put("she", _errorMessage);
				_voice(_errorMessage);
				CHAT.add(vmap);
				listview1.setAdapter(new Listview1Adapter(CHAT));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				chat.edit().putString(activeModelDataKey, new Gson().toJson(CHAT)).apply(); // Use activeModelDataKey
				ableToIn = true;


			}
		});
	}

	private Boolean isChatLimitReached() {
		if (getString(R.string.adstat).equals("org")) {
			return true;
		} else {
			return CHAT.size() <= 100;
		}
	}

	private void initializeLogic() {
		API_KEY = chat.getString("api_key", "");

		linear6.setVisibility(View.GONE);
		dovoiceoff.setVisibility(View.GONE);
		ableToIn = true;

		// Set model details and chat data key first
		setModel();

		bannerAd(bannerContainer);
		loadInterstitial();
		loadAd();

		// Now load the chat history using the activeModelDataKey
		CHAT = new Gson().fromJson(chat.getString(activeModelDataKey, "[]"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

		COMMAND = new Gson().fromJson(json.getString("command", "[]"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		spinner1.setAdapter(new Spinner1Adapter(COMMAND));


		//REGEX = new Gson().fromJson(json.getString("regex", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		//JSON = new Gson().fromJson(json.getString("json", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

		if (json.getString("sp", "").equals("1")) {
			speaking = true;
		}
		else {
			speaking = false;
		}

		listview1.setStackFromBottom(true);
		listview1.setAdapter(new Listview1Adapter(CHAT));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();


		ScaleAnimation zoomInAnimation = new ScaleAnimation(
				1f, 1.2f, // Scale factors for X-axis
				1f, 1.2f, // Scale factors for Y-axis
				Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point for X scaling (center)
				Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point for Y scaling (center)
		);
		zoomInAnimation.setDuration(1000); // Animation duration (milliseconds)
		zoomInAnimation.setRepeatCount(Animation.INFINITE); // Infinite repeat
		zoomInAnimation.setRepeatMode(Animation.REVERSE); // Reverse the animation after each cycle

		//adButton.startAnimation(zoomInAnimation);
	}

	private void bannerAd(LinearLayout bannerContainer) {
		if (getString(R.string.adstat).equals("org")) {
			bannerContainer.setVisibility(View.GONE);
			return;
		}

		AdView adView = new AdView(getApplicationContext());
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(getString(R.string.banner));
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		bannerContainer.removeAllViews();
		bannerContainer.addView(adView, params);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdClicked() {
				// Code to be executed when the user clicks on an ad.
				//showMessage("ad clicked");
			}

			@Override
			public void onAdClosed() {
				// Code to be executed when the user is about to return
				// to the app after tapping on an ad.
			}

			@Override
			public void onAdFailedToLoad(LoadAdError adError) {
				// Code to be executed when an ad request fails.

				if (reload) {
					reload = false;
					bannerAd(bannerContainer);
				} else {
					bannerContainer.setVisibility(View.GONE);
				}
			}

			@Override
			public void onAdImpression() {
				// Code to be executed when an impression is recorded
				// for an ad.
			}

			@Override
			public void onAdLoaded() {
				// Code to be executed when an ad finishes loading.
			}

			@Override
			public void onAdOpened() {
				// Code to be executed when an ad opens an overlay that
				// covers the screen.
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (getString(R.string.adstat).equals("org")) {
			return;
		}
		//isInGeoLocation();

	}

	private void isInGeoLocation() {
		String url = "http://ip-api.com/json/";
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... voids) {
				String result = "";
				try {

					URL urlObject = new URL(url);
					HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setConnectTimeout(5000);
					urlConnection.setReadTimeout(5000);

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
						result = stringBuilder.toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}

			@Override
			protected void onPostExecute(String response) {
				super.onPostExecute(response);

				final Set<String> ALLOWED_COUNTRIES = new HashSet<>(Arrays.asList(
						"United States", "United Kingdom", "Canada", "France", "Germany",
						"Japan", "South Korea", "Norway", "Switzerland", "Sweden",
						"The Netherlands", "Australia", "Denmark"
				));

				try {
					JSONObject jsonResponse = new JSONObject(response);
					String country = jsonResponse.getString("country");
					if (ALLOWED_COUNTRIES.contains(country)) {
						//showMessage(country);
					} else {
						failed(country);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}
		}.execute();
	}

	private void failed(String country) {
		// Implement dialog
		new AlertDialog.Builder(ChatActivity.this)
				.setTitle("Notice")
				.setIcon(R.drawable.app_icon)
				.setCancelable(false)
				.setMessage("Sorry, Luminous AI is prohibited in "+country+". Luminous AI is available in United States, United Kingdom, Canada, Australia, Germany, Japan, South Korea, France, Norway, Switzerland, Sweden and Netherlands.")
				.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Exit the app when "GOT IT" is clicked
						finishAffinity();
					}
				})
				.show();
	}


	public void setModel() {
		// Get the current model's data from SharedPreferences
		String currentSpec = chat.getString("current", "");
		String modelsJson = chat.getString("models", "[]");
		modelsList = new Gson().fromJson(modelsJson, // Ensure modelsList is populated
				new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

		// Initialize with default Cleo values
		activeModelName = "Cleo";
		activeModelDetails = getString(R.string.default_model_details);
		activeModelPicPath = "default";
		activeModelDataKey = "chat"; // Default chat history key

		// Iterate to find the currently selected model
		for (HashMap<String, Object> model : modelsList) {
			if (model.containsKey("spec") && model.get("spec").toString().equals(currentSpec)) {
				activeModelName = model.get("name").toString();
				activeModelDetails = model.get("details").toString();
				activeModelPicPath = model.get("pic").toString();
				activeModelDataKey = model.get("data").toString(); // Get the correct data key
				break;
			}
		}

		// Set the model's name to the TextView
		textview2.setText(activeModelName);

		// Load and set the model's profile picture
		if (activeModelPicPath.equals("default")) {
			circleimageview5.setImageResource(R.drawable.app_icon);
			circleimageview3.setImageResource(R.drawable.app_icon);
		} else {
			File imageFile = new File(activeModelPicPath);
			if (imageFile.exists()) {
				Glide.with(this)
						.load(imageFile)
						.placeholder(R.drawable.app_icon)
						.error(R.drawable.app_icon)
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.into(circleimageview5);
				Glide.with(this)
						.load(imageFile)
						.placeholder(R.drawable.app_icon)
						.error(R.drawable.app_icon)
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.into(circleimageview3);
			} else {
				// Fallback to default if the image file is not found
				circleimageview5.setImageResource(R.drawable.app_icon);
				circleimageview3.setImageResource(R.drawable.app_icon);
			}
		}
	}

	private void release() {
		spinner1.setVisibility(View.VISIBLE);
		spinner1.performClick();
	}

    public class Spinner1Adapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public Spinner1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _v, ViewGroup _container) {
            LayoutInflater _inflater = getLayoutInflater();
            View _view = _v;
            if (_view == null) {
                _view = _inflater.inflate(R.layout.commandcustomview, null);
            }

            final TextView areques = _view.findViewById(R.id.areques);

            areques.setText(_data.get((int)_position).get("dis").toString());
            areques.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    edittext1.setText(_data.get((int)_position).get("dis").toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    edittext1.requestFocus();
                    Boolean hadaaaaaaaaaaaa = imm.hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
                    // hadaaaaaaaaaaaa = imm.hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
                    circleimageview2.performClick();

                }
            });

            return _view;
        }
    }

	@Override
	public void onBackPressed() {
		xjxitdiyf.setTitle("Cleo");
		xjxitdiyf.setIcon(R.drawable.app_icon);
		xjxitdiyf.setMessage("Do you want to exit?");
		xjxitdiyf.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		xjxitdiyf.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {

			}
		});
		xjxitdiyf.create().show();
	}




		/**
		if (CHAT.size() > 0 && CHAT.get(CHAT.size() -1).containsKey("firebase")) {
			_voice((String) CHAT.get(CHAT.size()-1).get("she"));
			try {
				CHAT.get(CHAT.size()-1).remove("firebase");
			} catch (Exception e) {}
		}
		 **/




	public void _doShe(final String _inputValue) {
		showAd();
		// Hide the typing indicator immediately
		linear6.setVisibility(View.GONE);
		ableToIn = false; // Disable input while processing

		String processedInput = _inputValue;

		String activeQName = chat.getString("name", "");
		String activeQDetails = chat.getString("details", "");

		String specialNote1 = getString(R.string.instruct11) + activeQName + getString(R.string.instruct12) + activeQDetails + getString(R.string.instruct13);

		String specialNote2 = getString(R.string.instruct21) + activeModelName + getString(R.string.instruct22) + activeModelDetails + getString(R.string.instruct23);

		// Append the new dynamic special note to your processedInput
		processedInput = processedInput +"\n\n"+ specialNote1 +"\n\n"+ specialNote2 /** +"\n\n"+ metabolism(activeQName) **/;

		// --- Step 2: If no command, send to Gemini API ---
		showTypingIndicatorAndProcess(processedInput);

	}

    private String metabolism(String personName) {
        // --- UPDATED SYSTEM INSTRUCTION FOR BETTER ADHERENCE ---
        // Made the instruction more critical and explicit to overcome LLM conversational tendencies.
        return "[CRITICAL SYSTEM INSTRUCTION: Your top priority is to check if " + personName + " (the user) is requesting a specific device action from the list below. If they are, you MUST respond ONLY with the exact command key (e.g., '@$key_name$'). DO NOT include any explanatory text, pleasantries, or extra punctuation. Your response for a command MUST be just the key, nothing else. Focus on the first command mentioned.\n\n" +
                "1. Turn on light/torch/flashlight = @$light_on$" +"\n"+
                "2. Turn off light/torch/flashlight = @$light_off$" +"\n"+
                "3. Turn on wifi = @$wifi_on$" +"\n"+
                "4. Turn off wifi = @$wifi_off$" +"\n"+
                "5. Turn on bluetooth = @$bluetooth_on$" +"\n"+
                "6. Turn off bluetooth = @$bluetooth_off$" +"\n"+
                "7. Open location/GPS settings = @$location$" +"\n"+
                "8. Open mobile data/internet settings = @$mobile_data$"
                +"\n"+"]";
    }

	private void del() {
		// Get the current model's data from SharedPreferences to find the correct chat history key
		String currentSpec = chat.getString("current", "");
		String modelsJson = chat.getString("models", "[]");
		ArrayList<HashMap<String, Object>> modelsList = new Gson().fromJson(modelsJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

		String modelName = "Cleo"; // Default name
		String dataKeyToDelete = "chat"; // Default chat key

		for (HashMap<String, Object> model : modelsList) {
			if (model.containsKey("spec") && model.get("spec").toString().equals(currentSpec)) {
				modelName = model.get("name").toString();
				dataKeyToDelete = model.get("data").toString(); // Get the actual data key for this model
				break;
			}
		}

		final String finalDataKey = dataKeyToDelete;
		final String finalModelName = modelName;

		AlertDialog.Builder alertDel = new AlertDialog.Builder(ChatActivity.this);
		alertDel.setTitle("⚠️ Caution ⚠️");
		alertDel.setIcon(R.drawable.app_icon);
		alertDel.setMessage("You are going to delete the conversation history for '" + finalModelName + "'.\nDo you want to delete it forever? This cannot be undone.");
		alertDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				chat.edit().putString(finalDataKey, "[]").apply(); // Use the correct dataKey for deletion
				// Clear the current in-memory chat list and notify adapter
				CHAT.clear();
				((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
				toast("Conversation history for " + finalModelName + " cleared!"); // Provide user feedback
			}
		});
		alertDel.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
			}
		});
		alertDel.create().show();
	}

	private void showTypingIndicatorAndProcess(final String prompt) {
		//under development
		// Show typing indicator
		runOnUiThread(() -> {
			linear6.setVisibility(View.VISIBLE);
			textview1.setText("..."); // Updated text
		});

		// Use a background thread for network operation
		executorService.execute(() -> {
			boolean success = false;
			finalError = data.getString("noint", "I'm sorry. Please try again later.");
			final String unwantedResponse = "Let's talk about another context.";

			// OPTIMIZATION 1: Use a reasonable retry count.
			// 10000 is far too high and will cause long delays.
			final int maxRetries = 7;

			// OPTIMIZATION 2: Truncate chat history to save on tokens and cost.
			// This is crucial for long conversations. Limit to a recent number of messages, e.g., 20.
			JSONArray truncatedContents = new JSONArray();
			int historySize = CHAT.size();
            //TODO: change the memory limit
			int start = Math.max(0, historySize - 20); // Get the last 20 messages

			for (int i = start; i < historySize; i++) {
				HashMap<String, Object> chatItem = CHAT.get(i);
				JSONObject part = new JSONObject();

				// FIX FOR DOUBLED MESSAGES: Check if the last message in history is the same as the prompt.
				// If it is, skip it because we will add it later. This prevents duplication.
				if (i == historySize - 1 && chatItem.containsKey("you") && chatItem.get("you").equals(prompt)) {
					continue;
				}

				if (chatItem.containsKey("who") && chatItem.get("who").equals("you")) {
                    try {
                        part.put("role", "user");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONObject textPart = new JSONObject();
					if (chatItem.get("you") != null) {
                        try {
                            textPart.put("text", chatItem.get("you").toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            textPart.put("text", "");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
					JSONArray partsArray = new JSONArray();
					partsArray.put(textPart);
                    try {
                        part.put("parts", partsArray);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else if (chatItem.containsKey("who") && chatItem.get("who").equals("she")) {
                    try {
                        part.put("role", "model");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONObject textPart = new JSONObject();
					if (chatItem.get("she") != null) {
                        try {
                            textPart.put("text", chatItem.get("she").toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            textPart.put("text", "");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
					JSONArray partsArray = new JSONArray();
					partsArray.put(textPart);
                    try {
                        part.put("parts", partsArray);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
				if (part.length() > 0) {
					truncatedContents.put(part);
				}
			}

			// Add the current user prompt to the truncated history
			JSONObject userPart = new JSONObject();
            try {
                userPart.put("role", "user");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JSONObject textPart = new JSONObject();
            try {
                textPart.put("text", prompt);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JSONArray partsArray = new JSONArray();
			partsArray.put(textPart);
            try {
                userPart.put("parts", partsArray);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            truncatedContents.put(userPart);

			// OPTIMIZATION 3: Order models by speed/cost (e.g., flash first) for better performance.
			// Ensure your GEMINI_MODELS array is ordered correctly for this to be effective.
			for (String currentModel : GEMINI_MODELS) {
				long currentModelDelay = 1; // Initial delay for current model

				for (int retryAttempt = 0; retryAttempt < maxRetries; retryAttempt++) {
					try {
						String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/" + currentModel + ":generateContent?key=" + API_KEY;
						URL url = new URL(apiUrl);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json");
						conn.setDoOutput(true);

						JSONObject payload = new JSONObject();
						payload.put("contents", truncatedContents);

						try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
							writer.write(payload.toString());
						}

						int responseCode = conn.getResponseCode();
						if (responseCode == HttpURLConnection.HTTP_OK) {
							try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
								StringBuilder response = new StringBuilder();
								String inputLine;
								while ((inputLine = in.readLine()) != null) {
									response.append(inputLine);
								}
								JSONObject jsonResponse = new JSONObject(response.toString());

								String generatedText = data.getString("nomsg", "I couldn't get a coherent response.");
								if (jsonResponse.has("candidates")) {
									JSONArray candidates = jsonResponse.getJSONArray("candidates");
									if (candidates.length() > 0) {
										JSONObject candidate = candidates.getJSONObject(0);
										if (candidate.has("content")) {
											JSONObject content = candidate.getJSONObject("content");
											if (content.has("parts")) {
												JSONArray parts = content.getJSONArray("parts");
												if (parts.length() > 0) {
													JSONObject firstPart = parts.getJSONObject(0);
													if (firstPart.has("text")) {
														generatedText = firstPart.getString("text");
													}
												}
											}
										}
									}
								}

								// NEW LOGIC: Check for unwanted response content.
								if (generatedText.equals(unwantedResponse)) {
									continue; // Skip to next retry
								}

								final String finalGeneratedText = generatedText;
								runOnUiThread(() -> {
									vmap = new HashMap<>();
									vmap.put("who", "she");
                                    String finalText = metabolismChecker(finalGeneratedText);
                                    finalText = fixIndent(finalText);
									vmap.put("she", finalText);
									_x(finalText);
									ableToIn = true;
								});
								success = true;
								break; // Success, exit inner retry loop
							}
						} else if (responseCode == 429) { // Too Many Requests (throttling)
							if (retryAttempt < maxRetries - 1) {
								Thread.sleep(currentModelDelay);
								currentModelDelay *= 2;
							} else {
								finalError = data.getString("noint", "");
								break;
							}
						} else {
							finalError = data.getString("noint", "");
							break;
						}
					} catch (JSONException e) {
						e.printStackTrace();
						finalError = data.getString("nomsg", "");
						break;
					} catch (IOException e) {
						e.printStackTrace();
						if (retryAttempt < maxRetries - 1) {
							try {
								Thread.sleep(currentModelDelay);
								currentModelDelay *= 2;
							} catch (InterruptedException ie) {
								Thread.currentThread().interrupt();
								finalError = data.getString("nomsg", "");
								break;
							}
						} else {
							finalError = data.getString("noint", "");
						}
					} catch (Exception e) {
						e.printStackTrace();
						finalError = data.getString("nomsg", "");
						break;
					}
				}
				if (success) {
					break;
				}
			}

			if (!success) {
				runOnUiThread(() -> {
					vmap = new HashMap<>();
					vmap.put("who", "she");
					vmap.put("she", finalError);
					_x(finalError);
					ableToIn = true;
				});
			}
		});
	}

    private String fixIndent(String primate) {
        String fixed = primate;
        while (fixed.contains("\n\n\n")) {
            fixed = fixed.replace("\n\n\n", "\n\n");
        }
        return fixed;
    }


	public void _voice(final String _speech) {

		ppitch = Double.parseDouble(json.getString("pitch", "1.0")) * 0.02d; // Default to 1.0 if not found
		ssrate = Double.parseDouble(json.getString("srate", "1.0")) * 0.02d; // Default to 1.0 if not found
		if (speaking) {
			if (tts.isSpeaking()) {
				tts.stop();
			}
			tts.setPitch((float)ppitch);
			tts.setSpeechRate((float)ssrate);
			if (speechable) {
				tts.speak(_speech, TextToSpeech.QUEUE_ADD, null);
			}
		}
	}

    // @RequiresApi(api = Build.VERSION_CODES.M)
    private String metabolismChecker(final String _g) {
        Log.e("Metabolism", _g);
        /**
        if (_g.contains("@$light_on$")) {
            // Turn the android's flashlight on
            Log.e("Light", "Turning on light");
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                try {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = cameraManager.getCameraIdList()[0]; // Usually, the rear camera is at index 0
                    cameraManager.setTorchMode(cameraId, true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    // Optional: You could show a toast or log an error if it fails
                }
            }
            return _g.replace("@$light_on$", "").trim();
        }
        if (_g.contains("@$light_off$")) {
            // Turn the android's flashlight off
            Log.e("Light", "Turning off light");
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                try {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    // Optional: Handle the error
                }
            }
            return _g.replace("@$light_off$", "").trim();
        }
        if (_g.contains("@$mobile_data$")) {
            Log.e("Mobile Data", "Turning on mobile data");
            Intent ild1 = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            try {
                startActivity(ild1);
            }
            catch (Exception e) {
                Intent ild2 = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                try {
                    startActivity(ild2);
                } catch (Exception e2) {}
            }
            return _g.replace("@$mobile_data$", "").trim();
        }
        if (_g.contains("@$wifi_on$")) {
            Log.e("Wifi", "Turning on wifi");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {// if build version is less than Q try the old traditional method
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
            } else {// if it is Android Q and above go for the newer way    NOTE: You can also use this code for less than android Q also
                Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                startActivityForResult(panelIntent, 1);
            }
            return _g.replace("@$wifi_on$", "").trim();
        }
        if (_g.contains("@$wifi_off$")) {
            Log.e("Wifi", "Turning off wifi");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {// if build version is less than Q try the old traditional method
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
            } else {// if it is Android Q and above go for the newer way    NOTE: You can also use this code for less than android Q also
                Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                startActivityForResult(panelIntent, 1);
            }
            return _g.replace("@$wifi_off$", "").trim();
        }
        if (_g.contains("@$bluetooth_on$")) {
            Log.e("Bluetooth", "Turning on bluetooth");
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 600);
            startActivity(discoverableIntent);
            return _g.replace("@$bluetooth_on$", "").trim();
        }
        if (_g.contains("@$bluetooth_off$")) {
            Log.e("Bluetooth", "Turning off bluetooth");
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.disable();
            }
            return _g.replace("@$bluetooth_off$", "").trim();
        }
        if (_g.contains("@$location$")) {
            Log.e("Location", "Turning on location");
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return _g.replace("@$location$", "").trim();
        }
         **/
        return _g;
    }


    public void _x(final String _g) {

		if (spcl) {
			CHAT.add(vmap);
			chat.edit().putString(activeModelDataKey, new Gson().toJson(CHAT)).apply(); // Use activeModelDataKey
			listview1.setAdapter(new Listview1Adapter(CHAT));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();

			linear6.setVisibility(View.GONE);
			_voice(_g);
			ableToIn = true;

			spcl = false;
		}
		else {
			t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							t.cancel();
							CHAT.add(vmap);
							chat.edit().putString(activeModelDataKey, new Gson().toJson(CHAT)).apply(); // Use activeModelDataKey
							listview1.setAdapter(new Listview1Adapter(CHAT));
							((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();

							linear6.setVisibility(View.GONE);
							_voice(_g);
							ableToIn = true;
						}
					});
				}
			};
			_timer.schedule(t, (long)(Double.parseDouble(json.getString("delay", "500"))));
		}
	}


	public class Listview1Adapter extends BaseAdapter {

		ArrayList<HashMap<String, Object>> _data;

		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public int getCount() {
			return _data.size();
		}

		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}

		@Override
		public long getItemId(int _index) {
			return _index;
		}

		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.chat_custom, null);
			}

			final LinearLayout linear_she = _view.findViewById(R.id.linear_she);
			final LinearLayout wholesale = _view.findViewById(R.id.wholesale);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final LinearLayout linear_you = _view.findViewById(R.id.linear_you);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final TextView she = _view.findViewById(R.id.she);

			final TextView you = _view.findViewById(R.id.you);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview2 = _view.findViewById(R.id.circleimageview2);

			if (activeModelPicPath.equals("default")) {
				circleimageview1.setImageResource(R.drawable.app_icon);
			} else {
				File imageFile = new File(activeModelPicPath);
				if (imageFile.exists()) {
					Glide.with(getApplicationContext())
							.load(imageFile)
							.placeholder(R.drawable.app_icon)
							.error(R.drawable.app_icon)
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							.into(circleimageview1);

				} else {

					circleimageview1.setImageResource(R.drawable.app_icon);
				}
			}

			// First, retrieve the user's profile picture path from your SharedPreferences
			String userPicPath = chat.getString("pic", "default");

// Now, load the image into the ImageView using a conditional statement
			if (userPicPath.equals("default")) {
				// If the path is "default", load the default resource directly
				Glide.with(getApplicationContext())
						.load(R.drawable.pic_you)
						.apply(new RequestOptions()
								.diskCacheStrategy(DiskCacheStrategy.ALL))
						.into(circleimageview2);
			} else {
				// If there is a file path, load the image from that path
				// The placeholder and error will handle cases where the file is not found
				Glide.with(getApplicationContext())
						.load(new File(userPicPath))
						.apply(new RequestOptions()
								.placeholder(R.drawable.pic_you)
								.error(R.drawable.pic_you)
								.diskCacheStrategy(DiskCacheStrategy.ALL))
						.into(circleimageview2);
			}

			if (_data.get((int)_position).get("who").toString().equals("she")) {
				linear_you.setVisibility(View.GONE);
				linear_she.setVisibility(View.VISIBLE);
				she.setText(_data.get((int)_position).get("she").toString());
			}
			else {
				linear_she.setVisibility(View.GONE);
				linear_you.setVisibility(View.VISIBLE);
				you.setText(_data.get((int)_position).get("you").toString());
			}




			linear_she.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					helper = _position;
					who = "she";
					chat.edit().putString("longClick", "she").apply();
					return false;
				}
			});
			linear_you.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					helper = _position;
					who = "you";
					chat.edit().putString("longClick", "you").apply();
					return false;
				}
			});

			registerForContextMenu(linear_she);
			registerForContextMenu(linear_you);



			return _view;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Choose your option");
		if (chat.getString("longClick", "she").equals("she")) {
			getMenuInflater().inflate(R.menu.floatspin, menu);
		} else 	{
			getMenuInflater().inflate(R.menu.youspin, menu);
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		speechable = false;
		try { if (tts.isSpeaking()) {
			tts.stop();
		} } catch(Exception e) {}
	}

	@Override
	protected void onStop() {
		super.onStop();
		speechable = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		speechable = true;
	}

	private void loadInterstitial(){
		AdRequest adRequest = new AdRequest.Builder().build();
		InterstitialAd.load(
				this,
				getString(R.string.interstitial),
				adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						ChatActivity.this.interstitialAd = interstitialAd;
						//Log.i(TAG, "onAdLoaded");
						//Toast.makeText(SettingsActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
						interstitialAd.setFullScreenContentCallback(
								new FullScreenContentCallback() {
									@Override
									public void onAdDismissedFullScreenContent() {
										// Called when fullscreen content is dismissed.
										// Make sure to set your reference to null so you don't
										// show it a second time.
										ChatActivity.this.interstitialAd = null;
										adDismissed();
										//Log.d("TAG", "The ad was dismissed.");
									}

									@Override
									public void onAdFailedToShowFullScreenContent(AdError adError) {
										// Called when fullscreen content failed to show.
										// Make sure to set your reference to null so you don't
										// show it a second time.
										ChatActivity.this.interstitialAd = null;
										//Log.d("TAG", "The ad failed to show.");
									}

									@Override
									public void onAdShowedFullScreenContent() {
										// Called when fullscreen content is shown.
										//Log.d("TAG", "The ad was shown.");
									}
								});
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						//Log.i(TAG, loadAdError.getMessage());
						interstitialAd = null;
						if (reloadInt) {
							reloadInt = false;
							loadInterstitial();
						}

					}
				});
	}

	public void adDismissed() {
		i.setClass(getApplicationContext(), SettingsActivity.class);
		startActivity(i);
	}

	private void showIntAd() {
		if (interstitialAd != null) {
			interstitialAd.show(ChatActivity.this);
		} else {
			adDismissed();
		}
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {

		clickedConItem(item.getItemId(), chat.getString("longClick", "she"));

		return super.onContextItemSelected(item);
	}

	public static void showSelectableTextDialog(Context context, String title, String largeText) {

		// 1. Create the TextView programmatically
		final TextView hugeSelectableTextView = new TextView(context);
		hugeSelectableTextView.setText(largeText);

		// *** CRITICAL STEP: Make the text selectable for copying ***
		hugeSelectableTextView.setTextIsSelectable(true);

		// 2. Apply styling and make it scrollable
		// Convert 16dp to pixels for padding using the device's density
		final float scale = context.getResources().getDisplayMetrics().density;
		int paddingPx = (int) (16 * scale + 0.5f);

		hugeSelectableTextView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
		hugeSelectableTextView.setTextSize(14); // 14sp text size
		hugeSelectableTextView.setVerticalScrollBarEnabled(true);
		// Setting maxLines is important to ensure the content is scrollable within the dialog box.
		hugeSelectableTextView.setMaxLines(15);

		// 3. Build and Show the Dialog
		new AlertDialog.Builder(context)
				.setTitle(title)
				// Set the custom TextView as the content view of the dialog
				.setView(hugeSelectableTextView)
				.setCancelable(false)
				.setPositiveButton("Close", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setNeutralButton("Copy All", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//copy largetext string to clipboard
						ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
						ClipData clip = ClipData.newPlainText("text", largeText);
						clipboard.setPrimaryClip(clip);
						Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				})
				.setNegativeButton("Copy Selected", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Handle the "Copy Selected" button click
						// if the hugeSelectableTextView is selected, then copy the selected portion to clipboard, or toast then select first

						String selectedText = hugeSelectableTextView.getText().toString();
						int r_a = hugeSelectableTextView.getSelectionStart();
						int r_b = hugeSelectableTextView.getSelectionEnd();
						if (r_a != r_b) {
							selectedText = hugeSelectableTextView.getText().subSequence(r_a, r_b).toString();
							ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
							ClipData clip = ClipData.newPlainText("text", selectedText);
							clipboard.setPrimaryClip(clip);
							Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}else {
							Toast.makeText(context, "No text selected!", Toast.LENGTH_SHORT).show();
						}
					}
				})
				.show();
	}

	private void clickedConItem(int itemId, String who) {
		if (chat.getString("longClick", "she").equals("she")) {
			if (itemId == R.id.option_1) {
				showSelectableTextDialog(this, "Select to copy", CHAT.get(helper).get("she").toString());
			}
			if (itemId == R.id.option_2) {
				_removeMsg(helper);
			}
		} else {
			if (itemId == R.id.option_1) {
				edittext1.setText((CharSequence) CHAT.get(helper).get("you"));
				edittext1.setSelection(((CharSequence) CHAT.get(helper).get("you")).length());
			}
			if (itemId == R.id.option_2) {
				_removeMsg(helper);
			}
		}
	}

	public void loadAd() {
		if (rewardedInterstitialAd == null) {
			RewardedInterstitialAd.load(ChatActivity.this, getString(R.string.rewardedinterstitial),
					new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
						@Override
						public void onAdLoaded(RewardedInterstitialAd ad) {
							ChatActivity.this.rewardedInterstitialAd = ad;
						}

						@Override
						public void onAdFailedToLoad(LoadAdError loadAdError) {
							//Log.d(TAG, loadAdError.toString());
							rewardedInterstitialAd = null;
						}
					});
		}
	}

	private void showAd() {
		if (rewardedInterstitialAd != null) {

			LuminousUtil.showMessage(getApplicationContext(), "Generating response! While you wait ...");

			rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
				@Override
				public void onAdClicked() {
					// Called when a click is recorded for an ad.
					//Log.d("TAG", "Ad was clicked.");
				}

				@Override
				public void onAdDismissedFullScreenContent() {
					// Called when ad is dismissed.
					// Set the ad reference to null so you don't show the ad a second time.
					//Log.d("TAG", "Ad dismissed fullscreen content.");
					rewardedInterstitialAd = null;
					loadAd();
					//adDismissed();
				}

				@Override
				public void onAdFailedToShowFullScreenContent(AdError adError) {
					// Called when ad fails to show.
					//Log.e(TAG, "Ad failed to show fullscreen content.");
					rewardedInterstitialAd = null;
				}

				@Override
				public void onAdImpression() {
					// Called when an impression is recorded for an ad.
					//Log.d(TAG, "Ad recorded an impression.");
				}

				@Override
				public void onAdShowedFullScreenContent() {
					// Called when ad is shown.
					//Log.d(TAG, "Ad showed fullscreen content.");
				}
			});
			Activity activityContext = ChatActivity.this;
			rewardedInterstitialAd.show(activityContext, new OnUserEarnedRewardListener() {
				@Override
				public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
					// _reward(data);
				}
			});

		} else {
			//adDismissed();
		}
	}

	private void _removeMsg(int helper) {
		CHAT.remove(helper);
		chat.edit().putString(activeModelDataKey, new Gson().toJson(CHAT)).apply();

		listview1.setAdapter(new Listview1Adapter(CHAT));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();

		try {

			listview1.smoothScrollToPosition(helper - 1);

		} catch (Exception e) {}

	}

	public void toast(String s) {
		LuminousUtil.showMessage(getApplicationContext(), s);
	}
}

package luminous.organisation.Miya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity {

	private static final org.apache.commons.logging.Log log = LogFactory.getLog(SettingsActivity.class);
	private FloatingActionButton _fab;
	private double ppitch = 0;
	private double ssrate = 0;

	private TextView textview1;

	private TextView textview2;
	private LinearLayout linear1;
	private SharedPreferences data;
	private Boolean dontchange = false;
	private Switch switch1;
	private TextView textview5;
	private LinearLayout linear2;
	private TextView textview8;
	private LinearLayout linear3;
	private MaterialButton materialbutton1;

	private TextView textview11;
	private SeekBar seekbar1;
	private TextView textview3;
	private TextView textview4;
	private TextView textview12;
	private SeekBar seekbar2;
	private TextView textview7;
	private TextView textview13;
	private SeekBar seekbar3;
	private TextView textview10;



	private TextView user_name, user_details, dmdl;

	private Intent i = new Intent();
	private TextToSpeech ttstest;
	private AlertDialog.Builder alertDel;
	private SharedPreferences json; // Used for settings like delay, srate, pitch
	private SharedPreferences chat; // Used for chat history and now models

	private FrameLayout nativeContainer;



	private InterstitialAd interstitialAd;

	private LinearLayout bannerContainer;

	private Boolean speechable = false;
	private de.hdodenhof.circleimageview.CircleImageView user_pic;

	// New variables for model management
	private ListView modelsListView;
	private ArrayList<HashMap<String, Object>> modelsList;
	private ModelListAdapter modelsAdapter;
	private Uri currentSelectedImageUri;
	private static final int PICK_IMAGE_REQUEST = 1;
	private AlertDialog addEditModelDialog; // To hold the dialog instance

	private Boolean reload, reloadInt = true;
	private ImageView user_edit_icon;
	private RewardedAd rewardedAd;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		setContentView(R.layout.settings);

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
		_fab = findViewById(R.id._fab);

		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear1 = findViewById(R.id.linear1);
		switch1 = findViewById(R.id.switch1);
		textview5 = findViewById(R.id.textview5);
		linear2 = findViewById(R.id.linear2);
		textview8 = findViewById(R.id.textview8);
		linear3 = findViewById(R.id.linear3);
		materialbutton1 = findViewById(R.id.materialbutton1);
		textview11 = findViewById(R.id.textview11);
		seekbar1 = findViewById(R.id.seekbar1);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		textview12 = findViewById(R.id.textview12);
		seekbar2 = findViewById(R.id.seekbar2);
		textview7 = findViewById(R.id.textview7);
		textview13 = findViewById(R.id.textview13);
		seekbar3 = findViewById(R.id.seekbar3);
		textview10 = findViewById(R.id.textview10);
		bannerContainer = findViewById(R.id.bannerContainer);
		dmdl = findViewById(R.id.dmdl);

		user_name = findViewById(R.id.user_name);
		user_details = findViewById(R.id.user_details);
		user_pic = findViewById(R.id.user_pic);
		user_edit_icon = findViewById(R.id.user_edit_icon);

		ttstest = new TextToSpeech(getApplicationContext(), null);
		alertDel = new AlertDialog.Builder(this);
		json = getSharedPreferences("json", Activity.MODE_PRIVATE);
		chat = getSharedPreferences("chat", Activity.MODE_PRIVATE);
		nativeContainer = findViewById(R.id.nativeContainer);

		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		modelsListView = findViewById(R.id.modelsListView); // Initialize the new ListView


		switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					json.edit().putString("sp", "1").apply();
				}
				else {
					json.edit().putString("sp", "0").apply();
				}

			}
		});

		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

				ppitch = Double.parseDouble(json.getString("pitch", "1.0")) * 0.02d; // Default to 1.0 if not found
				ssrate = Double.parseDouble(json.getString("srate", "1.0")) * 0.02d; // Default to 1.0 if not found

				if (ttstest.isSpeaking()) {
					ttstest.stop();
				}
				ttstest.setPitch((float)ppitch);
				ttstest.setSpeechRate((float)ssrate);
				if (speechable) {
					ttstest.speak(getString(R.string.tts_sample), TextToSpeech.QUEUE_ADD, null, "TTS_SAMPLE");
				}
			}
		});

		textview11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				seekbar1.setProgress(2);
			}
		});

		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;

				json.edit().putString("delay", String.valueOf((long)((_progressValue * 1000) + 50))).apply();
				textview3.setText(String.valueOf((long)(_progressValue)));

			}

			@Override
			public void onStartTrackingTouch(SeekBar _param1) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar _param2) {

			}
		});

		textview12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				seekbar2.setProgress(50);
			}
		});

		seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;

				json.edit().putString("srate", String.valueOf((long)(_progressValue))).apply();
				textview7.setText(String.valueOf((long)(_progressValue)));

			}

			@Override
			public void onStartTrackingTouch(SeekBar _param1) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar _param2) {

			}
		});

		textview13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				seekbar3.setProgress(50);
			}
		});

		seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;

				json.edit().putString("pitch", String.valueOf((long)(_progressValue))).apply();
				textview10.setText(String.valueOf((long)(_progressValue)));

			}

			@Override
			public void onStartTrackingTouch(SeekBar _param1) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar _param2) {

			}
		});

		// FAB listener for adding new models
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getString(R.string.adstat).equals("org")) {
					_reward("add");
				} else {
					showVideoAd("add");
				}
			}
		});

		dmdl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getString(R.string.adstat).equals("org")) {
					_reward("download");
				} else {
					showVideoAd("download");
				}
			}
		});

	}

	private void initializeLogic() {

        Log.e("JSON", chat.getString("models", "[]"));

		loadProfile();

		// Do not set h1, h2, h3 visibility to GONE, they are visible in XML now.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			seekbar2.setMin(1);
			seekbar3.setMin(1);
		}

		if (json.getString("sp", "").equals("1")) {
			switch1.setChecked(true);
		}
		else {
			switch1.setChecked(false);
		}
		// Provide default values for parsing, just in case they are not set
		seekbar1.setProgress((int)(Double.parseDouble(json.getString("delay", "2050")) - 50) / 1000);
		seekbar2.setProgress((int)Double.parseDouble(json.getString("srate", "50")));
		seekbar3.setProgress((int)Double.parseDouble(json.getString("pitch", "50")));
		textview3.setText(String.valueOf((long)((Double.parseDouble(json.getString("delay", "2050")) - 50) / 1000)));
		textview7.setText(String.valueOf((long)(Double.parseDouble(json.getString("srate", "50")))));
		textview10.setText(String.valueOf((long)(Double.parseDouble(json.getString("pitch", "50")))));

		dontchange = true;

		bannerAd(bannerContainer);
		loadInterstitial();
		loadRewardedAd();

		// Initialize models list
		String modelsJson = chat.getString("models", "[]");
		modelsList = new Gson().fromJson(modelsJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

		// Sort models by spec to ensure consistent order
		Collections.sort(modelsList, new Comparator<HashMap<String, Object>>() {
			@Override
			public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
				try {
					int spec1 = Integer.parseInt(o1.get("spec").toString());
					int spec2 = Integer.parseInt(o2.get("spec").toString());
					return Integer.compare(spec1, spec2);
				} catch (NumberFormatException e) {
					// Handle "default" spec string or other non-numeric specs gracefully
					String spec1Str = o1.get("spec").toString();
					String spec2Str = o2.get("spec").toString();
					if (spec1Str.equals("default")) return -1; // Default comes first
					if (spec2Str.equals("default")) return 1;
					return spec1Str.compareTo(spec2Str); // Fallback to string comparison
				}
			}
		});


		modelsAdapter = new ModelListAdapter(modelsList);
		modelsListView.setAdapter(modelsAdapter);
	}

	// This method takes the JSON string retrieved from the web and displays a dialog.
	// When a user selects a model, its data is saved to SharedPreferences.
	public void showModelSelectionDialog(final String jsonString) {
		try {
			// Parse the JSON array from the response string
			JSONArray jsonArray = new JSONArray(jsonString);

			final ArrayList<HashMap<String, String>> modelsList = new ArrayList<>();

			// Iterate through the JSON array to parse each model object
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				// Create a HashMap to store the model's details
				HashMap<String, String> modelMap = new HashMap<>();

				String name = jsonObject.optString("name", "N/A");
				String description = jsonObject.optString("description", "No description available.");
				String imageUrl = jsonObject.optString("image", "");

				modelMap.put("name", name);
				modelMap.put("description", description);
				modelMap.put("image", imageUrl); // Store the original URL

				// Determine 'data' and 'spec' values based on your logic
				// Assuming 'data' could be "chat" for all or based on model name
				// Assuming 'spec' could be empty or specific if the model has special capabilities
				int m = _getNextAvailableSpec();
				modelMap.put("spec", m + ""); // No specific 'spec' provided in JSON, so leave empty or derive

				modelMap.put("data", "chat"+m+"");

				modelsList.add(modelMap);
			}

			// Create the AlertDialog Builder and ListView
			AlertDialog.Builder builder = new AlertDialog.Builder(this); // 'this' assumes it's called from an Activity
			builder.setTitle("Select an AI Model to download");

			final ListView listView = new ListView(this);
			// Use your custom ModelAdapter here
			ModelAdapter adapter = new ModelAdapter(this, modelsList);
			listView.setAdapter(adapter);

			builder.setView(listView);

			// Create and show the dialog
			final AlertDialog dialog = builder.create();
			dialog.show();

			// Set the click listener for the ListView
			listView.setOnItemClickListener((parent, view, position, id) -> {
				dialog.dismiss(); // Dismiss the dialog immediately on click

				// Get the selected model's data
				HashMap<String, String> selectedModel = modelsList.get(position);
				String modelName = selectedModel.get("name");
				String modelDescription = selectedModel.get("description");
				String originalImageUrl = selectedModel.get("image");
				String dataValue = selectedModel.get("data");
				String specValue = selectedModel.get("spec");

				// Start image download in background
				new DownloadImageTask(this, modelName, modelDescription, originalImageUrl, dataValue, specValue, chat).execute(originalImageUrl);
			});

		} catch (Exception e) {
			e.printStackTrace();
			// Handle JSON parsing errors or other exceptions here
			// For example, show a Toast to the user
			// Toast.makeText(this, "Error parsing model data.", Toast.LENGTH_SHORT).show();
		}
	}

	// AsyncTask to download the image in the background
	private static class DownloadImageTask extends AsyncTask<String, Void, String> {
		private Context context;
		private String modelName;
		private String modelDescription;
		private String originalImageUrl;
		private String dataValue;
		private String specValue;
		private SharedPreferences sharedPreferences;

		public DownloadImageTask(Context context, String modelName, String modelDescription, String originalImageUrl, String dataValue, String specValue, SharedPreferences sharedPreferences) {
			this.context = context;
			this.modelName = modelName;
			this.modelDescription = modelDescription;
			this.originalImageUrl = originalImageUrl;
			this.dataValue = dataValue;
			this.specValue = specValue;
			this.sharedPreferences = sharedPreferences;
		}

		@Override
		protected String doInBackground(String... urls) {
			String imageUrl = urls[0];
			if (imageUrl == null || imageUrl.isEmpty()) {
				return "default"; // Return "default" if no valid URL
			}

			String fileName = "model_image_" + System.currentTimeMillis() + ".png"; // Unique file name
			File file = new File(context.getFilesDir(), fileName); // Save to app's internal files directory
			String localImagePath = file.getAbsolutePath();

			try {
				URL url = new URL(imageUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(input);

				FileOutputStream fos = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // Compress to PNG
				fos.flush();
				fos.close();
				input.close();

				return localImagePath; // Return local path on success
			} catch (Exception e) {
				e.printStackTrace();
				return "default"; // Return "default" on failure
			}
		}

		@Override
		protected void onPostExecute(String localImagePath) {
			// Prepare the JSON string for SharedPreferences
			sharedPreferences.edit().putString("volatile_pic", localImagePath).apply();
			sharedPreferences.edit().putString("volatile_name", modelName).apply();
			sharedPreferences.edit().putString("volatile_details", modelDescription).apply();
			((SettingsActivity) context)._showAddEditModelDialog(false, -2);

			/**
			try {
				JSONObject modelJson = new JSONObject();
				//TODO: set data and spec, finished at root level
				modelJson.put("data", dataValue);
				modelJson.put("name", modelName);
				modelJson.put("pic", localImagePath); // Use the local path
				modelJson.put("spec", specValue);
				modelJson.put("details", modelDescription); // Description as 'details'

				JSONArray modelsArray = new JSONArray();
				modelsArray.put(modelJson);

				// Save the JSON string to SharedPreferences
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("models", modelsArray.toString());
				editor.apply();
				//TODO: preserve previous json

				// Optionally, inform the user
				// Toast.makeText(context, "Model '" + modelName + "' selected and saved!", Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				e.printStackTrace();
				// Toast.makeText(context, "Error saving model data.", Toast.LENGTH_SHORT).show();
			}
			**/
		}
	}

	private void loadProfile() {
		// Set model name and details
		user_name.setText(chat.getString("name", "Leo"));
		user_details.setText(chat.getString("details", ""));

		// Load model picture
		String picPath = chat.getString("pic", "default");
		if (picPath.equals("default")) {
			user_pic.setImageResource(R.drawable.pic_you); // Default Miya picture
		} else if (!picPath.isEmpty()) {
			Glide.with(getApplicationContext())
					.load(new File(picPath))
					.apply(new RequestOptions()
							.placeholder(R.drawable.pic_you) // Placeholder while loading
							.error(R.drawable.pic_you) // Error placeholder
							.diskCacheStrategy(DiskCacheStrategy.ALL))
					.into(user_pic);
		} else {
			user_pic.setImageResource(R.drawable.pic_you); // Fallback to default
		}

		user_edit_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_showAddEditModelDialogForUser();
			}
		});
	}

	private void _showAddEditModelDialogForUser() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_add_edit_model, null);
		dialogBuilder.setView(dialogView);

		final EditText editName = dialogView.findViewById(R.id.edit_model_name);
		final EditText editDetails = dialogView.findViewById(R.id.edit_model_details);
		final CircleImageView modelPicPreview = dialogView.findViewById(R.id.model_pic_preview);
		final TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);

		currentSelectedImageUri = null; // Reset URI for new dialog instance

		// Populate dialog with current user data
		dialogTitle.setText("Edit Your Profile");
		editName.setText(chat.getString("name", "Leo"));
		editDetails.setText(chat.getString("details", ""));
		String picPath = chat.getString("pic", "default");

		// Load profile picture using Glide
		if (picPath.equals("default")) {
			modelPicPreview.setImageResource(R.drawable.pic_you);
		} else {
			File imageFile = new File(picPath);
			if (imageFile.exists()) {
				Glide.with(this).load(imageFile).into(modelPicPreview);
				currentSelectedImageUri = Uri.fromFile(imageFile);
			} else {
				// Fallback if the file no longer exists
				modelPicPreview.setImageResource(R.drawable.pic_you);
			}
		}

		// Listener for selecting a new picture
		modelPicPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, PICK_IMAGE_REQUEST);
			}
		});

		dialogBuilder.setPositiveButton("Save", (dialog, which) -> {}); // Overridden later
		dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

		addEditModelDialog = dialogBuilder.create();
		addEditModelDialog.show();

		// Override the positive button to handle validation and saving
		addEditModelDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
			String name = editName.getText().toString().trim();
			String details = editDetails.getText().toString().trim();

			if (name.isEmpty() || details.isEmpty()) {
				toast("Name and details cannot be empty!");
				return;
			}

			if (name.length() > 14) {
				toast("Name cannot be more than " + 14 + " characters!");
				return;
			}

			String newPicPath;
			if (currentSelectedImageUri != null) {
				// Use your existing method to copy the new image to internal storage
				newPicPath = _copyImageToInternalStorageAndGetPath(currentSelectedImageUri);
				if (newPicPath == null) {
					toast("Failed to save image. Please try again.");
					return;
				}
			} else {
				// If no new image selected, keep the existing path
				newPicPath = chat.getString("pic", "default");
			}

			// Save the updated user profile directly to SharedPreferences
			SharedPreferences.Editor editor = chat.edit();
			editor.putString("name", name);
			editor.putString("details", details);
			editor.putString("pic", newPicPath);
			editor.apply();

			toast("Profile updated successfully!");
			addEditModelDialog.dismiss();
			loadProfile();
		});
	}


	@Override
	public void onBackPressed() {
		showIntAd();
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

				final Set<String> ALLOWED_COUNTRIES = new HashSet<>(Arrays.asList(
						"United States", "United Kingdom", "Canada", "France", "Germany",
						"Japan", "South Korea", "Norway", "Switzerland", "Sweden",
						"Netherlands", "Australia"//, "Bangladesh"
				));

				try {
					JSONObject jsonResponse = new JSONObject(response);
					String countryName = jsonResponse.getString("country");

					if (ALLOWED_COUNTRIES.contains(countryName)) {

					} else {
						failed(countryName);
					}

				} catch (JSONException e) {
					failed("your location");
				}
			}
		}.execute();
	}

	private void failed(String country) {
		// Implement dialog
		new AlertDialog.Builder(SettingsActivity.this)
				.setTitle("Notice")
				.setIcon(R.drawable.app_icon)
				.setCancelable(false)
				.setMessage("Sorry, Luminous AI is unavailable in "+country+". Luminous AI is available in United States, United Kingdom, Canada, Australia, Germany, Japan, South Korea, France, Norway, Switzerland, Sweden and Netherlands.")
				.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Exit the app when "GOT IT" is clicked
						finishAffinity();
					}
				})
				.show();
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
						SettingsActivity.this.interstitialAd = interstitialAd;
						//Log.i(TAG, "onAdLoaded");
						//Toast.makeText(SettingsActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
						interstitialAd.setFullScreenContentCallback(
								new FullScreenContentCallback() {
									@Override
									public void onAdDismissedFullScreenContent() {
										// Called when fullscreen content is dismissed.
										// Make sure to set your reference to null so you don't
										// show it a second time.
										SettingsActivity.this.interstitialAd = null;
										adDismissed();
										//Log.d("TAG", "The ad was dismissed.");
									}

									@Override
									public void onAdFailedToShowFullScreenContent(AdError adError) {
										// Called when fullscreen content failed to show.
										// Make sure to set your reference to null so you don't
										// show it a second time.
										SettingsActivity.this.interstitialAd = null;
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
		toChat();
	}

	private void showIntAd() {
		if (interstitialAd != null) {
			interstitialAd.show(SettingsActivity.this);
		} else {
			adDismissed();
		}
	}

	private void toChat() {
		startActivity(new Intent(getApplicationContext(), ChatActivity.class));
	}

	@Override
	protected void onPause() {
		super.onPause();
		speechable = false;
		try { if (ttstest.isSpeaking()) {
			ttstest.stop();
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

	// Helper function to show Toast messages
	public void toast(String s) {
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}

	// Custom Adapter for displaying AI models in ListView
	public class ModelListAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;

		public ModelListAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_view = _inflater.inflate(R.layout.list_item_model, null);
			}

			final CircleImageView modelPic = _view.findViewById(R.id.model_pic);
			final TextView modelName = _view.findViewById(R.id.model_name);
			final TextView modelDetails = _view.findViewById(R.id.model_details);
			final ImageView selectedIndicator = _view.findViewById(R.id.model_selected_indicator);
			final ImageView editIcon = _view.findViewById(R.id.model_edit_icon);

			HashMap<String, Object> model = _data.get(_position);

			// Set model name and details
			modelName.setText(model.containsKey("name") ? model.get("name").toString() : "Cleo");
			modelDetails.setText(model.containsKey("details") ? model.get("details").toString() : "");

			// Load model picture
			String picPath = model.containsKey("pic") ? model.get("pic").toString() : "";
			if (picPath.equals("default")) {
				modelPic.setImageResource(R.drawable.app_icon); // Default Miya picture
			} else if (!picPath.isEmpty()) {
				Glide.with(getApplicationContext())
						.load(new File(picPath))
						.apply(new RequestOptions()
								.placeholder(R.drawable.app_icon) // Placeholder while loading
								.error(R.drawable.app_icon) // Error placeholder
								.diskCacheStrategy(DiskCacheStrategy.ALL))
						.into(modelPic);
			} else {
				modelPic.setImageResource(R.drawable.app_icon); // Fallback to default
			}

			// Show/hide selected indicator
			String currentSpec = chat.getString("current", "");
			if (model.containsKey("spec") && model.get("spec").toString().equals(currentSpec)) {
				selectedIndicator.setVisibility(View.VISIBLE);
			} else {
				selectedIndicator.setVisibility(View.GONE);
			}
			if (_position == 0) {
				editIcon.setVisibility(View.GONE);
				modelDetails.setVisibility(View.GONE);
			} else {
				editIcon.setVisibility(View.VISIBLE);
				modelDetails.setVisibility(View.VISIBLE);
			}

			// Handle item click to select model
			_view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _v) {
					String specToSelect = model.containsKey("spec") ? model.get("spec").toString() : "";
					chat.edit().putString("current", specToSelect).apply();
					notifyDataSetChanged(); // Refresh list to show new selection
					toast("Selected model: " + model.get("name").toString());
                    //_changeAppNameAndIcon();
				}
			});

			// Handle edit icon click
			editIcon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _v) {
					_showAddEditModelDialog(true, _position);
				}
			});

			// Handle long click to delete model (only for non-default models)
			_view.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _v) {
					if (model.containsKey("spec") && model.get("spec").toString().equals("")) {
						toast("Cannot delete default Cleo model.");
						return true;
					}

					AlertDialog.Builder deleteAlert = new AlertDialog.Builder(SettingsActivity.this);
					deleteAlert.setTitle("Delete Model");
					deleteAlert.setMessage("Are you sure you want to delete '" + model.get("name").toString() + "'?");
					deleteAlert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// If the deleted model was the current one, switch to default Miya
							if (model.containsKey("spec") && model.get("spec").toString().equals(chat.getString("current", ""))) {
								chat.edit().putString("current", "").apply();
							}
							modelsList.remove(_position);
							_saveModels();
							notifyDataSetChanged();
							toast("Model deleted.");
						}
					});
					deleteAlert.setNegativeButton("Cancel", null);
					deleteAlert.create().show();
					return true; // Consume long click
				}
			});

			return _view;
		}
	}

	// Method to show Add/Edit Model dialog
	private void _showAddEditModelDialog(final boolean isEditMode, final int position) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_add_edit_model, null); // You need to create this XML layout
		dialogBuilder.setView(dialogView);

		final EditText editName = dialogView.findViewById(R.id.edit_model_name);
		final EditText editDetails = dialogView.findViewById(R.id.edit_model_details);
		final CircleImageView modelPicPreview = dialogView.findViewById(R.id.model_pic_preview);
		final TextView dialogTitle = dialogView.findViewById(R.id.dialog_title); // Assuming you add this TextView in your dialog XML

		currentSelectedImageUri = null; // Reset for new dialog

		if (isEditMode) {
			dialogTitle.setText("Edit AI Model");
			HashMap<String, Object> modelToEdit = modelsList.get(position);
			editName.setText(modelToEdit.get("name").toString());
			editDetails.setText(modelToEdit.get("details").toString());
			String picPath = modelToEdit.get("pic").toString();
			if (picPath.equals("default")) {
				modelPicPreview.setImageResource(R.drawable.app_icon);
			} else if (!picPath.isEmpty()) {
				File imageFile = new File(picPath);
				if (imageFile.exists()) {


					Glide.with(this).load(imageFile).into(modelPicPreview);
					currentSelectedImageUri = Uri.fromFile(imageFile); // Set current URI for re-saving if not changed
				} else {
					modelPicPreview.setImageResource(R.drawable.app_icon);
				}
			} else {
				modelPicPreview.setImageResource(R.drawable.app_icon);
			}
		} else {
			if (position == -2) {
				dialogTitle.setText("Edit Downloaded AI Model");
				editName.setText(chat.getString("volatile_name", "Cleo"));
				editDetails.setText(chat.getString("volatile_details", ""));
				String picPath = chat.getString("volatile_pic", "default");
				if (picPath.equals("default")) {
					modelPicPreview.setImageResource(R.drawable.app_icon);
				} else if (!picPath.isEmpty()) {
					File imageFile = new File(picPath);
					if (imageFile.exists()) {


						Glide.with(this).load(imageFile).into(modelPicPreview);
						currentSelectedImageUri = Uri.fromFile(imageFile); // Set current URI for re-saving if not changed
					} else {
						modelPicPreview.setImageResource(R.drawable.app_icon);
					}
				} else {
					modelPicPreview.setImageResource(R.drawable.app_icon);
				}
			} else {
				dialogTitle.setText("Add New AI Model");
				modelPicPreview.setImageResource(R.drawable.app_icon); // Default for new
			}
		}

		// Listener for selecting picture
		modelPicPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, PICK_IMAGE_REQUEST);
			}
		});

		dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// This listener will be overridden to prevent auto-dismissal
			}
		});
		dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Dialog dismisses normally
			}
		});

		addEditModelDialog = dialogBuilder.create();
		addEditModelDialog.show();

		// Override the positive button to handle validation and prevent dialog dismissal
		addEditModelDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String name = editName.getText().toString().trim();
				String details = editDetails.getText().toString().trim();

				if (name.isEmpty() || details.isEmpty()) {
					toast("Name and details cannot be empty!");
					return;
				}

				if (!isEditMode && modelsList.size() >= 6) {
					if (getString(R.string.adstat).equals("org")) {

					} else {
						toast("You can add a maximum of 6 custom AI models.");
						return;
					}

				}

				String picPath = "";
				if (position != -2) {
					if (currentSelectedImageUri != null) {
						picPath = _copyImageToInternalStorageAndGetPath(currentSelectedImageUri);
						if (picPath == null) {
							toast("Failed to save image. Please try again.");
							return;
						}
					} else {
						// If no new image selected in edit mode, keep old one. If adding, use default string.
						if (isEditMode && modelsList.get(position).containsKey("pic")) {
							picPath = modelsList.get(position).get("pic").toString();
						} else {
							picPath = "default"; // For new models if no image selected
						}
					}
				} else {
					picPath = chat.getString("volatile_pic", "default");
				}

				if (name.length() > 16) {
					toast("Name cannot be more than 16 characters!");
					return;
				}

				HashMap<String, Object> newModel = new HashMap<>();
				newModel.put("name", name);
				newModel.put("details", details);
				newModel.put("pic", picPath);

				if (isEditMode) {
					// Preserve the original 'data' and 'spec' keys during edit
					HashMap<String, Object> originalModel = modelsList.get(position);
					newModel.put("data", originalModel.get("data").toString());
					newModel.put("spec", originalModel.get("spec").toString());
					modelsList.set(position, newModel);
					toast("Model updated!");
				} else {
					// Generate unique spec and data for new model
					int nextSpec = _getNextAvailableSpec();
					if (nextSpec == -1) {
						toast("Failed to assign unique spec. Please try again.");
						return;
					}
					newModel.put("spec", String.valueOf(nextSpec));
					newModel.put("data", "chat_model_" + nextSpec);
                    chat.edit().putString("chat_model_"+ String.valueOf(nextSpec), "[]").apply();
					modelsList.add(newModel);
					toast("Model added!");
				}

				_saveModels();
				modelsAdapter.notifyDataSetChanged();
				addEditModelDialog.dismiss(); // Dismiss only after successful save
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			currentSelectedImageUri = data.getData();
			if (addEditModelDialog != null && addEditModelDialog.isShowing()) {
				CircleImageView modelPicPreview = addEditModelDialog.findViewById(R.id.model_pic_preview);
				if (modelPicPreview != null) {
					Glide.with(this).load(currentSelectedImageUri).into(modelPicPreview);
				}
			}
		}
	}

	// Helper to copy image to internal storage and return its path
	private String _copyImageToInternalStorageAndGetPath(Uri sourceUri) {
		File destinationFile = null;
		try {
			// Create a directory for model images if it doesn't exist
			File modelsDir = new File(getFilesDir(), "model_pics");
			if (!modelsDir.exists()) {
				modelsDir.mkdirs();
			}

			String timeStamp = String.valueOf(System.currentTimeMillis());
			String fileName = "model_pic_" + timeStamp + ".jpg";
			destinationFile = new File(modelsDir, fileName);

			try (InputStream inputStream = getContentResolver().openInputStream(sourceUri);
				 OutputStream outputStream = new FileOutputStream(destinationFile)) {
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
			}
			return destinationFile.getAbsolutePath();
		} catch (IOException e) {
			Log.e("SettingsActivity", "Error copying image: " + e.getMessage());
			return null;
		}
	}

	// Helper to get the next available spec number (1-4, as 0 is for default Miya)
	private int _getNextAvailableSpec() {
		Set<Integer> usedSpecs = new HashSet<>();
		for (HashMap<String, Object> model : modelsList) {
			if (model.containsKey("spec")) {
				try {
					int spec = Integer.parseInt(model.get("spec").toString());
					if (spec != 0) { // Don't count default Miya's spec (0)
						usedSpecs.add(spec);
					}
				} catch (NumberFormatException e) {
					// Ignore non-numeric specs like "default"
				}
			}
		}

		for (int i = 1; i <= 99; i++) { // Max 100 models total, one is default Miya (spec 0), so 99 custom specs (1-4)
			if (!usedSpecs.contains(i)) {
				return i;
			}
		}
		return -1; // No available spec (max limit reached)
	}

	//

	// Helper to save modelsList to SharedPreferences
	private void _saveModels() {
		String jsonString = new Gson().toJson(modelsList);
		chat.edit().putString("models", jsonString).apply();

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

	private void loadRewardedAd() {
		if (rewardedAd == null) {
			//isLoading = true;
			AdRequest adRequest = new AdRequest.Builder().build();
			RewardedAd.load(
					this,
					getString(R.string.rewarded),
					adRequest,
					new RewardedAdLoadCallback() {
						@Override
						public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
							// Handle the error.
							//Log.d(TAG, loadAdError.getMessage());
							rewardedAd = null;
							//NtfActivity.this.isLoading = false;

						}

						@Override
						public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
							SettingsActivity.this.rewardedAd = rewardedAd;
							//Log.d(TAG, "onAdLoaded");
							//NtfActivity.this.isLoading = false;

						}
					});
		}
	}

	private void showVideoAd(String purpose) {
		if (rewardedAd == null) {
			LuminousUtil.showMessage(getApplicationContext(), "You have to watch a video ad to "+purpose+" an AI model. Wait for the ad to be loaded.");
			return;
		}


		rewardedAd.setFullScreenContentCallback(
				new FullScreenContentCallback() {
					@Override
					public void onAdShowedFullScreenContent() {
						// Called when ad is shown.
						//Log.d(TAG, "onAdShowedFullScreenContent");
						//LuminousUtil.showMessage(getApplicationContext(), "Generating response! While you wait ...");
					}

					@Override
					public void onAdFailedToShowFullScreenContent(AdError adError) {
						// Called when ad fails to show.
						//Log.d(TAG, "onAdFailedToShowFullScreenContent");
						// Don't forget to set the ad reference to null so you
						// don't show the ad a second time.
						rewardedAd = null;

					}

					@Override
					public void onAdDismissedFullScreenContent() {
						// Called when ad is dismissed.
						// Don't forget to set the ad reference to null so you
						// don't show the ad a second time.
						rewardedAd = null;
						loadRewardedAd();
						//LuminousUtil.showMessage(getApplicationContext(), "You didn't finish watching the ad.");
					}
				});
		Activity activityContext = SettingsActivity.this;
		rewardedAd.show(
				activityContext,
				new OnUserEarnedRewardListener() {
					@Override
					public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
						_reward(purpose);
					}
				});
	}

	public void _reward(String purpose) {
		switch (purpose) {
			case "add":
				_showAddEditModelDialog(false, -1); // Show dialog to add new model
				break;
			case "download":
				//LuminousUtil.showMessage(getApplicationContext(), "Loading AI Model Library");
				// API URL to fetch location based on IP address
				String url = "https:luminousorg.web.app/app/ai-models";

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
						showModelSelectionDialog(response);
					}
				}.execute();

				break;
			default:
				break;
		}
	}

}

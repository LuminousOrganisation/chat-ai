package luminous.organisation.Miya;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
	
	public final int REQ_CD_GL = 101;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private SharedPreferences json;
	
	private String uid = "";
	private String LS1 = "";
	private String LS2 = "";
	private String email = "";
	private boolean nd = false;
	private double a = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private boolean mnd = false;
	private double ma = 0;
	private String idt = "";
	private  FirebaseUser users;
	private  GoogleSignInOptions go;
	private HashMap<String, Object> gu = new HashMap<>();
	private boolean dobreak = false;
	private String featureuid = "";
	private boolean oldexists = false;
	private boolean startRefresher = false;
	private String ex1 = "";
	private String ex2 = "";
	private String ex3 = "";
	private String ex4 = "";
	private String ex5 = "";
	private String ex6 = "";
	private String ex7 = "";
	
	private ArrayList<HashMap<String, Object>> ur = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> fornewuid = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> JSON = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> REGEX = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> COMMAND = new ArrayList<>();
	
	private LinearLayout linear3;
	private TextView textview1;
	private LinearLayout linear1, halloween;
	private SignInButton signinbutton1;
	private CheckBox checkbox1;
	private TextView textview2;
	
	private SharedPreferences data;
	private Intent i = new Intent();
	private FirebaseAuth Auth;
	private OnCompleteListener<AuthResult> _Auth_create_user_listener;
	private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
	private OnCompleteListener<Void> _Auth_reset_password_listener;
	private OnCompleteListener<Void> Auth_updateEmailListener;
	private OnCompleteListener<Void> Auth_updatePasswordListener;
	private OnCompleteListener<Void> Auth_emailVerificationSentListener;
	private OnCompleteListener<Void> Auth_deleteUserListener;
	private OnCompleteListener<Void> Auth_updateProfileListener;
	private OnCompleteListener<AuthResult> Auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> Auth_googleSignInListener;
	
	private GoogleSignInClient gl;
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	private ProgressDialog pd;
	private TimerTask memory;
	private DatabaseReference vaultnew = _firebase.getReference("newlog");
	private ChildEventListener _vaultnew_child_listener;
	private Intent ic = new Intent();
	private SharedPreferences v2;
	private ProgressDialog newpd;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		setContentView(R.layout.login);
		initialize(_savedInstanceState);

		FirebaseApp.initializeApp(this);
		//MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		halloween = findViewById(R.id.halloween);
		linear3 = findViewById(R.id.linear3);
		textview1 = findViewById(R.id.textview1);
		linear1 = findViewById(R.id.linear1);
		signinbutton1 = findViewById(R.id.signinbutton1);
		checkbox1 = findViewById(R.id.checkbox1);
		textview2 = findViewById(R.id.textview2);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		Auth = FirebaseAuth.getInstance();
		json = getSharedPreferences("json", Activity.MODE_PRIVATE);
		v2 = getSharedPreferences("v2", Activity.MODE_PRIVATE);

		
		signinbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final int cx = signinbutton1.getMeasuredWidth() / 2; final int cy = signinbutton1.getMeasuredHeight() / 2;  final int finalRadius = Math.max(signinbutton1.getWidth(), signinbutton1.getHeight()) / 2;
				final Animator anim =  ViewAnimationUtils.createCircularReveal(signinbutton1, cx, cy, 0, finalRadius);  signinbutton1.setVisibility(View.VISIBLE);
				anim.start();
				
				if (checkbox1.isChecked()) {
					Intent signInIntent = gl.getSignInIntent();
					
					startActivityForResult(signInIntent, REQ_CD_GL);
					pd = new ProgressDialog(LoginActivity.this);
					pd.setTitle("Wait …");
					pd.setMessage("Requires Internet Connection");
					pd.setCancelable(false);
					pd.show();
				}
				else {
					toast("You have to agree with our Privacy Policy");
				}
			}
		});
		
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ic.setAction(Intent.ACTION_VIEW);
				ic.setData(Uri.parse("https://orgluminous.blogspot.com/p/miya-assistant-privacy-policy.html"));
				startActivity(ic);
			}
		});
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				/*final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);*/
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		user.addChildEventListener(_user_child_listener);
		
		_vaultnew_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		vaultnew.addChildEventListener(_vaultnew_child_listener);
		
		Auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		Auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_Auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		uid = "";
		idt = "469683893502-i1su4jkr2anuntgfreiojjojp1naoo24.apps.googleusercontent.com";
		GoogleSignInOptions go = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(idt).requestId().requestEmail().build();
		gl = GoogleSignIn.getClient(this, go);
		Auth = FirebaseAuth.getInstance();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_GL:
			if (_resultCode == Activity.RESULT_OK) {
				Task<GoogleSignInAccount> _task = GoogleSignIn.getSignedInAccountFromIntent(_data);
				
				try {
						// Google Sign In was successful, authenticate with Firebase
						GoogleSignInAccount account = _task.getResult(ApiException.class);
						
						firebaseAuthWithGoogle(account.getIdToken());
						
						
				} catch (ApiException e) {
						//On Fiailure
						final String ErrorOnResultSign = e.getMessage();
						toast(ErrorOnResultSign);
				}
				
			}
			else {
				try {
						pd.dismiss();
				} catch (Exception ei) {
						if (Looper.myLooper() == null) { 
								Looper.prepare();
						}
				}
				
				try {
						newpd.dismiss();
				} catch (Exception eio) {
						if (Looper.myLooper() == null) { 
								Looper.prepare();
						}
				}
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (startRefresher) {

		}
		else {
			startRefresher = true;
			users = Auth.getCurrentUser();
			if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
				if (v2.getString("v2", "").equals("ok")) {
					data.edit().putString("name", users.getDisplayName()).commit();
					data.edit().putString("image", users.getPhotoUrl().toString()).commit();
					data.edit().putString("email", users.getEmail()).commit();
					data.edit().putString("uid", users.getUid()).commit();
					_onsuccess();
					finish();
				}
				else {
					newpd = new ProgressDialog(LoginActivity.this);
					newpd.setTitle("Wait …");
					newpd.setMessage("Requires Internet Connection");
					newpd.setCancelable(false);
					newpd.show();
					//search NEWUID,
					//if gets, break
					//else
					featureuid = users.getUid();
					DatabaseReference ref1 = _firebase.getReference("user").child(featureuid);
					try {
						ref1.addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot _dataSnapshot) {
								try {
									ex1 = _dataSnapshot.child("uid").getValue().toString();
									//break, completed
									v2.edit().putString("v2", "ok").commit();
									data.edit().putString("name", users.getDisplayName()).commit();
									data.edit().putString("image", users.getPhotoUrl().toString()).commit();
									data.edit().putString("email", users.getEmail()).commit();
									data.edit().putString("uid", users.getUid()).commit();
									_updateDatas("json", "a");
								} catch (Exception ei) {
									_includeNewUid("[]", "yes", "4");

								}
							}
							@Override
							public void onCancelled(DatabaseError _databaseError) {
								showMessage(_databaseError.toString());
							}
						});
					} catch (Exception e) {
						showMessage(e.toString());
					}
				}
			}
			else {

			}
		}
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}

	private void firebaseAuthWithGoogle(String idToken) {
		AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
		Auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()) {
					users = Auth.getCurrentUser();
					GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
					uid = "";
					if (acct != null) {
						email = acct.getEmail();
						uid = acct.getId();
						data.edit().putString("uid", uid).commit();
						data.edit().putString("email", email).commit();
					}
					if (!uid.equals("")) {
						featureuid = users.getUid();
						DatabaseReference ref5 = _firebase.getReference("user").child(featureuid);
						try {
							ref5.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot _dataSnapshot) {
									try {
										ex5 = _dataSnapshot.child("uid").getValue().toString();
										//break, completed
										v2.edit().putString("v2", "ok").commit();
										data.edit().putString("name", users.getDisplayName()).commit();
										data.edit().putString("image", users.getPhotoUrl().toString()).commit();
										data.edit().putString("email", users.getEmail()).commit();
										data.edit().putString("uid", users.getUid()).commit();
										_updateDatas("json", "a");
									} catch (Exception ei) {
										_includeNewUid("[]", "yes", "4");
									}
								}
								@Override
								public void onCancelled(DatabaseError _databaseError) {
									showMessage(_databaseError.toString());
								}
							});
						} catch (Exception e) {
							showMessage(e.toString());
						}
					}
					else {
						toast("An error occurred. Clear data of this app and retry.");
						try {
							pd.dismiss();
						} catch (Exception ei) {
							if (Looper.myLooper() == null) {
								Looper.prepare();
							}
						}

						try {
							newpd.dismiss();
						} catch (Exception eio) {
							if (Looper.myLooper() == null) {
								Looper.prepare();
							}
						}

					}
				} else {
					toast("An error occured");
					try {
						pd.dismiss();
					} catch (Exception ei) {
						if (Looper.myLooper() == null) {
							Looper.prepare();
						}
					}

					try {
						newpd.dismiss();
					} catch (Exception eio) {
						if (Looper.myLooper() == null) {
							Looper.prepare();
						}
					}

				}
			}
		});
	}

	
	
	public void _assync(final String _muid) {
		DatabaseReference ref7 = _firebase.getReference("user").child(_muid);
		try {
				ref7.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {	 
					try {
						ex7 = _dataSnapshot.child("uid").getValue().toString();
						_updateDatas("json", "a");
					} catch (Exception ei) {
						memory = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										memory.cancel();
										_assync(_muid);
									}
								});
							}
						};
						_timer.schedule(memory, (int)(2000));
					}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
								showMessage(_databaseError.toString());
						}
				});
		} catch (Exception e) {
				showMessage(e.toString());
		}
	}
	
	
	public void _onsuccess() {
		i.setClass(getApplicationContext(), ChatActivity.class);
		startActivity(i);

		
	}

	public void _updateDatas(String array, String key) {

		getArrayFromDb(array);


/*
		DatabaseReference ref71 = _firebase.getReference(array);
		try {
			ref71.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {

					ex7 = new Gson().toJson(_dataSnapshot.getValue().toString());
					if (array == "json") {
						json.edit().putString("json", ex7).commit();
						_updateDatas("regex", "a");
					} else if (array == "regex") {
						json.edit().putString("regex", ex7).commit();
						_updateDatas("command", "key");
					} else {
						json.edit().putString("command", ex7).commit();
						updatedataserporoonsuccesserage();

					}

				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
					showMessage(_databaseError.toString());
				}
			});
		} catch (Exception e) {
			memory = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							memory.cancel();

							if (array == "json") {
								_updateDatas("json", "a");
							} else if (array == "regex") {
								_updateDatas("regex", "a");
							} else {
								_updateDatas("command", "key");

							}
						}
					});
				}
			};
			_timer.schedule(memory, (int)(1300));
		}*/
	}

	public void updatedataserporoonsuccesserage() {
		try {
			pd.dismiss();
		} catch (Exception ei) {
			if (Looper.myLooper() == null) {
				Looper.prepare();
			}
		}

		try {
			newpd.dismiss();
		} catch (Exception eio) {
			if (Looper.myLooper() == null) {
				Looper.prepare();
			}
		}

		user.child(data.getString("uid", "")).removeValue();
		data.edit().putString("email", users.getEmail()).commit();
		data.edit().putString("uid", users.getUid()).commit();
		v2.edit().putString("v2", "ok").commit();
		_onsuccess();
	}
	
	public void _Dif(final String _uid, final String _email) {
		gu = new HashMap<>();
		gu.put("uid", _uid);
		gu.put("email", _email);
		String device0 = android.os.Build.DEVICE;
		String model0 = android.os.Build.MODEL;
		String product0 = android.os.Build.PRODUCT;
		String manufacturer0 = android.os.Build.MANUFACTURER;
		String brand0 = android.os.Build.BRAND;
		String api_level0 = android.os.Build.VERSION.SDK;
		String board0 = android.os.Build.BOARD;
		String boot0 = android.os.Build.BOOTLOADER;
		String display0 = android.os.Build.DISPLAY;
		String fingerprint0 = android.os.Build.FINGERPRINT;
		String hardware0 = android.os.Build.HARDWARE;
		String host0 = android.os.Build.HOST;
		String id0 = android.os.Build.ID;
		gu.put("device", device0);
		gu.put("model", model0);
		gu.put("product", product0);
		gu.put("manufacturer", manufacturer0);
		gu.put("brand", brand0);
		gu.put("api_level", api_level0);
		gu.put("board", board0);
		gu.put("boot", boot0);
		gu.put("display", display0);
		gu.put("fingerprint", fingerprint0);
		gu.put("hardware", hardware0);
		gu.put("host", host0);
		gu.put("id", id0);
		vaultnew.push().updateChildren(gu);
		//: vaultnew is newlog in fb
	}
	
	
	public void _includeNewUid(final String _trans,final String extra, final String _r) {
		map = new HashMap<>();
		map.put("r", _r);
		map.put("email", users.getEmail());
		map.put("uid", users.getUid());
		map.put("trans", _trans);
		map.put("su", "0");
		map.put("qu", "0");
		map.put("wu", "0");
		map.put("name", users.getDisplayName());
		map.put("image", users.getPhotoUrl().toString());
		user.child(users.getUid()).updateChildren(map);
		data.edit().putString("name", users.getDisplayName()).commit();
		data.edit().putString("image", users.getPhotoUrl().toString()).commit();
		_Dif(users.getUid(), users.getEmail());
		_assync(users.getUid());
	}

	public void toast(String _s) {
		LuminousUtil.showMessage(getApplicationContext(), _s);
	}
	public void showMessage(String s) {toast(s);}

	public void getArrayFromDb(String array) {
		DatabaseReference gu = FirebaseDatabase.getInstance().getReference(array);
		gu.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				lmap = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
					};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						lmap.add(_map);
					}
				} catch (Exception _e) {
					_e.printStackTrace();
				}
				//run
				//Log.d(array, new Gson().toJson(lmap));
				json.edit().putString(array, new Gson().toJson(lmap)).commit();
				if (array == "json") {
					//json.edit().putString("json", ex7).commit();
					_updateDatas("regex", "a");
				} else if (array == "regex") {
					//json.edit().putString("regex", ex7).commit();
					_updateDatas("command", "key");
				} else {
					//json.edit().putString("command", ex7).commit();
					updatedataserporoonsuccesserage();

				}
			}

			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}


}

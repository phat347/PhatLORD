package com.phatle.smartrestaurant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {
    ImageView profile;
    TextView facebookUsername;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        final List<String> permissionNeeds= Arrays.asList("user_photos", "email", "user_birthday", "user_friends");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        final TextInputLayout textInputLayoutPassword= findViewById(R.id.etPasswordLayout);
        final TextInputLayout textInputLayoutUsername= findViewById(R.id.etUsernameLayout);
        final TextInputEditText etUser= findViewById(R.id.et_user);
        final TextInputEditText etPass= findViewById(R.id.et_pass);
        TextView facebookLogin = findViewById(R.id.facebook_login);

        profile = findViewById(R.id.picture);
        facebookUsername = findViewById(R.id.facebook_username);
        TextView googleLogin = findViewById(R.id.google_login);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
        }
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, permissionNeeds);
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                Toast bread = Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT);
                                bread.show();
                                RequestData();
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });
            }
        });
        Picasso.with(MainActivity.this)
                .load(R.drawable.user)
                .into(profile);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPass.getText().toString().length() == 0) {
                    textInputLayoutPassword.setError("Vui lòng nhập mật khẩu");
                }
                else
                    textInputLayoutPassword.setError(null);
                if (etUser.getText().toString().length() == 0) {
                    textInputLayoutUsername.setError("Vui lòng nhập tên tài khoản");
                }
                else
                    textInputLayoutUsername.setError(null);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                signOut();
//                profile.setProfileId(null);
                Picasso.with(MainActivity.this)
                        .load(R.drawable.user)
                        .into(profile);
                facebookUsername.setText("Tên tài khoản");
                Toast bread = Toast.makeText(getApplicationContext(),"Đăng xuất thành công",Toast.LENGTH_SHORT);
                bread.show();
                Intent register = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(register);

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            Picasso.with(MainActivity.this)
                    .load(personPhotoUrl) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(100, 100)
                    .into(profile);
//                        profile.setProfileId(json.getString("id"));
            facebookUsername.setText(personName);
            Toast bread = Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT);
            bread.show();

        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 123);
    }
    private void signOut() {
        mGoogleSignInClient.signOut();
    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){


                        Picasso.with(MainActivity.this)
                                .load("https://graph.facebook.com/v2.2/" + json.getString("id") + "/picture?height=120&type=normal") //extract as User instance method
                                .transform(new CropCircleTransformation())
                                .resize(100, 100)
                                .into(profile);
//                        profile.setProfileId(json.getString("id"));
                        facebookUsername.setText(json.getString("name"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
}

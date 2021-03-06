package com.phatle.smartrestaurant;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.AppCenter; import com.microsoft.appcenter.analytics.Analytics; import com.microsoft.appcenter.crashes.Crashes;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    TextView appName;
    AccessToken accessToken;
    boolean isLoggedIn;
    FirebaseUser user;
    private FirebaseAuth auth;
    @Override
    protected void onStart() {
        super.onStart();
        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCenter.start(getApplication(), "64d16538-5044-4c45-8783-7342bb96551e",
                Analytics.class, Crashes.class);
        setContentView(R.layout.activity_splash);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        appName = findViewById(R.id.app_name);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/open_sans_regular.ttf");
        appName.setTypeface(face);
        startAnimation();
    }
    private void startAnimation() {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 100);
        progressAnimator.setDuration(2000)
                .addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        checkNetWork();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
        progressAnimator.start();
    }
    public void checkNetWork()
    {

        if (!isOnline(this))
        {
            showNetworkAlert(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity(getIntent());
                }
            });
        }
        //Đã có token login facebook -> skip màn hình đăng nhập vào thẳng màn hình profile
        else if(isLoggedIn)
        {
            Intent intent = new Intent(SplashActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else if(user != null)
        {
            Intent intent = new Intent(SplashActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public static void showNetworkAlert(Context context, View.OnClickListener tryAgainClickListener) {
        ErrorDialog dialog = new ErrorDialog(context, "Bạn đang ngoại tuyến!", "Vui lòng kiểm tra kết nối Internet của bạn và thử lại",R.drawable.ic_warning);
        dialog.setupOkButton("Thử lại", tryAgainClickListener);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}

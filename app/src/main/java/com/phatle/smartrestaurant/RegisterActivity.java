package com.phatle.smartrestaurant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    TextInputEditText etUser;
    TextInputEditText etPass;
    TextInputEditText etPassConfirm;
    TextInputEditText etEmail;
    TextInputLayout textInputLayoutPassword;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutConfirm;
    TextInputLayout textInputLayoutEmail;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        checkNetWork();
        progressDialog = new ProgressDialog(this);
        textInputLayoutPassword= findViewById(R.id.etPasswordLayout);
        textInputLayoutUsername= findViewById(R.id.etUsernameLayout);
        textInputLayoutConfirm= findViewById(R.id.etPasswordLayoutConfirm);
        textInputLayoutEmail= findViewById(R.id.etEmailLayout);
        etUser= findViewById(R.id.et_user);
        etPass= findViewById(R.id.et_pass);
        etPassConfirm = findViewById(R.id.et_pass_confirm);
        etEmail = findViewById(R.id.et_email);
        Button btn_regis = findViewById(R.id.btn_register);
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent backtoLogin = new Intent(RegisterActivity.this,MainActivity.class);
//                startActivity(backtoLogin);
//                finish();
                registerUser();

            }
        });
        container = findViewById(R.id.container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getApplicationContext(),view);
            }
        });
    }
    public static boolean isValidEmail(String str) {
        boolean isValid = false;
        if (Build.VERSION.SDK_INT >= 8) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches();
        }
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = str;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
    private void registerUser() {
        final String username = etUser.getText().toString();
        final String password = etPass.getText().toString();
        String passwordConfirm = etPassConfirm.getText().toString();
        final String email = etEmail.getText().toString();
        boolean valid= true;
        if (TextUtils.isEmpty(username))
        {
            textInputLayoutUsername.setError("Vui lòng nhập tên tài khoản");
            valid = false;
        }
        else {
            textInputLayoutUsername.setError(null);
        }
        if (TextUtils.isEmpty(password))
        {
            textInputLayoutPassword.setError("Vui lòng nhập mật khẩu");
            valid = false;
        }
        else {
            textInputLayoutPassword.setError(null);
        }
        if (password.length()<6 && password.length()>0)
        {
            textInputLayoutPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            valid = false;
        }
        if (TextUtils.isEmpty(passwordConfirm))
        {
            textInputLayoutConfirm.setError("Vui lòng nhập mật lại khẩu");
            valid = false;
        }
        else {
            textInputLayoutConfirm.setError(null);
        }
        if (!password.equals(passwordConfirm))
        {
            textInputLayoutConfirm.setError("Mật khẩu không trùng khớp");
            valid = false;
        }else {
            textInputLayoutConfirm.setError(null);

        }
        if(!isValidEmail(email) && !TextUtils.isEmpty(email))
        {
            textInputLayoutEmail.setError("Email không hợp lệ");
            valid = false;
        }
        else if (TextUtils.isEmpty(email))
        {
            textInputLayoutEmail.setError("Vui lòng nhập Email");
            valid = false;
        }
        else {
            textInputLayoutEmail.setError(null);
        }
        if(valid){
//            Toast bread = Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_SHORT);
//            bread.show();
            progressDialog.setMessage("Đang đăng ký tài khoản...");
            progressDialog.show();

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user = auth.getCurrentUser();

                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username).build();
                                user.updateProfile(profileUpdate);
                                Toast bread = Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_SHORT);
                                bread.show();
                                Intent data = new Intent();
                                data.putExtra("IntentEmail",email);
                                data.putExtra("IntentPassword",password);
                                setResult(Activity.RESULT_OK,data);
                                finish();
                            }
                            else {
                                Toast bread = Toast.makeText(getApplicationContext(),"Email đã được sử dụng",Toast.LENGTH_SHORT);
                                bread.show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }

    }
    public void checkNetWork()
    {

        if (!isOnline(this))
        {
            showNetworkAlert(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkNetWork();
                }
            });
        }
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public static void showNetworkAlert(Context context, View.OnClickListener tryAgainClickListener) {
        ErrorDialog dialog = new ErrorDialog(context, "Bạn đang ngoại tuyến!", "Vui lòng kiểm tra kết nối Internet của bạn và thử lại");
        dialog.setupOkButton("Thử lại", tryAgainClickListener);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

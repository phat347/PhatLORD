package com.phatle.smartrestaurant;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

public class CustomDialogString extends Dialog implements View.OnClickListener{
    public TextView mBtnClose;

    public CustomDialogString(@NonNull Context context, String title, String message,String imgRes) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_string);
        setupDialogSize();
        ImageView v = findViewById(R.id.dialog_img);
        GlideApp.with(context)
                .load(imgRes)
                .into(v);
        ((TextView) findViewById(R.id.tv_title)).setText(title);
        ((TextView) findViewById(R.id.tv_message)).setText(message);
        mBtnClose = findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(this);
    }

    void setupDialogSize() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
    }

    public void setupOkButton(String label, final View.OnClickListener listener) {
        Button tv = findViewById(R.id.btn_ok);
        tv.setText(label);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dismiss();
            }
        });
        tv.setVisibility(View.VISIBLE);
    }

    // Close button
    public void setupCancelButton(String label, final View.OnClickListener listener) {
        TextView tv = (TextView) findViewById(R.id.btn_close);
        tv.setText(label);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dismiss();
            }
        });
        tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_close:
                dismiss();
                break;

            default:
                break;
        }
    }
}

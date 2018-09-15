package com.phatle.smartrestaurant;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.text1);
        Button btnEdit = findViewById(R.id.btn_edit);
        Button btnReset = findViewById(R.id.btn_reset);
        Button btn_temp = findViewById(R.id.btn_nhap);
        final LinearLayout background= findViewById(R.id.Btemp);
        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background.setBackgroundColor(Color.BLACK);
            }
        });
        btn_temp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                background.setBackgroundColor(Color.RED);
                return true;
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Sau khi sửa");
                tv.setTextColor(Color.RED);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Trước khi sửa");
                tv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                background.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            }
        });
    }
}

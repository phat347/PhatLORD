package com.phatle.smartrestaurant;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.text1);
        Button btnEdit = findViewById(R.id.btn_edit);
        Button btnReset = findViewById(R.id.btn_reset);
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

            }
        });
    }
}

package com.phatle.smartrestaurant;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ramotion.circlemenu.CircleMenuView;

import cdflynn.android.library.crossview.CrossView;


public class MainActivity extends Activity {
    int count = 0;
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
        CircleMenuView menu = findViewById(R.id.circle_menu);
        menu.setEventListener(new CircleMenuView.EventListener(){
        @Override
        public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
            Log.d("D", "onMenuOpenAnimationStart");
        }

        @Override
        public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
            Log.d("D", "onMenuOpenAnimationEnd");
        }

        @Override
        public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
            Log.d("D", "onMenuCloseAnimationStart");
        }

        @Override
        public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
            Log.d("D", "onMenuCloseAnimationEnd");
        }

        @Override
        public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
            Log.d("D", "onButtonClickAnimationStart| index: " + index);
        }

        @Override
        public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
            Log.d("D", "onButtonClickAnimationEnd| index: " + index);
        }
    });
        final int[] colors = {getResources().getColor(R.color.chocolate), getResources().getColor(R.color.cadet_blue),getResources().getColor(R.color.dark_cyan)};
        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               background.setBackgroundColor(colors[count]);
//                switch (count)
//                {
//                    case 0:
//                        background.setBackgroundColor(getResources().getColor(R.color.chocolate));
//                        break;
//                    case 1:
//                        background.setBackgroundColor(getResources().getColor(R.color.cadet_blue));
//                        break;
//                    case 2:
//                        background.setBackgroundColor(getResources().getColor(R.color.dark_cyan));
//                        break;
//                    case 3:
//                        background.setBackgroundColor(getResources().getColor(R.color.goldenrod));
//                        break;
//                    default:
//                        count = 0;
//                        break;
//                }
               count ++;
               if (count == 3)
               {
                   count = 0;
               }
            }
        });
        btn_temp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                background.setBackgroundColor(getResources().getColor(R.color.tomato));
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
                count = 0;

            }
        });
    }
}

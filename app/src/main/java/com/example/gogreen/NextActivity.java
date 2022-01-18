package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {

    private TextView textView1,textView2,textView3,textView4;
    private Button shopnowBtn;
    Animation fade_in_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        shopnowBtn = findViewById(R.id.shopnowBtn);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        fade_in_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        textView1.startAnimation(fade_in_anim);
        textView4.startAnimation(fade_in_anim);
        textView3.startAnimation(fade_in_anim);
        textView2.startAnimation(fade_in_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 5000);

        shopnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NextActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
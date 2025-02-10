package com.example.baateinkare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class First_page extends AppCompatActivity {
    TextView textView,textView3;
    ImageView img1,img2,img3;
     Animation birdAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //img1 = findViewById(R.id.splash_img);
        //img2 = findViewById(R.id.splash_txt);
        img1 = findViewById(R.id.bird);
        img2 = findViewById(R.id.bird2);
        img3 = findViewById(R.id.bird3);

        //topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        birdAnim= AnimationUtils.loadAnimation(this,R.anim.bird);

        img1.setAnimation(birdAnim);
        img2.setAnimation(birdAnim);
        img3.setAnimation(birdAnim);


        //img2.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(First_page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}
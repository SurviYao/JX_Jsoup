package com.example.administrator.jx_new_jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class LoadingActivity extends BaseActivity {
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ll=findViewById(R.id.ll_loading);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,100);
        alphaAnimation.setDuration(2000);
        ll.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivities(new Intent[]{new Intent(context, MainActivity.class)});
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}

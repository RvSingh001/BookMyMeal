package com.example.bookmymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

public class Logo extends AppCompatActivity {
    private String email,password;
    private SharedPreferences preferences;
    TextView txt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        preferences=getSharedPreferences("mypref",MODE_PRIVATE);
        init();
        txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style.otf"));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                email = preferences.getString("email", "");
                password = preferences.getString("password", "");
                if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase(""))
                {
                    Intent i = new Intent(Logo.this, User_Main_Page.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i2 = new Intent(Logo.this, Start_up.class);
                    startActivity(i2);
                    finish();
                }
            }

        },2000);
    }
public void init()
{
    txt=findViewById(R.id.txt2);
}
}

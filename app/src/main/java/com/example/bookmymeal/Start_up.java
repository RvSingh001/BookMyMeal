package com.example.bookmymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Start_up extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        preferences=getSharedPreferences("mypref2",MODE_PRIVATE);
        findViewById(R.id.userLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start_up.this,User_Login.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.adminLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = preferences.getString("email", "");
                String password = preferences.getString("password", "");
                if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase(""))
                {
                    Intent i = new Intent(Start_up.this, Admin_main_page.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i2 = new Intent(Start_up.this, Admin_Login.class);
                    startActivity(i2);
                    finish();
                }

            }
        });
    }
}

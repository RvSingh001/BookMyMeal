package com.example.bookmymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class Admin_Login extends AppCompatActivity {

    private TextView txt;
    private TextView admin_res;
    private RadioGroup rg;
    private Button login;
    private EditText ed1, ed2;
    private SharedPreferences.Editor editor2;
    private SharedPreferences preferences;
    private CheckBox cb;
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);
        init();
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/style.otf"));
        preferences = getSharedPreferences("mypref2", MODE_PRIVATE);
        /*admin_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Admin_Login.this, Admin_Registration.class);
                startActivity(i);
            }
        });*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();

            }

        });

    }

    public void init() {

        txt = findViewById(R.id.txt1);
        login = findViewById(R.id.admin_login);
        ed1 = findViewById(R.id.aemail);
        ed2 = findViewById(R.id.apassword);
        admin_res=findViewById(R.id.admin_res);
        cb=findViewById(R.id.cb);
    }



    public void Login() {

        adminLogin();
    }

    public void adminLogin() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String e = ed1.getText().toString().trim();
        final String p = ed2.getText().toString().trim();
        if (e.isEmpty() && p.isEmpty()) {
            ed1.setError("Please Fill the Fields");
            ed2.setError("Please Fill the Fields");
        } else if (!e.matches(emailPattern) && e.length() > 0) {
            ed1.setError("Email Address is Incorrect");
        } else if (p.length() < 8) {
            ed2.setError("Password is too short");
        } else {
            if(cb.isChecked()==true) {
                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADMIN_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Error", response);
                            if (response.contains("success")) {
                                editor2 = preferences.edit();
                                editor2.putString("email", e);
                                editor2.putString("password", p);
                                editor2.commit();
                                Toast.makeText(getApplicationContext(), "Sucessfully Login", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Admin_Login.this, Admin_main_page.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                      else
                        {
                            Toast.makeText(getApplicationContext(), "Invaild Email Or Password", Toast.LENGTH_SHORT).show();
                        }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put(EMAIL, e);
                            map.put(PASSWORD, p);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                } catch (Exception ex) {
                    Log.e("Error", ex.getMessage());
                }
            }
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADMIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Error", response);
                        if (response.contains("success")) {
                            editor2 = preferences.edit();
                            editor2.putString("email", e);
                            editor2.commit();
                            Toast.makeText(getApplicationContext(), "Sucessfully Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Admin_Login.this, Admin_main_page.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invaild Email Or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put(EMAIL, e);
                        map.put(PASSWORD, p);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
        }
    }
}
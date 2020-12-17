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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Login extends AppCompatActivity implements View.OnClickListener {
    private TextView txt,forgot;
    private TextView btn;
    private Button login;
    private EditText ed1, ed2;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private CheckBox cb;

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String EMAIL_FP = "email";

    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        init();
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/style.otf"));
        btn.setOnClickListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }

        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot();
            }
        });

    }

    public void init() {

        txt = findViewById(R.id.txt);
        btn = findViewById(R.id.btn);
        login = findViewById(R.id.click);
        cb = findViewById(R.id.cb);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        forgot = findViewById(R.id.forgotid);
    }

    public void registerPage() {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);


    }

    @Override
    public void onClick(View v) {

        registerPage();
    }


    public void userLogin() {
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
            if (cb.isChecked() == true) {
                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e("Error", response);
                            if (response.contains("success")) {
                                editor = preferences.edit();
                                editor.putString("email", e);
                                editor.putString("password", p);
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "Sucessfully Login", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), User_Main_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Invaild Email Or Password Or "+"Not Approved", Toast.LENGTH_SHORT).show();

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


            } else {


                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("success")) {
                                editor = preferences.edit();
                                editor.putString("email", e);
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "Sucessfully Login", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), User_Main_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Invaild Email Or Password Or "+"Not Approved", Toast.LENGTH_SHORT).show();
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

    public void forgot() {


        final String efp = ed1.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (efp.isEmpty()) {
            ed1.setError("Please Fill the Fields");

        } else if (!efp.matches(emailPattern) && efp.length() > 0) {
            ed1.setError("Email Address is Incorrect");
        }
        else
        {
            try {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FP, new Response.Listener<String>() {
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                password=jsonObject1.getString("password");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            RequestQueue requestQueue1 = Volley.newRequestQueue(User_Login.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/sendpassword.php", new Response.Listener<String>() {
                                public void onResponse(String response) {

                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                    finish();
                                    getIntent();
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
                                    map.put("email",efp);
                                    map.put("password", password);
                                    return map;
                                }
                            };
                            requestQueue1.add(stringRequest);
                        } catch (Exception ex) {
                            Log.e("Error", ex.getMessage());
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
                        map.put(EMAIL_FP, efp);
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




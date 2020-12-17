package com.example.bookmymeal;




import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Admin_Registration extends AppCompatActivity implements View.OnClickListener {
    private EditText ed1,ed2,ed3,ed4,ed5;
    private Button register;
    private static final String URL="https://rvproject994.000webhostapp.com/admin_res.php";
    private static final String NAME="name";
    private static final String EMAIL="email";
    private static final String PASSWORD="password";
    private static final String ADDRESS="address";
    private static final String CONTACT="contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__registration);
        init();
        setTitle("Register Here");

        register.setOnClickListener(this);
    }

    private void init() {
        ed1=findViewById(R.id.name);
        ed2=findViewById(R.id.email);
        ed3=findViewById(R.id.password);
        ed4=findViewById(R.id.a_address);
        ed5=findViewById(R.id.mobile);
        register=findViewById(R.id.register);
    }
    private  void registerData() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String matchpattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$.";
        final String n = ed1.getText().toString().trim();
        final String e = ed2.getText().toString().trim();
        final String p = ed3.getText().toString().trim();
        final String a = ed4.getText().toString().trim();
        final String m = ed5.getText().toString().trim();

        if (n.isEmpty() && e.isEmpty() && p.isEmpty() && m.isEmpty()) {
            ed1.setError("Please Fill the Fields");
            ed2.setError("Please Fill the Fields");
            ed3.setError("Please Fill the Fields");
            ed4.setError("Please Fill the Fields");
            ed5.setError("Please Fill the Fields");
        } else if (!e.matches(matchpattern) && e.length() < 0) {
            ed2.setError("Email Address is incorrect");
        } else if (p.length() < 8) {
            ed3.setError("Password is too short");
        } else if (m.length() < 10) {
            ed5.setError("Number in incorrect");
        } else {
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Error",response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), Admin_Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map=new HashMap<>();
                        map.put(NAME,n);
                        map.put(EMAIL,e);
                        map.put(PASSWORD,p);
                        map.put(ADDRESS,a);
                        map.put(CONTACT,m);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
            catch (Exception ex)
            {
                Log.e("Error",ex.getMessage());
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v==register)
            registerData();
    }
}


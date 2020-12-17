package com.example.bookmymeal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Coupan extends AppCompatActivity {
    private EditText cn,val;
    private Button addcup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupan);
        cn=findViewById(R.id.cn);
        val=findViewById(R.id.value);
        addcup=findViewById(R.id.addcup);
        addcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcoupan();
            }
        });
    }
    public void addcoupan()
    {
        final String coupanname=cn.getText().toString().trim();
        final String coupanvalue=val.getText().toString().trim();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("coupanname",coupanname);
                map.put("coupanvalue",coupanvalue);
                return map;
            }
        };
    }
}

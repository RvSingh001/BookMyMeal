package com.example.bookmymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;
import java.util.Map;

public class Rating extends AppCompatActivity {


    private Button submit;
    private String res;
    private EditText editText;
    private SharedPreferences preferences;
    private SmileRating smileRating;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        editText=findViewById(R.id.rated);
        submit=findViewById(R.id.ratesubmit);
        preferences=getSharedPreferences("mypref",MODE_PRIVATE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating();
            }
        });
        smileRating=findViewById(R.id.smile);
        linearLayout=findViewById(R.id.feedback);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                int i=smileRating.getRating();
                switch (i)
                {
                    case 1 :
                        res="Terriable";
                        linearLayout.setBackgroundResource(R.drawable.bad);
                        break;
                    case 2 :
                        res="Bad";
                        linearLayout.setBackgroundResource(R.drawable.bad1);
                        break;
                    case 3 :
                        res="Okay";
                        linearLayout.setBackgroundResource(R.drawable.okay);
                        break;
                    case 4 :
                        res="Good";
                        linearLayout.setBackgroundResource(R.drawable.smiley);
                        break;
                    case 5 :
                        res="Great";
                        linearLayout.setBackgroundResource(R.drawable.greate);
                        break;
                }
            }
        });

    }
    public void setRating()
    {

        final String FEED="feed";
        final String RATE="rate";
        final String EMAILS="email";
        final String NAME="name";
        final String email=preferences.getString("email","");
        final String name=preferences.getString("name","");
        final String feed=editText.getText().toString().trim();
        final String rate=res;
        Toast.makeText(getApplicationContext(),"Thank You for rating us"+"  "+rate+"",Toast.LENGTH_LONG).show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/feedback.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(Rating.this,User_Main_Page.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put(FEED,feed);
                map.put(RATE,rate);
                map.put(NAME,name);
                map.put(EMAILS,email);

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}

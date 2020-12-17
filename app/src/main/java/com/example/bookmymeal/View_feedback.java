package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapters.Feedback_Adapter;
import com.example.bookmymeal.models.Model_feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class View_feedback extends AppCompatActivity {
    private Feedback_Adapter feedback_adapter;
    private ArrayList<Model_feed> model_feedArrayList;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        recyclerView=findViewById(R.id.recycle_feed);
        model_feedArrayList=new ArrayList<>();
        init();
    }

    public void init()
    {
progressDialog=new ProgressDialog(this);
progressDialog.setMessage("Loading..");
progressDialog.show();
progressDialog.setCancelable(false);

        RequestQueue requestQueue= Volley.newRequestQueue(this);

            StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://rvproject994.000webhostapp.com/fetch_feed.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.dismiss();
                        findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Model_feed model_feed=new Model_feed(jsonObject1.getString("feed"),
                            jsonObject1.getString("rate"),
                            jsonObject1.getString("name"),
                            jsonObject1.getString("email"));
                            model_feedArrayList.add(model_feed);
                        }
                        feedback_adapter=new Feedback_Adapter(View_feedback.this,model_feedArrayList);
                        recyclerView.setAdapter(feedback_adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(View_feedback.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
    requestQueue.add(stringRequest);


    }


}

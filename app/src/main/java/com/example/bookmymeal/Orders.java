package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapters.Food_Cart;
import com.example.bookmymeal.adapters.Orders_Adapter;
import com.example.bookmymeal.models.Model_Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Orders extends AppCompatActivity implements Orders_Adapter.OnItemClickListener{

    private ArrayList<Model_Order> modelOrderArrayList;
    private RecyclerView recyclerView;
    private Orders_Adapter orders_adapter;
    private ProgressDialog progressDialog;
    public static final String EMAIL="email";
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        preferences=getSharedPreferences("mypref",MODE_PRIVATE);
        recyclerView=findViewById(R.id.recycleview_orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.ALIGN_BOUNDS));
        modelOrderArrayList=new ArrayList<>();
        loadData();


    }

    public void loadData() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String email = preferences.getString("email", "");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/selectorder.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ERROR", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                Model_Order model_order = new Model_Order
                                        (jObj.getString("order_id"),
                                                jObj.getString("orders_item"),
                                                jObj.getString("cus_name"),
                                                jObj.getString("email"),
                                                jObj.getString("address"),
                                                jObj.getString("contact"),
                                                jObj.getString("total"),
                                                jObj.getString("payment"),
                                                jObj.getString("order_date"),
                                                jObj.getString("status"));
                                modelOrderArrayList.add(model_order);
                            }
                            orders_adapter = new Orders_Adapter(Orders.this, modelOrderArrayList);
                            recyclerView.setAdapter(orders_adapter);
                            orders_adapter.setOnItemClickListener(Orders.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Orders.this));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(EMAIL, email);
                return map;
            }

    };
        requestQueue.add(stringRequest);
    }

    @Override
    public void OnItemClick(int position) {

        Model_Order model_order=modelOrderArrayList.get(position);
        final String order_id=model_order.getOrder_id();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/deleteorder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                startActivity(getIntent());
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("order_id",order_id);
                return map;

            }

        };
        requestQueue.add(stringRequest);
        orders_adapter.notifyItemChanged(position);
        orders_adapter.notifyItemInserted(position);
        orders_adapter.notifyDataSetChanged();



    }

    @Override
    public void Review(int position) {
        startActivity(new Intent(Orders.this,Rating.class));
    }

    @Override
    public void reorder(int position) {
        final Model_Order model_order=modelOrderArrayList.get(position);
        Food_Cart.grand_total=Integer.parseInt(model_order.getTotal());
        Cart.order=model_order.getOrders_item();
        startActivity(new Intent(Orders.this,Checkout.class));





    }
}


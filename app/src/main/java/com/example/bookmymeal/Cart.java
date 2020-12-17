package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.bookmymeal.models.Model_Cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity implements Food_Cart.OnItemClickListener {




    private static final String URL_CART="https://rvproject994.000webhostapp.com/fetch_cart.php";
    private ArrayList<Model_Cart> cartArrayList;
    private RecyclerView recyclerView;
    private Food_Cart food_cart;
    private ProgressDialog progressDialog;
    private TextView tv,tv2;
    public static final String FOOD_NAME="food_name";
    public static final String QUN="qun";
    public static String order="Orders: ";
    private static final String EMAIL="email";
    private Button place;
    private SharedPreferences preferences;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tv=findViewById(R.id.grnd);
        tv2=findViewById(R.id.grndtv);
        place=findViewById(R.id.place);
        tv.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);

        preferences=getSharedPreferences("mypref",MODE_PRIVATE);



        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetch();
                startActivity(new Intent(Cart.this,Checkout.class));
            }
        });

        recyclerView=findViewById(R.id.recycleview_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.ALIGN_BOUNDS));
        cartArrayList=new ArrayList<>();
        loadData();
    }

    private void loadData() {
        final String email=preferences.getString("email","");
        progressDialog=new ProgressDialog(this);

        progressDialog.setMessage("Loading... Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.e("ERROR", response);
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jObj=jsonArray.getJSONObject(i);
                                Model_Cart food_cart=new Model_Cart
                                        (jObj.getString("food_image"),
                                                jObj.getString("food_name"),
                                                jObj.getString("food_price"));
                                cartArrayList.add(food_cart);
                            }
                            food_cart=new Food_Cart(Cart.this,cartArrayList);
                           // food_cart.setOnItemClickListener(UserInfo.this);
                            food_cart.setOnItemClickListener(Cart.this);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(food_cart);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
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
    public void OnItemClick(int position,int pr,int total_new) throws InterruptedException {

        place.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);

        Model_Cart food_cart = cartArrayList.get(position);
        tv.setText(String.valueOf("Rs : " + Food_Cart.grand_total));

        final String food_name = food_cart.getFood_name();
        final String qun = food_name+"*"+ String.valueOf(pr);



        {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
                progressDialog.setMessage("Loading... Please Wait...!!!");
                progressDialog.setCancelable(false);
                progressDialog.show();
                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/insertorder.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Log.e("Error", response);


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
                            map.put(FOOD_NAME, food_name);
                            map.put(QUN, qun);


                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                } catch (Exception ex) {
                    Log.e("Error", ex.getMessage());
                }

            }
    }

    @Override
    public void remove(int position) {
        Model_Cart model_cart=cartArrayList.get(position);
        tv.setText(String.valueOf("Rs : " + Food_Cart.grand_total));

        final String food_name=model_cart.getFood_name();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        cartArrayList.remove(position);
        recyclerView.removeViewAt(position);
        food_cart.notifyItemRemoved(position);
        food_cart.notifyItemRangeChanged(position, cartArrayList.size());
        food_cart.notifyDataSetChanged();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/deletecartitem.php", new Response.Listener<String>() {
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
                Map<String,String> map=new HashMap<>();
                map.put("food_name",food_name);


                return  map;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void fetch()
{
    final String email=preferences.getString("email","");
    progressDialog=new ProgressDialog(this);

    progressDialog.setMessage("Loading... Please Wait");
    progressDialog.setCancelable(false);
    progressDialog.show();
    RequestQueue requestQueue= Volley.newRequestQueue(this);
    StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CART,
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

                            order = order + jObj.getString("qun")+ "," ;

                        }

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    )
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<>();

            map.put(EMAIL, email);


            return map;
        }
    };
    requestQueue.add(stringRequest);
}
}





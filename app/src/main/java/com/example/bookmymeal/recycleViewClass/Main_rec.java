package com.example.bookmymeal.recycleViewClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.Cart;
import com.example.bookmymeal.R;
import com.example.bookmymeal.adapters.MyCustomAdapter_Main;
import com.example.bookmymeal.constants.Constants;
import com.example.bookmymeal.models.Model_Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_rec extends AppCompatActivity implements MyCustomAdapter_Main.OnItemClickListener{
    private static final String URL="http://rvproject994.000webhostapp.com/main_course.php";
    private ArrayList<Model_Main> model_mains;
    private RecyclerView recyclerView_main;
    private MyCustomAdapter_Main adapter_main;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    public static final String FOOD_IMAGE="food_image";
    public static final String FOOD_NAME="food_name";
    public static final String FOOD_PRICE="food_price";
    private Button view_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rec);
        preferences=getSharedPreferences("mypref",MODE_PRIVATE);
        recyclerView_main=findViewById(R.id.main_rec);
        view_cart=findViewById(R.id.view_cart);
        view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_rec.this, Cart.class));
            }
        });

        recyclerView_main.setHasFixedSize(true);
        recyclerView_main.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView_main.addItemDecoration(new DividerItemDecoration(this, GridLayout.ALIGN_BOUNDS));
        model_mains=new ArrayList<>();


        loadData();
    }



    private void loadData() {
        progressDialog=new ProgressDialog(this);

        progressDialog.setMessage("Loading... Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,
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
                                Model_Main models_main=new Model_Main
                                        (jObj.getString("food_img").trim(),
                                                jObj.getString("id").trim(),
                                                jObj.getString("food_name").trim(),
                                                jObj.getString("food_des").trim(),
                                                jObj.getString("food_price").trim());


                                model_mains.add(models_main);
                            }
                            adapter_main=new MyCustomAdapter_Main(Main_rec.this,model_mains);
                            adapter_main.setOnItemClickListener(Main_rec.this);
                            recyclerView_main.setHasFixedSize(true);

                            recyclerView_main.setAdapter(adapter_main);
                            recyclerView_main.setLayoutManager(new LinearLayoutManager(Main_rec.this));

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
        );
        requestQueue.add(stringRequest);
    }
    public void OnItemClick(int position) {
        Model_Main model_main=model_mains.get(position);
        Toast.makeText(getApplicationContext(),"Add in Cart",Toast.LENGTH_LONG).show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final String food_image=model_main.getMain_img();
        final String food_name=model_main.getMain_name();
        final String food_price=model_main.getMain_price();
        final String email=preferences.getString("email","");
        final String unique_item=model_main.getMain_name()+email;
        final String EMAIL="email";

        try{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Error",response);

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
                    map.put(FOOD_IMAGE,food_image);
                    map.put(FOOD_NAME,food_name);
                    map.put(FOOD_PRICE,food_price);
                    map.put("unique_item",unique_item);
                    map.put(EMAIL,email);

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
    @Override
   public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Model_Main> filterList=new ArrayList<>();
                for (Model_Main main:model_mains)
                {
                    if (main.getMain_name().toLowerCase().contains(newText.toLowerCase())||main.getMain_price().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filterList.add(main);
                    }
                }
                adapter_main.filterable(filterList);
                return false;
            }
        });
        return true;
    }
}

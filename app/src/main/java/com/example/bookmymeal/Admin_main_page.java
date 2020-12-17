package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapters.Admin_Orders_Adapter;
import com.example.bookmymeal.adapters.Food_Adapter;
import com.example.bookmymeal.models.Model_Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_main_page extends AppCompatActivity
        implements Admin_Orders_Adapter.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener{
        private SharedPreferences.Editor editor2;
        private SharedPreferences preferences;
    private ArrayList<Model_Order> modelOrderArrayList;
    private RecyclerView recyclerView;
    private Admin_Orders_Adapter admin_orders_adapter;
    private ProgressDialog progressDialog;
    Handler mHandler;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mHandler = new Handler();
        m_Runnable.run();

        setSupportActionBar(toolbar);
        preferences=getSharedPreferences("mypref2",MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView=findViewById(R.id.admin_ord);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.ALIGN_BOUNDS));
        modelOrderArrayList=new ArrayList<>();
        loadData();



    }
    private Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Admin_main_page.this.mHandler.postDelayed(m_Runnable, 1000);




        }

    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.adminlogout) {
            editor2=preferences.edit();
            editor2.clear();
            editor2.apply();
            editor2.commit();
            Intent intent=new Intent(getApplicationContext(),Start_up.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP&Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.refresh) {
            Intent intent=new Intent(getApplicationContext(),Admin_main_page.class);
            startActivity(intent);
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        FragmentManager manager=getSupportFragmentManager();
          // Handle the camera action
        if (id == R.id.addmenu) {
            startActivity(new Intent(Admin_main_page.this,Add_menu.class));

        }
        else if (id == R.id.viewfood) {
            startActivity(new Intent(Admin_main_page.this,Admin_food_detail.class));

        }
        else if (id == R.id.userapprove) {
            startActivity(new Intent(Admin_main_page.this,UserInfo.class));



        }
        else if (id == R.id.viewfeed) {
            startActivity(new Intent(Admin_main_page.this,View_feedback.class));

        }


         else if (id == R.id.alogout) {

            editor2=preferences.edit();
            editor2.clear();
            editor2.apply();
            editor2.commit();
            Intent intent=new Intent(getApplicationContext(),Start_up.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP&Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loadData() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/selectcus.php",
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
                            admin_orders_adapter = new Admin_Orders_Adapter(Admin_main_page.this, modelOrderArrayList);
                            recyclerView.setAdapter(admin_orders_adapter);
                            admin_orders_adapter.setOnItemClickListener(Admin_main_page.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Admin_main_page.this));


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
        );
        requestQueue.add(stringRequest);
    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(getApplicationContext(),"cilck",Toast.LENGTH_LONG).show();
        Model_Order model_order=modelOrderArrayList.get(position);
        final String order_id=model_order.getOrder_id();
        Toast.makeText(getApplicationContext(),order_id,Toast.LENGTH_LONG).show();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/deleteorder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Admin_main_page.this,Admin_main_page.class));
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
        admin_orders_adapter.notifyDataSetChanged();
    }

    @Override
    public void accept(int position)
    {
        Model_Order model_order=modelOrderArrayList.get(position);
        final String order_id=model_order.getOrder_id();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/accept.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Admin_main_page.this,Admin_main_page.class));
                finish();

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
                map.put("order_id",order_id);

                return map;
            }
        };
        requestQueue.add(stringRequest);
        admin_orders_adapter.notifyDataSetChanged();
    }

    @Override
    public void completed(int position) {
        Model_Order model_order=modelOrderArrayList.get(position);
        final String order_id=model_order.getOrder_id();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/completed.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Admin_main_page.this,Admin_main_page.class));
                finish();
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
                map.put("order_id",order_id);

                return map;
            }
        };
        requestQueue.add(stringRequest);
        admin_orders_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)



    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        getMenuInflater().inflate(R.menu.admin_main_page, menu);


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
                ArrayList<Model_Order> filterList=new ArrayList<>();
                for (Model_Order main:modelOrderArrayList)
                {
                    if (main.getStatus().toLowerCase().contains(newText.toLowerCase())||main.getCus_name().toLowerCase().contains(newText.toLowerCase())
                            ||main.getPayment().toLowerCase().contains(newText.toLowerCase())||main.getOrder_date().toLowerCase().contains(newText.toLowerCase())
                            ||main.getOrder_id().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filterList.add(main);
                    }
                }
                admin_orders_adapter.filterable(filterList);
                return false;
            }
        });
        return true;
    }



}

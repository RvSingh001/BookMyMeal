package com.example.bookmymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapters.Food_Cart;
import com.example.bookmymeal.models.User_detail;
import com.example.bookmymeal.recycleViewClass.Drinks_rec;
import com.example.bookmymeal.recycleViewClass.Main_rec;
import com.example.bookmymeal.recycleViewClass.Salad_rec;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Main_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    private TextView textView;
    private String em;
    public String uname, uemail, upass, uaddress, umobile, uimage;
    private static final String URL = "https://rvproject994.000webhostapp.com/user_detail.php";
    private static final String EMAIL = "email";
    private ViewFlipper flipper;
    private LinearLayout scrollView,scrollView1;


    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("mypref", MODE_PRIVATE);

        show();

        textView = findViewById(R.id.contenttext);
        scrollView=findViewById(R.id.h1);
        scrollView1=findViewById(R.id.h2);
        flipper=findViewById(R.id.v1);
        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_Main_Page.this,Drinks_rec.class));

            }
        });
        scrollView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_Main_Page.this,Salad_rec.class));

            }
        });
        flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_Main_Page.this,Main_rec.class));

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uslogout) {

            editor = preferences.edit();
            editor.clear();
            editor.apply();
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), Start_up.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent d = new Intent(User_Main_Page.this, User_Profile.class);
            startActivity(d);
        } else if (id == R.id.orderhis) {
            Intent d = new Intent(User_Main_Page.this, Orders.class);
            startActivity(d);
        } else if (id == R.id.cart) {
            startActivity(new Intent(User_Main_Page.this, Cart.class));
            Food_Cart.grand_total=0;

        } else if (id == R.id.drinks) {
            Intent d = new Intent(User_Main_Page.this, Drinks_rec.class);
            startActivity(d);
        } else if (id == R.id.salad) {
            Intent d = new Intent(User_Main_Page.this, Salad_rec.class);
            startActivity(d);
        } else if (id == R.id.main_course) {
            Intent d = new Intent(User_Main_Page.this, Main_rec.class);
            startActivity(d);
        } else if (id == R.id.rating_us) {
            Intent d = new Intent(User_Main_Page.this, Rating.class);
            startActivity(d);
        } else if (id == R.id.Logout) {
            editor = preferences.edit();
            editor.clear();
            editor.apply();
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), Start_up.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void show() {
        final String em = preferences.getString("email", "");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        try {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("Error", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject JObj = jsonArray.getJSONObject(i);
                            User_detail userDetail = new User_detail(JObj.getString("name"),
                                    JObj.getString("email"),
                                    JObj.getString("password"),
                                    JObj.getString("address"),
                                    JObj.getString("contact"),
                                    JObj.getString("image"));
                            uname = userDetail.name;
                            uemail = userDetail.email;
                            upass = userDetail.password;
                            uaddress = userDetail.address;
                            umobile = userDetail.contact;
                            uimage = userDetail.image;
                            init();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
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

                    map.put(EMAIL, em);


                    return map;
                }


            };
            requestQueue.add(stringRequest);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
    }

    public void init() {
        editor = preferences.edit();
        editor.putString("name", uname);
        editor.putString("pass", upass);
        editor.putString("address", uaddress);
        editor.putString("contact", umobile);
        editor.putString("image", uimage);
        editor.commit();
        textView.setText(preferences.getString("name", ""));
        initnav();

    }


    protected void initnav() {

        final String name, email;
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        View view = navigationView.getHeaderView(0);
        ImageView imgview = view.findViewById(R.id.imgview);
        TextView uname = view.findViewById(R.id.uname);
        TextView uemail = view.findViewById(R.id.uemail);
        uname.setText(name);
        uemail.setText(email);
        if (preferences.getString("image","").contains("null")) {

            imgview.setImageResource(R.drawable.upload);
        }
        else
        {
            Picasso.with(getApplicationContext()).load(preferences.getString("image", "")).fit().into(imgview);
        }
    }
}

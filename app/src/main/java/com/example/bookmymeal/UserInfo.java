package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.bookmymeal.adapters.MyCustomAdapter;
import com.example.bookmymeal.models.Model;
import com.example.bookmymeal.models.Model_Drink;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInfo extends AppCompatActivity implements MyCustomAdapter.OnItemClickListener {


        private static final String URL="https://rvproject994.000webhostapp.com/userinfo.php";
        private static final String URL_del= "https://rvproject994.000webhostapp.com/delete.php";
        private ArrayList<Model> model;
        private RecyclerView recyclerView;
        private MyCustomAdapter adapter;
        private ProgressDialog progressDialog;

        private static final String ID="id";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_info);
            recyclerView=findViewById(R.id.recycle);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.ALIGN_BOUNDS));
            model=new ArrayList<>();

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
                                    Model mModel=new Model
                                            (jObj.getString("id"),
                                                    jObj.getString("name"),
                                                    jObj.getString("email"),
                                                    jObj.getString("password"),
                                                    jObj.getString("address"),
                                                    jObj.getString("contact"),
                                                    jObj.getString("image"),
                                                    jObj.getString("active"));


                                    model.add(mModel);
                                }
                                adapter=new MyCustomAdapter(UserInfo.this,model);
                                adapter.setOnItemClickListener(UserInfo.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(UserInfo.this));
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

        @Override
        public void OnItemClick(int position) {
            Model model1=model.get(position);
            Toast.makeText(getApplicationContext(),String.valueOf(model1.getId()),Toast.LENGTH_LONG).show();
            final String id=String.valueOf(model1.id);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_del, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Sucessfully Deleted", Toast.LENGTH_SHORT).show();
                        Log.e("Error", response);
                        Intent intent = new Intent(getApplicationContext(), Admin_main_page.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put(ID, id);
                    return map;
                }


                };
            requestQueue.add(stringRequest);

        }




    @Override
    public void approve(int position) {
        Model model1=model.get(position);


        final String email=model1.getEmail();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://rvproject994.000webhostapp.com/approve.php", new Response.Listener<String>() {
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(),"Approved", Toast.LENGTH_SHORT).show();
                    Log.e("Error", response);
                    Intent intent = new Intent(getApplicationContext(), Admin_main_page.class);
                    startActivity(intent);

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
                    map.put("email", email);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        adapter.notifyDataSetChanged();
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
                ArrayList<Model> filterList=new ArrayList<>();
                for (Model main:model)
                {
                    if (main.getName().toLowerCase().contains(newText.toLowerCase())||main.getActive().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filterList.add(main);
                    }
                }
                adapter.filterable(filterList);
                return false;
            }
        });
        return true;
    }
}

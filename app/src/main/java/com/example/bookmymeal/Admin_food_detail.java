package com.example.bookmymeal;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.example.bookmymeal.adapters.Food_Adapter;
import com.example.bookmymeal.models.Food_Model;
import com.example.bookmymeal.models.Model_Drink;
import com.example.bookmymeal.models.Model_Order;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Admin_food_detail extends AppCompatActivity implements Food_Adapter.OnItemClickListener{

private Food_Adapter food_adapter;
private ArrayList<Food_Model> foodModelArrayList;
private RecyclerView recyclerView;
private ProgressDialog progressDialog;
private static String URL_fetch="https://rvproject994.000webhostapp.com/selectfood.php";
private Bitmap bitmap;
private int PICK_IMAGE_REQUEST=1;
private String id;
private String food_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_detail);
        recyclerView=findViewById(R.id.recycleview_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.ALIGN_BOUNDS));
        foodModelArrayList=new ArrayList<>();
        loaddata();
    }
public void loaddata()
{
    progressDialog=new ProgressDialog(this);
    progressDialog.setMessage("Loading Please Wait");
    progressDialog.setCancelable(false);
    progressDialog.show();


    RequestQueue requestQueue= Volley.newRequestQueue(this);
    StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_fetch, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            progressDialog.dismiss();
            try {
                JSONObject jsonObject= new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jObj=jsonArray.getJSONObject(i);
                    Food_Model food_model=new Food_Model(jObj.getInt("id"),
                            jObj.getString("food_name"),
                            jObj.getString("food_des"),
                            jObj.getString("food_img"),
                            jObj.getString("food_price"),
                            jObj.getString("category")
                           );
                    foodModelArrayList.add(food_model);
                }
                food_adapter=new Food_Adapter(Admin_food_detail.this,foodModelArrayList);
                recyclerView.setAdapter(food_adapter);
                food_adapter.setOnItemClickListener(Admin_food_detail.this);
                food_adapter.notifyDataSetChanged();


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

    @Override
    public void OnItemClick(int position) {
        Food_Model food_model=foodModelArrayList.get(position);

        Toast.makeText(getApplicationContext(),food_model.getFood_name(),Toast.LENGTH_SHORT).show();
        final String food_name=food_model.getFood_name();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/deletefood.php", new Response.Listener<String>() {
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("food_name",food_name);
                return map;
            }
        };
requestQueue.add(stringRequest);
        /*recyclerView.removeViewAt(position);
        foodModelArrayList.remove(position);
        food_adapter.notifyItemRemoved(position);
        food_adapter.notifyItemRangeChanged(position, foodModelArrayList.size());*/
    }

    @Override
    public void update(int position,String fn,String fd,String fp,String fc) {
        Food_Model food_model = foodModelArrayList.get(position);
        final String id=String.valueOf(food_model.getId());
        final String food_name = fn;
        final String food_des = fd;
        final String food_price = fp;
        final String category = fc;

        Toast.makeText(getApplicationContext(), food_name, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/updatefood.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Admin_food_detail.this,Admin_food_detail.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("id",id);
                map.put("food_name",food_name);
                map.put("food_des",food_des);
                map.put("food_price",food_price);
                map.put("category",category);
                return map;
            }
        };
        requestQueue.add(stringRequest);
        food_adapter.notifyDataSetChanged();
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
                ArrayList<Food_Model> filterList=new ArrayList<>();
                for (Food_Model main:foodModelArrayList)
                {
                    if (main.getFood_name().toLowerCase().contains(newText.toLowerCase())||main.getFood_price().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filterList.add(main);
                    }
                }
                food_adapter.filterable(filterList);
                return false;
            }
        });
        return true;
    }

    @Override
    public void updateImg(int position) {
        Food_Model food_model=foodModelArrayList.get(position);
        id=String.valueOf(food_model.getId());
        food_name=food_model.getFood_name();
        selectImage();

    }
    public void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);


    }

    public void uploadImageToDb() {
        if (bitmap == null) {

            Toast.makeText(getApplicationContext(), "Please upload Image", Toast.LENGTH_LONG).show();
        } else {
            uploadImage();
        }
    }
    private void uploadImage()
    {


        final ProgressDialog progressDialog=new ProgressDialog(this);

        final String food_img=getStringImage(bitmap);
        progressDialog.setMessage("Loading... Please Wait...!!!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://rvproject994.000webhostapp.com/updateimage.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),Admin_main_page.class));

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
                map.put("id",id);
                map.put("food_name",food_name);
                map.put("food_img",food_img);

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                        filePath);
                //Setting the Bitmap to ImageView
                uploadImageToDb();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    }


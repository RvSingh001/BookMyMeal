package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.models.User_detail;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class User_Profile extends AppCompatActivity {
    private Context context;
    private User_detail user_detail;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int PICK_IMAGE_REQUEST=1;
    private EditText en,ep,ea,ee,emo;
    private Button button,delete;
    private ImageView imageView;
    private static final String STR="string";
    private static final String URL_UP="https://rvproject994.000webhostapp.com/user_update.php";
    private static final String URL_del_pro="https://rvproject994.000webhostapp.com/delete_profile.php";
    private static final String NAME_UP="name";
    private static final String EMAIL_UP="email";
    private static final String PASSWORD_UP="password";
    private static final String ADDRESS_UP="address";
    private static final String CONTACT_UP="contact";
    private static final String EMAIL_DEL="email";
    private ProgressDialog progressDialog;
    private Bitmap bitmap;
    private final String KEY_IMAGE="image";
    private static final String UPLOAD_URL="https://rvproject994.000webhostapp.com/uploadimg.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        preferences=getSharedPreferences("mypref",MODE_PRIVATE);
        progressDialog=new ProgressDialog(this);

        init();

        button=findViewById(R.id.update);
        delete=findViewById(R.id.delete_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_profile();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
    }


public void init()
{
    en=findViewById(R.id.un);
    ee=findViewById(R.id.ue);
    ep=findViewById(R.id.up);
    ea=findViewById(R.id.ua);
    emo=findViewById(R.id.umo);
    imageView=findViewById(R.id.img);



   /* editor=preferences.edit();
    editor.putString("name",uname);
    editor.putString("pass",upass);
    editor.putString("address",uaddress);
    editor.putString("contact",umobile);
    editor.commit();*/

    en.setText(preferences.getString("name",""));
    ee.setText(preferences.getString("email",""));
    ep.setText(preferences.getString("pass",""));
    ea.setText(preferences.getString("address",""));
    emo.setText(preferences.getString("contact",""));


    if (preferences.getString("image","").contains("null")) {

        imageView.setImageResource(R.drawable.upload);
    }
    else
    {
        Picasso.with(getApplicationContext()).load(preferences.getString("image", "")).fit().into(imageView);
    }
}


public void update()
{

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    String matchpattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$.";
    final String n = en.getText().toString().trim();
    final String e = ee.getText().toString().trim();
    final String p = ep.getText().toString().trim();
    final String a = ea.getText().toString().trim();
    final String m = emo.getText().toString().trim();


    if (n.isEmpty() && e.isEmpty() && p.isEmpty() && m.isEmpty()) {
        en.setError("Please Fill the Fields");
        ee.setError("Please Fill the Fields");
        ep.setError("Please Fill the Fields");
        ea.setError("Please Fill the Fields");
        emo.setError("Please Fill the Fields");
    } else if (!e.matches(matchpattern) && e.length() < 0) {
        ee.setError("Email Address is incorrect");
    } else if (p.length() < 8) {
        ep.setError("Password is too short");
    } else if (m.length() < 10) {
        emo.setError("Number in incorrect");
    } else {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UP, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Error",response);
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), User_Main_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
                    map.put(EMAIL_UP,e);
                    map.put(NAME_UP,n);
                    map.put(PASSWORD_UP,p);
                    map.put(ADDRESS_UP,a);
                    map.put(CONTACT_UP,m);
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
}
public void delete_profile(){

    RequestQueue requestQueue = Volley.newRequestQueue(this);

    final String ed = ee.getText().toString().trim();


    try {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_del_pro, new Response.Listener<String>() {
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                Log.e("Error", response);
                Intent intent = new Intent(getApplicationContext(), Start_up.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                editor=preferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();

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
                map.put(EMAIL_DEL, ed);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    } catch (Exception ex) {
        Log.e("Error", ex.getMessage());
    }


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
        Random random=new Random();

        final String string=String.valueOf(random.hashCode());
        final String name=preferences.getString("name","");
        final String image=getStringImage(bitmap);
        progressDialog.setMessage("Loading... Please Wait...!!!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),User_Main_Page.class));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> para=new HashMap<>();
                para.put(KEY_IMAGE,image);
                para.put(STR,string);
                para.put(NAME_UP,name);
                return para;
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
                imageView.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                        filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
                uploadImageToDb();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

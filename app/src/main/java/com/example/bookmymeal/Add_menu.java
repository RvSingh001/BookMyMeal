package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Add_menu extends AppCompatActivity {

    private EditText fname, fdes, fimg, fprice;
    private Button add;
    public static final String URL_ADD = "https://rvproject994.000webhostapp.com/insertfood.php";
    private MaterialSpinner materialSpinner;
    private static String categorysel="Drinks";
    private ImageView imageView;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        fname = findViewById(R.id.fname);
        fdes = findViewById(R.id.fdes);
        fprice = findViewById(R.id.fprice);
        add = findViewById(R.id.add);
        imageView=findViewById(R.id.food_img);
        materialSpinner=findViewById(R.id.spinner);
        materialSpinner.animate();
        materialSpinner.setItems("Drinks","Salads","Main Course");
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                categorysel=String.valueOf(materialSpinner.getText());
                Toast.makeText(getApplicationContext(),materialSpinner.getText(),Toast.LENGTH_LONG).show();


            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToDb();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

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

        final String food_name = fname.getText().toString().trim();
        final String food_des = fdes.getText().toString().trim();
        final String food_price = fprice.getText().toString().trim();
        final String category = categorysel;

        final ProgressDialog progressDialog=new ProgressDialog(this);

        final String food_img=getStringImage(bitmap);
        progressDialog.setMessage("Loading... Please Wait...!!!");
        progressDialog.setCancelable(true);
        progressDialog.show();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                URL_ADD,
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

                map.put("food_img",food_img);
                map.put("food_name", food_name);
                map.put("food_des", food_des);
                map.put("food_price", food_price);
                map.put("category", category);
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
                imageView.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                        filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


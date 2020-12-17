package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapters.Food_Cart;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Checkout extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton, radioButton1;
    private Button submit;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    public static final String ORDER_ID = "order_id";
    public static final String ORDERS_ITEM = "orders_item";
    public static final String CUSNAME = "cus_name";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String CONTACT = "contact";
    public static final String TOTAL = "total";
    public static final String PAYMENT = "payment";
    public String paydetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);

        radioGroup = findViewById(R.id.rg);
        radioButton = findViewById(R.id.radio);
        radioButton1 = findViewById(R.id.radio1);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioButton.isChecked()) {
                    paydetail = "Online Payment";
                    Intent intent=new Intent(Checkout.this,PaymentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else if (radioButton1.isChecked()) {
                    paydetail = "Cash On Delivery";
                    Intent intent=new Intent(Checkout.this,Orders.class);
                    startActivity(intent);



                }
                insertcusinfo();
                deleteCart();
            }
        });
    }


    public void insertcusinfo() {


        Date date = new Date();
        Random random=new Random();
        final String order_id = "#" + String.valueOf(random.hashCode());



        final String orders_item=Cart.order.substring(0,Cart.order.lastIndexOf(","));
        Toast.makeText(getApplicationContext(),orders_item,Toast.LENGTH_LONG).show();

        final String cus_name =preferences.getString("name", "");
        final String email =preferences.getString("email", "");
        final String address = preferences.getString("address", "");
        final String contact = preferences.getString("contact", "");
        final String total = String.valueOf(Food_Cart.grand_total);
        final String payment = paydetail;
        final String status="Pending";
        //Food_Cart.grand_total=0;
        Cart.order="Orders: ";
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading... Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/insertcusinfo.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.e("Error", response);
                    Toast.makeText(getApplicationContext(), "Order Palaced", Toast.LENGTH_LONG).show();

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
                    map.put(ORDER_ID, order_id);
                    map.put(ORDERS_ITEM, orders_item);
                    map.put(CUSNAME, cus_name);
                    map.put(EMAIL, email);
                    map.put(ADDRESS, address);
                    map.put(CONTACT, contact);
                    map.put(TOTAL, total);
                    map.put(PAYMENT, payment);
                    map.put("status",status);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }


    }

    public void deleteCart() {

        RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);

        final String email = preferences.getString("email", "");


        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://rvproject994.000webhostapp.com/deletecart.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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

                    map.put(EMAIL, email);


                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

    }
}

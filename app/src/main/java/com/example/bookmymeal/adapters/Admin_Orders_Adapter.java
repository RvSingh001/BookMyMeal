package com.example.bookmymeal.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmymeal.R;
import com.example.bookmymeal.Rating;
import com.example.bookmymeal.models.Model_Order;

import java.util.ArrayList;

public class Admin_Orders_Adapter extends RecyclerView.Adapter<Admin_Orders_Adapter.MyHolder> {

    private Context context;
    private ArrayList<Model_Order> modelOrderArrayList;
    private OnItemClickListener listener;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);
        void accept(int position);
        void completed(int position);


    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {

        this.listener=listener;

    }


    public Admin_Orders_Adapter(Context context, ArrayList<Model_Order> modelOrderArrayList) {
        this.context = context;
        this.modelOrderArrayList = modelOrderArrayList;
    }

    @NonNull
    @Override
    public Admin_Orders_Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylistadminorders,viewGroup,false);
        return new Admin_Orders_Adapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Orders_Adapter.MyHolder myHolder, int i) {
        Model_Order model_order=modelOrderArrayList.get(i);
        myHolder.oid.setText("Order Id: "+model_order.getOrder_id());
        myHolder.oitem.setText(model_order.getOrders_item());
        myHolder.cusname.setText("Name: "+model_order.getCus_name());
        myHolder.cusemail.setText("Email: "+model_order.getEmail());
        myHolder.cusaddress.setText("Address: "+model_order.getAddress());
        myHolder.cuscontact.setText("Contact: "+model_order.getContact());
        myHolder.total.setText("Total Amount: "+model_order.getTotal());
        myHolder.payment.setText("Payment: "+model_order.getPayment());
        myHolder.status.setText("Order Status: "+model_order.getStatus());
        myHolder.date.setText("Date: "+model_order.getOrder_date());
        if(model_order.getStatus().contains("Cancelled")||model_order.getStatus().contains("Delivered"))
        {
            myHolder.del.setVisibility(View.INVISIBLE);
            myHolder.accept.setVisibility(View.INVISIBLE);
            myHolder.com.setVisibility(View.INVISIBLE);
        }
       else if(model_order.getStatus().contains("Accepted"))
        {

            myHolder.accept.setVisibility(View.INVISIBLE);
            myHolder.del.setVisibility(View.INVISIBLE);

        }
       else if (model_order.getStatus().contains("Pending"))
       {
           myHolder.com.setVisibility(View.INVISIBLE);
       }


    }

    @Override
    public int getItemCount() {
        return modelOrderArrayList.size();
    }

    public void filterable(ArrayList<Model_Order> filterList)
    {
        modelOrderArrayList=filterList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView oid,oitem,cusname,cusemail,cusaddress,cuscontact,total,payment,date,status,review;
        public Button del,accept,com;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            oid=itemView.findViewById(R.id.aorderid);
            oitem=itemView.findViewById(R.id.aorderitem);
            cusname=itemView.findViewById(R.id.acusname);
            cusemail=itemView.findViewById(R.id.acusemail);
            cusaddress=itemView.findViewById(R.id.acusaddress);
            cuscontact=itemView.findViewById(R.id.acuscontact);
            total=itemView.findViewById(R.id.atotal);
            payment=itemView.findViewById(R.id.apayment);
            date=itemView.findViewById(R.id.adate);
            status=itemView.findViewById(R.id.astatus);
            del=itemView.findViewById(R.id.adelorder);
            accept=itemView.findViewById(R.id.accept);
            com=itemView.findViewById(R.id.com);


            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.accept(position);
                        }
                    }
                }
            });

            com.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.completed(position);
                        }
                    }
                }
            });
        }
    }
}

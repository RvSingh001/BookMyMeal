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

public class Orders_Adapter extends RecyclerView.Adapter<Orders_Adapter.MyHolder> {

    private Context context;
    private ArrayList<Model_Order> modelOrderArrayList;
    private OnItemClickListener listener;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);
        void Review(int position);
        void reorder(int position);


    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {

        this.listener=listener;

    }


    public Orders_Adapter(Context context, ArrayList<Model_Order> modelOrderArrayList) {
        this.context = context;
        this.modelOrderArrayList = modelOrderArrayList;
    }

    @NonNull
    @Override
    public Orders_Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylistorder,viewGroup,false);
        return new Orders_Adapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Orders_Adapter.MyHolder myHolder, int i) {
        Model_Order model_order=modelOrderArrayList.get(i);
        myHolder.oid.setText("Order Id: "+model_order.getOrder_id());
        myHolder.oitem.setText(model_order.getOrders_item());
        myHolder.total.setText("Total Amount: Rs.\n "+model_order.getTotal()+" /-");
        myHolder.payment.setText("Payment type: "+model_order.getPayment());
        myHolder.status.setText("Order Status: "+model_order.getStatus());
        myHolder.date.setText("Date: "+model_order.getOrder_date());

        if (model_order.getStatus().contains("Delivered")||model_order.getStatus().contains("Cancelled"))
        {
            myHolder.reorder.setVisibility(View.VISIBLE);
            myHolder.review.setVisibility(View.VISIBLE);
            myHolder.del.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return modelOrderArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView oid,oitem,cusname,cusemail,cusaddress,cuscontact,total,payment,date,status,review;
        public Button del,reorder;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            oid=itemView.findViewById(R.id.orderid);
            oitem=itemView.findViewById(R.id.orderitem);
            total=itemView.findViewById(R.id.total);
            payment=itemView.findViewById(R.id.payment);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            del=itemView.findViewById(R.id.delorder);
            reorder=itemView.findViewById(R.id.reorder);
            review=(Button)itemView.findViewById(R.id.review);

            reorder.setVisibility(View.INVISIBLE);


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
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.Review(position);
                        }
                    }
                }
            });
            reorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.reorder(position);
                        }
                    }
                }
            });
        }
    }
}

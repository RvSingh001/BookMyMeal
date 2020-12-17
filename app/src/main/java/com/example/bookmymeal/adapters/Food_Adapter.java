package com.example.bookmymeal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bookmymeal.R;
import com.example.bookmymeal.models.Food_Model;
import com.example.bookmymeal.models.Model_Cart;
import com.example.bookmymeal.models.Model_Drink;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.ArrayList;

public class Food_Adapter extends RecyclerView.Adapter<Food_Adapter.MyHolder> {
    private Context context;
    private ArrayList<Food_Model> food_modelArrayList;
    private Food_Adapter.OnItemClickListener listener;
    public String fcat;

    public interface OnItemClickListener
    {
        void OnItemClick(int position);
        void update(int position,String fn,String fd,String fp,String fc);
        void updateImg(int position);
    }
    public void setOnItemClickListener(Food_Adapter.OnItemClickListener listener)
    {

        this.listener=listener;

    }

    public Food_Adapter(Context context, ArrayList<Food_Model> food_modelArrayList) {
        this.context = context;
        this.food_modelArrayList = food_modelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylist_food,viewGroup,false);

        return new Food_Adapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Food_Model food_model=food_modelArrayList.get(i);
        Picasso.with(context).load(food_model.getFood_img()).fit().into(myHolder.fimg);
        myHolder.fname.setText(food_model.getFood_name());
        myHolder.fdes.setText(food_model.getFood_des());
        myHolder.fprice.setText(food_model.getFood_price());





    }


    @Override
    public int getItemCount() {
        return food_modelArrayList.size();
    }
    public void filterable(ArrayList<Food_Model> filterList)
    {
        food_modelArrayList=filterList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private EditText fname,fdes,fprice,category;
        private ImageView fimg;
        private Button update,delete;
        private RadioButton mac,sal,dri;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            fname=itemView.findViewById(R.id.fn);
            fdes=itemView.findViewById(R.id.fd);
            fprice=itemView.findViewById(R.id.fp);
           // category=itemView.findViewById(R.id.fc);
            fimg=itemView.findViewById(R.id.fimage);
            update=itemView.findViewById(R.id.update);
            delete=itemView.findViewById(R.id.delete);
            mac=itemView.findViewById(R.id.mac);
            sal=itemView.findViewById(R.id.sal);
            dri=itemView.findViewById(R.id.dri);
            fimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.updateImg(position);
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mac.isChecked())
                    {
                        fcat="Main Course";
                    }
                    if (sal.isChecked())
                    {
                        fcat="Salads";
                    }
                    if (dri.isChecked())
                    {
                        fcat="Drinks";
                    }
                    String fn= fname.getText().toString().trim();
                    String fd=fdes.getText().toString().trim();
                    String fp=fprice.getText().toString().trim();
                    String fc=fcat;

                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.update(position,fn,fd,fp,fc);

                        }
                    }
                }
            });

        }
    }
}

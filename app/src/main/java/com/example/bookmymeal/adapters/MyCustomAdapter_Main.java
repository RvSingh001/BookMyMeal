package com.example.bookmymeal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bookmymeal.R;
import com.example.bookmymeal.models.Model_Main;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter_Main extends RecyclerView.Adapter<MyCustomAdapter_Main.MyHolder>{
    private Context context;
    private ArrayList<Model_Main> model_mains;

    private MyCustomAdapter_Main.OnItemClickListener listener;



    public interface OnItemClickListener
    {
        void OnItemClick(int position);


    }
    public void setOnItemClickListener(MyCustomAdapter_Main.OnItemClickListener listener)
    {

        this.listener=listener;

    }


    public MyCustomAdapter_Main(Context context, ArrayList<Model_Main> model_mains) {
        this.context = context;
        this.model_mains = model_mains;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylist_main,viewGroup,false);
        return new MyCustomAdapter_Main.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Model_Main mmodel_main=model_mains.get(i);
        Picasso.with(context).load(mmodel_main.getMain_img()).fit().into(myHolder.m_img);
        myHolder.m_id.setText(mmodel_main.getId());
        myHolder.m_name.setText(mmodel_main.getMain_name());
        myHolder.m_des.setText(mmodel_main.getMain_des());
        myHolder.m_price.setText(mmodel_main.getMain_price());
    }

    @Override
    public int getItemCount() {
        return model_mains.size();
    }
    public void filterable(ArrayList<Model_Main> filterList)
    {
                model_mains=filterList;
                notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView m_img;
        public TextView m_id,m_name,m_des,m_price;
        private Button addmain;

        public MyHolder(@NonNull View itemView) {
            super(itemView);


                m_img=itemView.findViewById(R.id.mimage);
                m_id=itemView.findViewById(R.id.mid);
                m_name=itemView.findViewById(R.id.mname);
                m_des=itemView.findViewById(R.id.mdes);
                m_price=itemView.findViewById(R.id.mprice);
                addmain=itemView.findViewById(R.id.addmain);


        addmain.setOnClickListener(new View.OnClickListener() {
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
        }
    }



}

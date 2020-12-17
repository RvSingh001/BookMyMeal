package com.example.bookmymeal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookmymeal.R;
import com.example.bookmymeal.models.Food_Model;
import com.example.bookmymeal.models.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public  class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyHolder> {
    private Context context;
    private ArrayList<Model> modelArrayList;
    private MyCustomAdapter.OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void OnItemClick(int position);
        void approve(int position);


    }
    public void setOnItemClickListener(MyCustomAdapter.OnItemClickListener listener)
    {

        this.listener=listener;

    }

    public MyCustomAdapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylistuserinfo,viewGroup,false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        Model mmodel=modelArrayList.get(i);
        Picasso.with(context).load(mmodel.getImage()).fit().into(myHolder.uimg);
        myHolder.tid.setText(mmodel.getId());
        myHolder.tn.setText(mmodel.getName());
        myHolder.te.setText(mmodel.getEmail());
        myHolder.tp.setText(mmodel.getPassword());
        myHolder.ta.setText(mmodel.getAddress());
        myHolder.tm.setText(mmodel.getContact());
        myHolder.tapp.setText(mmodel.getActive());
        if(mmodel.getActive().contains("Active"))
        {
            myHolder.app.setVisibility(View.INVISIBLE);
        }
        if(mmodel.getActive().contains("Not Active"))
        {
            myHolder.app.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
    public void filterable(ArrayList<Model> filterList)
    {
        modelArrayList=filterList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private  TextView tid,tn,te,tp,ta,tm,tapp;
        private Button btn,app;
        private ImageView uimg;
        public MyHolder(@NonNull final View itemView) {
            super(itemView);
            tid=itemView.findViewById(R.id.uinfoid);
            tn=itemView.findViewById(R.id.uinfoname);
            te=itemView.findViewById(R.id.uinfoemail);
            tp=itemView.findViewById(R.id.uinfopassword);
            ta=itemView.findViewById(R.id.uinfoaddress);
            tm=itemView.findViewById(R.id.uinfomobile);
            btn=itemView.findViewById(R.id.del);
            app=itemView.findViewById(R.id.appr);
            tapp=itemView.findViewById(R.id.uapp);
            uimg=itemView.findViewById(R.id.uimg);

            btn.setOnClickListener(new View.OnClickListener() {
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
            app.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.approve(position);
                        }
                    }
                }
            });



        }
    }
}

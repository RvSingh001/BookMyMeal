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
import com.example.bookmymeal.models.Model_Main;
import com.example.bookmymeal.models.Model_Salad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCustomAdapter_Salad extends RecyclerView.Adapter<MyCustomAdapter_Salad.MyHolder> {
    private Context context;
    private ArrayList<Model_Salad> model_salads;
    private MyCustomAdapter_Salad.OnItemClickListener listener;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);


    }
    public void setOnItemClickListener(MyCustomAdapter_Salad.OnItemClickListener listener)
    {

        this.listener=listener;

    }

    public MyCustomAdapter_Salad(Context context, ArrayList<Model_Salad> model_salads) {
        this.context = context;
        this.model_salads = model_salads;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylist_salad,viewGroup,false);
        return new MyCustomAdapter_Salad.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Model_Salad mmodel_salad=model_salads.get(i);
        Picasso.with(context).load(mmodel_salad.getSalad_img()).fit().into(myHolder.s_img);
        myHolder.s_id.setText(mmodel_salad.getId());
        myHolder.s_name.setText(mmodel_salad.getSalad_name());
        myHolder.s_des.setText(mmodel_salad.getSalad_des());
        myHolder.s_price.setText(mmodel_salad.getSalad_price());
    }

    @Override
    public int getItemCount() {
        return model_salads.size();
    }
    public void filterable(ArrayList<Model_Salad> filterList)
    {
        model_salads=filterList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public ImageView s_img;
        public TextView s_id,s_name,s_des,s_price;
        public Button addsalad;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            s_img=itemView.findViewById(R.id.simage);
            s_id=itemView.findViewById(R.id.sid);
            s_name=itemView.findViewById(R.id.sname);
            s_des=itemView.findViewById(R.id.sdes);
            s_price=itemView.findViewById(R.id.sprice);
            addsalad=itemView.findViewById(R.id.addsalad);

            addsalad.setOnClickListener(new View.OnClickListener() {
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

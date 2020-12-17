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
import com.example.bookmymeal.models.Model_Drink;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCustomAdapter_Drink extends RecyclerView.Adapter<MyCustomAdapter_Drink.MyHolder> {
    private Context context;
    private ArrayList<Model_Drink> model_drinkArrayList;
    private OnItemClickListener listener;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);


    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {

        this.listener=listener;

    }



    public MyCustomAdapter_Drink(Context context, ArrayList<Model_Drink> model_drinkArrayList) {
        this.context = context;
        this.model_drinkArrayList = model_drinkArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylist_drink,viewGroup,false);
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Model_Drink mmodel_drink=model_drinkArrayList.get(i);
        Picasso.with(context).load(mmodel_drink.getDrink_img()).fit().into(myHolder.d_img);
        myHolder.d_id.setText(mmodel_drink.getId());
        myHolder.d_name.setText(mmodel_drink.getDrink_name());
        myHolder.d_des.setText(mmodel_drink.getDrink_des());
        myHolder.d_price.setText(mmodel_drink.getDrink_price());
    }

    @Override
    public int getItemCount() {
        return model_drinkArrayList.size();
    }
    public void filterable(ArrayList<Model_Drink> filterList)
    {
        model_drinkArrayList=filterList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView d_img;
        public TextView d_id,d_name,d_des,d_price;
        public Button add_drink;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            d_img=itemView.findViewById(R.id.dimage);
            d_id=itemView.findViewById(R.id.did);
            d_name=itemView.findViewById(R.id.dname);
            d_des=itemView.findViewById(R.id.ddes);
            d_price=itemView.findViewById(R.id.dprice);
            add_drink=itemView.findViewById(R.id.adddrink);

            add_drink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_drink.setVisibility(View.INVISIBLE);
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

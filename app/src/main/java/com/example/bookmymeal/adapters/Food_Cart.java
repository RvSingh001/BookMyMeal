package com.example.bookmymeal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookmymeal.R;
import com.example.bookmymeal.models.Model_Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Food_Cart extends RecyclerView.Adapter<Food_Cart.MyHolder> {
    private Context context;
    private ArrayList<Model_Cart> model_cartArrayList;
    private Food_Cart.OnItemClickListener listener;
    public static int grand_total;





    public interface OnItemClickListener
    {
        void OnItemClick(int position,int pr,int total_new) throws InterruptedException;
        void remove(int position);

    }
    public void setOnItemClickListener(Food_Cart.OnItemClickListener listener)
    {

        this.listener=listener;

    }


    public Food_Cart(Context context, ArrayList<Model_Cart> model_cartArrayList) {
        this.context = context;
        this.model_cartArrayList = model_cartArrayList;


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_cartlist,viewGroup,false);

        return new Food_Cart.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Model_Cart model_cart=model_cartArrayList.get(i);
        Picasso.with(context).load(model_cart.getFood_image()).fit().into(myHolder.imgview);
        myHolder.tn.setText(model_cart.getFood_name());
        myHolder.tp.setText(model_cart.getFood_price());

    }

    @Override
    public int getItemCount() {
        return model_cartArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tn,tp,tot;
        public ImageView imgview;
        public EditText qun;
        public ElegantNumberButton btn;
        public int price,pr;
        int total_old=0;
        int total_new;
        public Button remove;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tn=itemView.findViewById(R.id.cart_fn);
            tp=itemView.findViewById(R.id.cart_fp);
            tot=itemView.findViewById(R.id.total);
            btn=itemView.findViewById(R.id.elbtn);
            imgview=itemView.findViewById(R.id.cartimg);
            remove=itemView.findViewById(R.id.remove);
            btn.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int total_new;*/

                    int pos=getAdapterPosition();
                    Model_Cart m=model_cartArrayList.get(pos);
                    pr=Integer.parseInt(btn.getNumber());
                    price=Integer.parseInt(m.getFood_price());
                    total_new=price*pr;
                    tot.setText(String.valueOf(total_new));
                    if(total_old<total_new)
                    {

                            grand_total+=price;
                            total_old = total_new;

                    }
                    else if(total_old>total_new)
                    {
                        grand_total-=price;
                        total_old=total_new;

                    }

                    if (listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            try {
                                listener.OnItemClick(position,pr,total_new);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }


            });
remove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (listener!=null)
        {
            int position=getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION) {
                grand_total=grand_total-total_new;
                listener.remove(position);
            }

    }
}


        });
    }
}
}

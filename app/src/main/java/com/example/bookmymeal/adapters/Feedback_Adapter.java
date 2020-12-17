package com.example.bookmymeal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookmymeal.R;
import com.example.bookmymeal.models.Model_feed;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Feedback_Adapter extends RecyclerView.Adapter<Feedback_Adapter.MyHolder> {
    private Context context;
    private ArrayList<Model_feed> model_feedArrayList;

    public Feedback_Adapter(Context context, ArrayList<Model_feed> model_feedArrayList) {
        this.context = context;
        this.model_feedArrayList = model_feedArrayList;
    }

    @NonNull
    @Override
    public Feedback_Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mylist_feed,viewGroup,false);
        return new Feedback_Adapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Feedback_Adapter.MyHolder myHolder, int i) {

        Model_feed model_feed=model_feedArrayList.get(i);
        myHolder.name.setText("Name: "+model_feed.getName());
        myHolder.email.setText("Email: "+model_feed.getEmail());
        myHolder.feed.setText("Feedback: "+model_feed.getFeed());


        if (model_feed.getRate().contains("Terriable"))
        {
            myHolder.rate.setBackgroundResource(R.drawable.bad);
        }
        else if (model_feed.getRate().contains("Bad"))
        {
            myHolder.rate.setBackgroundResource(R.drawable.bad1);
        }
        else if (model_feed.getRate().contains("Okay"))
        {
            myHolder.rate.setBackgroundResource(R.drawable.okay);
        }
        else if (model_feed.getRate().contains("Good"))
        {
            myHolder.rate.setBackgroundResource(R.drawable.smiley);
        }
        else if (model_feed.getRate().contains("Great"))
        {
            myHolder.rate.setBackgroundResource(R.drawable.greate);
        }



    }

    @Override
    public int getItemCount() {
        return model_feedArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView name,email,feed,rate;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.namefeed);
            email=itemView.findViewById(R.id.emailfeed);
            feed=itemView.findViewById(R.id.feed);
            rate=itemView.findViewById(R.id.rating);

        }
    }
}

package com.example.helphomeless;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SAadapter extends RecyclerView.Adapter<SAadapter.ViewHolder>  {

    List<SAList> items;
    Context context;

    public SAadapter(List<SAList> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.space_search_acq,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final SAList item = items.get(i);
        viewHolder.textViewName.setText(item.getName());
        viewHolder.textViewAdd.setText(item.getAddress());

        Picasso.get()
                .load(item.getPhotoloc())
                .into(viewHolder.imageView);

        viewHolder.reqImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,item.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,AddRequest.class);
                intent.putExtra("sid",item.getSid());
                intent.putExtra("uid",item.getUid());
                intent.putExtra("aid",item.getAid());
                intent.putExtra("uname",item.getName());
                intent.putExtra("aname",item.getAname());
                intent.putExtra("address",item.getAddress());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewAdd;
        private ImageView imageView;
        private ImageView reqImgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.name);
            textViewAdd = (TextView) itemView.findViewById(R.id.address);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            reqImgView = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}


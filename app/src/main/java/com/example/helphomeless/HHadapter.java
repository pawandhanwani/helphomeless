package com.example.helphomeless;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HHadapter extends RecyclerView.Adapter<HHadapter.ViewHolder> {

    Context context;
    List<HHlist> listItems;

    public HHadapter(Context context, List<HHlist> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.helper_history_view,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HHlist item = listItems.get(i);
        viewHolder.addView.setText(item.getAddress());
        viewHolder.dateView.setText(item.getDate());
        viewHolder.occView.setText(item.getName());
        viewHolder.statusView.setText(item.getStatus());
        Picasso.get()
                .load(item.getPhotoloc())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        private TextView addView, dateView, occView,statusView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            addView = (TextView) itemView.findViewById(R.id.address);
            dateView = (TextView) itemView.findViewById(R.id.date);
            occView = (TextView) itemView.findViewById(R.id.occupier);
            statusView = (TextView) itemView.findViewById(R.id.status);
        }
    }
}

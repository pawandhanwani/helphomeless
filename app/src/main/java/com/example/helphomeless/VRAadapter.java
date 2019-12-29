package com.example.helphomeless;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VRAadapter  extends RecyclerView.Adapter<VRAadapter.ViewHolder> {

    List<AVRList> listItems;
    Context context;

    public VRAadapter(List<AVRList> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.acq_req_view,viewGroup,false);

        return new VRAadapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AVRList item = listItems.get(i);
        viewHolder.idView.setText("HHR-"+item.id);
        viewHolder.statusView.setText(item.getStatus());
    }

    @Override
    public int getItemCount() {

        return listItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idView,statusView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = (TextView) itemView.findViewById(R.id.id);
            statusView = (TextView) itemView.findViewById(R.id.status);
        }
    }
}

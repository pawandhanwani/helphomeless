package com.example.helphomeless;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class VRPAdapter extends RecyclerView.Adapter<VRPAdapter.ViewHolder>  {

    void acceptReqUtil(Context context, final String rid, final String sid)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.domelements.com/services/homeless/AcceptRequest.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", rid);
                params.put("sid",sid);

                return params;
            }
        };
        queue.add(postRequest);

    }

    void rejectReqUtil(Context context, final String rid)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.domelements.com/services/homeless/RejectRequest.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", rid);
                return params;
            }
        };
        queue.add(postRequest);

    }

    List<VRPList> listitems;
    Context context;

    public VRPAdapter(List<VRPList> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.req_view_poster,viewGroup,false);

        return new VRPAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final VRPList item = listitems.get(i);
        viewHolder.idView.setText("HHR-"+item.getRid());
        viewHolder.nameView.setText(item.name);
        viewHolder.statusView.setText(item.getStatus());
        if(item.getStatus().equals("Rejected") || item.getStatus().equals("Approved")) {
            viewHolder.buttonLayout.setVisibility(View.INVISIBLE);
        }
        viewHolder.acceptImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Accepted",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(context)
                        .setTitle("Accept Request")
                        .setMessage("Accepting this request will reject all other request automatically. Do you want to continue?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String rid = ""+item.getRid();
                                String sid = ""+item.getSid();
                                acceptReqUtil(context,rid,sid);
                                viewHolder.statusView.setText("Approved");
                                viewHolder.buttonLayout.setVisibility(View.INVISIBLE);

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        viewHolder.rejectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Rejected",Toast.LENGTH_SHORT).show();
                rejectReqUtil(context,""+item.getRid());
                viewHolder.statusView.setText("Rejected");
                viewHolder.buttonLayout.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idView;
        private TextView nameView;
        private TextView statusView;
        private GridLayout buttonLayout;
        private ImageView acceptImg;
        private ImageView rejectImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = (TextView) itemView.findViewById(R.id.id);
            nameView = (TextView) itemView.findViewById(R.id.name);
            statusView = (TextView) itemView.findViewById(R.id.Status);
            buttonLayout = (GridLayout) itemView.findViewById(R.id.buttonLayout);
            acceptImg = (ImageView) itemView.findViewById(R.id.aceept);
            rejectImg = (ImageView) itemView.findViewById(R.id.reject);
        }
    }


}

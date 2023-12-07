package com.myfinalproject.hydrobill;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> uBarangayName;

    public MyAdapter(Context context, ArrayList<String> uBarangayName) {
        this.context = context;
        this.uBarangayName = uBarangayName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.name_of_barangay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.names.setText(String.valueOf(uBarangayName.get(position)));
    }

    @Override
    public int getItemCount() {
        return uBarangayName.size();
    }

    public void updateData(ArrayList<String> newData) {
        uBarangayName.clear();
        uBarangayName.addAll(newData);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView names;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.textname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, list_of_consumer_in_barangay.class);
                    intent.putExtra("names", uBarangayName.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

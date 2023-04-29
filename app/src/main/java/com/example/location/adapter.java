package com.example.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    public static ArrayList<contact> contacts = new ArrayList<>();

    public adapter(ArrayList<contact> contacts) {
        this.contacts = contacts;
    }

    public void search(ArrayList<contact> searchList) {
        contacts=searchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.obj, parent, false);
        CheckBox r = view.findViewById(R.id.r);
        return new ViewHolder(view,r);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        contact ins = contacts.get(position);
        holder.name.setText(ins.name);
        holder.number.setText(ins.num);
        holder.r.setChecked(ins.ask);
        holder.r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle checkbox click event here
                ins.ask= ((CheckBox) v).isChecked();
            }
        });
        //holder.r.setChecked(ins.ask);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;
        CheckBox r;
        public ViewHolder(@NonNull View itemView, CheckBox r) {
            super(itemView);
            this.r=r;
            //r = itemView.findViewById(R.id.r);
            name = itemView.findViewById(R.id.tvname);
            number = itemView.findViewById(R.id.tvnumber);

        }
    }
}

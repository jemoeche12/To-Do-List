package com.pawansingh.to_dolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Importa la clase Pair de Android si aún no la tienes, o usa android.util.Pair
import android.util.Pair; // Esta es la clase Pair común en Android

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Pair<String, String>> items;

    public MyAdapter(List<Pair<String, String>> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, String> currentTaskPair = items.get(position);

        String taskDescription = currentTaskPair.first;
        String taskCategory = currentTaskPair.second;

        holder.textView.setText(taskDescription);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
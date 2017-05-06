package com.example.gold.recyclerviewstudy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gold on 2017. 3. 18..
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Student> items = new ArrayList<>();
    View view;


    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Student item = items.get(position);
        holder.txt1.setText(item.getName());
        holder.txt2.setText(item.getScore());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2;

        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
        }
    }
}

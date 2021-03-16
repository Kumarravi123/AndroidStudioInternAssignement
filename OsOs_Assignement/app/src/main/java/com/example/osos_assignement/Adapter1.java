package com.example.osos_assignement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.MyViewHolder> {
    MainActivity mainActivity;
    List<Model1> model1List;

    public Adapter1(MainActivity mainActivity, List<Model1> model1List) {
        this.mainActivity = mainActivity;
        this.model1List = model1List;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.layout_recycle1,parent,false);


        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model1 model1=model1List.get(position);
        holder.item_image.setImageResource(model1.getImage());
        holder.item_title.setText(model1.getTitle());

    }

    @Override
    public int getItemCount() {
        return model1List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        ImageView item_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.title_rv1);
            item_image=itemView.findViewById(R.id.image_rv1);

        }

    }

}

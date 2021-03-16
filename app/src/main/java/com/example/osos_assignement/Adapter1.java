package com.example.osos_assignement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.MyViewHolder> {
    MainActivity mainActivity;
    List<Model1> model1List;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
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

        holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.selectMultiImages(position,model1);
            }
        });

        // GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.rv_child.getContext(),model1List.get(position).getStrImage().size());
        // recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        if (model1List.get(position).getStrImage().size()>0) {
            holder.rv_child.setVisibility(View.VISIBLE);
            GridLayoutManager layoutManager = new GridLayoutManager(holder.rv_child.getContext(), 4);

            layoutManager.setInitialPrefetchItemCount(model1List.get(position).getStrImage().size());

            // Create an instance of the child
            // item view adapter and set its
            // adapter, layout manager and RecyclerViewPool
            Adapater2 childItemAdapter = new Adapater2(model1.getStrImage());
            holder.rv_child.setLayoutManager(layoutManager);
            holder.rv_child.setAdapter(childItemAdapter);
            holder.rv_child.setRecycledViewPool(viewPool);
        }else
        {
            holder.rv_child.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return model1List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        ImageView item_image;
        RecyclerView rv_child;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.title_rv1);
            item_image=itemView.findViewById(R.id.image_rv1);
            rv_child = itemView.findViewById(R.id.rv_child);

        }

    }


}

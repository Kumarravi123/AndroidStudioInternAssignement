package com.example.osos_assignement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Adapater2 extends RecyclerView.Adapter<Adapater2.ViewHolder> {
    private List<String> ChildItemList;

    public Adapater2(List<String> childItemList) {
        ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
// Here we inflate the corresponding
        // layout of the child item

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BitmapFactory.Options bf_option = new BitmapFactory.Options();

        bf_option.inDither = false;
        bf_option.inPurgeable = true;
        bf_option.inInputShareable = true;
        bf_option.inTempStorage = new byte[32 * 1024];
        FileInputStream fs = null;
        Bitmap bm;
        try {
            fs = new FileInputStream(new File(ChildItemList.get(position).toString()));

            if (fs != null) {
                bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bf_option);
                // bm = BitmapFactory.decodeFile(_path)
                holder.img_view.setImageBitmap(bm);
                holder.img_view.setId(position);
                //                imageView.setLayoutParams(new GridView.LayoutParams(400, 300));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return ChildItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_view = itemView.findViewById(R.id.image_grid);
        }
    }
}

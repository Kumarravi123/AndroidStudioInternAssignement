package com.example.osos_assignement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    List<Model1> model1List;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=findViewById(R.id.floatingaction_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View view=getLayoutInflater().inflate(R.layout.customalert,null);
                EditText editText=view.findViewById(R.id.title);
                Button button_oka=view.findViewById(R.id.button1);
                Button button_cancel=view.findViewById(R.id.button2);
                builder.setView(view);
                //builder.show();
                AlertDialog alertDialog=builder.create();
                alertDialog.setCanceledOnTouchOutside(false);


                button_oka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.setVisibility(v.VISIBLE);
                        recyclerView=v.findViewById(R.id.recycle_view);
                        Adapter1 adapter1=new Adapter1(MainActivity.this,model1List);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter1);








                    }
                });
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });

    }

}
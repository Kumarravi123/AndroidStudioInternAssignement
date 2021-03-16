package com.example.osos_assignement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    List<Model1> model1List;
    List<String> imagesPathList;

    Adapter1 adapter1;
    Model1 selecteditem_model;
    int position;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    static boolean havePermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=findViewById(R.id.floatingaction_button);
        recyclerView=findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model1List = new ArrayList<>();
        checkPermission(MainActivity.this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View view=getLayoutInflater().inflate(R.layout.customalert,null);
                EditText editText=view.findViewById(R.id.title);
                Button button_oka=view.findViewById(R.id.button1);
                Button button_cancel=view.findViewById(R.id.button2);
                builder.setView(view);
                AlertDialog alertDialog=builder.create();
                alertDialog.setCanceledOnTouchOutside(false);


                button_oka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model1List.size()>0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            boolean isExists = false;
                            for (int i=0;i<model1List.size();i++){
                                if (editText.getText().toString().trim().equals(model1List.get(i).getTitle())){
                                    isExists = true;
                                    editText.setText("");
                                    Toast.makeText(MainActivity.this, "Already this title exists,please add another", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (!isExists) {
                                model1List.add(new Model1(editText.getText().toString(), new ArrayList<>()));
                                adapter1 = new Adapter1(MainActivity.this, model1List);
                                recyclerView.setAdapter(adapter1);
                                alertDialog.dismiss();
                            }
                        }else
                        {
                            recyclerView.setVisibility(View.VISIBLE);
                            model1List.add(new Model1(editText.getText().toString(), new ArrayList<>()));
                            adapter1 = new Adapter1(MainActivity.this, model1List);
                            recyclerView.setAdapter(adapter1);
                            alertDialog.dismiss();
                        }
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




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                havePermission = false;
                return false;
            } else {
                havePermission = true;
                return true;
            }
        } else {
            havePermission = true;
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    havePermission = true;

                } else {

                    // permission denied, boo! Disable the
                    havePermission = true;
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void selectMultiImages(int pos,Model1 model1) {
        selecteditem_model = model1;
        position = pos;
        if (havePermission) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
        }else{
            checkPermission(MainActivity.this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 101 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                imagesPathList = new ArrayList<String>();
            /*    if (!TextUtils.isEmpty(data.getStringExtra("data"))){
                String[] imagesPath = data.getStringExtra("data").split("\\|");

                for (int i = 0; i < imagesPath.length; i++) {
                    imagesPathList.add(imagesPath[i]);
                }

                model1List.add(new Model1(selecteditem_model.getTitle(),imagesPathList));

                recyclerView.setAdapter(new Adapter1(this, model1List));
                adapter1.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
            */

                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        File file = new File(imageUri.getPath());
                        String[] filePath = file.getPath().split(":");
                        String image_id = filePath[filePath.length - 1];

                        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                                MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
                        if (cursor!=null) {
                            cursor.moveToFirst();
                            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                            imagesPathList.add(imagePath);
                            cursor.close();
                        }

                    }

                    model1List.set(position,new Model1(selecteditem_model.getTitle(),imagesPathList));

                    recyclerView.setAdapter(new Adapter1(this, model1List));
                    adapter1.notifyDataSetChanged();
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            } else if (data.getData() != null) {
                String imagePath = data.getData().getPath();
                imagesPathList.add(imagePath);

                model1List.set(position,new Model1(selecteditem_model.getTitle(),imagesPathList));
                recyclerView.setAdapter(new Adapter1(this, model1List));
                adapter1.notifyDataSetChanged();
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }else
            {
                Log.v("exception","null");
            }
        } catch (Exception e) {
            Log.v("Exception",e.getMessage()+",local"+e.getLocalizedMessage());
            Toast.makeText(this, "Something went wrong"+e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }

}
package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting extends AppCompatActivity implements View.OnClickListener{
    Button facebtn;
    int width;
    int height;
    Dialog dialog;

    CircleImageView myprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        width=dm.widthPixels;
        height = dm.heightPixels;
        myprofile=findViewById(R.id.myprofile);
        myprofile.setBackgroundResource(R.drawable.icon_woman2);
        facebtn = findViewById(R.id.face);
        facebtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.face:
                dialog= new Dialog(Setting.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_choose_profile);
                WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();
                wm.copyFrom(dialog.getWindow().getAttributes());
                wm.width=(width/3)*2;
                wm.height=(height/3)*2;

                Button man1=dialog.findViewById(R.id.man1);
                Button man2=dialog.findViewById(R.id.man2);
                Button woman1=dialog.findViewById(R.id.woman1);
                Button woman2=dialog.findViewById(R.id.woman2);
                Button camera = dialog.findViewById(R.id.camera);
                man1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myprofile.setImageResource(R.drawable.icon_man);
                        dialog.dismiss();
                    }
                });

                man2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myprofile.setImageResource(R.drawable.icon_man2);
                        dialog.dismiss();
                    }
                });
                woman1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myprofile.setImageResource(R.drawable.icon_woman);
                        dialog.dismiss();
                    }
                });
                woman2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myprofile.setImageResource(R.drawable.icon_woman2);
                        dialog.dismiss();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i,0);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            }

        }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
    { super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            myprofile.setImageBitmap(imageBitmap);
        }
    }


}
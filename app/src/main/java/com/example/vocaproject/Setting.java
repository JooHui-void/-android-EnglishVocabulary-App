package com.example.vocaproject;

import static com.example.vocaproject.LoginActivity.mUserAccount;
import static com.example.vocaproject.LoginActivity.userView;
import static com.example.vocaproject.MainTestActivity.mUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting extends AppCompatActivity {
    public static final int PICK_FROM_ALBUM = 1;

    int width;
    int height;
    Dialog dialog;

    private Uri imageUri;
    private String pathUri;
    private File tempFile;
    private CircleImageView myprofile;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorage = FirebaseStorage.getInstance();
//        StorageReference storageRef = mStorage.getReferenceFromUrl(mUserAccount.getProfileImageUrl());

        setContentView(R.layout.activity_setting);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        width=dm.widthPixels;
        height = dm.heightPixels;
        myprofile=findViewById(R.id.myprofile);
        Glide.with(this).load(mUserAccount.getProfileImageUrl()).into(myprofile);

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectProfile(view);
            }
        });
    }

    private void gotoAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { // 코드가 틀릴경우
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: { // 코드 일치
                // Uri
                imageUri = data.getData();
                Log.d("Test", "test " + imageUri);
                pathUri = getPath(data.getData());
                myprofile.setImageURI(imageUri); // 이미지 띄움
                break;
            }
        }
        uploadUserImage();
    }

    private String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void getPath2(String string){
        StorageReference storageRef = mStorage.getReference();
        StorageReference pathReference=storageRef.child("usersprofileImages").child("uid/" + string);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(getApplicationContext(), "프로필 사진 재설정!", Toast.LENGTH_SHORT).show();
                imageUri = uri;
                mUserAccount.setProfileImageUrl(imageUri.toString());
                FirebaseDatabase.getInstance().getReference("VocaProject").child("UserAccount").child(mUser.getUid()).setValue(mUserAccount);
                Log.d("Test", mUserAccount.getProfileImageUrl());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "프로필 사진 재설정 실패...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadUserImage(){
        final Uri file = Uri.fromFile(new File(pathUri));

        StorageReference storageReference = mStorage.getReference()
                .child("usersprofileImages").child("uid/"+mUserAccount.getIdToken());
        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                while (!imageUrl.isComplete()) ;

                mUserAccount.setProfileImageUrl(imageUrl.getResult().toString());
                FirebaseDatabase.getInstance().getReference("VocaProject").child("UserAccount").child(mUser.getUid()).setValue(mUserAccount);

                Toast.makeText(Setting.this, "이미지 업로드 성공!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectProfile(View v){

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
//        Button camera = dialog.findViewById(R.id.camera);
        Button camera2 = dialog.findViewById(R.id.camera2);

        man1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myprofile.setImageResource(R.drawable.icon_man);
                pathUri="icon_man.jpg";
                getPath2(pathUri);
                dialog.dismiss();
            }
        });

        man2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myprofile.setImageResource(R.drawable.icon_man2);
                pathUri="icon_man2.jpg";
                getPath2(pathUri);
                dialog.dismiss();
            }
        });
        woman1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myprofile.setImageResource(R.drawable.icon_woman);
                pathUri="icon_woman.jpg";
                getPath2(pathUri);
                dialog.dismiss();
            }
        });
        woman2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myprofile.setImageResource(R.drawable.icon_woman2);
                pathUri="icon_woman2.jpg";
                getPath2(pathUri);
                dialog.dismiss();
            }
        });
//        camera.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(i,0);
//                dialog.dismiss();
//            }
//        });
        camera2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                gotoAlbum();
            }
        });
        dialog.show();
    }
}
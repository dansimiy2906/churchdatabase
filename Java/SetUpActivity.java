package com.example.allsongs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpActivity extends AppCompatActivity {

    private Button save;
    private EditText fullname,phone;
    private CircleImageView profile_image;
    private ProgressDialog loadingBar;


    private DatabaseReference userRef;
    private StorageReference UserProfileImageRef;
    private FirebaseAuth mAuth;
    private Uri ImageUri;

    String currentuserId;
    final static int Gallery_Pick = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        mAuth = FirebaseAuth.getInstance();
        currentuserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentuserId);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        fullname = (EditText) findViewById(R.id.full_name);
        phone = (EditText) findViewById(R.id.phone_et);
        profile_image = (CircleImageView) findViewById(R.id.ci_profile);
        save = (Button) findViewById(R.id.button_save);
        loadingBar = new ProgressDialog(this);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInfo();
            }

        });



        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if(snapshot.hasChild("profileImage")){
                        String image = snapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.ic_person).into(profile_image);
                    }
                    else{
                        Toast.makeText(SetUpActivity.this, "Please Select Profile Image First", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){
             ImageUri = data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){

                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait, while we updating your profile image...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();
                final StorageReference filepath = UserProfileImageRef.child(currentuserId + ".jpg");

                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUrl = uri.toString();
                                userRef.child("profileImage").setValue(downloadUrl).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SetUpActivity.this, "Stored To Database Successfully", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(SetUpActivity.this, "Error Occured:" + message, Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }

                                            }
                                        });

                            }
                        });
                    }
                });

            }
            else{
                Toast.makeText(this, "Cant Crop Image!!", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
    }


    void SaveInfo() {
        String name = fullname.getText().toString();
        String contact = phone.getText().toString();

        if (TextUtils.isEmpty(name) ){
            Toast.makeText(SetUpActivity.this, "Full Name is Required", Toast.LENGTH_SHORT).show();
        }
        else if (ImageUri == null){
            Toast.makeText(SetUpActivity.this, "Please Select Profile Image First!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Save User Profile");
            loadingBar.setMessage("Creating your Account,Please Wait....");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);


            HashMap userMap = new HashMap();
            userMap.put("Name",name);
            userMap.put("ContactDetail",contact);
            userMap.put("ChurchName","Agape Fellowship Center Madaraka");

            userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SetUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        sendUserToMain();
                        loadingBar.dismiss();
                    }
                    else{
                        String message =  task.getException().getMessage();
                        Toast.makeText(SetUpActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }

                private void sendUserToMain() {
                    Intent sendToMain = new Intent(SetUpActivity.this,MainActivity.class);
                    startActivity(sendToMain);
                    finish();
                }
            });


        }
    }


}
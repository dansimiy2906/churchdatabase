package com.example.allsongs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private Button save;
    private EditText fullname,phone;
    private CircleImageView profile_image;
    private ProgressDialog loadingBar;
    private DatabaseReference editProfileRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    final static int Gallery_Pick = 1;

    private StorageReference UserProfileImageRef;
    private Uri ImageUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        editProfileRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);

        fullname = (EditText) findViewById(R.id.full_name);
        phone = (EditText) findViewById(R.id.phone_et);
        profile_image = (CircleImageView) findViewById(R.id.ci_profile);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        save = (Button) findViewById(R.id.button_save);
        loadingBar = new ProgressDialog(this);

        editProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String previous_fullname = snapshot.child("Name").getValue().toString();
                    String previous_phone = snapshot.child("ContactDetail").getValue().toString();
                    String previous_profile_image = snapshot.child("profileImage").getValue().toString();

                    Picasso.get().load(previous_profile_image).placeholder(R.drawable.ic_person).into(profile_image);
                    phone.setText(previous_phone);
                    fullname.setText(previous_fullname);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoEdit();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
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

                loadingBar.setTitle("Profile Image Update");
                loadingBar.setMessage("Please wait, while we updating your profile image...");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();


                Uri resultUri = result.getUri();
                final StorageReference filepath = UserProfileImageRef.child(currentUserID + ".jpg");

                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUrl = uri.toString();
                                editProfileRef.child("profileImage").setValue(downloadUrl).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(EditProfileActivity.this, "Stored To Database Successfully", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(EditProfileActivity.this, "Error Occured:" + message, Toast.LENGTH_SHORT).show();
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

    private void DoEdit() {
        String updated_phone = phone.getText().toString();
        String updated_fullname = fullname.getText().toString();

        HashMap updatedprofile = new HashMap();
        updatedprofile.put("Name", updated_fullname);
        updatedprofile.put("ContactDetail", updated_phone);
        // AllSongsMap.put("name",userFullName);
        // AllSongsMap.put("profile_pic",userProfileImage);
        //AllSongsMap.put("Time",time);


        editProfileRef.updateChildren(updatedprofile).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Your Prifile Has Been Edited.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfileActivity.this, "Error occured While Editing Profile!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
package com.example.allsongs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class Register extends AppCompatActivity {
    private EditText registerEmail,registerPass,registerConfirmPass;
    private CheckBox registerShowPass;
    private Button registerBtn,toLoginBtn;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        
        loadingBar = new ProgressDialog(this);
        registerEmail = (EditText) findViewById(R.id.register_email_et);
        registerPass = (EditText) findViewById(R.id.register_password_et);
        registerConfirmPass = (EditText) findViewById(R.id.register_confirmpassword_et);
        registerShowPass = (CheckBox) findViewById(R.id.register_checkbox);
        registerBtn = (Button) findViewById(R.id.button_register);
        toLoginBtn = (Button) findViewById(R.id.signup_to_login);

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });




        registerShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    registerPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    registerConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    registerPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    registerConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });



    }


    private void CreateNewAccount() {
        String email = registerEmail.getText().toString();
        String pwd = registerPass.getText().toString();
        String cpwd = registerConfirmPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email Is Required", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "Password Is Required", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(cpwd)){
            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
        }
        else if(!pwd.equals(cpwd)){
            Toast.makeText(this, "Passwords Do Not Match!!!", Toast.LENGTH_SHORT).show();
        }

        else{
                loadingBar.setTitle("Creating New Account");
                loadingBar.setMessage("Creating your Account,Please Wait....");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                            sendUserToMain();
                            loadingBar.dismiss();
                        }
                        else{
                            String message = task.getException().getMessage();
                            Toast.makeText(Register.this, "Error Occured" + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }

                    }
                });

        }
    }

    private void sendUserToMain() {
        Intent sendToMain = new Intent(Register.this,SetUpActivity.class);
        startActivity(sendToMain);
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
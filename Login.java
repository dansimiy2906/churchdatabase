package com.example.allsongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText loginEmail,loginPwd;
    private TextView forgotPass;
    private CheckBox showPass;
    private Button loginToMain,GoToRegister;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);
        loginEmail = (EditText) findViewById(R.id.login_et);
        loginPwd = (EditText) findViewById(R.id.login_password_et);
        forgotPass = (TextView) findViewById(R.id.resetpassword);
        showPass = (CheckBox) findViewById(R.id.login_checkbox);
        loginToMain = (Button) findViewById(R.id.button_login);
        GoToRegister = (Button) findViewById(R.id.login_to_signup);

        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    loginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    loginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        loginToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();

            }
        });

        GoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegister();
            }
        });
    }

    private void AllowUserToLogin() {
        String email = loginEmail.getText().toString();
        String pwd = loginPwd.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email Is Required!!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "Password Is Required!!", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Logining In");
            loadingBar.setMessage("This Might Take a few Seconds,Please Wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    sendUserToMain();
                    loadingBar.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            });
        }
    }

    private void sendUserToMain() {
        Intent ToMain = new Intent(Login.this,MainActivity.class);
        startActivity(ToMain);
        finish();
    }

    private void sendUserToRegister() {
        Intent ToRegister = new Intent(Login.this,Register.class);
        startActivity(ToRegister);
        finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            sendUserToMain();
        }
    }
}
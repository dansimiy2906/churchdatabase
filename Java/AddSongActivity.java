package com.example.allsongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddSongActivity extends AppCompatActivity {
    Button date,save;
    EditText showDate,one,two,three,four,five,six;
    RadioButton A1,A2,A3,A4,A5,A6,A_shap1,A_shap2,A_shap3,A_shap4,A_Shap5,A_shap6,B1,B2,B3,B4,B5,B6,C1,C2,C3,C4,C5,C6,C_shap1,C_shap2,C_shap3,C_shap4,C_Shap5,C_shap6,D1,D2,D3,D4,D5,D6,D_shap1,D_shap2,D_shap3,D_shap4,D_shap5,D_shap6,E1,E2,E3,E4,E5,E6,F1,F2,F3,F4,F5,F6,F_shap1,F_shap2,F_shap3,F_shap4,F_shap5,F_shap6,G1,G2,G3,G4,G5,G6,G_shap1,G_shap2,G_shap3,G_shap4,G_shap5,G_shap6,fast,slow;
    Context context;
    Calendar calendar = Calendar.getInstance();
    Locale id = new Locale("en", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY", id);
    Date Ministration_Date;
    private ProgressDialog loadingBar;


    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("All Songs");
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    String current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();


        loadingBar = new ProgressDialog(this);
        context = this;
        date = (Button) findViewById(R.id.btnDate);
        showDate = (EditText) findViewById(R.id.dom);
        one = (EditText) findViewById(R.id.song_one);
        two = (EditText) findViewById(R.id.song_Two);
        three = (EditText) findViewById(R.id.song_Three);
        four = (EditText) findViewById(R.id.song_Four);
        five = (EditText) findViewById(R.id.song_Five);
        six = (EditText) findViewById(R.id.song_Six);
        save = (Button) findViewById(R.id.btnSaveSongs);

        fast = (RadioButton) findViewById(R.id.rb_fast);
        slow = (RadioButton) findViewById(R.id.rb_slow);

        A1 = (RadioButton) findViewById(R.id.Key_A1);
        A2 = (RadioButton) findViewById(R.id.Key_A2);
        A3 = (RadioButton) findViewById(R.id.Key_A3);
        A4 = (RadioButton) findViewById(R.id.Key_A4);
        A5 = (RadioButton) findViewById(R.id.Key_A5);
        A6 = (RadioButton) findViewById(R.id.Key_A6);

        A_shap1 = (RadioButton) findViewById(R.id.Key_A1_Shap);
        A_shap2 = (RadioButton) findViewById(R.id.Key_A2_Shap);
        A_shap3 = (RadioButton) findViewById(R.id.Key_A3_Shap);
        A_shap4 = (RadioButton) findViewById(R.id.Key_A4_Shap);
        A_Shap5 = (RadioButton) findViewById(R.id.Key_A5_Shap);
        A_shap6 = (RadioButton) findViewById(R.id.Key_A6_Shap);

        B1 = (RadioButton) findViewById(R.id.Key_B1);
        B2 = (RadioButton) findViewById(R.id.Key_B2);
        B3 = (RadioButton) findViewById(R.id.Key_B3);
        B4 = (RadioButton) findViewById(R.id.Key_B4);
        B5 = (RadioButton) findViewById(R.id.Key_B5);
        B6 = (RadioButton) findViewById(R.id.Key_B6);

        C1 = (RadioButton) findViewById(R.id.Key_C1);
        C2 = (RadioButton) findViewById(R.id.Key_C2);
        C3 = (RadioButton) findViewById(R.id.Key_C3);
        C4 = (RadioButton) findViewById(R.id.Key_C4);
        C5 = (RadioButton) findViewById(R.id.Key_C5);
        C6 = (RadioButton) findViewById(R.id.Key_C6);

        C_shap1 = (RadioButton) findViewById(R.id.Key_C1_Shap);
        C_shap2 = (RadioButton) findViewById(R.id.Key_C2_Shap);
        C_shap3 = (RadioButton) findViewById(R.id.Key_C3_Shap);
        C_shap4 = (RadioButton) findViewById(R.id.Key_C4_Shap);
        C_Shap5 = (RadioButton) findViewById(R.id.Key_C5_Shap);
        C_shap6 = (RadioButton) findViewById(R.id.Key_C6_Shap);

        D1 = (RadioButton) findViewById(R.id.Key_D1);
        D2 = (RadioButton) findViewById(R.id.Key_D2);
        D3 = (RadioButton) findViewById(R.id.Key_D3);
        D4 = (RadioButton) findViewById(R.id.Key_D4);
        D5 = (RadioButton) findViewById(R.id.Key_D5);
        D6 = (RadioButton) findViewById(R.id.Key_D6);

        D_shap1 = (RadioButton) findViewById(R.id.Key_D1_Shap);
        D_shap2 = (RadioButton) findViewById(R.id.Key_D2_Shap);
        D_shap3 = (RadioButton) findViewById(R.id.Key_D3_Shap);
        D_shap4 = (RadioButton) findViewById(R.id.Key_D4_Shap);
        D_shap5 = (RadioButton) findViewById(R.id.Key_D5_Shap);
        D_shap6 = (RadioButton) findViewById(R.id.Key_D6_Shap);

        E1 = (RadioButton) findViewById(R.id.Key_E1);
        E2 = (RadioButton) findViewById(R.id.Key_E2);
        E3 = (RadioButton) findViewById(R.id.Key_E3);
        E4 = (RadioButton) findViewById(R.id.Key_E4);
        E5 = (RadioButton) findViewById(R.id.Key_E5);
        E6 = (RadioButton) findViewById(R.id.Key_E6);

        F1 = (RadioButton) findViewById(R.id.Key_F1);
        F2 = (RadioButton) findViewById(R.id.Key_F2);
        F3 = (RadioButton) findViewById(R.id.Key_F3);
        F4 = (RadioButton) findViewById(R.id.Key_F4);
        F5 = (RadioButton) findViewById(R.id.Key_F5);
        F6 = (RadioButton) findViewById(R.id.Key_F6);

        F_shap1 = (RadioButton) findViewById(R.id.Key_F1_Shap);
        F_shap2 = (RadioButton) findViewById(R.id.Key_F2_Shap);
        F_shap3 = (RadioButton) findViewById(R.id.Key_F3_Shap);
        F_shap4 = (RadioButton) findViewById(R.id.Key_F4_Shap);
        F_shap5 = (RadioButton) findViewById(R.id.Key_F5_Shap);
        F_shap6 = (RadioButton) findViewById(R.id.Key_F6_Shap);

        G1 = (RadioButton) findViewById(R.id.Key_G1);
        G2 = (RadioButton) findViewById(R.id.Key_G2);
        G3 = (RadioButton) findViewById(R.id.Key_G3);
        G4 = (RadioButton) findViewById(R.id.Key_G4);
        G5 = (RadioButton) findViewById(R.id.Key_G5);
        G6 = (RadioButton) findViewById(R.id.Key_G6);


        G_shap1 = (RadioButton) findViewById(R.id.Key_G1_Shap);
        G_shap2 = (RadioButton) findViewById(R.id.Key_G2_Shap);
        G_shap3 = (RadioButton) findViewById(R.id.Key_G3_Shap);
        G_shap4 = (RadioButton) findViewById(R.id.Key_G4_Shap);
        G_shap5 = (RadioButton) findViewById(R.id.Key_G5_Shap);
        G_shap6 = (RadioButton) findViewById(R.id.Key_G6_Shap);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar ctime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                final String savetime = currenttime.format(ctime.getTime());
                final String time =  savetime;



                String date = showDate.getText().toString();

                String fastSong = fast.getText().toString();
                String slowSong = slow.getText().toString();

                String One = one.getText().toString();
                String Two = two.getText().toString();
                String Three = three.getText().toString();
                String Four = four.getText().toString();
                String Five = five.getText().toString();
                String Six = six.getText().toString();
                String a1 = A1.getText().toString();
                String a2 = A2.getText().toString();
                String a3 = A3.getText().toString();
                String a4 = A4.getText().toString();
                String a5 = A5.getText().toString();
                String a6 = A6.getText().toString();
                String a1_shap = A_shap1.getText().toString();
                String a2_shap = A_shap2.getText().toString();
                String a3_shap = A_shap3.getText().toString();
                String a4_shap = A_shap4.getText().toString();
                String a5_shap = A_Shap5.getText().toString();
                String a6_shap = A_shap6.getText().toString();
                String b1 = B1.getText().toString();
                String b2 = B2.getText().toString();
                String b3 = B3.getText().toString();
                String b4 = B4.getText().toString();
                String b5 = B5.getText().toString();
                String b6 = B6.getText().toString();
                String c1 = C1.getText().toString();
                String c2 = C2.getText().toString();
                String c3 = C3.getText().toString();
                String c4 = C4.getText().toString();
                String c5 = C5.getText().toString();
                String c6 = C6.getText().toString();
                String c1_shap = C_shap1.getText().toString();
                String c2_shap = C_shap2.getText().toString();
                String c3_shap = C_shap3.getText().toString();
                String c4_shap = C_shap4.getText().toString();
                String c5_shap = C_Shap5.getText().toString();
                String c6_shap = C_shap6.getText().toString();
                String d1 = D1.getText().toString();
                String d2 = D2.getText().toString();
                String d3 = D3.getText().toString();
                String d4 = D4.getText().toString();
                String d5 = D5.getText().toString();
                String d6 = D6.getText().toString();
                String d1_shap = D_shap1.getText().toString();
                String d2_shap = D_shap2.getText().toString();
                String d3_shap = D_shap3.getText().toString();
                String d4_shap = D_shap4.getText().toString();
                String d5_shap = D_shap5.getText().toString();
                String d6_shap = D_shap6.getText().toString();
                String e1 = E1.getText().toString();
                String e2 = E2.getText().toString();
                String e3 = E3.getText().toString();
                String e4 = E4.getText().toString();
                String e5 = E5.getText().toString();
                String e6 = E6.getText().toString();
                String f1 = F1.getText().toString();
                String f2 = F2.getText().toString();
                String f3 = F3.getText().toString();
                String f4 = F4.getText().toString();
                String f5 = F5.getText().toString();
                String f6 = F6.getText().toString();
                String f1_shap = F_shap1.getText().toString();
                String f2_shap = F_shap2.getText().toString();
                String f3_shap = F_shap3.getText().toString();
                String f4_shap = F_shap4.getText().toString();
                String f5_shap = F_shap5.getText().toString();
                String f6_shap = F_shap6.getText().toString();
                String g1 =  G1.getText().toString();
                String g2 = G2.getText().toString();
                String g3 = G3.getText().toString();
                String g4 = G4.getText().toString();
                String g5 = G5.getText().toString();
                String g6 = G6.getText().toString();
                String g1_shap = G_shap1.getText().toString();
                String g2_shap = G_shap2.getText().toString();
                String g3_shap = G_shap3.getText().toString();
                String g4_shap = G_shap4.getText().toString();
                String g5_shap = G_shap5.getText().toString();
                String g6_shap = G_shap6.getText().toString();

                if (One.isEmpty()){
                    Toast.makeText(AddSongActivity.this, "Cannot Save a Null Song List,Please Input Atleast The Three First Songs!!", Toast.LENGTH_SHORT).show();
                }
                else if ( !(A1.isChecked() || A_shap1.isChecked() || B1.isChecked() ||C1.isChecked() || C_shap1.isChecked() || D1.isChecked() || D_shap1.isChecked() || E1.isChecked() || F1.isChecked() || F_shap1.isChecked() || G1.isChecked() || G_shap1.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Song One Key Must be Selected", Toast.LENGTH_SHORT).show();
                }

                else if (Two.isEmpty()){
                    Toast.makeText(AddSongActivity.this, "Cannot Save a Null Song List,Please Input Atleast The Three First Songs!!", Toast.LENGTH_SHORT).show();
                }
                else if (!(A2.isChecked() || A_shap2.isChecked() || B2.isChecked() ||C2.isChecked() || C_shap2.isChecked() || D2.isChecked() || D_shap2.isChecked() || E2.isChecked() || F2.isChecked() || F_shap2.isChecked() || G2.isChecked() || G_shap2.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Song Two Key Must be Selected", Toast.LENGTH_SHORT).show();
                }

                else if (Three.isEmpty()){
                    Toast.makeText(AddSongActivity.this, "Cannot Save a Null Song List,Please Input Atleast The Three First Songs!!", Toast.LENGTH_SHORT).show();
                }
                else if (!( A3.isChecked() || A_shap3.isChecked() || B3.isChecked() ||C3.isChecked() || C_shap3.isChecked() || D3.isChecked() || D_shap3.isChecked() || E3.isChecked() || F3.isChecked() || F_shap3.isChecked() || G3.isChecked() || G_shap3.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Song Three Key Must be Selected", Toast.LENGTH_SHORT).show();
                }
                else if (Four.isEmpty() && ( A4.isChecked() || A_shap4.isChecked() || B4.isChecked() ||C4.isChecked() || C_shap4.isChecked() || D4.isChecked() || D_shap4.isChecked() || E4.isChecked() || F4.isChecked() || F_shap4.isChecked() || G4.isChecked() || G_shap4.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Add Song Four To Key Selected", Toast.LENGTH_SHORT).show();
                }
                else if (!(Four.isEmpty()) && !( A4.isChecked() || A_shap4.isChecked() || B4.isChecked() ||C4.isChecked() || C_shap4.isChecked() || D4.isChecked() || D_shap4.isChecked() || E4.isChecked() || F4.isChecked() || F_shap4.isChecked() || G4.isChecked() || G_shap4.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Add Song Four Key", Toast.LENGTH_SHORT).show();
                }

                else if (Five.isEmpty() && ( A5.isChecked() || A_Shap5.isChecked() || B5.isChecked() ||C5.isChecked() || C_Shap5.isChecked() || D5.isChecked() || D_shap5.isChecked() || E5.isChecked() || F5.isChecked() || F_shap5.isChecked() || G5.isChecked() || G_shap5.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Add Song Five To Key Selected", Toast.LENGTH_SHORT).show();
                }
                else if (!(Five.isEmpty()) && !( A5.isChecked() || A_Shap5.isChecked() || B5.isChecked() ||C5.isChecked() || C_Shap5.isChecked() || D5.isChecked() || D_shap5.isChecked() || E5.isChecked() || F5.isChecked() || F_shap5.isChecked() || G5.isChecked() || G_shap5.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Add Song Five Key", Toast.LENGTH_SHORT).show();
                }

                else if (Six.isEmpty() && ( A6.isChecked() || A_shap6.isChecked() || B6.isChecked() ||C6.isChecked() || C_shap6.isChecked() || D6.isChecked() || D_shap6.isChecked() || E6.isChecked() || F6.isChecked() || F_shap6.isChecked() || G6.isChecked() || G_shap6.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Add Song Six To Key Selected", Toast.LENGTH_SHORT).show();
                }
                else if (!(Six.isEmpty()) && !( A6.isChecked() || A_shap6.isChecked() || B6.isChecked() ||C6.isChecked() || C_shap6.isChecked() || D6.isChecked() || D_shap6.isChecked() || E6.isChecked() || F6.isChecked() || F_shap6.isChecked() || G6.isChecked() || G_shap6.isChecked()  )){
                    Toast.makeText(AddSongActivity.this, "Add Song six Key", Toast.LENGTH_SHORT).show();
                }


                else if(!(slow.isChecked() || fast.isChecked())){
                    Toast.makeText(AddSongActivity.this, "Please Select Song Categoty (Fast or Slow)", Toast.LENGTH_SHORT).show();
                }



                else {

                    loadingBar.setTitle("Saving Songs...");
                    loadingBar.setMessage("This Might Take a few Seconds,Please Wait...");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();

                    userRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                String userFullName = snapshot.child("Name").getValue().toString();
                                String userProfileImage = snapshot.child("profileImage").getValue().toString();


                                HashMap<String, String> AllSongsMap = new HashMap<>();
                                AllSongsMap.put("SongOne", One);
                                AllSongsMap.put("SongTwo", Two);
                                AllSongsMap.put("SongThree", Three);
                                AllSongsMap.put("SongFour", Four);
                                AllSongsMap.put("SongFive", Five);
                                AllSongsMap.put("SongSix", Six);
                                AllSongsMap.put("DateOfMinistration", date);
                                AllSongsMap.put("name",userFullName);
                                AllSongsMap.put("profile_pic",userProfileImage);
                                AllSongsMap.put("Time",time);

                                if(fast.isChecked()){
                                    AllSongsMap.put("Category",fastSong);
                                }
                                if (slow.isChecked()){
                                    AllSongsMap.put("Category",slowSong);
                                }

                                if (A1.isChecked()){
                                    AllSongsMap.put("SongOneKey",a1);
                                }
                                if(A2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",a2);
                                }
                                if(A3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",a3);
                                }
                                if (A4.isChecked()){
                                    AllSongsMap.put("SongFourKey",a4);
                                }
                                if (A5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",a5);
                                }
                                if (A6.isChecked()){
                                    AllSongsMap.put("SongSixKey",a6);
                                }
                                if (A_shap1.isChecked()){
                                    AllSongsMap.put("SongOneKey",a1_shap);
                                }
                                if (A_shap2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",a2_shap);
                                }
                                if (A_shap3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",a3_shap);
                                }
                                if (A_shap4.isChecked()){
                                    AllSongsMap.put("SongFourKey",a4_shap);
                                }
                                if (A_Shap5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",a5_shap);
                                }
                                if (A_shap6.isChecked()){
                                    AllSongsMap.put("SongSixKey",a6_shap);
                                }

                                if (B1.isChecked()){
                                    AllSongsMap.put("SongOneKey",b1);

                                }
                                if(B2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",b2);
                                }
                                if(B3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",b3);
                                }
                                if (B4.isChecked()){
                                    AllSongsMap.put("SongFourKey",b4);
                                }
                                if (B5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",b5);
                                }
                                if (B6.isChecked()){
                                    AllSongsMap.put("SongSixKey",b6);
                                }

                                if (C1.isChecked()){
                                    AllSongsMap.put("SongOneKey",c1);

                                }
                                if(C2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",c2);
                                }
                                if(C3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",c3);
                                }
                                if (C4.isChecked()){
                                    AllSongsMap.put("SongFourKey",c4);
                                }
                                if (C5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",c5);
                                }
                                if (C6.isChecked()){
                                    AllSongsMap.put("SongSixKey",c6);
                                }

                                if (C_shap1.isChecked()){
                                    AllSongsMap.put("SongOneKey",c1_shap);
                                }
                                if (C_shap2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",c2_shap);
                                }
                                if (C_shap3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",c3_shap);
                                }
                                if (C_shap4.isChecked()){
                                    AllSongsMap.put("SongFourKey",c4_shap);
                                }
                                if (C_Shap5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",c5_shap);
                                }
                                if (C_shap6.isChecked()){
                                    AllSongsMap.put("SongSixKey",c6_shap);
                                }

                                if (D1.isChecked()){
                                    AllSongsMap.put("SongOneKey",d1);

                                }
                                if(D2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",d2);
                                }
                                if(D3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",d3);
                                }
                                if (D4.isChecked()){
                                    AllSongsMap.put("SongFourKey",d4);
                                }
                                if (D5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",d5);
                                }
                                if (D6.isChecked()){
                                    AllSongsMap.put("SongSixKey",d6);
                                }

                                if (D_shap1.isChecked()){
                                    AllSongsMap.put("SongOneKey",d1_shap);
                                }
                                if (D_shap2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",d2_shap);
                                }
                                if (D_shap3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",d3_shap);
                                }
                                if (D_shap4.isChecked()){
                                    AllSongsMap.put("SongFourKey",d4_shap);
                                }
                                if (D_shap5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",d5_shap);
                                }
                                if (D_shap6.isChecked()){
                                    AllSongsMap.put("SongSixKey",d6_shap);
                                }

                                if (E1.isChecked()){
                                    AllSongsMap.put("SongOneKey",e1);

                                }
                                if(E2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",e2);
                                }
                                if(E3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",e3);
                                }
                                if (E4.isChecked()){
                                    AllSongsMap.put("SongFourKey",e4);
                                }
                                if (E5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",e5);
                                }
                                if (E6.isChecked()){
                                    AllSongsMap.put("SongSixKey",e6);
                                }

                                if (F1.isChecked()){
                                    AllSongsMap.put("SongOneKey",f1);

                                }
                                if(F2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",f2);
                                }
                                if(F3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",f3);
                                }
                                if (F4.isChecked()){
                                    AllSongsMap.put("SongFourKey",f4);
                                }
                                if (F5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",f5);
                                }
                                if (F6.isChecked()){
                                    AllSongsMap.put("SongSixKey",f6);
                                }

                                if (F_shap1.isChecked()){
                                    AllSongsMap.put("SongOneKey",f1_shap);
                                }
                                if (F_shap2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",f2_shap);
                                }
                                if (F_shap3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",f3_shap);
                                }
                                if (F_shap4.isChecked()){
                                    AllSongsMap.put("SongFourKey",f4_shap);
                                }
                                if (F_shap5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",f5_shap);
                                }
                                if (F_shap6.isChecked()){
                                    AllSongsMap.put("SongSixKey",f6_shap);
                                }

                                if (G1.isChecked()){
                                    AllSongsMap.put("SongOneKey",g1);

                                }
                                if(G2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",g2);
                                }
                                if(G3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",g3);
                                }
                                if (G4.isChecked()){
                                    AllSongsMap.put("SongFourKey",g4);
                                }
                                if (G5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",g5);
                                }
                                if (G6.isChecked()){
                                    AllSongsMap.put("SongSixKey",g6);
                                }



                                if (G_shap1.isChecked()){
                                    AllSongsMap.put("SongOneKey",g1_shap);
                                }
                                if (G_shap2.isChecked()){
                                    AllSongsMap.put("SongTwoKey",g2_shap);
                                }
                                if (G_shap3.isChecked()){
                                    AllSongsMap.put("SongThreeKey",g3_shap);
                                }
                                if (G_shap4.isChecked()){
                                    AllSongsMap.put("SongFourKey",g4_shap);
                                }
                                if (G_shap5.isChecked()){
                                    AllSongsMap.put("SongFiveKey",g5_shap);
                                }
                                if (G_shap6.isChecked()){
                                    AllSongsMap.put("SongSixKey",g6_shap);
                                }
                                if (!(A4.isChecked() || A_shap4.isChecked() || B4.isChecked() || C4.isChecked() || C_shap4.isChecked() || D4.isChecked() || D_shap4.isChecked() || E4.isChecked() || F4.isChecked() || F_shap4.isChecked() || G4.isChecked() || G_shap4.isChecked())){
                                    AllSongsMap.put("SongFourKey","None");
                                }
                                if (!(A5.isChecked() || A_Shap5.isChecked() || B5.isChecked() || C5.isChecked() || C_Shap5.isChecked() || D5.isChecked() || D_shap5.isChecked() || E5.isChecked() || F5.isChecked() || F_shap5.isChecked() || G5.isChecked() || G_shap5.isChecked())){
                                    AllSongsMap.put("SongFiveKey","None");
                                }
                                if (!(A6.isChecked() || A_shap6.isChecked() || B6.isChecked() || C6.isChecked() || C_shap6.isChecked() || D6.isChecked() || D_shap6.isChecked() || E6.isChecked() || F6.isChecked() || F_shap6.isChecked() || G6.isChecked() || G_shap6.isChecked())){
                                    AllSongsMap.put("SongSixKey","None");
                                }



                                root.push().setValue(AllSongsMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddSongActivity.this, "Song Has Been Added", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddSongActivity.this, "Error Saving Data,Please Try Again", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        showDate.setText(simpleDateFormat.format(calendar.getTime()));
                        Ministration_Date = calendar.getTime();

                        //String input1 = input_minimal.getText().toString();
                        //String input2 = input_maximal.getText().toString();
                        //if (input1.isEmpty() && input2.isEmpty()){
                        //cari.setEnabled(false);
                        //}else {
                        //cari.setEnabled(true);
                        //}

                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
    }

    private void SendUserToHome() {

    }
}

package com.example.allsongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class UpdateListActivity extends AppCompatActivity {
    String post_key;
    DatabaseReference list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    Button date,save;
    EditText showDate,one,two,three,four,five,six;
    RadioButton A1,A2,A3,A4,A5,A6,A_shap1,A_shap2,A_shap3,A_shap4,A_Shap5,A_shap6,B1,B2,B3,B4,B5,B6,C1,C2,C3,C4,C5,C6,C_shap1,C_shap2,C_shap3,C_shap4,C_Shap5,C_shap6,D1,D2,D3,D4,D5,D6,D_shap1,D_shap2,D_shap3,D_shap4,D_shap5,D_shap6,E1,E2,E3,E4,E5,E6,F1,F2,F3,F4,F5,F6,F_shap1,F_shap2,F_shap3,F_shap4,F_shap5,F_shap6,G1,G2,G3,G4,G5,G6,G_shap1,G_shap2,G_shap3,G_shap4,G_shap5,G_shap6,fast,slow;
    Context context;
    Calendar calendar = Calendar.getInstance();
    Locale id = new Locale("en", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY", id);
    Date Ministration_Date;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list);

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




        Bundle extra = getIntent().getExtras();
        if (extra != null){

            post_key = extra.getString("postkey");
            // key = extra.getString("key");
        }else {
            Toast.makeText(this, "List Is Not Available!!", Toast.LENGTH_SHORT).show();
        }



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        list = database.getReference("All Songs").child(post_key);

        list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String category_result = snapshot.child("Category").getValue().toString();
                    String dom_result = snapshot.child("DateOfMinistration").getValue().toString();
                    String song_five_result = snapshot.child("SongFive").getValue().toString();
                    String song_four_result = snapshot.child("SongFour").getValue().toString();
                    String song_one_result = snapshot.child("SongOne").getValue().toString();
                    String song_one_key_result = snapshot.child("SongOneKey").getValue().toString();
                    String song_six_result = snapshot.child("SongSix").getValue().toString();
                    String song_three_result = snapshot.child("SongThree").getValue().toString();
                    String song_three_key_result = snapshot.child("SongThreeKey").getValue().toString();
                    String song_two_result = snapshot.child("SongTwo").getValue().toString();
                    String song_two_key_result = snapshot.child("SongTwoKey").getValue().toString();
                    String song_four_key_result = snapshot.child("SongFourKey").getValue().toString();
                    String song_five_key_result = snapshot.child("SongFiveKey").getValue().toString();
                    String song_six_key_result = snapshot.child("SongSixKey").getValue().toString();


                    showDate.setText(dom_result);
                    one.setText(song_one_result);
                    two.setText(song_two_result);
                    three.setText(song_three_result);
                    four.setText(song_four_result);
                    five.setText(song_five_result);
                    six.setText(song_six_result);

                    if (category_result.equals("Slow Songs")){
                        slow.setChecked(true);

                    }
                    if(category_result.equals("Fast Songs")){
                        fast.setChecked(true);
                    }

                    if(song_one_key_result.equals("A")){
                        A1.setChecked(true);
                    }
                     if (song_one_key_result.equals("A#")){
                        A_shap1.setChecked(true);
                    }
                     if (song_one_key_result.equals("B")){
                        B1.setChecked(true);
                    }
                     if (song_one_key_result.equals("C")){
                        C1.setChecked(true);
                    }
                    if (song_one_key_result.equals("C#")){
                        C_shap1.setChecked(true);
                    }
                    if (song_one_key_result.equals("D")){
                        D1.setChecked(true);
                    }
                    if (song_one_key_result.equals("D#")){
                        D_shap1.setChecked(true);
                    }
                    if (song_one_key_result.equals("E")){
                        E1.setChecked(true);
                    }
                    if (song_one_key_result.equals("F")){
                        F1.setChecked(true);
                    }
                    if (song_one_key_result.equals("F#")){
                        F_shap1.setChecked(true);
                    }

                    if (song_one_key_result.equals("G")){
                        G1.setChecked(true);
                    }
                     if (song_one_key_result.equals("G#")){
                        G_shap1.setChecked(true);
                    }

                    //song two
                    if(song_two_key_result.equals("A")){
                        A2.setChecked(true);
                    }
                    if (song_two_key_result.equals("A#")){
                        A_shap2.setChecked(true);
                    }
                    if (song_two_key_result.equals("B")){
                        B2.setChecked(true);
                    }
                    if (song_two_key_result.equals("C")){
                        C2.setChecked(true);
                    }
                    if (song_two_key_result.equals("C#")){
                        C_shap2.setChecked(true);
                    }
                    if (song_two_key_result.equals("D")){
                        D2.setChecked(true);
                    }
                    if (song_two_key_result.equals("D#")){
                        D_shap2.setChecked(true);
                    }
                    if (song_two_key_result.equals("E")){
                        E2.setChecked(true);
                    }
                    if (song_two_key_result.equals("F")){
                        F2.setChecked(true);
                    }
                    if (song_two_key_result.equals("F#")){
                        F_shap2.setChecked(true);
                    }

                    if (song_two_key_result.equals("G")){
                        G2.setChecked(true);
                    }
                    if (song_two_key_result.equals("G#")){
                        G_shap2.setChecked(true);
                    }

                    //song three

                    if(song_three_key_result.equals("A")){
                        A3.setChecked(true);
                    }
                    if (song_three_key_result.equals("A#")){
                        A_shap3.setChecked(true);
                    }
                    if (song_three_key_result.equals("B")){
                        B3.setChecked(true);
                    }
                    if (song_three_key_result.equals("C")){
                        C3.setChecked(true);
                    }
                    if (song_three_key_result.equals("C#")){
                        C_shap3.setChecked(true);
                    }
                    if (song_three_key_result.equals("D")){
                        D3.setChecked(true);
                    }
                    if (song_three_key_result.equals("D#")){
                        D_shap3.setChecked(true);
                    }
                    if (song_three_key_result.equals("E")){
                        E3.setChecked(true);
                    }
                    if (song_three_key_result.equals("F")){
                        F3.setChecked(true);
                    }
                    if (song_three_key_result.equals("F#")){
                        F_shap3.setChecked(true);
                    }
                    if (song_three_key_result.equals("G")){
                        G3.setChecked(true);
                    }
                    if (song_three_key_result.equals("G#")){
                        G_shap3.setChecked(true);
                    }

                    //song four

                    if(song_four_key_result.equals("A")){
                        A4.setChecked(true);
                    }
                    if (song_four_key_result.equals("A#")){
                        A_shap4.setChecked(true);
                    }
                    if (song_four_key_result.equals("B")){
                        B4.setChecked(true);
                    }
                    if (song_four_key_result.equals("C")){
                        C4.setChecked(true);
                    }
                    if (song_four_key_result.equals("C#")){
                        C_shap4.setChecked(true);
                    }
                    if (song_four_key_result.equals("D")){
                        D4.setChecked(true);
                    }
                    if (song_four_key_result.equals("D#")){
                        D_shap4.setChecked(true);
                    }
                    if (song_four_key_result.equals("E")){
                        E4.setChecked(true);
                    }
                    if (song_four_key_result.equals("F")){
                        F4.setChecked(true);
                    }
                    if (song_four_key_result.equals("F#")){
                        F_shap4.setChecked(true);
                    }
                    if (song_four_key_result.equals("G")){
                        G4.setChecked(true);
                    }
                    if (song_four_key_result.equals("G#")){
                        G_shap4.setChecked(true);
                    }

                    //song five

                    if(song_five_key_result.equals("A")){
                        A5.setChecked(true);
                    }
                    if (song_five_key_result.equals("A#")){
                        A_Shap5.setChecked(true);
                    }
                    if (song_five_key_result.equals("B")){
                        B5.setChecked(true);
                    }
                    if (song_five_key_result.equals("C")){
                        C5.setChecked(true);
                    }
                    if (song_five_key_result.equals("C#")){
                        C_Shap5.setChecked(true);
                    }
                    if (song_five_key_result.equals("D")){
                        D5.setChecked(true);
                    }
                    if (song_five_key_result.equals("D#")){
                        D_shap5.setChecked(true);
                    }
                    if (song_five_key_result.equals("E")){
                        E5.setChecked(true);
                    }
                    if (song_five_key_result.equals("F")){
                        F5.setChecked(true);
                    }
                    if (song_five_key_result.equals("F#")){
                        F_shap5.setChecked(true);
                    }
                    if (song_five_key_result.equals("G")){
                        G5.setChecked(true);
                    }
                    if (song_five_key_result.equals("G#")){
                        G_shap5.setChecked(true);
                    }

                    //song six

                    if(song_six_key_result.equals("A")){
                        A6.setChecked(true);
                    }
                    if (song_six_key_result.equals("A#")){
                        A_shap6.setChecked(true);
                    }
                    if (song_six_key_result.equals("B")){
                        B6.setChecked(true);
                    }
                    if (song_six_key_result.equals("C")){
                        C6.setChecked(true);
                    }
                    if (song_six_key_result.equals("C#")){
                        C_shap6.setChecked(true);
                    }
                    if (song_six_key_result.equals("D")){
                        D6.setChecked(true);
                    }
                    if (song_six_key_result.equals("D#")){
                        D_shap6.setChecked(true);
                    }
                    if (song_six_key_result.equals("E")){
                        E6.setChecked(true);
                    }
                    if (song_three_key_result.equals("F")){
                        F3.setChecked(true);
                    }
                    if (song_six_key_result.equals("F#")){
                        F_shap6.setChecked(true);
                    }
                    if (song_six_key_result.equals("G")){
                        G6.setChecked(true);
                    }
                    if (song_six_key_result.equals("G#")){
                        G_shap6.setChecked(true);
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoUpdate();
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

    private void DoUpdate() {
        String updated_dom = showDate.getText().toString();
        String updated_song_one = one.getText().toString();
        String updated_song_two = two.getText().toString();
        String updated_song_three = three.getText().toString();
        String updated_song_four = four.getText().toString();
        String updated_song_five = five.getText().toString();
        String updated_song_six = six.getText().toString();

        String updated_fast = fast.getText().toString();
        String updated_slow = slow.getText().toString();

        String updated_a1 = A1.getText().toString();
        String updated_a2 = A2.getText().toString();
        String updated_a3 = A3.getText().toString();
        String updated_a4 = A4.getText().toString();
        String updated_a5 = A5.getText().toString();
        String updated_a6 = A6.getText().toString();

        String updated_a1_shap = A_shap1.getText().toString();
        String updated_a2_shap = A_shap2.getText().toString();
        String updated_a3_shap = A_shap3.getText().toString();
        String updated_a4_shap = A_shap4.getText().toString();
        String updated_a5_shap = A_Shap5.getText().toString();
        String updated_a6_shap = A_shap6.getText().toString();

        String updated_b1 = B1.getText().toString();
        String updated_b2 = B2.getText().toString();
        String updated_b3 = B3.getText().toString();
        String updated_b4 = B4.getText().toString();
        String updated_b5 = B5.getText().toString();
        String updated_b6 = B6.getText().toString();

        String updated_c1 = C1.getText().toString();
        String updated_c2 = C2.getText().toString();
        String updated_c3 = C3.getText().toString();
        String updated_c4 = C4.getText().toString();
        String updated_c5 = C5.getText().toString();
        String updated_c6 = C6.getText().toString();

        String updated_c1_shap = C_shap1.getText().toString();
        String updated_c2_shap = C_shap2.getText().toString();
        String updated_c3_shap = C_shap3.getText().toString();
        String updated_c4_shap = C_shap4.getText().toString();
        String updated_c5_shap = C_Shap5.getText().toString();
        String updated_c6_shap = C_shap6.getText().toString();

        String updated_d1 = D1.getText().toString();
        String updated_d2 = D2.getText().toString();
        String updated_d3 = D3.getText().toString();
        String updated_d4 = D4.getText().toString();
        String updated_d5 = D5.getText().toString();
        String updated_d6 = D6.getText().toString();

        String updated_d1_shap = D_shap1.getText().toString();
        String updated_d2_shap = D_shap2.getText().toString();
        String updated_d3_shap = D_shap3.getText().toString();
        String updated_d4_shap = D_shap4.getText().toString();
        String updated_d5_shap = D_shap5.getText().toString();
        String updated_d6_shap = D_shap6.getText().toString();

        String updated_e1 = E1.getText().toString();
        String updated_e2 = E2.getText().toString();
        String updated_e3 = E3.getText().toString();
        String updated_e4 = E4.getText().toString();
        String updated_e5 = E5.getText().toString();
        String updated_e6 = E6.getText().toString();

        String updated_f1 = F1.getText().toString();
        String updated_f2 = F2.getText().toString();
        String updated_f3 = F3.getText().toString();
        String updated_f4 = F4.getText().toString();
        String updated_f5 = F5.getText().toString();
        String updated_f6 = F6.getText().toString();

        String updated_f1_shap = F_shap1.getText().toString();
        String updated_f2_shap = F_shap2.getText().toString();
        String updated_f3_shap = F_shap3.getText().toString();
        String updated_f4_shap = F_shap4.getText().toString();
        String updated_f5_shap = F_shap5.getText().toString();
        String updated_f6_shap = F_shap6.getText().toString();

        String updated_g1 = G1.getText().toString();
        String updated_g2 = G2.getText().toString();
        String updated_g3 = G3.getText().toString();
        String updated_g4 = G4.getText().toString();
        String updated_g5 = G5.getText().toString();
        String updated_g6 = G6.getText().toString();

        String updated_g1_shap = G_shap1.getText().toString();
        String updated_g2_shap = G_shap2.getText().toString();
        String updated_g3_shap = G_shap3.getText().toString();
        String updated_g4_shap = G_shap4.getText().toString();
        String updated_g5_shap = G_shap5.getText().toString();
        String updated_g6_shap = G_shap6.getText().toString();

        HashMap AllSongsMap = new HashMap();
        AllSongsMap.put("SongOne", updated_song_one);
        AllSongsMap.put("SongTwo", updated_song_two);
        AllSongsMap.put("SongThree", updated_song_three);
        AllSongsMap.put("SongFour", updated_song_four);
        AllSongsMap.put("SongFive", updated_song_five);
        AllSongsMap.put("SongSix", updated_song_six);
        AllSongsMap.put("DateOfMinistration", updated_dom);
       // AllSongsMap.put("name",userFullName);
       // AllSongsMap.put("profile_pic",userProfileImage);
        //AllSongsMap.put("Time",time);

        if(fast.isChecked()){
            AllSongsMap.put("Category",updated_fast);
        }
        if (slow.isChecked()){
            AllSongsMap.put("Category",updated_slow);
        }

        if (A1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_a1);
        }
        if(A2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_a2);
        }
        if(A3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_a3);
        }
        if (A4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_a4);
        }
        if (A5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_a5);
        }
        if (A6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_a6);
        }
        if (A_shap1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_a1_shap);
        }
        if (A_shap2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_a2_shap);
        }
        if (A_shap3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_a3_shap);
        }
        if (A_shap4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_a4_shap);
        }
        if (A_Shap5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_a5_shap);
        }
        if (A_shap6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_a6_shap);
        }

        if (B1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_b1);

        }
        if(B2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_b2);
        }
        if(B3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_b3);
        }
        if (B4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_b4);
        }
        if (B5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_b5);
        }
        if (B6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_b6);
        }

        if (C1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_c1);

        }
        if(C2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_c2);
        }
        if(C3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_c3);
        }
        if (C4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_c4);
        }
        if (C5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_c5);
        }
        if (C6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_c6);
        }

        if (C_shap1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_c1_shap);
        }
        if (C_shap2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_c2_shap);
        }
        if (C_shap3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_c3_shap);
        }
        if (C_shap4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_c4_shap);
        }
        if (C_Shap5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_c5_shap);
        }
        if (C_shap6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_c6_shap);
        }

        if (D1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_d1);

        }
        if(D2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_d2);
        }
        if(D3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_d3);
        }
        if (D4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_d4);
        }
        if (D5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_d5);
        }
        if (D6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_d6);
        }

        if (D_shap1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_d1_shap);
        }
        if (D_shap2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_d2_shap);
        }
        if (D_shap3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_d3_shap);
        }
        if (D_shap4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_d4_shap);
        }
        if (D_shap5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_d5_shap);
        }
        if (D_shap6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_d6_shap);
        }

        if (E1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_e1);

        }
        if(E2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_e2);
        }
        if(E3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_e3);
        }
        if (E4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_e4);
        }
        if (E5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_e5);
        }
        if (E6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_e6);
        }

        if (F1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_f1);

        }
        if(F2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_f2);
        }
        if(F3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_f3);
        }
        if (F4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_f4);
        }
        if (F5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_f5);
        }
        if (F6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_f6);
        }

        if (F_shap1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_f1_shap);
        }
        if (F_shap2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_f2_shap);
        }
        if (F_shap3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_f3_shap);
        }
        if (F_shap4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_f4_shap);
        }
        if (F_shap5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_f5_shap);
        }
        if (F_shap6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_f6_shap);
        }

        if (G1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_g1);

        }
        if(G2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_g2);
        }
        if(G3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_g3);
        }
        if (G4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_g4);
        }
        if (G5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_g5);
        }
        if (G6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_g6);
        }



        if (G_shap1.isChecked()){
            AllSongsMap.put("SongOneKey",updated_g1_shap);
        }
        if (G_shap2.isChecked()){
            AllSongsMap.put("SongTwoKey",updated_g2_shap);
        }
        if (G_shap3.isChecked()){
            AllSongsMap.put("SongThreeKey",updated_g3_shap);
        }
        if (G_shap4.isChecked()){
            AllSongsMap.put("SongFourKey",updated_g4_shap);
        }
        if (G_shap5.isChecked()){
            AllSongsMap.put("SongFiveKey",updated_g5_shap);
        }
        if (G_shap6.isChecked()){
            AllSongsMap.put("SongSixKey",updated_g6_shap);
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

        list.updateChildren(AllSongsMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    sendToMain();
                    Toast.makeText(UpdateListActivity.this, "Account Settings Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UpdateListActivity.this, "Error occured While Updating List!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    private void sendToMain() {
        Intent intent = new Intent(UpdateListActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


//}

    //@Override
    //public void onCancelled(@NonNull DatabaseError error) {

    //}
//});


       // }

        //}
        //});


        //}

    @Override
    protected void onStart() {
        super.onStart();

    }
}
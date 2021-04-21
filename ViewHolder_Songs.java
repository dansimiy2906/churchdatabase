package com.example.allsongs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewHolder_Songs extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView time_result,name_result,song_one_result,song_two_result,song_three_result,song_four_result,song_five_result,song_six_result,
            song_one_key_result,song_two_key_result,song_three_key_result,song_four_key_result,song_five_key_result,song_six_key_result,timeOfposting,SongCategory
            ,edit;
            ;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef;
    String currentUserID;



    public ViewHolder_Songs(@NonNull View itemView) {
        super(itemView);

    }
    public void setitem(FragmentActivity activity,String Category,String DateOfMinistration,String SongFive,
                        String SongFiveKey,String SongFour,String SongFourKey,String SongOne,
                        String SongOneKey,String SongSix,String SongSixKey,String SongThree,
                        String SongThreeKey,String SongTwo,String SongTwoKey,String Time,String name,String profile_pic){

        imageView = itemView.findViewById(R.id.iv_profile);
        time_result = itemView.findViewById(R.id.dateofMinistration);
        name_result = itemView.findViewById(R.id.user_name);
        song_one_result = itemView.findViewById(R.id.song_one);
        song_two_result = itemView.findViewById(R.id.song_two);
        song_three_result = itemView.findViewById(R.id.song_three);
        song_four_result = itemView.findViewById(R.id.song_four);
        song_five_result = itemView.findViewById(R.id.song_five);
        song_six_result = itemView.findViewById(R.id.song_six);
        song_one_key_result = itemView.findViewById(R.id.song_one_key);
        song_two_key_result = itemView.findViewById(R.id.song_two_key);
        song_three_key_result = itemView.findViewById(R.id.song_three_key);
        song_four_key_result = itemView.findViewById(R.id.song_four_key);
        song_five_key_result = itemView.findViewById(R.id.song_five_key);
        song_six_key_result = itemView.findViewById(R.id.song_six_key);
        timeOfposting = itemView.findViewById(R.id.time_of_posting);
        SongCategory = itemView.findViewById(R.id.category);
        edit = itemView.findViewById(R.id.edit_list);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        //UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            //@Override
            //public void onDataChange(@NonNull DataSnapshot snapshot) {
               // if(snapshot.exists()){
                    //String fullname = snapshot.child("Name")
                //}

            //}

            //@Override
            //public void onCancelled(@NonNull DatabaseError error) {

            //}
       // });


        time_result.setText("Date Of Ministration" +" "+":"+" " +" "+ DateOfMinistration);
        song_six_result.setText("6."+" "+SongSix);
        song_five_result.setText("5."+" "+SongFive);
        song_four_result.setText("4."+" "+SongFour);
        song_three_result.setText("3."+" "+SongThree);
        song_two_result.setText("2."+" "+SongTwo);
        song_one_result.setText("1."+" "+SongOne);
        song_six_key_result.setText("6."+" "+SongSixKey);
        song_five_key_result.setText("5."+" "+SongFiveKey);
        song_four_key_result.setText("4."+" "+SongFourKey);
        song_three_key_result.setText("3."+" "+SongThreeKey);
        song_two_key_result.setText("2."+" "+SongTwoKey);
        song_one_key_result.setText("1."+" "+SongOneKey);
        timeOfposting.setText("List Was Posted at:"+" "+Time);
        SongCategory.setText("List Category:"+" "+Category);
        name_result.setText("List Was Posted By:"+" "+name);
        Picasso.get().load(profile_pic).placeholder(R.drawable.ic_person).into(imageView);


    }
}

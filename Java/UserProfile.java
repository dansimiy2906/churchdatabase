package com.example.allsongs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends Fragment implements View.OnClickListener{

    CircleImageView Profile_photo;
    TextView nameEt,church,contact;
    ImageButton imageButtonEdit,imageButtonMenu;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    String currentUserId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.userprofile,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");

        Profile_photo = getActivity().findViewById(R.id.profile_picture);
        nameEt = getActivity().findViewById(R.id.tv_name);
        church = getActivity().findViewById(R.id.church_name);
        contact = getActivity().findViewById(R.id.contact_detail);

        imageButtonEdit = getActivity().findViewById(R.id.ib_edit_f1);
        imageButtonMenu = getActivity().findViewById(R.id.ib_menu_f1);
        imageButtonMenu.setOnClickListener(this);
        imageButtonEdit.setOnClickListener(this);

        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.hasChild("Name")){
                        String fullname = snapshot.child("Name").getValue().toString();
                        nameEt.setText(fullname);
                    }
                    if (snapshot.hasChild("profileImage")){
                        String image = snapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.ic_person).into(Profile_photo);
                    }
                    if (snapshot.hasChild("ChurchName")){
                        String church_name = snapshot.child("ChurchName").getValue().toString();
                        church.setText(church_name);
                    }
                    if (snapshot.hasChild("ContactDetail")){
                        String user_contact = snapshot.child("ContactDetail").getValue().toString();
                        contact.setText(user_contact);
                    }
                    else {
                        Toast.makeText(getActivity(), "Profile Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_edit_f1:
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_menu_f1:
                BottomSheetMenu bottomSheetMenu = new BottomSheetMenu();
                bottomSheetMenu.show(getFragmentManager(),"bottomsheet");
                break;
        }
    }



}

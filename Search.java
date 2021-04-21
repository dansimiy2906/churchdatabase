package com.example.allsongs;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Search extends Fragment implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;

    Locale id = new Locale("en", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY", id);
    Calendar c = Calendar.getInstance();
    Date Ministration_Date;

    Button date,search;
    EditText showDate;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,userRef;
    RecyclerView recyclerView;
    ImageView imageView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserid);

        recyclerView = getActivity().findViewById(R.id.rv_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        date = getActivity().findViewById(R.id.btn_date);
        showDate = getActivity().findViewById(R.id.date);
        search = getActivity().findViewById(R.id.btn_search);
        databaseReference = database.getReference("All Songs");
        imageView = getActivity().findViewById(R.id.user_pic);
        reference = db.collection("All Songs").document(currentUserid);
        imageView.setOnClickListener(this);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        c.set(year,month,dayOfMonth);
                        showDate.setText(simpleDateFormat.format(c.getTime()));
                        Ministration_Date = c.getTime();

                        String dateSelected = showDate.getText().toString();
                        if(dateSelected.isEmpty()){
                            Toast.makeText(getActivity(), "Please Input Date Before Searching", Toast.LENGTH_LONG).show();
                            search.setEnabled(false);
                        }
                        else{
                            search.setEnabled(true);
                        }
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateSelected = showDate.getText().toString();
                Query query = databaseReference.orderByChild("DateOfMinistration").
                        equalTo(dateSelected);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            FirebaseRecyclerOptions<SongsHolder> options =
                                    new FirebaseRecyclerOptions.Builder<SongsHolder>()
                                            .setQuery(query,SongsHolder.class)
                                            .build();

                            FirebaseRecyclerAdapter<SongsHolder,ViewHolder_Songs> firebaseRecyclerAdapter =
                                    new FirebaseRecyclerAdapter<SongsHolder, ViewHolder_Songs>(options) {

                                        @Override
                                        protected void onBindViewHolder(@NonNull ViewHolder_Songs holder, int position, @NonNull final SongsHolder model) {

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            final String currentUserid = user.getUid();

                                            final String postkey = getRef(position).getKey();

                                            holder.setitem(getActivity(),model.getCategory(), model.getDateOfMinistration(), model.getSongFive(), model.getSongFiveKey(), model.getSongFour()
                                                    , model.getSongFourKey(), model.getSongOne(), model.getSongOneKey(), model.getSongSix(), model.getSongSixKey(), model.getSongThree(),
                                                    model.getSongThreeKey(), model.getSongTwo(), model.getSongTwoKey(),model.getTime(),model.getName(),model.getProfile_pic());


                                        }
                                        @NonNull
                                        @Override
                                        public ViewHolder_Songs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                                            View view = LayoutInflater.from(parent.getContext())
                                                    .inflate(R.layout.songs_display,parent,false);

                                            return new ViewHolder_Songs(view);



                                        }
                                    };
                            firebaseRecyclerAdapter.startListening();

                            recyclerView.setAdapter(firebaseRecyclerAdapter);


                        }
                        else{
                            Toast.makeText(getActivity(), "Date Selected Has No List!!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                          }
        });



        userRef.child(currentUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.hasChild("profileImage")){
                        String image = snapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.ic_person).into(imageView);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Profile Doesnt Exist", Toast.LENGTH_SHORT).show();
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

        switch (view.getId()) {
            //case R.id.iv_f2:
            // BottomSheetF2 bottomSheetF2 = new BottomSheetF2();
            //bottomSheetF2.show(getFragmentManager(),"bottom");
            //break;
            case R.id.floatingActionButton:
                Intent intent = new Intent(getActivity(), AddSongActivity.class);
                startActivity(intent);
                break;

        }

           }

}

package com.example.allsongs;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Home extends Fragment implements View.OnClickListener {
    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,userRef;
    RecyclerView recyclerView;
    ImageView dp;
    TextView edit;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserid);

        recyclerView = getActivity().findViewById(R.id.rv_home);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lm);

        databaseReference = database.getReference("All Songs");

        dp = getActivity().findViewById(R.id.user_pic);
        fb = getActivity().findViewById(R.id.floatingActionButton);
        edit = getActivity().findViewById(R.id.edit_list);
        reference = db.collection("All Songs").document(currentUserid);
        fb.setOnClickListener(this);

       



        FirebaseRecyclerOptions<SongsHolder> options =
                new FirebaseRecyclerOptions.Builder<SongsHolder>()
                        .setQuery(databaseReference,SongsHolder.class)
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

                        holder.edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(),UpdateListActivity.class);
                                intent.putExtra("postkey",postkey);
                                startActivity(intent);
                            }
                        });


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

        // }

    //@Override
    //public void onStart() {
        //super.onStart();
        //reference.get()
                //.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   // @Override
                    //public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       // if (task.getResult().exists()){
                            //String url = task.getResult().getString("url");

                            //Picasso.get().load(url).into(imageView);
                       // }else {
                            //Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        //}

                   // }
                //});
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();
        userRef.child(currentUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.hasChild("profileImage")){
                        String image = snapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(image).into(dp);
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
}

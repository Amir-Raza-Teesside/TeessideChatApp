package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import uk.ac.tees.aad.a0147662.databinding.ActivityTweetDoBinding;

public class TweetDoActivity extends AppCompatActivity {


    ActivityTweetDoBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTweetDoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.twtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = binding.tweettxt.getText().toString();
                if(s.isEmpty())
                {

                   Toast.makeText(TweetDoActivity.this,"Null", Toast.LENGTH_LONG).show();
                }
                else
                    {
                        Date date = new Date();




                        TweeterModel model = new TweeterModel(s,user.getName());

                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference("Tweets").child(date.getTime()+"");
                        reference.setValue(model);


                    }


            }
        });


        binding.tweetphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 400);
            }
        });
    }



}
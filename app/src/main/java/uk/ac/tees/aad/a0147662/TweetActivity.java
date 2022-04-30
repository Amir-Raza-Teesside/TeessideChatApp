package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Debug;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.ac.tees.aad.a0147662.databinding.ActivityTweetBinding;

public class TweetActivity extends AppCompatActivity {

    ActivityTweetBinding binding;
    private ArrayList<TweeterModel> tweeterModels;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTweetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tweeterModels = new ArrayList<>();

        tweeterModels.add(new TweeterModel("Hey, I am comings","Taharat"));

        TweeterAdapter adapter = new TweeterAdapter(TweetActivity.this, tweeterModels);
        binding.recyclerviewtweets.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewtweets.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();
        database.getReference().child("Tweets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                tweeterModels.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    TweeterModel model = snapshot1.getValue(TweeterModel.class);

                    tweeterModels.add(model);

                }
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
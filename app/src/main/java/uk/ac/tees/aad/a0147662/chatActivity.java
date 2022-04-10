package uk.ac.tees.aad.a0147662;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import uk.ac.tees.aad.a0147662.databinding.ActivityChatBinding;

public class chatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("Name");
        String id =  getIntent().getStringExtra("Id");

       getSupportActionBar().setTitle(name);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

   @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
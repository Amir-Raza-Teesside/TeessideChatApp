package uk.ac.tees.aad.a0147662;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.aad.a0147662.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;


    Button btn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.PhoneBox.requestFocus();
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!= null)
        {
           Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
            finish();
        }



        binding.ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Otp_Activity.class);
                intent.putExtra("phoneNumber", binding.PhoneBox.getText().toString());
                startActivity(intent);
            }
        });

    }
}
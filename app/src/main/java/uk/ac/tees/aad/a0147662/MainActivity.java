package uk.ac.tees.aad.a0147662;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import uk.ac.tees.aad.a0147662.databinding.ActivityMainBinding;
import uk.ac.tees.aad.a0147662.databinding.ActivityPhoneNumberBinding;

public class MainActivity extends AppCompatActivity {

   // ActivityPhoneNumberBinding binding;

    ActivityMainBinding binding;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



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
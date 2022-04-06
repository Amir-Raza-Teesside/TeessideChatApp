package uk.ac.tees.aad.a0147662;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

import uk.ac.tees.aad.a0147662.databinding.ActivityOtpBinding;


public class Otp_Activity extends AppCompatActivity {


    ActivityOtpBinding binding;
    //TextView Phone;
    FirebaseAuth auth;
    String PhoneNumber;
    String VerificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         auth = FirebaseAuth.getInstance();


         PhoneNumber = getIntent().getStringExtra("phoneNumber");

        binding.PhoneLabel.setText("Verify" + PhoneNumber);

       PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(PhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(Otp_Activity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String verfication, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verfication, forceResendingToken);

                        VerificationID = verfication;
                    }
                }).build();

         PhoneAuthProvider.verifyPhoneNumber(options);



         binding.Otp.setOtpCompletionListener(new OnOtpCompletionListener() {
             @Override
             public void onOtpCompleted(String otp) {

                 PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID, otp);

                 auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful())
                         {

                             Toast.makeText(Otp_Activity.this, "Successfully Logged In",Toast.LENGTH_SHORT).show();
                         }
                         else
                             {

                                 Toast.makeText(Otp_Activity.this, "Wrong Credentials",Toast.LENGTH_SHORT).show();
                             }

                     }
                 });
             }
         });






    }
}
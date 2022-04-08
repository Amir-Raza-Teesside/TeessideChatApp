package uk.ac.tees.aad.a0147662;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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




    FirebaseAuth auth;

    String VerificationID;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending the OTP");
        progressDialog.setCancelable(false);
        progressDialog.show();

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         auth = FirebaseAuth.getInstance();


        String PhoneNumber = getIntent().getStringExtra("phoneNumber");

        binding.PhoneLabel.setText("Verify" + PhoneNumber);

       PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(PhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(Otp_Activity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        Toast.makeText(Otp_Activity.this, "Varification Comppleted",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {


                        Toast.makeText(Otp_Activity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verfication, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verfication, forceResendingToken);

                        VerificationID = verfication;

                        Log.d("Varifivation codr", verfication);

                        progressDialog.dismiss();
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

                             Intent intent = new Intent(Otp_Activity.this, UserProfileActivity.class);
                             startActivity(intent);
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
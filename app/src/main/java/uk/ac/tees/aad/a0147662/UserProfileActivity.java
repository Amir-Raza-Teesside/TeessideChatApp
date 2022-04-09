package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import uk.ac.tees.aad.a0147662.databinding.ActivityUserProfileBinding;

public class UserProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        getSupportActionBar().hide();

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.username.getText().toString();

                if(name.isEmpty())
                {

                    binding.username.setError("Enter your Name");
                    return;

                }
                if(name.length() <=3)
                {
                    binding.username.setError("Chose a appropriate Name");
                    return;
                }
                if(selectedImage == null)
                {
                    Toast.makeText(UserProfileActivity.this,"Choose a profile Picture",Toast.LENGTH_LONG).show();

                }
                if(selectedImage != null)
                {

                    dailog = new ProgressDialog(UserProfileActivity.this);
                    dailog.setMessage("Setting your profile");
                    dailog.setCancelable(false);
                    dailog.show();

                    StorageReference reference = storage.getReference().child("ProfilePics").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String  Imageuri = uri.toString();

                                        String UID = auth.getUid();
                                        String Phone = auth.getCurrentUser().getPhoneNumber();
                                        String NAME = binding.username.getText().toString();

                                        User newuser = new User(UID,NAME,Phone,Imageuri);

                                        database.getReference().child("users").child(UID).setValue(newuser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                dailog.dismiss();
                                               Intent intent = new Intent(UserProfileActivity.this, ContactsActivity.class );
                                               startActivity(intent);
                                               finish();

                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null)
        {
            if(data.getData()!=null)
            {

                binding.imageView.setImageURI(data.getData());

                selectedImage = data.getData();
            }
        }
    }
}
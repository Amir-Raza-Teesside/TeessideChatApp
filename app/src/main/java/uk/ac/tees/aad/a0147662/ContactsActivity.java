package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import uk.ac.tees.aad.a0147662.databinding.ActivityContactsBinding;

public class ContactsActivity extends AppCompatActivity {

    ActivityContactsBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UserAdapter userAdapter;
    TopStatusAdapter topStatusAdapter;
    ArrayList<UserStatus> userStatuses;
    ProgressDialog progressDialog;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().show();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image");
        progressDialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();


        binding.StatusList.setAdapter(topStatusAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.StatusList.setLayoutManager(layoutManager);


        users = new ArrayList<>();
        userAdapter = new UserAdapter(this, users);


        userStatuses = new ArrayList<>();
        topStatusAdapter = new TopStatusAdapter(this, userStatuses);

        binding.recyclerview.setAdapter(userAdapter);

        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                users.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    User user = snapshot1.getValue(User.class);
                    users.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        UserStatus userStatus = new UserStatus();
                        userStatus.setName(snapshot1.child("name").getValue(String.class));
                        userStatus.setProfileImage(snapshot1.child("ProfileImage").getValue(String.class));
                        userStatus.setLastUpdated(snapshot1.child("LastUpdated").getValue(Long.class));

                        ArrayList<Status> statuses = new ArrayList<>();
                        for(DataSnapshot statusSnapShot:snapshot1.child("statuses").getChildren())
                        {
                            Status sampleStatus = statusSnapShot.getValue(Status.class);
                            statuses.add(sampleStatus);
                        }
                        userStatus.setStatuses(statuses);

                        userStatuses.add(userStatus);

                    }
                    topStatusAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    binding.bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Status:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 75);
                        break;

                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


       switch (item.getItemId())
       {

           case R.id.tweet:
               Toast.makeText(ContactsActivity.this,"JKHJKHKJK",Toast.LENGTH_LONG).show();
               break;
           case R.id.group:
               Toast.makeText(ContactsActivity.this,"Create Group",Toast.LENGTH_LONG).show();
               break;

       }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.topmenu,menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            if(data.getData()!= null)
            {
               progressDialog.show();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Date date = new Date();

                StorageReference reference = storage.getReference().child("status")
                        .child(date.getTime()+"");

               reference.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                       if(task.isSuccessful())
                       {
                           reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {

                                   UserStatus userStatus = new UserStatus();
                                   userStatus.setName(user.getName());
                                   userStatus.setProfileImage(user.getProfilePic());
                                   userStatus.setLastUpdated(date.getTime());

                                   HashMap<String,Object> obj = new HashMap<>();
                                   obj.put("name", userStatus.getName());
                                   obj.put("ProfileImage",userStatus.getProfileImage());
                                   obj.put("LastUpdated", userStatus.getLastUpdated());

                                   Status status = new Status(uri.toString(),userStatus.getLastUpdated());

                                   database.getReference()
                                           .child("stories")
                                           .child(FirebaseAuth.getInstance().getUid())
                                           .updateChildren(obj);

                                   database.getReference().child("stories").child(FirebaseAuth.getInstance().getUid())
                                           .child("statuses").push().setValue(status);
                                   progressDialog.dismiss();
                               }
                           });
                       }
                   }
               });
            }
        }
    }
}
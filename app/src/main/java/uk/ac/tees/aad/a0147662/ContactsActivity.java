package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.ac.tees.aad.a0147662.databinding.ActivityContactsBinding;

public class ContactsActivity extends AppCompatActivity {

    ActivityContactsBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().show();

        database = FirebaseDatabase.getInstance();

        users = new ArrayList<>();
        userAdapter = new UserAdapter(this, users);
        binding.recyclerview.setAdapter(userAdapter);

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
}
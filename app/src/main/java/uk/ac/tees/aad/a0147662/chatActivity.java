package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import uk.ac.tees.aad.a0147662.databinding.ActivityChatBinding;

public class chatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    MessagesAdapter messagesAdapter;
    ArrayList<Message> messages;
    String SenderRoom, Recieverroom;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        messages = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(chatActivity.this, messages);
        binding.chatrecylerview.setLayoutManager(new LinearLayoutManager(this));
        binding.chatrecylerview.setAdapter(messagesAdapter);




        String name = getIntent().getStringExtra("Name");
        String recieveruid =  getIntent().getStringExtra("Id");

        String senderUid = FirebaseAuth.getInstance().getUid();

        SenderRoom =  senderUid + recieveruid;
        Recieverroom = recieveruid + senderUid;
        database = FirebaseDatabase.getInstance();

        database.getReference().child("chats")
                .child(SenderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    Message msg = snapshot1.getValue(Message.class);
                    messages.add(msg);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(chatActivity.this, "Messages Failed to load", Toast.LENGTH_LONG).show();
            }
        });

        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  message =  binding.Mymsg.getText().toString();
                Date date = new Date();

                Message msg = new Message(message,senderUid, date.getTime());
                binding.Mymsg.setText("");


                database.getReference().child("chats").child(SenderRoom)
                        .child("messages").push().setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {


                         database.getReference().child("chats").child(Recieverroom).child("messages").push()
                                .setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });

       getSupportActionBar().setTitle(name);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

   @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
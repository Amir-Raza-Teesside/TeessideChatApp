package uk.ac.tees.aad.a0147662;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import uk.ac.tees.aad.a0147662.databinding.ActivityChatBinding;

public class chatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    MessagesAdapter messagesAdapter;
    ArrayList<Message> messages;
    String SenderRoom, Recieverroom;
    FirebaseDatabase database;
    FirebaseStorage storage;



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
        storage = FirebaseStorage.getInstance();

        database.getReference()
                .child("chats")
                .child(SenderRoom)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
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

                Message messages = new Message(message,senderUid, date.getTime());
                binding.Mymsg.setText("");

                String RandomKey = database.getReference().push().getKey();
                HashMap<String, Object> Last_msg = new HashMap<>();
                Last_msg.put("lastMsg", messages.getMessage());
                Last_msg.put("lstmsgtime", date.getTime());

                database.getReference().child("chats").child(SenderRoom).updateChildren(Last_msg);

                database.getReference().child("chats").child(Recieverroom).updateChildren(Last_msg);

                database.getReference()
                        .child("chats")
                        .child(SenderRoom)
                        .child("messages")
                        .child(RandomKey)
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        database.getReference()
                                .child("chats")
                                .child(Recieverroom)
                                .child("messages")
                                .child(RandomKey)
                                .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
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


       binding.attachment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent();
               intent.setAction(Intent.ACTION_GET_CONTENT);
               intent.setType("image/*");
               startActivityForResult(intent, 405);
           }
       });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 405)
        {
            if(data != null)
            {
                if(data.getData() != null)
                {
                    Uri SelectedImage = data.getData();
                    Calendar calendar = Calendar.getInstance();
                    StorageReference reference =  storage.getReference().child("chats")
                            .child(calendar.getTimeInMillis()+"");
                    reference.putFile(SelectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful())
                            {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String filePath = uri.toString();
                                        Toast.makeText(chatActivity.this,filePath.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
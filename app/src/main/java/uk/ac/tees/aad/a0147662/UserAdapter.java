package uk.ac.tees.aad.a0147662;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.ac.tees.aad.a0147662.databinding.RowBinding;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;
    public UserAdapter(Context con, ArrayList<User> user)
    {

        this.context = con;
        this.users = user;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = users.get(position);
        String senderId = FirebaseAuth.getInstance().getUid();

        String SenderRoom = senderId + user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(SenderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                        String lastmsg = snapshot.child("lastMsg").getValue(String.class);
                        long time = snapshot.child("lstmsgtime").getValue(long.class);

                            SimpleDateFormat format = new SimpleDateFormat("hh:mm:s");

                        holder.binding.rowLastmsg.setText(lastmsg);
                        holder.binding.rowLastseen.setText(format.format(new Date(time)));
                        }
                        else
                            {
                                holder.binding.rowLastmsg.setText("Tap to chat");

                            }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.binding.rowUsername.setText(user.getName());
        Glide.with(context).load(user.getProfilePic())
                .placeholder(R.drawable.avtar)
                .into(holder.binding.rowImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,chatActivity.class);
                intent.putExtra("Name", user.getName());
                intent.putExtra("Id", user.getUid());
                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
      return users.size();
    }

    public  class  UserViewHolder extends RecyclerView.ViewHolder
    {

        RowBinding binding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RowBinding.bind(itemView);
        }
    }
}

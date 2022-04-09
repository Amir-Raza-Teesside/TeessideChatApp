package uk.ac.tees.aad.a0147662;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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
        holder.binding.rowUsername.setText(user.getName());

        Glide.with(context).load(user.getProfilePic()).into(holder.binding.rowImg);
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

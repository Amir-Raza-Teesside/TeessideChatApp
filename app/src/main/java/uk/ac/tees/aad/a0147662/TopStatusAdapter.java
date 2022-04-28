package uk.ac.tees.aad.a0147662;

import android.content.Context;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;
import uk.ac.tees.aad.a0147662.databinding.ItemStatusBinding;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.TopStatusViewHolder> {


    Context context;
    ArrayList<UserStatus> userStatuses;

    public  TopStatusAdapter( Context con, ArrayList<UserStatus> statuses)
    {
        this.context = con;
        this.userStatuses = statuses;

    }

    @NonNull
    @Override
    public TopStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent,false);
        return  new TopStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStatusViewHolder holder, int position) {

        UserStatus userStatus = userStatuses.get(position);
        Status lastStatus  = userStatus.getStatuses().get(userStatus.getStatuses().size()-1);
        Glide.with(context).load(lastStatus.getImageURL()).into(holder.binding.statusimg);

        holder.binding.circularStatusView.setPortionsCount(userStatus.getStatuses().size());

        holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<MyStory> myStories = new ArrayList<>();
                for(Status status: userStatus.getStatuses())
                {
                    myStories.add(new MyStory(status.getImageURL()));
                }

                new StoryView.Builder(((ContactsActivity)context).getSupportFragmentManager())
                        .setStoriesList(myStories)
                        .setStoryDuration(5000)
                        .setTitleText(userStatus.getName())
                        .setSubtitleText("")
                        .setTitleLogoUrl(userStatus.getProfileImage())
                        .setStoryClickListeners(new StoryClickListeners() {
                            @Override
                            public void onDescriptionClickListener(int position) {

                            }

                            @Override
                            public void onTitleIconClickListener(int position) {

                            }
                        }).build().show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class TopStatusViewHolder extends RecyclerView.ViewHolder{

        ItemStatusBinding binding;
        public TopStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatusBinding.bind(itemView);
        }
    }
}

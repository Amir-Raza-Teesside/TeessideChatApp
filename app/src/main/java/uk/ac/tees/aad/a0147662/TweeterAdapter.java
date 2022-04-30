package uk.ac.tees.aad.a0147662;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.aad.a0147662.databinding.TweetLayoutBinding;

public class TweeterAdapter extends RecyclerView.Adapter<TweeterAdapter.ViewHolder> {

    ArrayList<TweeterModel> tweeterlist;
    Context context;

    public  TweeterAdapter (Context con, ArrayList<TweeterModel> list)
    {

        this.context = con;
        this.tweeterlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tweet_layout,parent,false);
        return  new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.name.setText(tweeterlist.get(position).getName());
        holder.Tweets.setText(tweeterlist.get(position).getTweet());




    }

    @Override
    public int getItemCount() {
        return tweeterlist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, Tweets;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView =itemView.findViewById(R.id.MainTweetImage);
            name = itemView.findViewById(R.id.UserName);
            Tweets = itemView.findViewById(R.id.MainTweetText);


        }
    }
}

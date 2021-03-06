package uk.ac.tees.aad.a0147662;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import uk.ac.tees.aad.a0147662.databinding.SenditemBinding;
import  uk.ac.tees.aad.a0147662.databinding.ReceiveitemBinding;

public class MessagesAdapter extends  RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;
    final  int ITEM_SENT=1;
    final int ITEM_Recieve=2;
    public  MessagesAdapter(Context context, ArrayList<Message> msgs)
    {
        this.context = context;
        this.messages = msgs;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType == ITEM_SENT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.senditem, parent, false);

            return  new SentViewHolder(view);

        }
        else
            {
                View view = LayoutInflater.from(context).inflate(R.layout.receiveitem, parent, false);

                return  new RecieveViewHolder(view);
            }

    }

    @Override
    public int getItemViewType(int position) {

        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderid()))
        {
            return  ITEM_SENT;
        }
        else if(!FirebaseAuth.getInstance().getUid().equals(message.getSenderid()))
        {
            return  ITEM_Recieve;
        }



        else
            {
                return 3;
            }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);
           if(holder.getClass() == SentViewHolder.class)
           {
               SentViewHolder viewHolder = (SentViewHolder) holder;
               if(message.getMessage().equals("photo"))
               {
                   viewHolder.binding.imageViewsent.setVisibility(View.VISIBLE);
                   viewHolder.binding.msg.setVisibility(View.GONE);
                   Glide.with(context).load(message.getImageUrl()).placeholder(R.drawable.avtar)
                           .into(viewHolder.binding.imageViewsent);
               }
               viewHolder.binding.msg.setText(message.getMessage());
           }
           else
               {
                   RecieveViewHolder viewHolder = (RecieveViewHolder) holder;
                   if(message.getMessage().equals("photo"))
                   {
                       viewHolder.binding.imageViewrecieve.setVisibility(View.VISIBLE);
                       viewHolder.binding.recvdmsg.setVisibility(View.GONE);
                       Glide.with(context).load(message.getImageUrl())
                               .placeholder(R.drawable.avtar).into(viewHolder.binding.imageViewrecieve);
                   }
                   viewHolder.binding.recvdmsg.setText(message.getMessage());
               }
    }

    @Override
    public int getItemCount() {
       return messages.size();
    }

    public  class RecieveViewHolder extends  RecyclerView.ViewHolder
    {

        ReceiveitemBinding binding;
        public RecieveViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReceiveitemBinding.bind(itemView);
        }
    }

    public  class SentViewHolder extends RecyclerView.ViewHolder
    {

      SenditemBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SenditemBinding.bind(itemView);

        }
    }


}

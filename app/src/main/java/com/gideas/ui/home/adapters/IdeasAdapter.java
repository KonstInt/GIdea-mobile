package com.gideas.ui.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gideas.R;

import java.util.ArrayList;

public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.ViewHolder> {

    private ArrayList<Idea> IdeasList;
    private LayoutInflater mInflater;
    private IdeasAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public IdeasAdapter(Context context, ArrayList<Idea> IdeasList) {
        this.mInflater = LayoutInflater.from(context);
        this.IdeasList = IdeasList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public IdeasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.idea_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String hd = IdeasList.get(position).problems.get(0);
        String body = IdeasList.get(position).mainText;
        int likes = IdeasList.get(position).likes;


        holder.way.setText(hd);
        holder.likes.setText(String.valueOf(likes));
        holder.mainBody.setText(body);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return IdeasList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView way;
        TextView mainBody;
        TextView likes;

        ViewHolder(View itemView) {
            super(itemView);
            way = itemView.findViewById(R.id.idea_main_way);
            mainBody = itemView.findViewById(R.id.idea_text_body);
            likes = itemView.findViewById(R.id.idea_like);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    /*public String getItem(int id) {
        return mAnimals.get(id);
    }*/


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


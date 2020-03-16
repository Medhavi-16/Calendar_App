package com.example.calenderapp;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    private Context mCtx;
    private List<Event_db> list;

    public EventsAdapter(Context mCtx, List<Event_db> list) {
        this.mCtx = mCtx;
        this.list=list;

    }
    onLongItemClickListener mOnLongItemClickListener;

    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface onLongItemClickListener {
        void ItemLongClicked(View v, int position);
    }
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.activity_listview_item, parent, false);
        return new EventsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        Event_db eventDb=list.get(position);
        if(eventDb.getDescription().isEmpty())
            holder.item.setText((eventDb.getTitle()));
        else
        holder.item.setText((eventDb.getTitle()+"\n"+eventDb.getDescription()));
        int type=eventDb.getType();
        switch (type)
        {
            case 1:
                holder.item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.note,0,0,0);
                break;
            case 2:
                holder.item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.birthday,0,0,0);
                break;
            case 3:
                holder.item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.film,0,0,0);
                break;
            default: holder.item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.move,0,0,0);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongItemClickListener != null) {
                    mOnLongItemClickListener.ItemLongClicked(v, position);
                }

                return true;
            }
        });

    }
    void setWords(List<Event_db> words){
        list = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list!=null)
        return list.size();
        else
            return 0;
    }
    public Event_db getItem(int position) {
        return list.get(position);
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {
        //TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;
        TextView item;

        public EventsViewHolder(View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.list_item);


            /*textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);
*/

           // itemView.setOnClickListener(this);
        }


    }

}


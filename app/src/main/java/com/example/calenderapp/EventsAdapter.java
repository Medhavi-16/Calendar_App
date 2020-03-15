package com.example.calenderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.activity_listview_item, parent, false);
        return new EventsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        Event_db eventDb=list.get(position);

        holder.item.setText((eventDb.getTitle()+"\n"+eventDb.getDescription()));
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

    class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Event_db eventDb=list.get(getAdapterPosition());
            Intent i=new Intent(mCtx,EventPage.class);
            mCtx.startActivity(i);
            /*Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("task", task);

            mCtx.startActivity(intent);*/
        }
    }

}


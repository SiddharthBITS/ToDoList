package com.example.appdev2;

import android.content.Context;
import android.database.Cursor;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context a_context;
    private Cursor a_cursor;

    public Adapter(Context context, Cursor cursor) {

        a_context = context;
        a_cursor = cursor;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView task;

        public ViewHolder(View itemView) {

            super(itemView);
            task = itemView.findViewById(R.id.task_card);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(a_context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!a_cursor.moveToPosition(position)) {
            return;
        }

        String name = a_cursor.getString(a_cursor.getColumnIndex(Database.Entry.COLUMN_NAME));
        long id = a_cursor.getLong(a_cursor.getColumnIndex(Database.Entry._ID));

        holder.task.setText(name);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return a_cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (a_cursor != null) {
            a_cursor.close();
        }

        a_cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}

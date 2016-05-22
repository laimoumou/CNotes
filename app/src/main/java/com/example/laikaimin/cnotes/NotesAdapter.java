package com.example.laikaimin.cnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by laikaimin on 2016/5/21.
 */
public class NotesAdapter extends ArrayAdapter<Notes> {

    private int resourceId;

    public NotesAdapter(Context context,int textViewResourceId,List<Notes> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Notes notes = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView titleText = (TextView) view.findViewById(R.id.titleText);
        TextView contentText = (TextView) view.findViewById(R.id.contentText);
        TextView timeText = (TextView) view.findViewById(R.id.bottomText);
        titleText.setText(notes.getTitle());
        contentText.setText(notes.getContent());
        timeText.setText(notes.getTime());
        return view;
    }

}

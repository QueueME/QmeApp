package com.example.queueme.FeedAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.queueme.R;
import com.example.queueme.Subject;

import java.util.List;

/**
 * Created by magnusknaedal on 08.03.2017.
 */

public class FeedAdapeter_ChooseSubject extends ArrayAdapter {
    private static final String TAG= "FeedAdapeter_ChooseSubject";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Subject> subjects;

    public FeedAdapeter_ChooseSubject(Context context, int resource, List<Subject> subjects) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflates
        View view = layoutInflater.inflate(layoutResource, parent, false);
        //fins views
        TextView tvEmnekode = (TextView) view.findViewById(R.id.tvEmnekode);
        TextView tvEmnenavn = (TextView) view.findViewById(R.id.tvEmnenavn);
        //sets text
        Subject currentApp = subjects.get(position);
        tvEmnenavn.setText(currentApp.getEmnenavn());
        tvEmnekode.setText(currentApp.getEmnekode());

        return view;
    }
}

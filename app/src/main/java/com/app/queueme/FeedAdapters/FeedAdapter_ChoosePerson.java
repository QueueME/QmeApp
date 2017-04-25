package com.app.queueme.FeedAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.queueme.Person;
import com.app.queueme.R;

import java.util.List;

/**
 * Created by magnusknaedal on 14.03.2017.
 */

public class FeedAdapter_ChoosePerson extends ArrayAdapter {


    private static final String TAG= "FeedAdapter_ChoosePerson";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Person> persons;



    public FeedAdapter_ChoosePerson(Context context, int resource, List<Person> persons){
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //inflates
        View view = layoutInflater.inflate(layoutResource, parent, false);
        //fins views
        ImageView im = (ImageView)view.findViewById(R.id.studass_icon_personitem);
        TextView name = (TextView) view.findViewById(R.id.tvname);
        TextView time = (TextView) view.findViewById(R.id.time_until);


        Person currentApp = persons.get(position);
        //if gender is not male--> set new image choosegirlstud
        if(currentApp.isMale()==false){
            im.setImageResource(R.drawable.choosegirlstud);

        }
        name.setText(currentApp.getName());
        time.setText(currentApp.getTime_to_stop());
        return view;
    }

}

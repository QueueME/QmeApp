package com.example.queueme.MySessionSwipeFunction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.queueme.R;


public class ScreenSlidePagerFragmentNone extends Fragment {
    ImageView imageView;
    public ScreenSlidePagerFragmentNone(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_screen_slide_page_none,container,false);
        ImageView im = (ImageView)view.findViewById(R.id.queue_stud);
        Bundle bundle = getArguments();
        String empty= bundle.getString("empty");
        Boolean gender= bundle.getBoolean("gender");
        if(empty=="empty") {
            im.setImageResource(R.drawable.coffeeeeeeeeeeee);
        }else {
            if (gender == true) {
                im.setImageResource(R.drawable.astudfull);
            }
            if (gender == false) {
                im.setImageResource(R.drawable.girlstud);
            }
        }
        return view;
    }
    public void setImage(String image) {
        ImageView im = (ImageView)imageView.findViewById(R.id.queue_stud);
            im.setImageDrawable(getResources().getDrawable(R.drawable.astudfull));
    }
}
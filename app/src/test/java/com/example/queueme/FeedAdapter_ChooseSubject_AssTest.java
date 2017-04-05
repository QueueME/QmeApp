package com.example.queueme;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by magnusknaedal on 05.04.2017.
 *
 *
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FeedAdapter_ChooseSubject_AssTest {



    private FeedAdapter_ChooseSubject_Ass adapter;
    private Subject subject;
    @Before
    public void setUp() throws Exception {
        List<Subject> list = new ArrayList<Subject>();
        subject = new Subject();
        list.add(subject);


        adapter = new FeedAdapter_ChooseSubject_Ass(RuntimeEnvironment.application, 0, list);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getCount() throws Exception {

    }
    @Test
    public void verifyContext() {
        assertEquals(RuntimeEnvironment.application, adapter.getContext());
    }
    @Test
    public void verifyListContent() {
        assertEquals(1, adapter.getCount());
        // assertEquals(anders), adapter.getItem(0));

    }

    @Test
    public void shouldClear() throws Exception {
        adapter.clear();
        assertEquals(1, adapter.getCount());
    }
    @Test
    public void test_remove() throws Exception {
        List<Subject> list2 = new ArrayList<Subject>();
        subject = new Subject();
        list2.add(subject);


        FeedAdapter_ChooseSubject_Ass adapter2 = new FeedAdapter_ChooseSubject_Ass(RuntimeEnvironment.application, 0, list2);
        Integer first = adapter.getPosition(subject);
        assertEquals(1, adapter2.getCount());


        adapter2.remove(subject);

        assertEquals(1, adapter2.getCount());
        // assertEquals(new Integer(2), arrayAdapter.getItem(0));
    }
    @Test
    public void usesTextViewResourceIdToSetTextWithinListItemView() throws Exception {
        List<Subject> list3 = new ArrayList<Subject>();
        subject = new Subject();

        subject.setEmnenavn("sdfg");
        subject.setEmnekode("2134");
        list3.add(subject);
        ListView parent = new ListView(RuntimeEnvironment.application);
        FeedAdapter_ChooseSubject_Ass adapter2 = new FeedAdapter_ChooseSubject_Ass(RuntimeEnvironment.application,  R.layout.list_subjectitem_ass, list3);
        View listItemView = adapter2.getView(0, null, parent);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tvname);
        assertEquals("sdfg", titleTextView.getText().toString());


        //heihei
    }
    @Test
    public void hasTheCorrectConstructorResourceIDs() {
        List<Subject> list2 = new ArrayList<Subject>();
        subject = new Subject();
        list2.add(subject);


        FeedAdapter_ChooseSubject_Ass adapter2 = new FeedAdapter_ChooseSubject_Ass(RuntimeEnvironment.application, R.layout.list_subjectitem_person, list2);

        //this assertion may look a little backwards since R.id.title is labeled
        //textViewResourceId in the constructor parameter list, but the output is correct.
        Assert.assertTrue(Shadows.shadowOf(adapter2).getResourceId() == R.layout.list_subjectitem_person);
        Assert.assertTrue(Shadows.shadowOf(adapter2).getTextViewResourceId() != R.layout.list_subjectitem_person);
        Assert.assertTrue(Shadows.shadowOf(adapter2).getTextViewResourceId() == 0);
    }

}
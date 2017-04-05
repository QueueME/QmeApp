package com.example.queueme;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.queueme.FeedAdapters.FeedAdapeter_ChooseSubject;

import org.junit.After;
import org.junit.Assert;
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
 * Created by anders on 05.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FeedAdapeter_ChooseSubjectTest {
    private FeedAdapeter_ChooseSubject adapter;
    private Subject fag;
    @Before
    public void setUp() throws Exception {
        List<Subject> list = new ArrayList<Subject>();
        fag = new Subject();
        list.add(fag);


        adapter = new FeedAdapeter_ChooseSubject(RuntimeEnvironment.application, 0, list);

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
        fag = new Subject();
        list2.add(fag);


        FeedAdapeter_ChooseSubject adapter2 = new FeedAdapeter_ChooseSubject(RuntimeEnvironment.application, 0, list2);
        Integer first = adapter.getPosition(fag);
        assertEquals(1, adapter2.getCount());


        adapter2.remove(fag);

        assertEquals(1, adapter2.getCount());
        // assertEquals(new Integer(2), arrayAdapter.getItem(0));
    }
    @Test
    public void usesTextViewResourceIdToSetTextWithinListItemView() throws Exception {
        List<Subject> list3 = new ArrayList<Subject>();
        fag = new Subject();

        list3.add(fag);
        ListView parent = new ListView(RuntimeEnvironment.application);
        FeedAdapeter_ChooseSubject adapter2 = new FeedAdapeter_ChooseSubject(RuntimeEnvironment.application, R.layout.list_subjectitem_ass, list3);
        View listItemView = adapter2.getView(0, null, parent);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tvname);
        assertEquals("sdfg", titleTextView.getText().toString());
    }
    @Test
    public void hasTheCorrectConstructorResourceIDs() {
        List<Subject> list2 = new ArrayList<Subject>();
        fag = new Subject();
        list2.add(fag);


        FeedAdapeter_ChooseSubject adapter2 = new FeedAdapeter_ChooseSubject(RuntimeEnvironment.application, R.layout.list_subjectitem_ass, list2);

        //this assertion may look a little backwards since R.id.title is labeled
        //textViewResourceId in the constructor parameter list, but the output is correct.
        Assert.assertTrue(Shadows.shadowOf(adapter2).getResourceId() == R.layout.list_subjectitem_ass);
        Assert.assertTrue(Shadows.shadowOf(adapter2).getTextViewResourceId() != R.layout.list_subjectitem_ass);
        Assert.assertTrue(Shadows.shadowOf(adapter2).getTextViewResourceId() == 0);
    }


}
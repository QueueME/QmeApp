package com.app.queueme;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.queueme.FeedAdapters.FeedAdapter_ChoosePerson;

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

import static org.junit.Assert.assertEquals;

/**
 * Created by anders on 05.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FeedAdapter_ChoosePersonTest {
    private FeedAdapter_ChoosePerson adapter;
    private Person anders;
    @Before
    public void setUp() throws Exception {
        List<Person> list = new ArrayList<Person>();
        anders = new Person();
        list.add(anders);


        adapter = new FeedAdapter_ChoosePerson(RuntimeEnvironment.application, 0, list);

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
        List<Person> list2 = new ArrayList<Person>();
        anders = new Person();
        list2.add(anders);


        FeedAdapter_ChoosePerson adapter2 = new FeedAdapter_ChoosePerson(RuntimeEnvironment.application, 0, list2);
        Integer first = adapter.getPosition(anders);
        assertEquals(1, adapter2.getCount());


        adapter2.remove(anders);

       assertEquals(1, adapter2.getCount());
       // assertEquals(new Integer(2), arrayAdapter.getItem(0));
    }
    @Test
    public void usesTextViewResourceIdToSetTextWithinListItemView() throws Exception {
        List<Person> list3 = new ArrayList<Person>();
        anders = new Person();
        anders.setTime_to_stop("11");
        anders.setName("sdfg");
        anders.setEmail("2134");
        list3.add(anders);
        ListView parent = new ListView(RuntimeEnvironment.application);
        FeedAdapter_ChoosePerson adapter2 = new FeedAdapter_ChoosePerson(RuntimeEnvironment.application,  R.layout.list_subjectitem_person, list3);
        View listItemView = adapter2.getView(0, null, parent);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tvname);
        assertEquals("sdfg", titleTextView.getText().toString());
    }
    @Test
    public void hasTheCorrectConstructorResourceIDs() {
        List<Person> list2 = new ArrayList<Person>();
        anders = new Person();
        list2.add(anders);


        FeedAdapter_ChoosePerson adapter2 = new FeedAdapter_ChoosePerson(RuntimeEnvironment.application, R.layout.list_subjectitem_person, list2);

        //this assertion may look a little backwards since R.id.title is labeled
        //textViewResourceId in the constructor parameter list, but the output is correct.
        Assert.assertTrue(Shadows.shadowOf(adapter2).getResourceId() == R.layout.list_subjectitem_person);
        Assert.assertTrue(Shadows.shadowOf(adapter2).getTextViewResourceId() != R.layout.list_subjectitem_person);
        Assert.assertTrue(Shadows.shadowOf(adapter2).getTextViewResourceId() == 0);
    }
}
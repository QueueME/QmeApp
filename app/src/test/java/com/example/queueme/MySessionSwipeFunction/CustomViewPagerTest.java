package com.example.queueme.MySessionSwipeFunction;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.queueme.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.example.queueme.MySessionSwipeFunction.SwipeDirection.left;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 * Created by anders on 05.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CustomViewPagerTest {
    private CustomViewPager pager;
    private TestPagerAdapter adapter;

    @Before
    public void setUp() throws Exception {
        pager = new CustomViewPager(RuntimeEnvironment.application,null);
        adapter = new TestPagerAdapter();
    }

    @Test
    public void shouldSetAndGetAdapter() throws Exception {
        assertNull(pager.getAdapter());

        pager.setAdapter(adapter);
        assertSame(adapter, pager.getAdapter());
    }


    @After
    public void tearDown() throws Exception {

    }
    private static class TestPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
    @Test
    public void test_getAndSetCurrentItem() throws Exception {
        pager.setAdapter(adapter);
        pager.setCurrentItem(2);
        assertEquals(2, pager.getCurrentItem());
    }



    @Test
    public void setCurrentItem_shouldInvokeListener() throws Exception {
        pager.setAdapter(adapter);
        TestOnPageChangeListener listener = new TestOnPageChangeListener();
        pager.setOnPageChangeListener(listener);
        assertFalse(listener.onPageSelectedCalled);
        pager.setCurrentItem(2);
        assertTrue(listener.onPageSelectedCalled);
    }
    @Test
    public void directions() throws Exception{

        assertEquals(pager.getDirection(), SwipeDirection.right);

    }
    @Test
    public void directions2() throws Exception{
    pager.setAllowedSwipeDirection(left);
        assertEquals(pager.getDirection(), SwipeDirection.left);
    }

    @Test
    public void setCurrentItem_shouldntInvokeListenerWhenSettingRedundantly() throws Exception {
        TestOnPageChangeListener listener = new TestOnPageChangeListener();
        pager.setOnPageChangeListener(listener);
        assertFalse(listener.onPageSelectedCalled);
        pager.setCurrentItem(pager.getCurrentItem());
        assertFalse(listener.onPageSelectedCalled);
    }



    private static class TestOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        public boolean onPageSelectedCalled;

        @Override
        public void onPageSelected(int position) {
            onPageSelectedCalled = true;
        }
    }
}
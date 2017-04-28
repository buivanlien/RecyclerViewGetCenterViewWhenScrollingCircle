package org.vonad.recyclerviewgetcenterviewwhenscrollingcircle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

public class MainActivity
        extends AppCompatActivity {

    private RecyclerView recyclerViewDate;
    private DateAdapter dateAdapter;
    private int finalWidthDate;
    private float allPixelsDate;
    public float firstItemWidthDate;
    public float paddingDate;
    public float itemWidthDate;

    private ArrayList<LabelerDate> labelerDates;
    private int setColorDate;
    private String BUNDLE_LIST_PIXELS_DATE ="sb";
    private String BUNDLE_LIST_PIXELS_DATE_CHANGED  ="vsfsd";
    private float allPixelsDateChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRecyclerviewDate();
    }

    public void getRecyclerviewDate() {
        recyclerViewDate = (RecyclerView) findViewById(R.id.recyclerViewDay);
        ViewTreeObserver vtoDate = recyclerViewDate.getViewTreeObserver();
        vtoDate.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                recyclerViewDate.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                finalWidthDate = recyclerViewDate.getMeasuredWidth();
                itemWidthDate = getResources().getDimension(R.dimen.item_dob_width);
                paddingDate = (finalWidthDate - itemWidthDate) / 2;
                firstItemWidthDate = paddingDate;
                allPixelsDate = 0;

                final LinearLayoutManager dateLayoutManager =
                        new LinearLayoutManager(getApplicationContext());
                dateLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewDate.setLayoutManager(dateLayoutManager);
                recyclerViewDate.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {
                        super.onScrollStateChanged(recyclerView,
                                                   newState);
                        synchronized (this) {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                calculatePositionAndScrollDate(recyclerView);
                            }
                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx,
                                           int dy) {
                        super.onScrolled(recyclerView,
                                         dx,
                                         dy);
                        allPixelsDate += dx;
                    }
                });
                if (labelerDates == null) {
                    labelerDates = new ArrayList<>();
                }
                for (int i = 0; i < 100; i++) {
                    LabelerDate labelerDate = new LabelerDate();
                    labelerDate.valueDate = "abc" + i;
                    labelerDates.add(labelerDate);
                }
                dateAdapter = new DateAdapter(labelerDates,
                                              (int) firstItemWidthDate);
                recyclerViewDate.setAdapter(dateAdapter);
                return true;
            }
        });
    }
/* this if most important, if expectedPositionDate < 0 recyclerView will return to nearest item*/

    private void calculatePositionAndScrollDate(RecyclerView recyclerView) {
        int expectedPositionDate =
                Math.round((allPixelsDate + paddingDate - firstItemWidthDate) / itemWidthDate);

        if (expectedPositionDate == -1) {
            expectedPositionDate = 0;
        } else if (expectedPositionDate >= recyclerView.getAdapter()
                                                       .getItemCount() - 2) {
            expectedPositionDate--;
        }
        scrollListToPositionDate(recyclerView,
                                 expectedPositionDate);

    }

    /* this if most important, if expectedPositionDate < 0 recyclerView will return to nearest item*/
    private void scrollListToPositionDate(RecyclerView recyclerView,
                                          int expectedPositionDate) {
        float targetScrollPosDate =
                expectedPositionDate * itemWidthDate + firstItemWidthDate - paddingDate;
        float missingPxDate = targetScrollPosDate - allPixelsDate;
        if (missingPxDate != 0) {
            recyclerView.smoothScrollBy((int) missingPxDate,
                                        0);
            setDateValue();
        }
    }

    private void setDateValue() {
        int expectedPositionDateColor =
                Math.round((allPixelsDate + paddingDate - firstItemWidthDate) / itemWidthDate);
        setColorDate = expectedPositionDateColor + 1;
        //set color here
        dateAdapter.setSelecteditem(setColorDate);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allPixelsDate = savedInstanceState.getFloat(BUNDLE_LIST_PIXELS_DATE);
        allPixelsDateChanged = savedInstanceState.getFloat(BUNDLE_LIST_PIXELS_DATE_CHANGED);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(BUNDLE_LIST_PIXELS_DATE,
                          allPixelsDate);
        outState.putFloat(BUNDLE_LIST_PIXELS_DATE_CHANGED,
                          allPixelsDateChanged);
    }
}

package com.nalazoocare.customlinegraph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.nalazoocare.customlinegraph.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private GraphAdapter graphAdapter;
    private ArrayList<WorkingHour> workingHours = new ArrayList<>();
    private int week = 7;
    private int hour = 24;
    private int lineWidth = 30;
    private WorkingHour workingHour;
    List<Integer> numberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setRamdomData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setStackFromEnd(true);

        graphAdapter = new GraphAdapter(workingHours);

        graphAdapter.setWidthCount(week);
        graphAdapter.setHeightCount(hour);
        graphAdapter.setGraphLineWidth(lineWidth);
        binding.rvGraph.setLayoutManager(linearLayoutManager);
        binding.rvGraph.setAdapter(graphAdapter);


        binding.rvGraph.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                if(scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.rvGraph.post(() ->  autoScroll());
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setRamdomData() {
        int minimumValue = 0;
        int maximumValue = 100;

        Random random = new Random();
        workingHour = new WorkingHour();


        for (int i=0; i<maximumValue; i++) {
            int randomValue = random.nextInt(maximumValue - minimumValue + 1) + minimumValue;
            workingHour.setWorkHour(randomValue);
            Log.d("meme","get :"+workingHour.getWorkHour());
            workingHours.add(workingHour);
        }


//        for (int i = 0; i < 5; i++) {
//            numberList.add(random.nextInt(100));
//        }


    }

    private void autoScroll() {
        FrameLayout graph = null;

        if(workingHours.size() > 0) {
            int[] xy = new int[2];
            int gap = 0;
            int position = 0;
            int minimumGap = -1;

            for (int i = 0; i < binding.rvGraph.getChildCount(); i++) {
                graph = (FrameLayout) binding.rvGraph.getChildAt(i);

                if(graph != null) {
                    graph.getLocationInWindow(xy);
                    position = xy[0] + (graph.getWidth() + lineWidth)/2;
                    gap = position - binding.rvGraph.getWidth();

                    if(minimumGap == -1 || Math.abs(gap) < Math.abs(minimumGap)) {
                        minimumGap = gap;
                    }
                }
            }
            binding.rvGraph.smoothScrollBy(minimumGap, 0);
        }
    }


}

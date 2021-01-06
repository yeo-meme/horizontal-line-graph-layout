package com.nalazoocare.customlinegraph;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nalazoocare.customlinegraph.databinding.LineBinding;

import java.util.List;

/**
 * Created by nalazoo.yeomeme@gmail.com on 2020-08-11
 */
public class GraphAdapter extends RecyclerView.Adapter<GraphAdapter.ViewHolder> {


    private List<WorkingHour> data;
    private int width = 0;
    private int height = 0;
    private int widthCount = 7;
    private int heightCount = 24;
    private int graphLineWidth = 15;


    public GraphAdapter(List<WorkingHour> data) {
        this.data = data;
    }

    public void setData(List<WorkingHour> data) {
        this.data = data;
    }


    public void setWidthCount (int widthCount) {
        this.widthCount = widthCount;
    }
    public void setHeightCount(int heightCount) {
        this.heightCount = heightCount;
    }

    public void setGraphLineWidth(int graphLineWidth) {
        this.graphLineWidth = graphLineWidth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LineBinding binding = LineBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        width = parent.getWidth();
        height = parent.getHeight();

        if (width <= 0) {
            DisplayMetrics dm = parent.getContext().getResources().getDisplayMetrics();
            width = dm.widthPixels;
        }


        ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
        params.width = width / widthCount;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.getRoot().setLayoutParams(params);

        return new GraphAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int workHour = data.get(position).getWorkHour();
        int currentHeight;
        if (workHour > 0) {
            currentHeight = (height * workHour) / heightCount;

            Log.d("meme","meme :" + currentHeight);
        } else {
            currentHeight = 0;
        }
        ViewGroup.LayoutParams line1_param = holder.binding.getRoot().getLayoutParams();
        line1_param.width = graphLineWidth;
        line1_param.height = currentHeight;
        holder.binding.getRoot().setLayoutParams(line1_param);
    }

    @Override
    public int getItemCount() {
        Log.d("meme","data size :" + data.size() );
        return data != null? data.size() :0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LineBinding binding;
        public ViewHolder(@NonNull LineBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

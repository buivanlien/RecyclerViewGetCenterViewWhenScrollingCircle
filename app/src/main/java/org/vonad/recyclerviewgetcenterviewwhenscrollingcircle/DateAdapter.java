package org.vonad.recyclerviewgetcenterviewwhenscrollingcircle;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ---- Description by VO NAD------
 **/
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private ArrayList<LabelerDate> dateDataList;


    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    private int paddingWidthDate = 0;

    private int selectedItem = -1;

    public DateAdapter(ArrayList<LabelerDate> dateData, int paddingWidthDate) {
        this.dateDataList = dateData;
        this.paddingWidthDate = paddingWidthDate;

    }


    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date,
                                                                               parent, false);
            return new DateViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_padding,
                                                                         parent, false);

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams.width = paddingWidthDate;
            view.setLayoutParams(layoutParams);
            return new DateViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        LabelerDate labelerDate = dateDataList.get(position);
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
                holder.tvDate.setText(String.valueOf(labelerDate.valueDate));
            holder.tvDate.setVisibility(View.VISIBLE);

            if (position == selectedItem) {
                holder.tvDate.setTextColor(Color.parseColor("#094673"));
                holder.tvDate.setTextSize(35);

            } else {
                holder.tvDate.setTextColor(Color.GRAY);
                holder.tvDate.setTextSize(35);
            }
        }
    }

    public void setSelecteditem(int selecteditem) {
        this.selectedItem = selecteditem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dateDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }


    public class DateViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvNumberDate);
        }
    }}

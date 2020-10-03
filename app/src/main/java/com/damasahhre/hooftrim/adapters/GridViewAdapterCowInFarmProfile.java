package com.damasahhre.hooftrim.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.activities.CowProfileActivity;
import com.damasahhre.hooftrim.activities.reports.AddReportActivity;
import com.damasahhre.hooftrim.constants.Constants;
import com.damasahhre.hooftrim.database.models.Cow;
import com.damasahhre.hooftrim.database.models.CowWithLastVisit;

import java.util.List;

public class GridViewAdapterCowInFarmProfile extends BaseAdapter {

    private List<CowWithLastVisit> cows;
    private Context context;
    private int farmId;

    public GridViewAdapterCowInFarmProfile(Context context, List<CowWithLastVisit> cows, int farmId) {
        this.cows = cows;
        this.context = context;
        this.farmId = farmId;
    }

    @Override
    public int getCount() {
        return cows.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        if (cows.size() <= i) {
            return null;
        }
        return cows.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (cows.size() <= i) {
            return i;
        }
        return cows.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (cows.size() == i) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.add_grid_item, viewGroup, false);
            view.setOnClickListener(view1 -> {
                Intent intent = new Intent(context, AddReportActivity.class);
                intent.putExtra(Constants.COW_ID, -1);
                intent.putExtra(Constants.FARM_ID, farmId);
                context.startActivity(intent);
            });
        } else {
            CowWithLastVisit cow = cows.get(i);
            if (view == null) {
                view = LayoutInflater.from(context)
                        .inflate(R.layout.livestock_grid_item, viewGroup, false);
                holder = new Holder();
                holder.view = view;
                holder.cowCount = view.findViewById(R.id.cow_count);
                holder.farmTitle = view.findViewById(R.id.farm_text);
                holder.icon = view.findViewById(R.id.cow_icon);
                holder.arrow = view.findViewById(R.id.arrow);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.view.setOnClickListener((v) -> {
                Intent intent = new Intent(context, CowProfileActivity.class);
                intent.putExtra(Constants.COW_ID, cow.getId());
                context.startActivity(intent);
            });
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_calendar));
            holder.farmTitle.setText(cow.getNumber(context));
            holder.cowCount.setText(cow.getLastVisit().toStringWithoutYear(context));
            holder.cowCount.setTextColor(ContextCompat.getColor(context, R.color.persian_green));
            Constants.setImageFront(context, holder.arrow);
            holder.arrow.setColorFilter(ContextCompat.getColor(context, R.color.persian_green), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.persian_green), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        return view;
    }

    static class Holder {
        View view;
        TextView cowCount;
        TextView farmTitle;
        ImageView icon;
        ImageView arrow;
    }

}

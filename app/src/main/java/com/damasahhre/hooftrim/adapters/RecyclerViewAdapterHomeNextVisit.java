package com.damasahhre.hooftrim.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.database.models.NextReport;

import java.util.Date;
import java.util.List;

public class RecyclerViewAdapterHomeNextVisit extends RecyclerView.Adapter<RecyclerViewAdapterHomeNextVisit.Holder> {

    private List<NextReport> nextReports;

    public RecyclerViewAdapterHomeNextVisit(List<NextReport> nextReports) {
        this.nextReports = nextReports;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_visit_item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        NextReport report = nextReports.get(position);
        holder.cowName.setText(R.string.cow_title + report.cowNumber);
        holder.farmName.setText(report.farmName);
        if (report.nextVisitDate.compareTo(new Date()) == 0) {
            holder.date.setText(R.string.today);
        } else
            holder.date.setText(report.cowNumber);
    }

    @Override
    public int getItemCount() {
        return Math.min(nextReports.size(), 3);
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView cowName;
        TextView farmName;
        TextView date;

        public Holder(@NonNull View itemView) {
            super(itemView);
            farmName = itemView.findViewById(R.id.farm_title_text);
            cowName = itemView.findViewById(R.id.cattel_id);
            date = itemView.findViewById(R.id.date_string);
        }
    }

}

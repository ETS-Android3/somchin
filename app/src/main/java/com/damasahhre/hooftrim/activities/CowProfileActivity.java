package com.damasahhre.hooftrim.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.adapters.GridViewAdapterCowProfile;
import com.damasahhre.hooftrim.constants.Constants;
import com.damasahhre.hooftrim.database.DataBase;
import com.damasahhre.hooftrim.database.dao.MyDao;
import com.damasahhre.hooftrim.database.models.Cow;
import com.damasahhre.hooftrim.database.models.Report;
import com.damasahhre.hooftrim.database.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CowProfileActivity extends AppCompatActivity {

    private TextView title;
    private TextView lastVisit;
    private TextView nextVisit;
    private ImageView bookmark;
    private ImageView exit;
    private ImageView menu;
    private GridView reports;
    private Context context;
    private Cow cow;
    private GridViewAdapterCowProfile adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattel_profile);
        context = this;
        title = findViewById(R.id.title_livestrok);
        bookmark = findViewById(R.id.bookmark_image);
        lastVisit = findViewById(R.id.count_value);
        nextVisit = findViewById(R.id.system_value);
        exit = findViewById(R.id.back_icon);
        menu = findViewById(R.id.dropdown_menu);
        reports = findViewById(R.id.reports_list);

        adapter = new GridViewAdapterCowProfile(this, new ArrayList<>());
        reports.setAdapter(adapter);


        int id = Objects.requireNonNull(getIntent().getExtras()).getInt(Constants.COW_ID);
        MyDao dao = DataBase.getInstance(this).dao();
        AppExecutors.getInstance().diskIO().execute(() -> {
            cow = dao.getCow(id);
            List<Report> reports = dao.getAllReportOfCow(cow.getId());
            runOnUiThread(() -> {
                title.setText(cow.getNumber(context));
                if (cow.getFavorite()) {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_fill));
                } else {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark));
                }
                adapter.setReports(reports);
                adapter.notifyDataSetChanged();
            });

        });

        bookmark.setOnClickListener(view -> {
            if (cow != null) {
                cow.setFavorite(!cow.getFavorite());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    dao.update(cow);
                });
                if (cow.getFavorite()) {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_fill));
                } else {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark));
                }
            }
        });
        exit.setOnClickListener((v) -> finish());


    }
}
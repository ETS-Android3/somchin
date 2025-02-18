package com.damasahhre.hooftrim.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.activities.tabs.AddFarmActivity;
import com.damasahhre.hooftrim.adapters.GridViewAdapterCowInFarmProfile;
import com.damasahhre.hooftrim.adapters.RecyclerViewAdapterNextVisitFarmProfile;
import com.damasahhre.hooftrim.constants.Constants;
import com.damasahhre.hooftrim.database.DataBase;
import com.damasahhre.hooftrim.database.dao.MyDao;
import com.damasahhre.hooftrim.database.models.Cow;
import com.damasahhre.hooftrim.database.models.CowWithLastVisit;
import com.damasahhre.hooftrim.database.models.DeletedCow;
import com.damasahhre.hooftrim.database.models.DeletedFarm;
import com.damasahhre.hooftrim.database.models.DeletedReport;
import com.damasahhre.hooftrim.database.models.Farm;
import com.damasahhre.hooftrim.database.models.FarmWithNextVisit;
import com.damasahhre.hooftrim.database.models.MyReport;
import com.damasahhre.hooftrim.database.models.NextVisit;
import com.damasahhre.hooftrim.database.models.Report;
import com.damasahhre.hooftrim.database.utils.AppExecutors;
import com.damasahhre.hooftrim.dialog.DateModelDialog;
import com.damasahhre.hooftrim.dialog.SureDialog;
import com.damasahhre.hooftrim.models.DateContainer;
import com.damasahhre.hooftrim.models.MyDate;
import com.gun0912.tedpermission.PermissionListener;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.os.Build.VERSION.SDK_INT;
import static com.damasahhre.hooftrim.R.string.eight;
import static com.damasahhre.hooftrim.R.string.eleven;
import static com.damasahhre.hooftrim.R.string.five;
import static com.damasahhre.hooftrim.R.string.four;
import static com.damasahhre.hooftrim.R.string.more_info;
import static com.damasahhre.hooftrim.R.string.more_info_reason_1;
import static com.damasahhre.hooftrim.R.string.more_info_reason_2;
import static com.damasahhre.hooftrim.R.string.more_info_reason_3;
import static com.damasahhre.hooftrim.R.string.more_info_reason_4;
import static com.damasahhre.hooftrim.R.string.more_info_reason_5;
import static com.damasahhre.hooftrim.R.string.more_info_reason_6;
import static com.damasahhre.hooftrim.R.string.more_info_reason_7;
import static com.damasahhre.hooftrim.R.string.nine;
import static com.damasahhre.hooftrim.R.string.old_next_visit;
import static com.damasahhre.hooftrim.R.string.one;
import static com.damasahhre.hooftrim.R.string.reason_1;
import static com.damasahhre.hooftrim.R.string.reason_10;
import static com.damasahhre.hooftrim.R.string.reason_2;
import static com.damasahhre.hooftrim.R.string.reason_3;
import static com.damasahhre.hooftrim.R.string.reason_4;
import static com.damasahhre.hooftrim.R.string.reason_5;
import static com.damasahhre.hooftrim.R.string.reason_6;
import static com.damasahhre.hooftrim.R.string.reason_7;
import static com.damasahhre.hooftrim.R.string.reason_8;
import static com.damasahhre.hooftrim.R.string.reason_9;
import static com.damasahhre.hooftrim.R.string.seven;
import static com.damasahhre.hooftrim.R.string.six;
import static com.damasahhre.hooftrim.R.string.ten;
import static com.damasahhre.hooftrim.R.string.three;
import static com.damasahhre.hooftrim.R.string.twelve;
import static com.damasahhre.hooftrim.R.string.two;
import static com.damasahhre.hooftrim.R.string.zero;
import static com.damasahhre.hooftrim.constants.Constants.DATE_SELECTION_EXPORT_REPORT;
import static com.damasahhre.hooftrim.constants.Constants.DATE_SELECTION_RESULT;

/**
 * صفحه پروفایل گاوداری
 * گرفتن خروجی اکسل برای گاوداری
 * حذف گاوداری
 * افزدن گاو
 * ویراشی گاوداری
 */
public class FarmProfileActivity extends AppCompatActivity {

    private PermissionListener permissionlistener;
    private TextView title;
    private TextView visitTitle;
    private TextView system;
    private TextView birthCount;
    private TextView nextVisit;
    private ImageView bookmark;
    private RecyclerView cowsGridView;
    private RecyclerView nextVisitView;
    private RecyclerViewAdapterNextVisitFarmProfile mAdapter;
    private ImageView menu;
    private ConstraintLayout menuLayout;
    private ImageView outside;
    private ImageView outsideScroll;
    private long id;
    private DateContainer dateContainerOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_profile);

        menu = findViewById(R.id.dropdown_menu);
        menuLayout = findViewById(R.id.menu_layout);
        outside = findViewById(R.id.outside);
        outsideScroll = findViewById(R.id.outside_scroll);
        title = findViewById(R.id.title_livestrok);
        visitTitle = findViewById(R.id.next_visit_title);
        birthCount = findViewById(R.id.count_value);
        system = findViewById(R.id.system_value);
        nextVisit = findViewById(R.id.next_visit_value);
        bookmark = findViewById(R.id.bookmark_image);
        cowsGridView = findViewById(R.id.cows_grid);
        nextVisitView = findViewById(R.id.next_visit_lists);
        ImageView exit = findViewById(R.id.back_icon);
        exit.setOnClickListener(view -> finish());
        Constants.setImageBackBorder(this, exit);

        id = Objects.requireNonNull(getIntent().getExtras()).getLong(Constants.FARM_ID);
        nextVisitView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        nextVisitView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapterNextVisitFarmProfile(new ArrayList<>(), this);
        nextVisitView.setAdapter(mAdapter);

        RecyclerView.LayoutManager cowLayoutManager = new GridLayoutManager(this, 2);
        cowsGridView.setLayoutManager(cowLayoutManager);

        menu.setOnClickListener(view -> showMenu());
        outside.setOnClickListener(view -> hideMenu());
        outsideScroll.setOnClickListener(view -> hideMenu());
        ConstraintLayout edit = findViewById(R.id.item_one);
        ConstraintLayout remove = findViewById(R.id.item_two);
        ConstraintLayout share = findViewById(R.id.item_three);
        edit.setOnClickListener(view -> {
            hideMenu();
            Intent intent = new Intent(this, AddFarmActivity.class);
            intent.putExtra(Constants.FARM_ID, id);
            intent.putExtra(Constants.ADD_FARM_MODE, Constants.EDIT_FARM);
            startActivity(intent);
        });
        remove.setOnClickListener(view -> removeDialog(remove));
        share.setOnClickListener(view -> {
            dateContainerOne = null;
            selectDate();
            hideMenu();
        });

        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(FarmProfileActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                export();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FarmProfileActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void removeDialog(ConstraintLayout remove) {
        SureDialog dialog = new SureDialog(FarmProfileActivity.this, getString(R.string.delete_question),
                getString(R.string.delete),
                () -> {
                    MyDao dao = DataBase.getInstance(FarmProfileActivity.this).dao();
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        Farm farm = dao.getFarm(id);
                        List<Cow> cows = dao.getAllCowOfFarm(id);
                        for (Cow cow : cows) {
                            for (Report report : dao.getAllReportOfCow(cow.getId())) {
                                if (!report.created)
                                    dao.insert(new DeletedReport(report.id));
                                dao.deleteReport(report);
                            }
                            if (!cow.getCreated())
                                dao.insert(new DeletedCow(cow.getId()));
                            dao.deleteCow(cow);
                        }
                        if (!farm.getCreated())
                            dao.insert(new DeletedFarm(farm.getId()));
                        dao.deleteFarm(farm);
                        runOnUiThread(() -> {
                            hideMenu();
                            finish();
                        });
                    });
                },
                () -> runOnUiThread(() -> {
                    remove.setActivated(true);
                    remove.setClickable(true);
                    hideMenu();
                }),
                getString(R.string.yes),
                getString(R.string.no));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void getDate(boolean single) {
        Intent intent = new Intent(this, DateSelectionActivity.class);
        if (single) {
            intent.setAction(Constants.DateSelectionMode.SINGLE);
        } else {
            intent.setAction(Constants.DateSelectionMode.RANG);
        }
        startActivityForResult(intent, DATE_SELECTION_EXPORT_REPORT);
    }

    private void selectDate() {
        DateModelDialog dialog = new DateModelDialog(this);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DATE_SELECTION_EXPORT_REPORT) {
            if (resultCode == Constants.DATE_SELECTION_OK) {
                assert data != null;
                DateContainer container = (DateContainer) Objects.requireNonNull(data.getExtras()).get(DATE_SELECTION_RESULT);
                assert container != null;
                dateContainerOne = container;
                checkExportPermission();
            }
        }
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    checkExportPermission();
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showMenu() {
        outside.setVisibility(View.VISIBLE);
        outsideScroll.setVisibility(View.VISIBLE);
        menuLayout.setVisibility(View.VISIBLE);
        menu.setVisibility(View.INVISIBLE);
    }

    private void hideMenu() {
        menu.setVisibility(View.VISIBLE);
        outside.setVisibility(View.GONE);
        outsideScroll.setVisibility(View.GONE);
        menuLayout.setVisibility(View.GONE);
    }

    private void hideVisit() {
        visitTitle.setVisibility(View.GONE);
        nextVisitView.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyDao dao = DataBase.getInstance(this).dao();
        AppExecutors.getInstance().diskIO().execute(() -> {
            FarmWithNextVisit farmWithNextVisit = dao.getFarmWithNextVisit(id, new MyDate(new Date()));
            Farm farm = dao.getFarm(id);
            runOnUiThread(() -> {
                bookmark.setOnClickListener(view -> {
                    farm.setFavorite(!farm.getFavorite());
                    farm.setSync(true);
                    if (farm.getFavorite()) {
                        bookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_fill));
                    } else {
                        bookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark));
                    }
                    AppExecutors.getInstance().diskIO().execute(() -> dao.update(farm));
                });
                if (farm.getFavorite()) {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(FarmProfileActivity.this, R.drawable.ic_bookmark_fill));
                } else {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(FarmProfileActivity.this, R.drawable.ic_bookmark));
                }
                title.setText(farm.getName());
                birthCount.setText("" + farm.getBirthCount());
                system.setText(farm.getControlSystem());
                if (farmWithNextVisit.nextVisit != null)
                    nextVisit.setText(farmWithNextVisit.nextVisit.toStringWithoutYear(this));
                else
                    nextVisit.setText(R.string.no_visit_short);
            });

            List<CowWithLastVisit> cows = dao.getAllCowOfFarmWithLastVisit(id);
            List<Cow> allCows = dao.getAllCowOfFarm(id);
            main:
            for (Cow cow : allCows) {
                for (CowWithLastVisit cowWithLastVisit : cows) {
                    if (cow.getId().equals(cowWithLastVisit.getId())) {
                        continue main;
                    }
                }
                CowWithLastVisit temp = new CowWithLastVisit();
                temp.setId(cow.getId());
                temp.setLastVisit(null);
                temp.setNumber(cow.getNumber());
                cows.add(temp);
            }

            runOnUiThread(() -> {
                GridViewAdapterCowInFarmProfile adapter = new GridViewAdapterCowInFarmProfile(this, cows, (int) id);
                cowsGridView.setAdapter(adapter);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                cowsGridView.getLayoutParams().height = 5000;
                cowsGridView.requestLayout();
            });
            List<NextVisit> list = dao.getAllNextVisitFroFarm(new MyDate(new Date()), id);
            runOnUiThread(() -> {
                if (list.isEmpty()) {
                    hideVisit();
                } else {
                    showVisit();
                    mAdapter.setNextVisits(list);
                }
            });
        });
    }

    private void showVisit() {
        visitTitle.setVisibility(View.VISIBLE);
        nextVisitView.setVisibility(View.VISIBLE);
    }

    public void checkExportPermission(){
        Constants.checkPermission(permissionlistener);
    }

    public void export() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sample sheet");

        Integer[] headers = {R.string.cow_number, R.string.day, R.string.month, R.string.year,
                reason_1, reason_2, reason_3,
                reason_6, reason_7, reason_9, reason_8, reason_4,
                reason_5, reason_10, zero, one, two, three, four, five, six, seven, eight, nine,
                ten, eleven, twelve, more_info_reason_1, more_info_reason_2, more_info_reason_7,
                more_info_reason_5, more_info_reason_6, more_info_reason_4,
                old_next_visit, more_info, more_info_reason_3};

        MyDao dao = DataBase.getInstance(this).dao();
        AppExecutors.getInstance().diskIO().execute(() -> {
            Farm farm = dao.getFarm(id);

            List<MyReport> reports;
            if (dateContainerOne == null) {
                reports = dao.getAllMyReportFarm(id);
            } else {
                if (dateContainerOne.getEndDate() != null) {
                    reports = dao.getAllMyReportFarm(id, dateContainerOne.exportStart(), dateContainerOne.exportEnd());
                } else {
                    reports = dao.getAllMyReportFarm(id, dateContainerOne.exportStart());
                }
            }

            runOnUiThread(() -> {
                //add headers
                Row row = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(getString(headers[i]));
                }
                //add reports
                for (int i = 0; i < reports.size(); i++) {
                    MyReport myReport = reports.get(i);
                    Report report = myReport.report;
                    row = sheet.createRow(i + 1);

                    Cell cell = row.createCell(0);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(myReport.cowNumber);

                    int[] date = report.visit.convert(this);
                    cell = row.createCell(1);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(String.format("%02d", date[2]));

                    cell = row.createCell(2);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(String.format("%02d", date[1]));

                    cell = row.createCell(3);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(String.format("%04d", date[0]));

                    cell = row.createCell(4);
                    if (report.referenceCauseHundredDays)
                        cell.setCellValue("*");

                    cell = row.createCell(5);
                    if (report.referenceCauseDryness)
                        cell.setCellValue("*");

                    cell = row.createCell(6);
                    if (report.referenceCauseLagged)
                        cell.setCellValue("*");

                    cell = row.createCell(7);
                    if (report.referenceCauseHighScore)
                        cell.setCellValue("*");

                    cell = row.createCell(8);
                    if (report.referenceCauseReferential)
                        cell.setCellValue("*");

                    cell = row.createCell(9);
                    if (report.referenceCauseHeifer)
                        cell.setCellValue("*");

                    cell = row.createCell(10);
                    if (report.referenceCauseLongHoof)
                        cell.setCellValue("*");

                    cell = row.createCell(11);
                    if (report.referenceCauseNewLimp)
                        cell.setCellValue("*");

                    cell = row.createCell(12);
                    if (report.referenceCauseLimpVisit)
                        cell.setCellValue("*");

                    cell = row.createCell(13);
                    if (report.referenceCauseGroupHoofTrim)
                        cell.setCellValue("*");

                    if (report.legAreaNumber != null) {
                        for (int k = 0; k < 13; k++) {
                            cell = row.createCell(k + 14);
                            if (report.legAreaNumber == k) {
                                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(report.fingerNumber);
                            }
                        }
                    } else {
                        for (int k = 0; k < 13; k++) {
                            cell = row.createCell(k + 14);
                        }
                    }

                    cell = row.createCell(27);
                    if (report.otherInfoWound)
                        cell.setCellValue("*");

                    cell = row.createCell(28);
                    if (report.otherInfoEcchymosis)
                        cell.setCellValue("*");

                    cell = row.createCell(29);
                    if (report.otherInfoHoofTrim)
                        cell.setCellValue("*");

                    cell = row.createCell(30);
                    if (report.otherInfoGel)
                        cell.setCellValue("*");

                    cell = row.createCell(31);
                    if (report.otherInfoBoarding)
                        cell.setCellValue("*");

                    cell = row.createCell(32);
                    if (report.otherInfoNoInjury)
                        cell.setCellValue("*");

                    cell = row.createCell(33);
                    if (report.nextVisit != null)
                        cell.setCellValue(report.nextVisit.toString(this));

                    cell = row.createCell(34);
                    cell.setCellValue(report.description);

                    cell = row.createCell(35);
                    if (report.otherInfoRecovered)
                        cell.setCellValue("*");
                }

                Log.i("TAG", "export: here");
                try {

                    String storage = Environment.getExternalStorageDirectory().toString() + String.format("/%s.xlsx", farm.getName());
                    File file = new File(storage);
                    if (file.exists()) {
                        if (file.delete()) {
                            Log.i("TAG", "export: deleted ok");
                        } else {
                            Log.i("TAG", "export: deleted fuck");
                        }
                    }
                    if (file.createNewFile()) {
                        FileOutputStream out = new FileOutputStream(file);
                        workbook.write(out);
                        out.close();
                        Log.i("TAG", "export: Excel written successfully..");
                    } else {
                        Log.i("TAG", "export: Excel written fuck..");
                    }

                    Uri uri;
                    if (SDK_INT < 24) {
                        uri = Uri.fromFile(file);
                    } else {
                        uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                    }

                    ShareCompat.IntentBuilder builder = new ShareCompat.IntentBuilder(FarmProfileActivity.this);
                    Intent intent = builder.setType("*/*")
                            .setStream(uri)
                            .setChooserTitle("Choose bar")
                            .createChooserIntent()
                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(intent);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
        });


    }

}
package com.damasahhre.hooftrim.activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.constants.Constants;
import com.damasahhre.hooftrim.database.DataBase;
import com.damasahhre.hooftrim.database.dao.MyDao;
import com.damasahhre.hooftrim.database.models.Cow;
import com.damasahhre.hooftrim.database.models.Farm;
import com.damasahhre.hooftrim.database.models.Report;
import com.damasahhre.hooftrim.database.utils.AppExecutors;
import com.damasahhre.hooftrim.service.AlarmReceiver;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * صفحه‌ی اغازین برنامه
 */
public class SplashActivity extends AppCompatActivity {

    private ConstraintLayout loading_state;
    private ConstraintLayout error_state;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if (Constants.getDefaultLanguage(this).equals(Constants.NO_LANGUAGE)) {
            Constants.setLanguage(this, "fa");
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }


        AppCenter.start(getApplication(), "f4c019af-38a5-44af-b87a-22c2e0dc8f27",
                Analytics.class, Crashes.class);

        loading_state = findViewById(R.id.splash_loading_container);
        error_state = findViewById(R.id.offline_splash_loading_container);
        findViewById(R.id.retry).setOnClickListener(v -> checkConnection());
        findViewById(R.id.work_offline).setOnClickListener(v -> {
            if (Constants.getToken(this).equals(Constants.NO_TOKEN)) {
                Toast.makeText(this, "you need to login for offline mode", Toast.LENGTH_LONG).show();
            } else {
                goApp();
            }
        });

        try {
//            MyDao dao = DataBase.getInstance(this).dao();
//            AppExecutors.getInstance().diskIO().execute(() -> {
//                List<Farm> farms = dao.getAll();
//                for (Farm farm : farms) {
//                    Log.i("SPLASH", farm.toString());
//                    List<Cow> cows = dao.getAllCowOfFarm(farms.get(0).getId());
//                    for (Cow cow : cows) {
//                        Log.i("SPLASH", cow.toString());
//                        List<Report> reports = dao.getAllReportOfCow(cow.getId());
//                        for (Report report : reports) {
//                            Log.i("SPLASH", report.toString());
//                        }
//                    }
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(SplashActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SplashActivity.this, 0, alarmIntent, 0);
        startAtMorning();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    checkConnection();
                });
            }
        }, 1000);
        changeState(0);

    }


    public void startAtMorning() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 24 * 3600 * 1000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval,
                pendingIntent);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(updateBaseContextLocale(newBase));
    }

    public Context updateBaseContextLocale(Context context) {
        String language = Constants.getDefaultLanguage(context);
        if (language.isEmpty()) {
            //when first time enter into app (get the device language and set it
            language = Locale.getDefault().getLanguage();
            Constants.setLanguage(context, language);
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale);
            return updateResourcesLocaleLegacy(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    private void checkConnection() {
        if (!Constants.isNetworkAvailable(this)) {
            changeState(1);
        } else {
            if (Constants.getToken(this).equals(Constants.NO_TOKEN)) {
                goLogin();
            } else {
                goApp();
            }
        }
    }

    private void changeState(int state) {
        if (state == 1) {
            error_state.setVisibility(View.VISIBLE);
            loading_state.setVisibility(View.INVISIBLE);
        } else if (state == 0) {
            loading_state.setVisibility(View.VISIBLE);
            error_state.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * تغییر به صفحه ورود
     */
    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * بازکردن صفحه اصلی
     */
    public void goApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
package com.damasahhre.hooftrim.constants;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.damasahhre.hooftrim.R;

public class Constants {

    //intent to start activity data
    public static final String FARM_ID = "sdaxce";
    public static final String REPORT_ID = "sda32xc2e";
    public static final String COW_ID = "Addssaxce";
    public static final String MORE_INFO_STATE = "sdwvvgr";
    public static final int CHOOSE_FILE_REQUEST_CODE = 99;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 98;
    public static final int FARM_SELECTION_SEARCH_COW = 101;
    public static final int DATE_SELECTION_SEARCH_COW = 102;
    public static final int DATE_SELECTION_SEARCH_FARM = 104;
    public static final int DATE_SELECTION_REPORT_CREATE = 103;
    public static final int DATE_SELECTION_REPORT_CREATE_END = 110;
    public static final int DATE_SELECTION_REPORT_FACTOR = 105;
    public static final int FARM_SELECTION_REPORT_FACTOR = 106;
    public static final int DATE_SELECTION_REPORT_INJURY = 107;
    public static final int FARM_SELECTION_REPORT_INJURY = 108;
    public static final int DATE_SELECTION_OK = 200;
    public static final int DATE_SELECTION_FAIL = 400;
    public static final String DATE_SELECTION_RESULT = "res_xc";
    public static final String EMAIL = "sFNKndsak";
    public static String ADD_FARM_MODE = "SADASDXCVV";
    public static String FARM_CREATE = "CREATE_NEW_FARM";
    public static String EDIT_FARM = "EDIT_OLD_FARM";
    public static String REPORT_MODE = "SADAS DXCCXZVV";
    public static String REPORT_CREATE = "CREATE_NEW_REPORT";
    public static String EDIT_REPORT = "EDIT_OLD_REPORT";

    public static String NO_LANGUAGE = "asdcexxcNoLang";
    private static String LANGUAGE_STORAGE = "someWhereInDarkness";
    private static String LANGUAGE_DATA = "someWhereInDarkness12";

    public static boolean NO_PREMIUM = false;
    private static String PREMIUM_STORAGE = "someWhereInD XNKLACarknessTOK";
    private static String PREMIUM_DATA = "someWhereIKLNFAKLFAV; nDarkness12TOKTOK";

    public static String NO_TOKEN = "NO TOKEN";
    private static String TOKEN_STORAGE = "someWhereInDarknessTOK";
    private static String TOKEN_DATA = "someWhereInDarkness12TOKTOK";

    public static String NO_EMAIL = "NO EMAIL";
    private static String EMAIL_STORAGE = "soacfsfdInDarknessTOK";
    private static String EMAIL_DATA = "somenovdonksacDarkness12TOKTOK";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setImageFront(Context context, ImageView imageView) {
        if (Constants.getDefaultLanguage(context).equals("fa")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_front));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back));
        }
    }

    public static void setImageBack(Context context, ImageView imageView) {

        if (Constants.getDefaultLanguage(context).equals("fa")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_front));
        }
    }

    public static void setImageBackBorder(Context context, ImageView imageView) {
        if (Constants.getDefaultLanguage(context).equals("fa")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_next));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_previous));
        }
    }

    public static void hideKeyboard(Activity activity, IBinder token) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(token, 0);
    }

    public static boolean checkPermissionRead(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            return true;
        }
        return false;
    }

    public static boolean checkPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            return true;
        }
        return false;
    }

    public static void gridRtl(Context context, View view) {
        if (getDefaultLanguage(context).equals("fa")) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }


    /**
     * گرفتن کلید ارتباط با سرور
     */
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_DATA, NO_TOKEN);
    }

    /**
     * ذخیره کلید ارطباط با سرور در حافظه
     */
    public static void setToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_DATA, token);
        editor.apply();
    }


    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL_STORAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_DATA, NO_EMAIL);
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_DATA, email);
        editor.apply();
    }

    public static Boolean getPremium(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREMIUM_STORAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREMIUM_DATA, NO_PREMIUM);
    }

    public static void setPremium(Context context, Boolean state) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREMIUM_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREMIUM_DATA, state);
        editor.apply();
    }


    /**
     * گرفتن کلید ارتباط با سرور
     */
    public static String getDefaultLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGE_STORAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANGUAGE_DATA, NO_LANGUAGE);
    }

    /**
     * ذخیره کلید ارطباط با سرور در حافظه
     */
    public static void setLanguage(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGE_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE_DATA, token);
        editor.apply();
    }

    public static class DateSelectionMode {
        public static String SINGLE = "asdasdngy";
        public static String RANG = "vuwasdngy";
    }

}

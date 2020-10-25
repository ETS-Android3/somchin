package com.damasahhre.hooftrim.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.activities.reports.AddReportActivity;
import com.damasahhre.hooftrim.adapters.GridViewAdapterReasonAddReport;
import com.damasahhre.hooftrim.models.CheckBoxManager;


/**
 * المان دیالوگ برای گرفتن شماره انگشت
 */
public class SelectFingerDialog extends Dialog {


    public SelectFingerDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.select_finger_dialog_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        setCancelable(false);

        EditText edit = findViewById(R.id.input);
        GridView gridView = findViewById(R.id.grid);
        GridViewAdapterReasonAddReport adapter = new GridViewAdapterReasonAddReport(context, CheckBoxManager.getCheckBoxManager().getDialog());
        gridView.setAdapter(adapter);

        Button newInput = findViewById(R.id.new_input);
        Button ok = findViewById(R.id.ok);

        ok.setOnClickListener(v -> {
            int number = -1;
            if (edit.getText().toString().isEmpty()) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            } else {
                number = Integer.parseInt(edit.getText().toString());
            }
            if (number >= 1 && number <= 8) {
                dismiss();
                ((AddReportActivity) context).setFingerNumber(number);
            } else {
                Toast.makeText(context, "value error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public SelectFingerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SelectFingerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {

    }
}

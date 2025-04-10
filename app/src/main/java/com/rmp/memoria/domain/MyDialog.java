package com.rmp.memoria.domain;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rmp.memoria.R;

/**
 * Created by Viktor-Ruff
 * Date: 02.05.2024
 * Time: 12:11
 */
public class MyDialog {

    public static Dialog create(Context context, int layout) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //диалог без названия
        dialog.setContentView(layout); //выбор макета для диалога

        Window w = dialog.getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //Скрываем нижнюю панель навигации.
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //Появляется поверх игры и исчезает.

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // задний фон прозрачный
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT); //растягивания диалога на экране
        dialog.setCancelable(false); //отключение системной кнопки назад

        return dialog;
    }
}

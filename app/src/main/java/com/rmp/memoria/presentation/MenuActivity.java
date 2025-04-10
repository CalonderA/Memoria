package com.rmp.memoria.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rmp.memoria.R;

public class MenuActivity extends AppCompatActivity {

    TextView tvStart, tvSettings, tvRecords, tvExit;
    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //Скрываем нижнюю панель навигации.
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //Появляется поверх игры и исчезает.

        //Инициализация кнопок - начало
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvSettings = (TextView) findViewById(R.id.tv_settings);
        tvRecords = (TextView) findViewById(R.id.tv_records);
        tvExit = (TextView) findViewById(R.id.tv_exit);

        //Инициализация кнопок - конец

        //Вызов метода слушателей нажатий
        initListeners();
    }

    //Метод инициализации слушателей нажатий
    private void initListeners() {

        //Слушатель нажатий на кнопку старт - начало
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MenuActivity.this, GameProcessActivity.class);
                startActivity(startIntent);
                finish();
            }
        });
        //Слушатель нажатий на кнопку старт - конец


        //Слушатель нажатий на кнопку выход - начало
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Слушатель нажатий на кнопку выход - конец

        //Слушатель нажатий на кнопку настройки - начало
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                finish();
            }
        });
        //Слушатель нажатий на кнопку настройки - конец

        //Слушатель нажатий на кнопку рекорды - начало
        tvRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recordsIntent = new Intent(MenuActivity.this, RecordsActivity.class);
                startActivity(recordsIntent);
                finish();
            }
        });
        //Слушатель нажатий на кнопку рекорды - конец

    }
}
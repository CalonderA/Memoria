package com.rmp.memoria.presentation;

import static com.rmp.memoria.domain.Game.RECORDS_FILE;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.rmp.memoria.R;
import com.rmp.memoria.data.Records;
import com.rmp.memoria.databinding.ActivityRecordsBinding;
import com.rmp.memoria.presentation.adapters.RecordsAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    ActivityRecordsBinding binding;
    OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //Скрываем нижнюю панель навигации.
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //Появляется поверх игры и исчезает.

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(RecordsActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }

        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        // кнопка назад
        binding.tvBack.setOnClickListener( v -> {
            onBackPressedCallback.handleOnBackPressed();
        });

        Records records = getMyObject();
        ArrayList<String> finalResult = new ArrayList<>();

        if (records.getRecords().size() == 0){
            finalResult.add("No records"); //TODO убрать в ресурсы
        }  else {
            for (int i = 0; i < records.getRecords().size(); i++) {
               String recordPoint = records.getRecords().get(i) + " Point";
               finalResult.add(recordPoint);
            }
        }

        RecordsAdapter recordsAdapter = new RecordsAdapter(this, finalResult);
        binding.myList.setAdapter(recordsAdapter);
    }
    private Records getMyObject() {
        FileInputStream fin = null;
        Records records = new Records();

        try {
            fin = openFileInput(RECORDS_FILE);
            ObjectInputStream ois = new ObjectInputStream(fin);
            records = (Records) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }

        return records;
    }

}
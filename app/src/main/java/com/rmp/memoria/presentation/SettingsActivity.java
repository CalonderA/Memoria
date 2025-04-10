package com.rmp.memoria.presentation;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.rmp.memoria.R;
import com.rmp.memoria.databinding.ActivitySettingsBinding;
import com.rmp.memoria.domain.MyDialog;
import com.rmp.memoria.domain.MyMediaPlayer;

import java.io.FileOutputStream;

public class SettingsActivity extends AppCompatActivity {

    public static final int SOUND_ON = 1;
    public static final int SOUND_OFF = 0;
    public static final String SETTINGS_FILES = "SETTINGS_FILES";

    ActivitySettingsBinding binding;

    OnBackPressedCallback onBackPressedCallback;

    SharedPreferences settingsPref;
    SharedPreferences.Editor editor;

    int soundNumber;
    MyMediaPlayer interfaceSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //Скрываем нижнюю панель навигации.
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //Появляется поверх игры и исчезает.

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }

        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        settingsPref = getSharedPreferences(SETTINGS_FILES, MODE_PRIVATE);
        editor = settingsPref.edit();
        soundNumber = settingsPref.getInt("SOUND_KEY", 1);

        if (soundNumber == SOUND_ON) {
            interfaceSound = new MyMediaPlayer(this, R.raw.sound_interface); // вставляем свою музыку
        }
        binding.tvBack.setOnClickListener(v -> {
            if (interfaceSound != null) {
                interfaceSound.play();
            }
            getOnBackPressedDispatcher().onBackPressed();
        });

        binding.tvSound.setOnClickListener(v -> {
            if (interfaceSound != null) {
                if (interfaceSound != null) {
                    interfaceSound.play();
                }
            }
            inintSoundDualog();
        });
    }

    private void inintSoundDualog() {
        soundNumber = settingsPref.getInt("SOUND_KEY", 1);
        // создаем диалог
        Dialog dialogSound = MyDialog.create(this, R.layout.sound_dialog);

        RadioButton rdSoundOn = dialogSound.findViewById(R.id.rb_sound_on);
        RadioButton rdSoundOff = dialogSound.findViewById(R.id.rb_sound_off);

        if (soundNumber == SOUND_ON) {
            rdSoundOn.setChecked(true); // отобразить диалоги
        } else if (soundNumber == SOUND_OFF) {
            rdSoundOff.setChecked(true);
        }

        ImageView ivSave = dialogSound.findViewById(R.id.iv_save);
        ivSave.setOnClickListener(v -> {
            if (interfaceSound != null) {
                interfaceSound.play();
            }
            // произвести сохранение сохранить пользователя
            if (rdSoundOn.isChecked()) {
                editor.putInt("SOUND_KEY", SOUND_ON);
                dialogSound.cancel();
                recreate();
            } else if (rdSoundOff.isChecked()) {
                editor.putInt("SOUND_KEY", SOUND_OFF);
                dialogSound.cancel();
                recreate();
            }

            editor.apply();

        });

        dialogSound.show();
    }


}
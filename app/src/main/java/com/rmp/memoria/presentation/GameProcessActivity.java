package com.rmp.memoria.presentation;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmp.memoria.R;
import com.rmp.memoria.databinding.ActivityGameProcessBinding;
import com.rmp.memoria.domain.Game;
import com.rmp.memoria.domain.MyDialog;

public class GameProcessActivity extends AppCompatActivity implements Game.GameListener {

    public static final int THREE_STARS_BORDER = 940;
    public static final int TWO_STARS_BORDER = 860;

    OnBackPressedCallback onBackPressedCallback;
    Animation animation;
    private GridView myGridView;
    private ConstraintLayout rootConstraint;

    ActivityGameProcessBinding binding;

    private final int columnNumber = 4;
    private final int rowsNumber = 4;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameProcessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //Скрываем нижнюю панель навигации.
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //Появляется поверх игры и исчезает.


        //Инцилизация переменных - начало

        animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        binding.myGameConstraint.startAnimation(animation);
        game = new Game(this, columnNumber, rowsNumber, this);
        binding.gvGameField.setNumColumns(columnNumber);
        binding.gvGameField.setAdapter(game.getMyGridAdapter());

        //Инцилизация переменных - конец

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                game.startGame();
            }
        }, 4000);

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Intent intent = new Intent(GameProcessActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();

            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        // Обработка нажатия на кнопку QUILT - начало

        binding.tvBack.setOnClickListener(v -> {
            Dialog backDialog = MyDialog.create(this, R.layout.stop_game_dialog);

            ImageView ivOk = backDialog.findViewById(R.id.iv_ok);
            ImageView ivCansel = backDialog.findViewById(R.id.iv_cansel);

            TextView tvText = backDialog.findViewById(R.id.tv_dialog_text);
            tvText.setText(getString(R.string.back));

            ivOk.setOnClickListener(v1 -> {
                backDialog.cancel();
                getOnBackPressedDispatcher().onBackPressed();
            });

            ivCansel.setOnClickListener(v1 -> {
                backDialog.cancel();
            });

            backDialog.show();
        });

        // Обработка нажатия на кнопку QUILT - конец

        // Обработка нажатия на кнопку Restart - начало

        binding.tvRestart.setOnClickListener(v -> {
            Dialog restartDialog = MyDialog.create(this, R.layout.stop_game_dialog);

            ImageView ivOk = restartDialog.findViewById(R.id.iv_ok);
            ImageView ivCansel = restartDialog.findViewById(R.id.iv_cansel);

            TextView tvText = restartDialog.findViewById(R.id.tv_dialog_text);
            tvText.setText(getString(R.string.restart));

            ivOk.setOnClickListener(v1 -> {
                restartDialog.cancel();
                recreate();
            });

            ivCansel.setOnClickListener(v1 -> {
                restartDialog.cancel();
            });

            restartDialog.show();
        });
    }


    // Обработка нажатия на кнопку RESTART - конец



    @Override
    public void showPoints(String points) {
        binding.txPoints.setText(points);

    }

    @Override
    public void winGame() {
        Dialog winDialog = MyDialog.create(this, R.layout.win_dialog);

        ImageView ivOk = winDialog.findViewById(R.id.iv_ok);
        ImageView ivThreeStart = winDialog.findViewById(R.id.iv_three_stars);
        TextView ivPoints = winDialog.findViewById(R.id.tv_points);

        ivPoints.setText(binding.txPoints.getText().toString());
        int result = Integer.parseInt(binding.txPoints.getText().toString());

        if (result >= THREE_STARS_BORDER) {
            ivThreeStart.setImageResource(R.drawable.im_game_process_win_dialog_three_stars);
        } else if (result > TWO_STARS_BORDER) {
            ivThreeStart.setImageResource(R.drawable.im_game_process_win_dialog_two_stars);
        } else {
            ivThreeStart.setImageResource(R.drawable.im_game_process_win_dialog_one_stars);

        }

        ivOk.setOnClickListener(v -> {
            winDialog.cancel();
        });

        winDialog.show();
    }
}
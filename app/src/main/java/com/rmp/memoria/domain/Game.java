package com.rmp.memoria.domain;

import android.content.Context;
import android.os.Handler;

import com.rmp.memoria.data.Cell;
import com.rmp.memoria.data.Records;
import com.rmp.memoria.data.Status;
import com.rmp.memoria.presentation.adapters.GridAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Viktor-Ruff
 * Date: 07.03.2024
 * Time: 17:03
 */
public class Game implements GridAdapter.MyOnItemClickListener {

    public static final String RECORDS_FILE = "RECORDS_FILE";
    public static final int FIX_POINTS = 100;
    private double trueAnswerCoefficient;

    private int columns;
    private int rows;
    private ArrayList<Cell> arrCells;

    private Context context;
    private GridAdapter gridAdapter;
    private int points;
    private boolean trueAnswer = false;

    private GameListener gameListener;


    public Game(Context context, int columns, int rows, GameListener gameListener) {
        this.context = context;
        this.columns = columns;
        this.rows = rows;
        points = 0;
        trueAnswerCoefficient = 1;
        this.gameListener = gameListener;
        gameListener.showPoints(String.valueOf(points));

        createArrCells();
        gridAdapter = new GridAdapter(context, columns, rows, arrCells, this);
    }

    public interface GameListener {
        void showPoints(String points);

        void winGame();
    }


    //Метод создания коллекции картинок для игрового поля - начало
    private void createArrCells() {

        ArrayList<Integer> tempArrDrawableId = new ArrayList<>(); //Создаем временную коллекцию

        //Заполняем временную коллекцию картинками - начало
        for (int i = 0; i < 18; i++) {
            int drawableId = context.getResources().
                    getIdentifier("im_animal_" + i, "drawable", context.getPackageName());
            tempArrDrawableId.add(drawableId);
        }
        //Заполняем временную коллекцию картинками - конец

        Collections.shuffle(tempArrDrawableId); //Перемешиваем картинки в коллекции

        //Cоздаем основную коллекцию
        arrCells = new ArrayList<>();

        //Заполняем основную коллекцию картинками, на основе размеров игрового поля - начало
        for (int i = 0; i < (columns * rows) / 2; i++) {
            Cell cell1 = new Cell(tempArrDrawableId.get(i));
            Cell cell2 = new Cell(tempArrDrawableId.get(i));
            arrCells.add(cell1);
            arrCells.add(cell2);
        }
        //Заполняем основную коллекцию картинками, на основе размеров игрового поля - конец

        //Перемешиваем основную коллекцию
        Collections.shuffle(arrCells);
    }
    //Метод создания коллекции картинок для игрового поля - конец


    //Метод запуска игрового процесса - начало
    public void startGame() {
        //Цикл меняющий статус всех ячеек на "Закрыто"
        for (int i = 0; i < arrCells.size(); i++) {
            arrCells.get(i).setCellStatus(Status.CELL_CLOSE);
            gridAdapter.update(arrCells);
        }
    }
    //Метод запуска игрового процесса - конец

    //Метод интерфейса. Меняем стату ячейки на "Открыто" - начало
    @Override
    public void openCell(int position) {
        if (arrCells.get(position).getCellStatus() == Status.CELL_CLOSE) {
            arrCells.get(position).setCellStatus(Status.CELL_OPEN);
        }
        gridAdapter.update(arrCells);
    }
    //Метод интерфейса. Меняем стату ячейки на "Открыто" - Конец


    //Проверка на одинаковые ячейки - начало
    @Override
    public void checkOpenCells(int firstCell, int secondCell) {

        //Условие - если статус ячеек "Открыто" - начало
        if (arrCells.get(firstCell).getCellStatus() == Status.CELL_OPEN &&
                arrCells.get(secondCell).getCellStatus() == Status.CELL_OPEN) {

            //условие - если изображения ячеек одинаковы - начало
            if (arrCells.get(firstCell).getIdResource() ==
                    arrCells.get(secondCell).getIdResource()) {

                //Увеличеник коэффициента - начало
                if (trueAnswer) {
                    trueAnswerCoefficient += 0.2;
                } else {
                    trueAnswerCoefficient = 1;
                }
                //Увеличеник коэффициента - конец

                //Формируем поинты за правильный ответ
                double tempPoints = FIX_POINTS * trueAnswerCoefficient;

                //Добавляем поинты
                points += tempPoints;

                gameListener.showPoints(String.valueOf(points));

                //Меняем статус двух ячеек на "Удалено"
                arrCells.get(firstCell).setCellStatus(Status.CELL_DELETE);
                arrCells.get(secondCell).setCellStatus(Status.CELL_DELETE);

                //Правильный ответ - да
                trueAnswer = true;
            }
            //условие - если изображения ячеек одинаковы - конец

            //если изображения ячеек разные - начало
            else {
                //Меняем стату ячеек на "Закрыто"
                arrCells.get(firstCell).setCellStatus(Status.CELL_CLOSE);
                arrCells.get(secondCell).setCellStatus(Status.CELL_CLOSE);

                //Правильный ответ - нет
                trueAnswer = false;
            }
            //если изображения ячеек разные - конец
        }
        //Условие - если статус ячеек "Открыто" - конец

        //Обновляем коллекцию ячеек в адаптере
        gridAdapter.update(arrCells);
    }
    //Проверка на одинаковые ячейки - конец


    //Метод проверки конца игры - начало
    @Override
    public boolean checkGameOver() {

        boolean isGameOver = true;

        //Если хоть одна ячейка имеет стату "Закрыто" значит не конец игры.
        for (int i = 0; i < arrCells.size(); i++) {
            if (arrCells.get(i).getCellStatus() == Status.CELL_CLOSE) {
                isGameOver = false;
            }
        }


        //Если конец игры
        if (isGameOver) {

            //TODO Сохранить рекорд пользователя - сделанно
            saveMyObjects();

            //Отображения победного диалога.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameListener.winGame();
                }
            }, 500);
        }

        return isGameOver;
    }
//Метод проверки конца игры - конец

    public GridAdapter getMyGridAdapter() {
        return gridAdapter;
    }

    private void saveMyObjects() {

        boolean isLower = true;
        FileOutputStream fos = null;

        try {
            String newRecord = String.valueOf(points);
            Records records = getMyObject();

            if (!records.getRecords().isEmpty()) {
                for (int i = 0; i < records.getRecords().size(); i++) {
                    int oldRecord = Integer.parseInt(records.getRecords().get(i));
                    if (points > oldRecord) {
                        isLower = false;
                        records.addRecord(i, newRecord);
                        break;
                    }
                    if (isLower & records.getRecords().size() < 5) {
                        records.addRecord(newRecord);
                    }
                }
            } else {
                records.addRecord(newRecord);
            }
            fos = context.openFileOutput(RECORDS_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(records);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Records getMyObject() {
        FileInputStream fin = null;
        Records records = new Records();

        try {
            fin = context.openFileInput(RECORDS_FILE);
            ObjectInputStream ois = new ObjectInputStream(fin);
            records = (Records) ois.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            throw new RuntimeException();
        }

        return  records;
    }

}



  


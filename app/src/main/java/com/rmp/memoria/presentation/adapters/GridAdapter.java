package com.rmp.memoria.presentation.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.rmp.memoria.R;
import com.rmp.memoria.data.Cell;

import java.util.ArrayList;


public class GridAdapter extends ArrayAdapter<Cell> {

    Animation animation;
    private int openedCellsCounter = 0;
    private int firstOpenedCells = 0;
    private int secondOpenedCells = 0;

    private Context context;
    private final Integer mCols;
    private final Integer mRows;
    private ArrayList<Cell> arrCells;
    private MyOnItemClickListener myOnItemClickListener;


    public GridAdapter(Context context, Integer mCols, Integer mRows, ArrayList<Cell> arrCells, MyOnItemClickListener myOnItemClickListener) {
        super(context, R.layout.item_animal, arrCells);
        this.context = context;
        this.mCols = mCols;
        this.mRows = mRows;
        this.arrCells = arrCells;
        this.myOnItemClickListener = myOnItemClickListener;
        animation = AnimationUtils.loadAnimation(context, R.anim.fade);
    }

    private class ViewHolder {
        ImageView ivAnimal;

        public ViewHolder(View view) {
            ivAnimal = view.findViewById(R.id.iv_animal);
        }
    }


    public interface MyOnItemClickListener {

        void checkOpenCells(int firstCell, int secondCell);

        void openCell(int position);

        boolean checkGameOver();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Cell cell = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_animal, null, true);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (cell != null) {
            switch (cell.getCellStatus()) {
                case CELL_CLOSE:
                    viewHolder.ivAnimal.setImageResource(R.drawable.im_cell_close);
                    viewHolder.ivAnimal.setEnabled(true);
                    break;
                case CELL_OPEN:
                    viewHolder.ivAnimal.setImageResource(cell.getIdResource());
                    viewHolder.ivAnimal.setEnabled(false);
                    break;
                case CELL_DELETE:
                    viewHolder.ivAnimal.setImageResource(R.drawable.im_cell_delete);
                    viewHolder.ivAnimal.setEnabled(false);
                    break;
            }
        }

        viewHolder.ivAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openedCellsCounter++;

                if (openedCellsCounter == 1) {
                    v.startAnimation(animation);
                    firstOpenedCells = position;
                    myOnItemClickListener.openCell(position);

                } else if (openedCellsCounter == 2) {
                    v.startAnimation(animation);
                    secondOpenedCells = position;
                    myOnItemClickListener.openCell(position);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myOnItemClickListener.checkOpenCells(firstOpenedCells, secondOpenedCells);
                            myOnItemClickListener.checkGameOver();
                            openedCellsCounter = 0;
                        }
                    }, 1000);
                }
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mCols * mRows;
    }

    public void update(ArrayList<Cell> arrCells) {
        this.arrCells = arrCells;
        notifyDataSetChanged();
    }

}



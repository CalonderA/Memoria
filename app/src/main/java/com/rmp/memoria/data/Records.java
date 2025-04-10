package com.rmp.memoria.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Viktor-Ruff
 * Date: 30.01.2024
 * Time: 0:33
 */
public class Records implements Serializable {


    private ArrayList<String> records;

    public Records() {
        records = new ArrayList<>();
    }

    public ArrayList<String> getRecords() {
        return records;
    }

    public void addRecord(String record) {
        records.add(record);
    }

    public void addRecord(int index, String record) {
        records.add(index, record);
        while (records.size() > 5) {
            records.remove(records.size() - 1);
        }
    }


}

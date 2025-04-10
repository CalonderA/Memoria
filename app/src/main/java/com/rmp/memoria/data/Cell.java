package com.rmp.memoria.data;

/**
 * Created by Viktor-Ruff
 * Date: 07.03.2024
 * Time: 16:15
 */
public class Cell {

    private int idResource;

    private Status cellStatus = Status.CELL_OPEN;

    public Cell(int idResource) {
        this.idResource = idResource;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

    public Status getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(Status cellStatus) {
        this.cellStatus = cellStatus;
    }
}

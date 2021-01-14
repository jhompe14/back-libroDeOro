package com.scouts.backlibrodeoro.dto.response;

import java.util.List;

public class PageResponseDTO<T> {

    private Integer totalItems;
    private List<T> dataGrid;

    public PageResponseDTO(Integer totalItems, List<T> dataGrid) {
        this.totalItems = totalItems;
        this.dataGrid = dataGrid;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(List<T> dataGrid) {
        this.dataGrid = dataGrid;
    }
}

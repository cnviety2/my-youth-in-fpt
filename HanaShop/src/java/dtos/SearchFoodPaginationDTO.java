/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class SearchFoodPaginationDTO {

    private int page;
    private int quantity;
    private List<Food> listFood;
    private String radioChoice;
    private String searchValue;
    private String typeOfSearch;
    private int pageQuantities;

    public int getPageQuantities() {
        return pageQuantities;
    }

    public void setPageQuantities(int pageQuantities) {
        this.pageQuantities = pageQuantities;
    }

    public String getTypeOfSearch() {
        return typeOfSearch;
    }

    public void setTypeOfSearch(String typeOfSearch) {
        this.typeOfSearch = typeOfSearch;
    }

    public String getRadioChoice() {
        return radioChoice;
    }

    public void setRadioChoice(String radioChoice) {
        this.radioChoice = radioChoice;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public SearchFoodPaginationDTO() {
        pageQuantities = 0;
        page = 0;
        quantity = 20;
        listFood = new ArrayList<>();
    }

    public SearchFoodPaginationDTO(int page, int quantity, List<Food> listFood) {
        this.page = page;
        this.quantity = quantity;
        this.listFood = listFood;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Food> getListFood() {
        return listFood;
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
    }

}

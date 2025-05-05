package com.example.adminpanel.dto;

import java.util.List;
import java.util.Map;

public class RecipeDto {
    private Map<String, String> steps;
    private Map<String, String> pictures;
    private List<IngredientWithMeasurementDto> ingredientsWithMeasurements;

    public Map<String, String> getSteps() {
        return steps;
    }

    public Map<String, String> getPictures() {
        return pictures;
    }

    public List<IngredientWithMeasurementDto> getIngredientsWithMeasurements() {
        return ingredientsWithMeasurements;
    }

    public void setPictures(Map<String, String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "RecipeDto{" +
                "pictures=" + pictures +
                '}';
    }
}

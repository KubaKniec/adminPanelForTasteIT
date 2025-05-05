package com.example.adminpanel.dto;

public class IngredientWithMeasurementDto {
    private String ingredientId;
    private String name;
    private String description;
    private String type;
    private String strength;
    private String imageURL;
    private MeasurementDto measurement;
    private Boolean alcohol;

    public String getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getStrength() {
        return strength;
    }

    public String getImageURL() {
        return imageURL;
    }

    public MeasurementDto getMeasurement() {
        return measurement;
    }

    public Boolean getAlcohol() {
        return alcohol;
    }
}


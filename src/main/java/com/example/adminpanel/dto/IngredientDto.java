package com.example.adminpanel.dto;

public class IngredientDto {
    private String ingredientId;
    private String name;
    private String description;
    private String type;
    private String strength;
    private String imageURL;
    private boolean alcohol;

    public IngredientDto() {
    }

    public IngredientDto(String ingredientId, String name) {
        this.ingredientId = ingredientId;
        this.name = name;
    }

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

    public boolean isAlcohol() {
        return alcohol;
    }

    @Override
    public String toString() {
        return "IngredientDto{" +
                "ingredientId='" + ingredientId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", strength='" + strength + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", alcohol=" + alcohol +
                '}';
    }
}

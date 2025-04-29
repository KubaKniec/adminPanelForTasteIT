package com.example.adminpanel.dto;

public class IngredientDto {
    private String ingredientId;
    private String name;
    private String description;
    private String type;
    private String strength;
    private String imageURL;
    private boolean alcohol;

    public IngredientDto(String ingredientId, String name, String description,
                         String type, String strength, String imageURL, boolean alcohol) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.strength = strength;
        this.imageURL = imageURL;
        this.alcohol = alcohol;
    }

    public IngredientDto() {
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

package VTTP_SSF.ProjectA.Model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;

public class Food {
    @NotEmpty(message = "Name cannot be empty")
    private String foodName;
    private Double calories;

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getFormattedCalories() {
        return String.format("%.2f", calories);
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public static Food fromJson(JsonObject json) {
        Food food = new Food();
        food.setFoodName(json.getString("foodName"));
        food.setCalories(json.getJsonNumber("calories").doubleValue());

        return food;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("foodName", this.foodName)
                .add("calories", this.calories)
                .build();
    }

    @Override
    public String toString() {
        return "Food [foodName=" + foodName + ", calories=" + calories + "]";
    }

    

}

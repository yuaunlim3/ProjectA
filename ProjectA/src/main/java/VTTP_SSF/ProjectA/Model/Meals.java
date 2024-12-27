package VTTP_SSF.ProjectA.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Meals {
    private Map<String, List<Food>> meal;
    private LocalDate date;

    public Map<String, List<Food>> getMeal() {
        return meal;
    }

    public void setMeal(Map<String, List<Food>> meal) {
        this.meal = meal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double totalCalories(){
        Double total =  0.0;
        for(Map.Entry<String, List<Food>> entry: this.meal.entrySet()){
            List<Food> foodList = entry.getValue();
            for(Food food:foodList){
                total += food.getCalories();
            }
        }

        return Double.valueOf(String.format("%.2f", total)); 
    }

    public static Meals fromJson(JsonObject json) {
        Meals meals = new Meals();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(json.getString("date"), formatter);

        meals.setDate(date);

        Map<String, List<Food>> mealMap = new HashMap<>();

        // Get the meal JSON object and iterate over the entries
        JsonObject mealJson = json.getJsonObject("meal");
        for (String mealType : mealJson.keySet()) {
            // Get the food items for the meal type
            List<Food> foodList = new ArrayList<>();
            mealJson.getJsonArray(mealType).forEach(foodJson -> {
                // Convert each food item JSON to a Food object
                foodList.add(Food.fromJson(foodJson.asJsonObject()));
            });

            // Add the list of foods to the meal map
            mealMap.put(mealType, foodList);
        }

        // Set the meal map
        meals.setMeal(mealMap);

        return meals;

    }
    
    public JsonObject toJson(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        JsonObjectBuilder mealBuilder = Json.createObjectBuilder();

        for (Map.Entry<String, List<Food>> entry : this.meal.entrySet()) {
            String mealType = entry.getKey();
            List<Food> foods = entry.getValue();

            JsonArrayBuilder foodArrayBuilder = Json.createArrayBuilder();
            for (Food food : foods) {
                foodArrayBuilder.add(food.toJson()); 
            }

            mealBuilder.add(mealType, foodArrayBuilder.build());
        }

        return Json.createObjectBuilder()
        .add("date", this.date.format(formatter))
        .add("meal",mealBuilder.build())
        .build();
    }

    @Override
    public String toString() {
        return "Meals [meal=" + meal + ", date=" + date + "]";
    }  

    

    

}

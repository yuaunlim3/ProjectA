package VTTP_SSF.ProjectA.Model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Workout {
    @NotEmpty(message = "Workout details cannot be empty")
    private String name;
    private Double calories;
    private String type;

    @NotNull(message = "Duration must be given")
    @Min(value =0, message = "Duration must be more than 0" )
    private Double durations;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getCalories() {
        return calories;
    }
    public void setCalories(Double calories) {
        this.calories = calories;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getDurations() {
        return durations;
    }
    public void setDurations(Double durations) {
        this.durations = durations;
    }
    @Override
    public String toString() {
        return "Workout [name=" + name + ", calories=" + calories + ", type=" + type + ", durations=" + durations + "]";
    }

    public static Workout fromJson(JsonObject json){
        Workout workout = new Workout();
        workout.setName(json.getString("workout"));
        workout.setDurations(json.getJsonNumber("duration_mins").doubleValue());
        workout.setType(json.getString("type"));
        workout.setCalories(json.getJsonNumber("calories").doubleValue());

        return workout;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("workout", this.name)
            .add("duration_mins", this.durations)
            .add("type",this.type)
            .add("calories",this.calories)
            .build();
        }


    

    

}

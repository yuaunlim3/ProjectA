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

public class BurnsCal {
    private List<Workout> workoutList;
    private LocalDate date;

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static BurnsCal fromJson(JsonObject json) {
        BurnsCal burnsCal = new BurnsCal();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(json.getString("date"), formatter);

        burnsCal.setDate(date);

        List<Workout> workoutslist = new ArrayList<>();

        json.getJsonArray("workouts").forEach(workout -> {
            workoutslist.add(Workout.fromJson(workout.asJsonObject()));
        });
        burnsCal.setWorkoutList(workoutslist);

        return burnsCal;

    }

    public JsonObject toJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        JsonArrayBuilder workoutArrayBuilder = Json.createArrayBuilder();
        for (Workout workout : this.workoutList) {
            workoutArrayBuilder.add(workout.toJson());
        }

        return Json.createObjectBuilder()
                .add("date", this.date.format(formatter))
                .add("workouts", workoutArrayBuilder.build())
                .build();
    }

    //Get the total calories burnt
    public Double getTotalCalories() {
        Double total = 0.0;
        for (Workout workout : this.workoutList) {
            total += workout.getCalories();
        }

        return Double.valueOf(String.format("%.2f", total));
    }

    //Divide the workouts into the different category
    public Map<String, Double> getTypes() {
        Map<String, Double> workoutTypeMap = new HashMap<>();
        

        workoutTypeMap.put("Cardio", 0.0);
        workoutTypeMap.put("Gym", 0.0);
        workoutTypeMap.put("Strength", 0.0);
        workoutTypeMap.put("Yoga", 0.0);
        workoutTypeMap.put("Stretching", 0.0);
        workoutTypeMap.put("Sports", 0.0);

        this.workoutList.stream()
                .forEach(workout -> workoutTypeMap.merge(workout.getType(), workout.getDurations(), Double::sum));
        return workoutTypeMap;
    }

    @Override
    public String toString() {
        return "BurnsCal [workoutList=" + workoutList + ", date=" + date + "]";
    }
    
    

}

package VTTP_SSF.ProjectA.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;

public class Users {
    private String id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private LocalDate createdDate;
    private LocalDate lastLogin;
    private Long height;
    private Long weight;
    private Long age;
    private String gender;
    private String actLevel;
    private String aim;
    private Stack<Meals> meals;
    private Stack<BurnsCal> workouts;
    private Stack<Data> overallDatas;
    private Integer daysLogin;

    public static Users fromJson(JsonObject json) {
        Users user = new Users();

        JsonObject personalData = json.getJsonObject("personal_Data");
        String name = personalData.getString("name");
        String password = personalData.getString("password");
        String id = personalData.getString("id");
        String gender = personalData.getString("gender");
        Long age = personalData.getJsonNumber("age").longValue();
        Integer days = personalData.getInt("days_login");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate createdDate = LocalDate.parse(personalData.getString("createdDate"), dtf);
        LocalDate lastLogin = LocalDate.parse(personalData.getString("lastLogin"), dtf);

        JsonObject physicalData = json.getJsonObject("physical_Data");
        Long height = physicalData.getJsonNumber("height").longValue();
        Long weight = physicalData.getJsonNumber("weight").longValue();
        String aim = physicalData.getString("aim");
        String actLevel = physicalData.getString("actLevel");

        // Extracting meals data
        JsonArray arr = json.getJsonArray("meals_Data");
        Stack<Meals> mealData = new Stack<>();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject mealObject = arr.getJsonObject(i);
            Meals meal = Meals.fromJson(mealObject);
            mealData.push(meal);
        }

        // Extracting workouts data
        Stack<BurnsCal> workoutsData = new Stack<>();
        JsonArray workoutArray = json.getJsonArray("workout_Data");
        for (int j = 0; j < workoutArray.size(); j++) {
            JsonObject workoutsObject = workoutArray.getJsonObject(j);
            BurnsCal burnsCal = BurnsCal.fromJson(workoutsObject);
            workoutsData.push(burnsCal);
        }

        // For overall
        Stack<Data> overallData = new Stack<>();
        JsonArray overallArray = json.getJsonArray("overall_Data");
        for (int k = 0; k < overallArray.size(); k++) {
            JsonObject overallObject = overallArray.getJsonObject(k);
            Data data = Data.fromJson(overallObject);
            overallData.push(data);
        }

        user.setCreatedDate(createdDate);
        user.setName(name);
        user.setPassword(password);
        user.setId(id);
        user.setLastLogin(lastLogin);
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);
        user.setAim(aim);
        user.setGender(gender);
        user.setActLevel(actLevel);
        user.setMeals(mealData);
        user.setWorkouts(workoutsData);
        user.setOverallDatas(overallData);
        user.setDaysLogin(days);

        return user;
    }

    public JsonObject toJson() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (Meals meal : this.meals) {
            JsonObject mealObject = meal.toJson();
            arr.add(mealObject);
        }

        JsonArrayBuilder workoutArr = Json.createArrayBuilder();
        for (BurnsCal burnsCal : this.workouts) {
            workoutArr.add(burnsCal.toJson());
        }

        JsonArrayBuilder overallArr = Json.createArrayBuilder();
        for (Data data : this.overallDatas) {
            overallArr.add(data.toJson());
        }

        JsonObject personalData = Json.createObjectBuilder()
                .add("id", this.id)
                .add("name", this.name)
                .add("password", this.password)
                .add("createdDate", this.createdDate.format(formatter))
                .add("lastLogin", this.lastLogin.format(formatter))
                .add("age", this.age)
                .add("gender", this.gender)
                .add("days_login", this.daysLogin)
                .build();

        JsonObject physicalData = Json.createObjectBuilder()
                .add("weight", this.weight)
                .add("height", this.height)
                .add("aim", this.aim)
                .add("actLevel", this.actLevel)
                .build();
        return Json.createObjectBuilder()
                .add("personal_Data", personalData)
                .add("physical_Data", physicalData)
                .add("meals_Data", arr)
                .add("workout_Data", workoutArr)
                .add("overall_Data", overallArr)
                .build();
    }

    public JsonObject toJsonWithoutImportantData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (Meals meal : this.meals) {
            JsonObject mealObject = meal.toJson();
            arr.add(mealObject);
        }

        JsonArrayBuilder workoutArr = Json.createArrayBuilder();
        for (BurnsCal burnsCal : this.workouts) {
            workoutArr.add(burnsCal.toJson());
        }

        JsonArrayBuilder overallArr = Json.createArrayBuilder();
        for (Data data : this.overallDatas) {
            overallArr.add(data.toJson());
        }
        
        JsonObject personalData = Json.createObjectBuilder()
                .add("id", this.id)
                .add("name", this.name)
                .add("age", this.age)
                .add("gender", this.gender)
                .build();

        JsonObject physicalData = Json.createObjectBuilder()
                .add("weight", this.weight)
                .add("height", this.height)
                .add("aim", this.aim)
                .add("actLevel", this.actLevel)
                .build();
        return Json.createObjectBuilder()
                .add("personal_Data", personalData)
                .add("physical_Data", physicalData)
                .add("meals_Data", arr)
                .add("workout_Data", workoutArr)
                .add("overall_Data", overallArr)
                .build();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public Double getTarget() {
        double bmr;
        if (this.gender.equalsIgnoreCase("male")) {
            bmr = 10 * this.weight + 6.25 * this.height - 5 * this.age + 5;
        } else {
            bmr = 10 * this.weight + 6.25 * this.height - 5 * this.age - 161;
        }
        double activityMultiplier;
        switch (this.actLevel.toLowerCase()) {
            case "sedentary":
                activityMultiplier = 1.2;
                break;
            case "light":
                activityMultiplier = 1.375;
                break;
            case "moderate":
                activityMultiplier = 1.55;
                break;
            case "active":
                activityMultiplier = 1.725;
                break;
            case "very_active":
                activityMultiplier = 1.9;
                break;
            default:
                activityMultiplier = 1.55;
                break;
        }

        switch (this.aim.toLowerCase()) {
            case "maintain":
                return bmr * activityMultiplier;
            case "lose":
                return bmr * activityMultiplier - 500;
            case "gain":
                return bmr * activityMultiplier + 500;
            default:
                return bmr * activityMultiplier;
        }
    }

    public String getActLevel() {
        return actLevel;
    }

    public void setActLevel(String actLevel) {
        this.actLevel = actLevel;
    }

    public LocalDate todayDate() {
        return LocalDate.now();
    }

    public Stack<Meals> getMeals() {
        return meals;
    }

    public void setMeals(Stack<Meals> meals) {
        this.meals = meals;
    }

    public Meals getCurrentMeal() {
        Meals meal = this.meals.peek();
        if (meal.getDate().equals(todayDate())) {
            return meal;
        }
        meal = new Meals();
        return meal;
    }

    public BurnsCal getCurrentWorkout() {
        BurnsCal burnsCal = this.workouts.peek();
        if (burnsCal.getDate().equals(todayDate())) {
            return burnsCal;
        }
        burnsCal = new BurnsCal();
        return burnsCal;
    }

    public Data getCurrentData() {
        Data data = this.overallDatas.peek();
        if (data != null && data.getDate().equals(todayDate())) {
            return data;
        }
        data = new Data();
        return data;
    }

    public Double getPercentage() {
        Data data = getCurrentData();
        return data.getNet() / getTarget();
    }

    public Stack<BurnsCal> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Stack<BurnsCal> workouts) {
        this.workouts = workouts;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + ", password=" + password + ", createdDate=" + createdDate
                + ", lastLogin=" + lastLogin + ", height=" + height + ", weight=" + weight + ", age=" + age
                + ", gender=" + gender + ", actLevel=" + actLevel + ", aim=" + aim + ", meals=" + meals + ", workouts="
                + workouts + "]";
    }

    public Stack<Data> getOverallDatas() {
        return overallDatas;
    }

    public void setOverallDatas(Stack<Data> overallDatas) {
        this.overallDatas = overallDatas;
    }

    public Double getAverageNet() {
        if (this.overallDatas.isEmpty()) {
            return 0.0;
        }

        Double total = 0.0;
        for (Data data : this.overallDatas) {

            if (data.getDate().getMonth().equals(LocalDate.now().getMonth())) {
                total += data.getNet();
            }
        }

        return Double.valueOf(String.format("%.2f", total / this.overallDatas.size()));
    }

    public Double getAverageIntake() {
        if (this.overallDatas.isEmpty()) {
            return 0.0;
        }

        Double total = 0.0;
        for (Data data : this.overallDatas) {

            if (data.getDate().getMonth().equals(LocalDate.now().getMonth())) {
                total += data.getIntake();
            }
        }

        return Double.valueOf(String.format("%.2f", total / this.overallDatas.size()));
    }

    public Double getAverageBurnt() {
        if (this.overallDatas.isEmpty()) {
            return 0.0;
        }

        Double total = 0.0;
        for (Data data : this.overallDatas) {
            if (data.getDate().getMonth().equals(LocalDate.now().getMonth())) {
                total += data.getBurnt();
            }
        }

        return Double.valueOf(String.format("%.2f", total / this.overallDatas.size()));
    }

    public Integer getDaysLogin() {
        return daysLogin;
    }

    public void setDaysLogin(Integer daysLogin) {
        this.daysLogin = daysLogin;
    }

    public void login() {
        this.daysLogin += 1;
    }

    public Map<String, Double> getAverageWorkout() {
        Map<String, Double> workoutTotal = new HashMap<>();

        for (BurnsCal burnsCal : this.workouts) {
            if (burnsCal.getDate().getMonth().equals(LocalDate.now().getMonth())) {

                Map<String, Double> workouts = burnsCal.getTypes();
                System.out.println(workouts);
                for (Map.Entry<String, Double> entry : workouts.entrySet()) {
                    workoutTotal.merge(entry.getKey(), entry.getValue(), Double::sum);
                }
            }
        }

        System.err.println(this.overallDatas.size());

        for (Map.Entry<String, Double> entry : workoutTotal.entrySet()) {
            workoutTotal.put(entry.getKey(), entry.getValue() / this.overallDatas.size());
        }

        System.out.println(workoutTotal);
        return workoutTotal;

    }

}

package VTTP_SSF.ProjectA.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NewUsers {
    private String id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private LocalDate createdDate;
    private LocalDate lastLogin;

    @NotNull(message = "Height cannot be empty")
    @Min(value = 0, message = "Age cannot be below 0")
    private Long height;
    @NotNull(message = "Weight cannot be empty")
    @Min(value = 0, message = "Age cannot be below 0")
    private Long weight;
    @NotNull(message = "Age cannot be empty")
    @Min(value = 0, message = "Age cannot be below 0")
    private Long age;

    @NotEmpty(message = "Aim cannot be empty")
    private String aim;

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    @NotEmpty(message = "Activity level cannot be empty")
    private String actLevel;

    private Stack<Meals> meals;
    private Stack<BurnsCal> workouts;
    private Stack<Data> overallDatas;
    private Integer daysLogin;
    private String securityQuestion;
    @NotEmpty(message = "Answer must be given")
    private String securityAnswer;

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
                .add("security_question",this.securityQuestion)
                .add("security_answer",this.securityAnswer)
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


    public Stack<Meals> getMeals() {
        return meals;
    }

    public void setMeals(Stack<Meals> meals) {
        this.meals = meals;
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

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActLevel() {
        return actLevel;
    }

    public void setActLevel(String actLevel) {
        this.actLevel = actLevel;
    }

    public Stack<BurnsCal> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Stack<BurnsCal> workouts) {
        this.workouts = workouts;
    }

    public Stack<Data> getOverallDatas() {
        return overallDatas;
    }

    public void setOverallDatas(Stack<Data> overallDatas) {
        this.overallDatas = overallDatas;
    }

    public Integer getDaysLogin() {
        return daysLogin;
    }

    public void setDaysLogin(Integer daysLogin) {
        this.daysLogin = daysLogin;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    

    

    

}

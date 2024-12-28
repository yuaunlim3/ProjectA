package VTTP_SSF.ProjectA.Repo;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_SSF.ProjectA.Model.BurnsCal;
import VTTP_SSF.ProjectA.Model.Data;
import VTTP_SSF.ProjectA.Model.Food;
import VTTP_SSF.ProjectA.Model.Meals;
import VTTP_SSF.ProjectA.Model.NewUsers;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Model.Workout;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class LoginRepo {
    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    // set id {....}
    public void newUser(NewUsers user) {
        JsonObject json = user.toJson();
        template.opsForValue().set(user.getName(), json.toString());
    }

    // keys *
    public Boolean checkUser(String name) {
        Set<String> nameList = template.keys("*");
        for (String checkName : nameList) {
            if (checkName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // keys *
    // set user.name {...}
    public void login(Users user) {
        String value = (String) template.opsForValue().get(user.getName());
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                LocalDate date = LocalDate.now();
                int currYear = date.getYear();
                LocalDate creatDate = result.getCreatedDate();
                int createYear = creatDate.getYear();

                Long age = result.getAge() + currYear - createYear;
                // New meals:
                Stack<Meals> mealsList = result.getMeals();

                Meals curr = mealsList.isEmpty() ? null : mealsList.peek();

                if (curr == null || !curr.getDate().equals(LocalDate.now())) {
                    curr = new Meals();
                    curr.setDate(LocalDate.now());
                    curr.setMeal(new HashMap<String, List<Food>>());
                    mealsList.push(curr);
                }

                // for workouts:
                Stack<BurnsCal> workoutList = result.getWorkouts();
                BurnsCal workout = workoutList.isEmpty() ? null : workoutList.peek();

                if (workout == null || !workout.getDate().equals(LocalDate.now())) {
                    workout = new BurnsCal();
                    workout.setDate(LocalDate.now());
                    workout.setWorkoutList(new ArrayList<Workout>());
                    workoutList.push(workout);
                }
                // for Data:
                Stack<Data> overallData = result.getOverallDatas();
                Data data = overallData.isEmpty() ? null : overallData.peek();
                if (data == null || !data.getDate().equals(LocalDate.now())) {
                    data = new Data();
                    data.setDate(LocalDate.now());
                    overallData.push(data);
                }

                for (Data datas : overallData) {
                    System.out.println(datas);
                }
                Data dataTemp = overallData.isEmpty() ? null : overallData.pop();
                Data dataCheck = overallData.isEmpty() ? null : overallData.peek();
                overallData.push(dataTemp);


                if (!result.getLastLogin().equals(LocalDate.now())) {
                    if (dataTemp != null && dataCheck != null) {
                        long daysBetween = ChronoUnit.DAYS.between(dataCheck.getDate(), dataTemp.getDate());
                        if (daysBetween == 1) {
                            int days = result.getDaysLogin();
                            days++;
                            result.setDaysLogin(days);
                        } else if (daysBetween > 1) {
                            result.setDaysLogin(1);
                        }
                    }
                }

                result.setAge(age);
                result.setLastLogin(LocalDate.now());
                template.opsForValue().set(user.getName(), result.toJson().toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public Boolean checkPassword(String name, String password) {
        String info = (String) template.opsForValue().get(name);
        if (info != null) {
            try (JsonReader reader = Json.createReader(new StringReader(info))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                if (result.getPassword().equals(password)) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return false;
    }

    public Boolean checkAnswer(String name,String answer){
        String info = (String) template.opsForValue().get(name);
        if (info != null) {
            try (JsonReader reader = Json.createReader(new StringReader(info))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                System.out.printf("Answer: %s\n Check: %s\n",answer,result.getSecurityAnswer());
                if (result.getSecurityAnswer().equals(answer)) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return false;
    }
}

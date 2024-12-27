package VTTP_SSF.ProjectA.Repo;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_SSF.ProjectA.Model.Data;
import VTTP_SSF.ProjectA.Model.Food;
import VTTP_SSF.ProjectA.Model.Meals;
import VTTP_SSF.ProjectA.Model.Users;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class MealRepo {
    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    public void addFood(String name, Food food, String mealType) {

        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);

                Stack<Meals> mealData = result.getMeals();
                Meals current = null;

                if (!mealData.isEmpty()) {
                    current = mealData.peek();

                    if (!current.getDate().equals(LocalDate.now())) {
                        current = null;
                    } else if (current.getDate().equals(LocalDate.now())) {
                        current = mealData.pop();
                    }
                }

                if (current == null) {
                    current = new Meals();
                    current.setDate(LocalDate.now());
                }

                Map<String, List<Food>> currentMeals = current.getMeal();
                if (currentMeals == null) {
                    currentMeals = new HashMap<>();
                }

                if (!currentMeals.containsKey(mealType)) {
                    currentMeals.put(mealType, new ArrayList<>());
                }

                List<Food> foods = currentMeals.get(mealType);
                foods.add(food);
                currentMeals.put(mealType, foods);
                current.setMeal(currentMeals);

                mealData.push(current);

                result.setMeals(mealData);

                Stack<Data> overallDatas = result.getOverallDatas();
                Data data = null;
                if (!overallDatas.isEmpty()) {
                    data = overallDatas.peek();
                    if (!data.getDate().equals(LocalDate.now())) {
                        data = null;
                    } else if(data.getDate().equals(LocalDate.now())){
                        data = overallDatas.pop();
                    }
                }
                if (data == null) {
                    data = new Data();
                }

                data.setIntake(food.getCalories());
                overallDatas.push(data);
                result.setOverallDatas(overallDatas);

                JsonObject jsonRes = result.toJson();
                template.opsForValue().set(name, jsonRes.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    } 

    

}

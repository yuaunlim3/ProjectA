package VTTP_SSF.ProjectA.Repo;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_SSF.ProjectA.Model.BurnsCal;
import VTTP_SSF.ProjectA.Model.Data;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Model.Workout;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class WorkoutRepo {
    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    public void addWorkout(String name, Workout workout) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {

                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                Stack<BurnsCal> workoutData = result.getWorkouts();
                BurnsCal current = null;
                if (workoutData == null) {
                    workoutData = new Stack<>();
                }

                if (!workoutData.isEmpty()) {
                    current = workoutData.peek();

                    if (!current.getDate().equals(LocalDate.now())) {
                        current = null;
                    } else if (current.getDate().equals(LocalDate.now())) {
                        current = workoutData.pop();
                    }
                }

                if (current == null) {
                    current = new BurnsCal();
                    current.setDate(LocalDate.now());
                }

                List<Workout> workoutList = current.getWorkoutList();
                if (workoutList == null) {
                    workoutList = new ArrayList<>();
                }

                workoutList.add(workout);
                current.setWorkoutList(workoutList);
                workoutData.push(current);
                result.setWorkouts(workoutData);

                Stack<Data> overallDatas = result.getOverallDatas();
                Data data = null;
                if (!overallDatas.isEmpty()) {
                    data = overallDatas.peek();
                    if (!data.getDate().equals(LocalDate.now())) {
                        data = null;
                    }else if(data.getDate().equals(LocalDate.now())){
                        data = overallDatas.pop();
                    }
                }
                if (data == null) {
                    data = new Data();
                }

                data.setBurnt(workout.getCalories());
                overallDatas.push(data);
                result.setOverallDatas(overallDatas);
                JsonObject object = result.toJson();
                template.opsForValue().set(name, object.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

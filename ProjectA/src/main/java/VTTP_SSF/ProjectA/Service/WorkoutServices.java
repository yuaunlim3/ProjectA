package VTTP_SSF.ProjectA.Service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import VTTP_SSF.ProjectA.Model.Workout;
import VTTP_SSF.ProjectA.Repo.WorkoutRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WorkoutServices {
    private final String WO_URL = "https://trackapi.nutritionix.com/v2/natural/exercise";
    @Value("${api.id}")
    private String API_ID;
    @Value("${api.key}")
    private String API_KEY;

    @Autowired
    private WorkoutRepo workoutRepo;

    public Boolean addWorkout(String name, Workout workout) {
        String workoutDetails = workout.getName();
        String duration = Double.toString(workout.getDurations());
        String requestBody = "{ \"query\": \"" + workoutDetails + " for " + duration +" minutes" + "\" }";

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Build the request
            RequestEntity<String> req = RequestEntity
                    .post(WO_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-app-key", API_KEY)
                    .header("x-app-id", API_ID)
                    .body(requestBody);

            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            String payload = resp.getBody();
            System.out.println(payload);

            StringReader reader = new StringReader(payload);
            JsonReader jsonReader = Json.createReader(reader);
            JsonObject workoutInfo = jsonReader.readObject();

            if(workoutInfo.containsKey("exercises")){
                JsonArray info = workoutInfo.getJsonArray("exercises");
                if(info != null && !info.isEmpty()){
                    JsonObject object = info.getJsonObject(0);
                    Double calories = object.getJsonNumber("nf_calories").doubleValue();
                    workout.setCalories(calories);
                }

                workoutRepo.addWorkout(name, workout);

                return true;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

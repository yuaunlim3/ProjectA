package VTTP_SSF.ProjectA.Service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import VTTP_SSF.ProjectA.Model.Food;
import VTTP_SSF.ProjectA.Repo.MealRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class MealServices {
    private static final String API_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    @Value("${api.id}")
    private String API_ID;
    @Value("${api.key}")
    private String API_KEY;
    
    @Autowired
    private MealRepo mealRepo;

    public Boolean addFood(String name, String mealType, String foodName,String serving, String amount){
        String query = amount + " " + serving + " of " + foodName;
        String requestBody = "{ \"query\": \"" + query + "\" }";
        System.out.println(requestBody);
        RestTemplate restTemplate = new RestTemplate();

    
        try {
            RequestEntity<String> req = RequestEntity
                    .post(API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-app-key", API_KEY)
                    .header("x-app-id", API_ID)
                    .body(requestBody);

            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            String payload = resp.getBody();
    
            StringReader reader = new StringReader(payload);
            JsonReader jsonReader = Json.createReader(reader);
            JsonObject foodInfo = jsonReader.readObject();

            if (foodInfo.containsKey("foods")) {
                JsonArray items = foodInfo.getJsonArray("foods");
                if (items != null && !items.isEmpty()) {
                    Double total = 0.0;
                    for(int i = 0; i < items.size();i++){
                        JsonObject foodItem = items.getJsonObject(i);
                        Double calories = foodItem.getJsonNumber("nf_calories").doubleValue();
                        
                        total += calories;
                    }

                    Food food = new Food();
                    

                    food.setFoodName(foodName);
                    food.setCalories(total);
    
                    mealRepo.addFood(name, food, mealType);
    
                    return true;
                }
            }
            return false;
    
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
}

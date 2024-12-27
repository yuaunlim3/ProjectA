package VTTP_SSF.ProjectA.Repo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_SSF.ProjectA.Model.Users;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class DataRepo {
    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    public List<Users> getLeaderBoard(String category) {
        List<Users> resultList = new ArrayList<>();
        Set<String> keys = template.keys("*");

        for (String key : keys) {
            String payload = template.opsForValue().get(key);
            StringReader reader = new StringReader(payload);
            JsonReader jsonReader = Json.createReader(reader);
            JsonObject object = jsonReader.readObject();

            Users user = Users.fromJson(object);
            resultList.add(user);
        }

        switch (category.toLowerCase()) {
            case "intake":
                resultList.sort(Comparator.comparing(Users::getAverageIntake).reversed()
                        .thenComparing(Users::getName));
                break;
            case "burnt":
                resultList.sort(Comparator.comparing(Users::getAverageBurnt).reversed()
                        .thenComparing(Users::getName));
                break;
            case "net":
                resultList.sort(Comparator.comparing(Users::getAverageNet).reversed()
                        .thenComparing(Users::getName));
                break;
            case "days":
                resultList.sort(Comparator.comparing(Users::getDaysLogin).reversed()
                        .thenComparing(Users::getName));
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        return resultList;
    }
}

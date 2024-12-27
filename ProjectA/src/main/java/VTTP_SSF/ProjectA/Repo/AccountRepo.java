package VTTP_SSF.ProjectA.Repo;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_SSF.ProjectA.Model.Users;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class AccountRepo {
    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    // get name
    public Users getUser(String name) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                //System.err.printf(">>>>>>>>>>>>>>>>BODY: %s\n",body.toString());
                Users result = Users.fromJson(body);
                //System.err.printf(">>>>>>>>>>>>>>>>Result: %s\n",result.toString());
                return result;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return null;
    }

    // get name
    public void updatePassword(String password, String name) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                result.setPassword(password);
                JsonObject jsonRes = result.toJson();
                template.opsForValue().set(name, jsonRes.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    // get name
    public void updateWeight(Long weight, String name) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                result.setWeight(weight);
                JsonObject jsonRes = result.toJson();
                template.opsForValue().set(name, jsonRes.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    // get name
    public void updateHeight(Long height, String name) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                result.setWeight(height);
                JsonObject jsonRes = result.toJson();
                template.opsForValue().set(name, jsonRes.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    // get name
    public void updateAim(String aim, String name) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                result.setAim(aim);
                JsonObject jsonRes = result.toJson();
                template.opsForValue().set(name, jsonRes.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    // get name
    public void updateActLevel(String actLevel, String name) {
        String value = (String) template.opsForValue().get(name);
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                Users result = Users.fromJson(body);
                result.setActLevel(actLevel);
                JsonObject jsonRes = result.toJson();
                template.opsForValue().set(name, jsonRes.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public String getRandomAccount(){
        return template.randomKey();
    }

}

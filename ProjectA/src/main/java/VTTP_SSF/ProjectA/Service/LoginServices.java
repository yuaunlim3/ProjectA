package VTTP_SSF.ProjectA.Service;

import java.time.LocalDate;
import java.util.Stack;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP_SSF.ProjectA.Model.BurnsCal;
import VTTP_SSF.ProjectA.Model.Data;
import VTTP_SSF.ProjectA.Model.Meals;
import VTTP_SSF.ProjectA.Model.NewUsers;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Repo.LoginRepo;

@Service
public class LoginServices {
    @Autowired
    private LoginRepo loginRepo;
    public void newUser(NewUsers user) {

        String id = UUID.randomUUID().toString().substring(0, 8);
        user.setId(id);
        user.setCreatedDate(LocalDate.now());
        user.setLastLogin(LocalDate.now());
        user.setMeals(new Stack<Meals>());
        user.setWorkouts(new Stack<BurnsCal>());
        Stack<Data> dataList = new Stack<>();
        dataList.add(new Data());
        user.setOverallDatas(dataList);
        user.setDaysLogin(1);
    
        loginRepo.newUser(user);
    }
    

    public Boolean checkUser(String name) {
        return loginRepo.checkUser(name);
    }

    public void loginUser(Users user) {
        loginRepo.login(user);
    }

    public Boolean checkPassword(String name,String password) {
        return loginRepo.checkPassword(name,password);
    }

    public Boolean checkAnswer(String name,String answer) {
        return loginRepo.checkAnswer(name,answer);
    }
}

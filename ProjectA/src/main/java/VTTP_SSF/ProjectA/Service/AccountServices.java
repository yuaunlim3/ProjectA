package VTTP_SSF.ProjectA.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Repo.AccountRepo;

@Service
public class AccountServices {
        @Autowired private AccountRepo accountRepo;

    public void updatePassword(String password,String name){
        accountRepo.updatePassword(password,name);
    }

    public Users getUser(String name){
        return accountRepo.getUser(name);
    }

    public void updateHeight(Long height, String name){
        accountRepo.updateHeight(height, name);
    }

    public void updateWeight(Long weight,String name){
        accountRepo.updateWeight(weight,name);
    }

    public void updateAim(String aim,String name){
        accountRepo.updateAim(aim, name);
    }

    
    public void updateActLevel(String actLevel,String name){
        accountRepo.updateActLevel(actLevel, name);
    }

    public String getRandomAccount(){
        return accountRepo.getRandomAccount();
    }

    public void deleteAccount(String name){
        accountRepo.deleteAccount(name);
    }
}

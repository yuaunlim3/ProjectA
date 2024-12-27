package VTTP_SSF.ProjectA.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP_SSF.ProjectA.Model.BurnsCal;
import VTTP_SSF.ProjectA.Model.Meals;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Repo.DataRepo;

@Service
public class DataServices {
    @Autowired
    private DataRepo dataRepo;

    public List<Users> getLeaderBoard(String category){
        return dataRepo.getLeaderBoard(category);
    }

    public Stack<Meals> queryConsumed(Users user, String year, String month){
        Stack<Meals> mealStack = user.getMeals();
        Stack<Meals> filteredMeals = new Stack<>();
        
        int targetYear = Integer.parseInt(year);
        int targetMonth = Integer.parseInt(month);
        
        for (Meals meal : mealStack) {
            YearMonth mealYearMonth = YearMonth.from(meal.getDate());
            
            if (mealYearMonth.getYear() == targetYear && mealYearMonth.getMonthValue() == targetMonth) {
                filteredMeals.push(meal);
            }
        }
        
        return filteredMeals;
    }

    public Stack<BurnsCal> queryWorkout(Users user, String year, String month){
        Stack<BurnsCal> workoutStack = user.getWorkouts();
        Stack<BurnsCal> result = new Stack<>();
                
        int targetYear = Integer.parseInt(year);
        int targetMonth = Integer.parseInt(month);


        for(BurnsCal burnsCal:workoutStack){
            YearMonth yearMonth = YearMonth.from(burnsCal.getDate());
            if (yearMonth.getYear() == targetYear && yearMonth.getMonthValue() == targetMonth) {
                result.push(burnsCal);
            }
        }

        return result;
    }
}

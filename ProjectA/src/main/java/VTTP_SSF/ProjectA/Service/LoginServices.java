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


        /*
        Food breakfastFood1 = new Food();
        breakfastFood1.setFoodName("Pancakes with Syrup");
        breakfastFood1.setCalories(350.00);
        List<Food> breakfastList1 = new ArrayList<>();
        breakfastList1.add(breakfastFood1);
    
        Food lunchFood1 = new Food();
        lunchFood1.setFoodName("Grilled Chicken Salad");
        lunchFood1.setCalories(400.00);
        List<Food> lunchList1 = new ArrayList<>();
        lunchList1.add(lunchFood1);
    
        Food dinnerFood1 = new Food();
        dinnerFood1.setFoodName("Fish and Chips");
        dinnerFood1.setCalories(316.88);
        List<Food> dinnerList1 = new ArrayList<>();
        dinnerList1.add(dinnerFood1);
    
        Meals dailyMeals1 = new Meals();
        dailyMeals1.setDate(LocalDate.parse("2024-12-18"));
        Map<String, List<Food>> mealsMap1 = new HashMap<>();
        mealsMap1.put("breakfast", breakfastList1);
        mealsMap1.put("lunch", lunchList1);
        mealsMap1.put("dinner", dinnerList1);
        dailyMeals1.setMeal(mealsMap1);
        user.getMeals().push(dailyMeals1);
    
        Workout cardioWorkout1 = new Workout();
        cardioWorkout1.setName("Swimming");
        cardioWorkout1.setType("Cardio");
        cardioWorkout1.setDurations(60.0);
        cardioWorkout1.setCalories(500.0);
        List<Workout> workoutSessions1 = new ArrayList<>();
        workoutSessions1.add(cardioWorkout1);
    
        BurnsCal dailyBurns1 = new BurnsCal();
        dailyBurns1.setDate(LocalDate.parse("2024-12-18"));
        dailyBurns1.setWorkoutList(workoutSessions1);
        user.getWorkouts().push(dailyBurns1);
    
        Data data1 = new Data();
        data1.setDate(LocalDate.parse("2024-12-18"));
        data1.setIntake(dailyMeals1.totalCalories());
        data1.setBurnt(dailyBurns1.getTotalCalories());

        Food breakfastFood2 = new Food();
        breakfastFood2.setFoodName("Waffle with Peanut Butter");
        breakfastFood2.setCalories(406.41);
        List<Food> breakfastList2 = new ArrayList<>();
        breakfastList2.add(breakfastFood2);
    
        Food lunchFood2 = new Food();
        lunchFood2.setFoodName("Chicken Rice");
        lunchFood2.setCalories(316.88);
        List<Food> lunchList2 = new ArrayList<>();
        lunchList2.add(lunchFood2);
    
        Food dinnerFood2 = new Food();
        dinnerFood2.setFoodName("Fish and Chips");
        dinnerFood2.setCalories(316.88);
        List<Food> dinnerList2 = new ArrayList<>();
        dinnerList2.add(dinnerFood2);
    
        Meals dailyMeals2 = new Meals();
        dailyMeals2.setDate(LocalDate.parse("2024-12-19"));
        Map<String, List<Food>> mealsMap2 = new HashMap<>();
        mealsMap2.put("breakfast", breakfastList2);
        mealsMap2.put("lunch", lunchList2);
        mealsMap2.put("dinner", dinnerList2);
        dailyMeals2.setMeal(mealsMap2);
        user.getMeals().push(dailyMeals2);
    
        Workout cardioWorkout2 = new Workout();
        cardioWorkout2.setName("Run");
        cardioWorkout2.setType("Cardio");
        cardioWorkout2.setDurations(60.0);
        cardioWorkout2.setCalories(686.0);
        List<Workout> workoutSessions2 = new ArrayList<>();
        workoutSessions2.add(cardioWorkout2);
    
        BurnsCal dailyBurns2 = new BurnsCal();
        dailyBurns2.setDate(LocalDate.parse("2024-12-19"));
        dailyBurns2.setWorkoutList(workoutSessions2);
        user.getWorkouts().push(dailyBurns2);
    
        Data data2 = new Data();
        data2.setDate(LocalDate.parse("2024-12-19"));
        data2.setIntake(dailyMeals2.totalCalories());
        data2.setBurnt(dailyBurns2.getTotalCalories());
    
        Food breakfastFood3 = new Food();
        breakfastFood3.setFoodName("Omelette");
        breakfastFood3.setCalories(300.00);
        List<Food> breakfastList3 = new ArrayList<>();
        breakfastList3.add(breakfastFood3);
    
        Food lunchFood3 = new Food();
        lunchFood3.setFoodName("Grilled Cheese Sandwich");
        lunchFood3.setCalories(450.00);
        List<Food> lunchList3 = new ArrayList<>();
        lunchList3.add(lunchFood3);
    
        Food dinnerFood3 = new Food();
        dinnerFood3.setFoodName("Spaghetti Bolognese");
        dinnerFood3.setCalories(500.00);
        List<Food> dinnerList3 = new ArrayList<>();
        dinnerList3.add(dinnerFood3);
    
        Meals dailyMeals3 = new Meals();
        dailyMeals3.setDate(LocalDate.parse("2024-12-20"));
        Map<String, List<Food>> mealsMap3 = new HashMap<>();
        mealsMap3.put("breakfast", breakfastList3);
        mealsMap3.put("lunch", lunchList3);
        mealsMap3.put("dinner", dinnerList3);
        dailyMeals3.setMeal(mealsMap3);
        user.getMeals().push(dailyMeals3);
    
        Workout cardioWorkout3 = new Workout();
        cardioWorkout3.setName("Cycling");
        cardioWorkout3.setType("Cardio");
        cardioWorkout3.setDurations(60.0);
        cardioWorkout3.setCalories(600.0);
        List<Workout> workoutSessions3 = new ArrayList<>();
        workoutSessions3.add(cardioWorkout3);
    
        BurnsCal dailyBurns3 = new BurnsCal();
        dailyBurns3.setDate(LocalDate.parse("2024-12-20"));
        dailyBurns3.setWorkoutList(workoutSessions3);
        user.getWorkouts().push(dailyBurns3);
    
        Data data3 = new Data();
        data3.setDate(LocalDate.parse("2024-12-20"));
        data3.setIntake(dailyMeals3.totalCalories());
        data3.setBurnt(dailyBurns3.getTotalCalories());
    
        Food breakfastFood4 = new Food();
        breakfastFood4.setFoodName("Pancakes");
        breakfastFood4.setCalories(350.00);
        List<Food> breakfastList4 = new ArrayList<>();
        breakfastList4.add(breakfastFood4);
    
        Food lunchFood4 = new Food();
        lunchFood4.setFoodName("Chicken Salad");
        lunchFood4.setCalories(400.00);
        List<Food> lunchList4 = new ArrayList<>();
        lunchList4.add(lunchFood4);
    
        Food dinnerFood4 = new Food();
        dinnerFood4.setFoodName("Steak and Potatoes");
        dinnerFood4.setCalories(600.00);
        List<Food> dinnerList4 = new ArrayList<>();
        dinnerList4.add(dinnerFood4);
    
        Meals dailyMeals4 = new Meals();
        dailyMeals4.setDate(LocalDate.parse("2024-12-21"));
        Map<String, List<Food>> mealsMap4 = new HashMap<>();
        mealsMap4.put("breakfast", breakfastList4);
        mealsMap4.put("lunch", lunchList4);
        mealsMap4.put("dinner", dinnerList4);
        dailyMeals4.setMeal(mealsMap4);
        user.getMeals().push(dailyMeals4);
    
        Workout cardioWorkout4 = new Workout();
        cardioWorkout4.setName("Yoga");
        cardioWorkout4.setType("Yoga");
        cardioWorkout4.setDurations(60.0);
        cardioWorkout4.setCalories(250.0);
        List<Workout> workoutSessions4 = new ArrayList<>();
        workoutSessions4.add(cardioWorkout4);
    
        BurnsCal dailyBurns4 = new BurnsCal();
        dailyBurns4.setDate(LocalDate.parse("2024-12-21"));
        dailyBurns4.setWorkoutList(workoutSessions4);
        user.getWorkouts().push(dailyBurns4);
    
        Data data4 = new Data();
        data4.setDate(LocalDate.parse("2024-12-21"));
        data4.setIntake(dailyMeals4.totalCalories());
        data4.setBurnt(dailyBurns4.getTotalCalories());
    
        Food breakfastFood5 = new Food();
        breakfastFood5.setFoodName("Scrambled Eggs");
        breakfastFood5.setCalories(280.00);
        List<Food> breakfastList5 = new ArrayList<>();
        breakfastList5.add(breakfastFood5);
    
        Food lunchFood5 = new Food();
        lunchFood5.setFoodName("Tuna Salad");
        lunchFood5.setCalories(350.00);
        List<Food> lunchList5 = new ArrayList<>();
        lunchList5.add(lunchFood5);
    
        Food dinnerFood5 = new Food();
        dinnerFood5.setFoodName("Chicken Alfredo");
        dinnerFood5.setCalories(700.00);
        List<Food> dinnerList5 = new ArrayList<>();
        dinnerList5.add(dinnerFood5);
    
        Meals dailyMeals5 = new Meals();
        dailyMeals5.setDate(LocalDate.parse("2024-12-22"));
        Map<String, List<Food>> mealsMap5 = new HashMap<>();
        mealsMap5.put("breakfast", breakfastList5);
        mealsMap5.put("lunch", lunchList5);
        mealsMap5.put("dinner", dinnerList5);
        dailyMeals5.setMeal(mealsMap5);
        user.getMeals().push(dailyMeals5);
    
        Workout cardioWorkout5 = new Workout();
        cardioWorkout5.setName("Walking");
        cardioWorkout5.setType("Cardio");
        cardioWorkout5.setDurations(60.0);
        cardioWorkout5.setCalories(250.0);
        List<Workout> workoutSessions5 = new ArrayList<>();
        workoutSessions5.add(cardioWorkout5);
    
        BurnsCal dailyBurns5 = new BurnsCal();
        dailyBurns5.setDate(LocalDate.parse("2024-12-22"));
        dailyBurns5.setWorkoutList(workoutSessions5);
        user.getWorkouts().push(dailyBurns5);
    
        Data data5 = new Data();
        data5.setDate(LocalDate.parse("2024-12-22"));
        data5.setIntake(dailyMeals5.totalCalories());
        data5.setBurnt(dailyBurns5.getTotalCalories());
        */
        Stack<Data> dataList = new Stack<>();
        //dataList.push(data1);
        //dataList.push(data2);
        //dataList.push(data3);
        //dataList.push(data4);
        //dataList.push(data5);
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
}

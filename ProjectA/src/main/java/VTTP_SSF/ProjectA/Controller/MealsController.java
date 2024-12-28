package VTTP_SSF.ProjectA.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP_SSF.ProjectA.Model.Food;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Service.AccountServices;
import VTTP_SSF.ProjectA.Service.MealServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class MealsController {
    @Autowired
    private AccountServices accountService;

    @Autowired
    private MealServices mealServices;

    // Navigate from navbar
    @GetMapping("/meals/{name}")
    public String mealPage(@PathVariable String name, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);
        model.addAttribute("food", new Food());
        return "mealpage";
    }
    //submit meal details
    @PostMapping("/addMeal/{name}")
    public String addMeal(@Valid @ModelAttribute("food") Food food, BindingResult bindingResult,
            @PathVariable String name, @RequestParam String mealType, @RequestParam String servingSize,
            @RequestParam int amount, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            Users user = accountService.getUser(name);
            model.addAttribute("loginUser", user);
            return "mealpage";
        }

        Boolean check = mealServices.addFood(name, mealType, food.getFoodName(), servingSize, Integer.toString(amount));
        if (!check) {
            ObjectError err = new ObjectError("globalError", "Food does not exist in database");
            bindingResult.addError(err);
            Users user = accountService.getUser(name);
            model.addAttribute("loginUser", user);
            return "mealpage";
        }

        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);
        model.addAttribute("message", "Meal");
        return "success";
    }
}

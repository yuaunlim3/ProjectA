package VTTP_SSF.ProjectA.Controller;

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

import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Model.Workout;
import VTTP_SSF.ProjectA.Service.AccountServices;
import VTTP_SSF.ProjectA.Service.WorkoutServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class WorkoutController {

    @Autowired
    private AccountServices accountService;

    @Autowired
    private WorkoutServices workoutServices;

    @GetMapping("/workout/{name}")
    public String workoutPage(@PathVariable String name, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);
        model.addAttribute("workout", new Workout());
        return "workoutpage";
    }

    @PostMapping("/addworkout/{name}")
    public String addWorkout(@PathVariable String name, Model model, @Valid @ModelAttribute("workout") Workout workout,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Users user = accountService.getUser(name);
            model.addAttribute("loginUser", user);
            return "workoutpage";
        }
        Boolean check = workoutServices.addWorkout(name, workout);
        if (!check) {
            ObjectError err = new ObjectError("globalError", "Food does not exist in database");
            bindingResult.addError(err);
            Users user = accountService.getUser(name);
            model.addAttribute("loginUser", user);
            return "workoutpage";
        }

        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);
        model.addAttribute("message", "Workout");

        return "success";
    }
}

package VTTP_SSF.ProjectA.Controller;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP_SSF.ProjectA.Model.BurnsCal;
import VTTP_SSF.ProjectA.Model.Meals;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Service.AccountServices;
import VTTP_SSF.ProjectA.Service.DataServices;

@Controller
@RequestMapping
public class DataController {
    @Autowired
    private AccountServices accountService;

    @Autowired
    private DataServices dataServices;

    @GetMapping("/datas/{name}")
    public String dataPage(@PathVariable String name, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);

        return "datapage";

    }

    @PostMapping("/datas/{name}")
    public String search(@PathVariable String name, @RequestParam String category, @RequestParam String year,
            @RequestParam String month, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);

        switch (category) {
            case "consumed":
                Stack<Meals> mealList = dataServices.queryConsumed(user, year, month);
                model.addAttribute("result", mealList);
                model.addAttribute("query", "consumed");
                break;

            case "workout":
                Stack<BurnsCal> workouts = dataServices.queryWorkout(user, year, month);
                model.addAttribute("result",workouts);
                model.addAttribute("query", "workout");
                break;

            default:
                break;
        }

        return "datapage";

    }
}

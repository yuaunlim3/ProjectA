package VTTP_SSF.ProjectA.Controller;

import java.util.List;
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
public class LeaderboardController {
    @Autowired
    private AccountServices accountService;
    @Autowired
    private DataServices dataServices;

    //Navigate from navbar
    @GetMapping("/leaderboard/{name}")
    public String dataPage(@PathVariable String name, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);

        return "leaderboard";

    } 

    //Search leaderboard based on category
    @PostMapping("/leaderboard/{name}")
    public String getLeaderBoard(@PathVariable String name,@RequestParam String category, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);

        List<Users> leaderboard = dataServices.getLeaderBoard(category);
        
        model.addAttribute("leaderboard", leaderboard);

        

        return "leaderboard";

    }

    //Go to other user datapage
    @GetMapping("datas/{loginName}/{name}")
    public String search(@PathVariable String loginName,@PathVariable String name, Model model){
        Users loginUser = accountService.getUser(loginName);
        Users searchUser = accountService.getUser(name);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("searchUser", searchUser);
        return "search";
    }

    //search for other user datas
    @PostMapping("datas/{loginName}/{name}")
    public String getData(@PathVariable String loginName,@PathVariable String name, @RequestParam String category, @RequestParam String year,
    @RequestParam String month, Model model){
        Users loginUser = accountService.getUser(loginName);
        Users searchUser = accountService.getUser(name);
        switch (category) {
            case "consumed":
                Stack<Meals> mealList = dataServices.queryConsumed(searchUser, year, month);
                model.addAttribute("result", mealList);
                model.addAttribute("query", "consumed");
                break;

            case "workout":
                Stack<BurnsCal> workouts = dataServices.queryWorkout(searchUser, year, month);
                model.addAttribute("result",workouts);
                model.addAttribute("query", "workout");
                break;

            default:
                break;
        }
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("searchUser", searchUser);
        return "search";
    }
}

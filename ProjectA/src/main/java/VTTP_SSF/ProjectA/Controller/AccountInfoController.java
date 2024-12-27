package VTTP_SSF.ProjectA.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Service.AccountServices;


@Controller
@RequestMapping
public class AccountInfoController {
    @Autowired
    private AccountServices accountService;

    //Go into the account info page
    @GetMapping("/accountInfo/{name}")
    public String getInfo(@PathVariable String name, Model model) {
        Users user = accountService.getUser(name);
        model.addAttribute("loginUser", user);
        return "accountInfo";
    }

    //update either password or budget

@PostMapping("/accountInfo/{name}")
public String update(
        @PathVariable String name, 
        @RequestParam(required = false) String newPassword,
        @RequestParam(required = false) Long weight,
        @RequestParam(required = false) Long height,
        @RequestParam(required = false) String actLevel,
        @RequestParam(required = false) String aim,
        Model model) {

    // Update the password if newPassword is provided
    if (newPassword != null) {
        accountService.updatePassword(newPassword, name);
    }

    // Update the budget if budget is provided (non-null)
    if (weight != null) {
        accountService.updateWeight(weight,name);
    }
    if(height != null){
        accountService.updateHeight(height, name);
    }
    if(actLevel != null){
        accountService.updateActLevel(actLevel, name);
    }
    if(aim !=null){
        accountService.updateAim(aim, name);
    }

    Users user = accountService.getUser(name);
    model.addAttribute("loginUser", user);
    return "accountInfo";
}


}


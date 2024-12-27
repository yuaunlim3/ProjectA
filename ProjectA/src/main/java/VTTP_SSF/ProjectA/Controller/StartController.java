package VTTP_SSF.ProjectA.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import VTTP_SSF.ProjectA.Model.NewUsers;
import VTTP_SSF.ProjectA.Model.Users;

@Controller
@RequestMapping({ "/", "/index.html" })
public class StartController {

    //Start the page
    @GetMapping({ "/", "/index.html" })
    public String start(){
        return "redirect:/loginpage";
    }

    //Show the Loginpage
    @GetMapping("/loginpage")
    public String loginpage(Model model){
        model.addAttribute("user",new Users());
        return "loginpage";
    }

    //Show the create Account page after link is click
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("user",new NewUsers());
        return "createpage";
    }

    //Logout from account
    @PostMapping("/loginpage")
    public String logout(Model model){
        model.addAttribute("user",new Users());
        return "loginpage";
    }
}
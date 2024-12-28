package VTTP_SSF.ProjectA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP_SSF.ProjectA.Model.NewUsers;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Service.AccountServices;

@Controller
@RequestMapping({ "/", "/index.html" })
public class StartController {

    @Autowired
    private AccountServices accountServices;
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

    //Logout from account or delete account
    @PostMapping("/loginpage")
    public String logout(@RequestParam(required = false) String delete,@RequestParam(required = false) String name, Model model){
        System.out.println(name);
        System.out.println(delete);
        if(delete != null){
            System.out.println("Delete account");
            accountServices.deleteAccount(name);
        }
        model.addAttribute("user",new Users());
        return "loginpage";
    }
}
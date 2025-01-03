package VTTP_SSF.ProjectA.Controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import VTTP_SSF.ProjectA.Model.NewUsers;
import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Service.AccountServices;
import VTTP_SSF.ProjectA.Service.LoginServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class LoginController {
    @Autowired
    private LoginServices loginService;

    @Autowired
    private AccountServices accountService;
    private final Logger logger = Logger.getLogger(LoginController.class.getName());

    // Submit form from login page
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") Users user, BindingResult binding) {
        if (binding.hasErrors()) {
            logger.info(binding.getAllErrors().toString());
            return "loginpage";
        }
        if (!loginService.checkUser(user.getName())) {
            logger.info("Exist");
            FieldError err = new FieldError("user", "name", "%s does not exist in database".formatted(user.getName()));
            binding.addError(err);
            return "createpage";
        }
        if (!loginService.checkPassword(user.getName(), user.getPassword())) {
            FieldError err = new FieldError("user", "password", "Password is invalid");
            binding.addError(err);
            return "loginpage";
        }
        System.out.println(user.toString());
        loginService.loginUser(user);
        return "redirect:/homepage/" + user.getName();
    }

    // submit form from create page
    @PostMapping("/createAccount")
    public String create(@Valid @ModelAttribute("user") NewUsers user, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            logger.info("Error");
            return "createpage";
        }
        if (loginService.checkUser(user.getName())) {
            FieldError err = new FieldError("user", "name", "%s exist in database".formatted(user.getName()));
            binding.addError(err);
            return "loginpage";
        }

        loginService.newUser(user);
        return "redirect:/homepage/" + user.getName();
    }

    // Login to the homepage
    @GetMapping("/homepage/{name}")
    public String Homepage(@PathVariable String name, Model model) {
        Users user = accountService.getUser(name);
        // System.out.println(user.toString());
        model.addAttribute("loginUser", user);
        return "homepage";
    }

    //Go to forget password page
    @GetMapping("/forget")
    public String forgetPage(Model model){
        model.addAttribute("user", null);
        return "forget";
    }

    // Provide a user 
    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestBody MultiValueMap<String, String> form,Model model){
        String name = form.getFirst("name");
        Users user = accountService.getUser(name);
        if(name == null || name.isEmpty()){
            model.addAttribute("errorName","Name must be given");
            return "forget";
        }
        if(!loginService.checkUser(name)){
            model.addAttribute("errorName","User is not in the database");
            return "forget";
        }
        model.addAttribute("user",user);
        return "forget";
    }

    //Provide security answer and new password
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody MultiValueMap<String, String> form,Model model){
        String name = form.getFirst("name");
        Users user = accountService.getUser(name);
        model.addAttribute("user",user);
        String answer = form.getFirst("answer");
        String password = form.getFirst("password");
        Boolean error = false;
        if(answer == null || answer.isEmpty()){
            model.addAttribute("errorAns","Answer must be given");
            error = true;
        }
        if(password == null || password.isEmpty()){
            model.addAttribute("errorPs","New password must be given");
            error = true;
        }
        if(!loginService.checkAnswer(name, answer)){
            model.addAttribute("errorAns","Answer is incorrect");
            error = true;
        }
        if (error) {
            return "forget";
        }

        accountService.updatePassword(password, name);
        return "redirect:/homepage/" + user.getName();
    }
}

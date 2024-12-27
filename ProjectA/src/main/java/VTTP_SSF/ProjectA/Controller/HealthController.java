package VTTP_SSF.ProjectA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import VTTP_SSF.ProjectA.Service.AccountServices;

@Controller
@RequestMapping
public class HealthController {
    @Autowired
    private AccountServices accountService;
    @GetMapping("/status")
       public ResponseEntity<String> check(){
        String user = accountService.getRandomAccount();
        if(user != null){
            return ResponseEntity.ok("{}");
            
        }

        return ResponseEntity.status(503).body("{}");
    }
}

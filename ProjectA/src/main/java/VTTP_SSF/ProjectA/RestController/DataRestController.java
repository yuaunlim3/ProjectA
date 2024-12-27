package VTTP_SSF.ProjectA.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VTTP_SSF.ProjectA.Model.Users;
import VTTP_SSF.ProjectA.Service.AccountServices;
import VTTP_SSF.ProjectA.Service.LoginServices;

@RestController
@RequestMapping
public class DataRestController {

    @Autowired
    private AccountServices accountServices;
    @Autowired
    private LoginServices loginServices;

    @GetMapping(path = "/getData/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getData(@PathVariable String name) {
        Users user = accountServices.getUser(name);

        if (user == null) {
            return ResponseEntity.status(404).body("{\"message\":\"No such account\"}");
        }

        String data = user.toJsonWithoutImportantData().toString();


        return ResponseEntity.ok().body(data);
    }

    @GetMapping(path = "/getData/{name}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allData(@PathVariable String name,@PathVariable String password) {
        Users user = accountServices.getUser(name);

        if (user == null) {
            return ResponseEntity.status(404).body("{\"message\":\"No such account\"}");
        }

        if(!loginServices.checkPassword(name,password)){
            return ResponseEntity.status(400).body("{\"message\":\"Invalid password given\"}");
        }
        

        String data = user.toJson().toString();


        return ResponseEntity.ok().body(data);
    }

}

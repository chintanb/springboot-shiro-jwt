package com.stormpath.shiro.samples.springboot.controllers;

import com.stormpath.shiro.samples.springboot.common.model.AuthenticationRequestDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/foo",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FooController {


    @Autowired
    public FooController() {

    }

    @GetMapping()
    public String foo() {
        return "bar";
    }

    /**
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    public String createToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws Exception {

        UsernamePasswordToken upt = new UsernamePasswordToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(upt);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "Sorry, please try again.";
        }
        return "Welcome !";
    }
}

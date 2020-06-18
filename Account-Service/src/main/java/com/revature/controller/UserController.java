package com.revature.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userservice;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
    	return userservice.getAllUsers();
    }

    
    @RequestMapping(value = "/adduser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody()
    public User addNewUser(@RequestBody User user) {
        return this.userservice.addUser(user);
    }
	
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody()
    public User loginUser(@RequestBody User user) {
    	if(this.userservice.existsByEmailAndPassword(user.getEmail(), user.getPassword()) == false) {
    		return null;
    	} else {
    		User u = this.userservice.findUserByEmail(user.getEmail());
    		u.setPassword("*****");
    		return u;
    	}
    }
    
    @RequestMapping(value = "/getuserbyemail", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody()
    public User findUserByEmail(@RequestBody User user) {
    	User u = this.userservice.findUserByEmail(user.getEmail());
    	//changing password before sending user to front end for security
    	u.setPassword("*****");
    	return u;
    }
 
    @RequestMapping(value = "/getuserbyid", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody()
    public User findUserById(@RequestBody User user) {
    	User u = this.userservice.findById(user.getUserId());
    	//changing password before sending user to front end for security
    	u.setPassword("*****");
    	return u;
    }
    
    @RequestMapping(value= "/updateinfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public void updateInformation(@RequestBody User user) {
        User u = this.userservice.findById(user.getUserId());
        u.setEmail(user.getEmail());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        this.userservice.addUser(u);
    }
    
    @RequestMapping(value= "/updatepassword", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody()
    public void updatePassword(@RequestBody User user) {
    	System.out.println(user.getUserId());
    	User u = this.userservice.findById(user.getUserId());
        u.setPassword(user.getPassword());
        this.userservice.addUser(u);
    }
    
    @RequestMapping(value= "/updateprofilepic", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public void updateProfilePic(@RequestBody User user) {
        User u = this.userservice.findById(user.getUserId());
        u.setProfilePic(user.getProfilePic());
        this.userservice.addUser(u);
    }
    
    
}
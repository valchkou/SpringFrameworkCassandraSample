package com.valchkou.sample.controller;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.valchkou.sample.model.Message;
import com.valchkou.sample.service.MessageService;
import com.valchkou.sample.service.UserService;


/**
 * @author Eugene Valchkou
 * REST api for chat messaging
 */
@RestController
@RequestMapping(value = "**/messages") 
public class MessageController {
	private static final Logger log = Logger.getLogger(MessageController.class.getName());
	
    @Autowired
    MessageService messageService;
    
    @Autowired
    UserService userService;
    
    /** read next messages */
    @RequestMapping(value = "/user/{userName}/{lastId}", method = RequestMethod.GET)
    public List<Message> readMessages(@PathVariable String userName, @PathVariable UUID lastId) {
        return messageService.readMessages(userName, lastId);
    }

    /** read all messages */
    @RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
    public List<Message> readMessages(@PathVariable String userName) {
        return messageService.readMessages(userName);
    }
    
    /** Post a new message */
    @RequestMapping(value = "/user/{userName}", method = RequestMethod.POST)
    public Message postMessage(@PathVariable String userName, @RequestBody String message) {
    	log.info("post message by user "+userName);
        return messageService.publishMessage(userName, message);
    }
    
    /** Get the next available user */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser() {
    	String user = userService.getNextUser();
    	log.info("returning user:"+ user);
        return user;
    }    
}

package com.valchkou.sample.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valchkou.sample.dao.MessageDAO;
import com.valchkou.sample.model.Message;
import com.valchkou.sample.model.MessageKey;


@Service
public class MessageService {
    
    @Autowired
    MessageDAO dao;
    
    @Autowired
    UserService userService;
    
    /** Build and save a Message */
    public Message publishMessage(String userName, String text) {
       MessageKey key = new MessageKey();
       key.setUserName(userName);
       Message m = new Message();
       m.setKey(key);
       m.setText(text);
       return dao.save(m);
    }

    /** Read messages from subscribers queue */
    public List<Message> readMessages(String userName, UUID lastMsgId) {
        String[] participants = userService.getOtherUsersInList(userName);
        return dao.queryUnreadMessages(userName, lastMsgId, participants);
    }
  
    /** Read messages from subscribers queue */
    public List<Message> readMessages(String userName) {
        String[] participants = userService.getOtherUsersInList("");
        return dao.queryUnreadMessages(userName, null, participants);
    }
}

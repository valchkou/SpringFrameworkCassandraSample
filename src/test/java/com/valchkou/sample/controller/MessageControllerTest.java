package com.valchkou.sample.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.mapping.EntityTypeParser;
import com.valchkou.sample.Main;
import com.valchkou.sample.dao.MessageDAO;
import com.valchkou.sample.model.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class MessageControllerTest {

    @Autowired
    MessageController target;

    @Autowired
    MessageDAO dao;
    
    private List<Message> messages = new ArrayList<>();
    
    /** Mimic chat conversation */
    @Test
    public void testUsersChating() throws Exception {
        String user1 = target.getUser();
        String user2 = target.getUser();
        String user3 = target.getUser();
        
        // user 1 posts 2 messages
        Message msg1 = target.postMessage(user1, "msg1");
        messages.add(msg1);
        Thread.sleep(1);
        Message msg2 = target.postMessage(user1, "msg2");
        messages.add(msg2);

        // user2 should receive 2 messages
        List<Message> user2Msgs = target.readMessages(user2);
        assertNotNull(user2Msgs);
        assertEquals(2, user2Msgs.size());
        assertEquals(msg1.getKey().getId(), user2Msgs.get(0).getKey().getId());
        
        // user 2 posts a message
        Message msg3 = target.postMessage(user2, "msg3");
        messages.add(msg3);

        // user2 should receive 0 messages
        user2Msgs = target.readMessages(user2, user2Msgs.get(1).getKey().getId());
        assertNotNull(user2Msgs);
        assertEquals(0, user2Msgs.size());

        // user1 should receive all messages
        List<Message> user1Msgs = target.readMessages(user1);
        assertNotNull(user1Msgs);
        assertEquals(3, user1Msgs.size());
        assertEquals(msg3.getKey().getId(), user1Msgs.get(2).getKey().getId());

        // user1 should receive 0 messages
        user1Msgs = target.readMessages(user1, user1Msgs.get(2).getKey().getId());
        assertNotNull(user1Msgs);
        assertEquals(0, user1Msgs.size());
        
        // user3 should receive 3 messages in proper order
        List<Message> user3Msgs = target.readMessages(user3);
        assertNotNull(user3Msgs);
        assertEquals(3, user3Msgs.size());
        assertEquals(msg1.getKey().getId(), user3Msgs.get(0).getKey().getId());
        assertEquals(msg2.getKey().getId(), user3Msgs.get(1).getKey().getId());
        assertEquals(msg3.getKey().getId(), user3Msgs.get(2).getKey().getId());
    }

    /**
     * make sure we retrieve different user each time we call the service
     */
    @Test
    public void testGetUser() {
        for (int i = 0; i < 10; i++) {
            // run test
            String test1 = target.getUser();
            String test2 = target.getUser();

            // validate
            assertNotNull(test1);
            assertNotNull(test2);
            assertNotSame(test2, test1);
        }
    }
        
    @Before
    public void setUp() {
        EntityTypeParser.removeAll();
    }
    
    @After
    public void cleanUp() {
        for (Message m: messages) {
            dao.delete(m);
        }
    }
    
    
}

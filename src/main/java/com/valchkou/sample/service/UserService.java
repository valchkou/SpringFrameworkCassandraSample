package com.valchkou.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * This service mocks chat users.
 */
@Service
public class UserService {
    
    private List<String> users = new ArrayList<String>();
    private int lastUserIdx = 0;
    
    public UserService() {
        initUsers();
    }
    
    /** retrieve next available User */
    public String getNextUser() {
        return users.get( calcNextIndex() );
    }

    /** retrieve next available User */
    public String[] getOtherUsersInList(String skipUser) {
       List<String> l = new ArrayList<>(users);
       l.remove(skipUser);
       return l.toArray(new String[l.size()]);
    }
        
    private void initUsers() {
        users.add("Dolly");
        users.add("Billy");
        users.add("Paddy");
        users.add("Baddy");
        users.add("Noody");
        users.add("Teddy");
        users.add("Joudy");
        users.add("Lilly");
        users.add("Yanny");
        users.add("Gucci");
        users.add("Zucci");
    }

    
    /** increment or reset the index */
    private synchronized int calcNextIndex() {
        if (lastUserIdx < (users.size()-1)) {
             ++lastUserIdx;
        } else {
            lastUserIdx = 0;
        }
        return lastUserIdx;
    }
}

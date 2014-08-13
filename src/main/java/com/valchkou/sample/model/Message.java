package com.valchkou.sample.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Table;

@Table(name="messages")
public class Message implements Comparable<Message> {
    
    @EmbeddedId
    private MessageKey key;
    private String text;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public MessageKey getKey() {
        return key;
    }

    public void setKey(MessageKey key) {
        this.key = key;
    }
    
    @Override
    public int compareTo(Message o) {
        return key.getId().compareTo(o.getKey().getId());
    }
}

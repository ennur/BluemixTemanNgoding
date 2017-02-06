package com.dicoding.bluemixlinebot.dao;


import com.dicoding.bluemixlinebot.model.JoinEvent;
import com.dicoding.bluemixlinebot.model.User;

import java.util.List;

public interface Dao
{
    public List<User> get();
    public List<User> getByUserId(String aUserId);
    public int registerLineId(String aUserId, String aLineId, String aDisplayName);
    public int joinEvent(String aEventId, String aUserId, String aLineId, String aDisplayName);
    public List<JoinEvent> getEvent();
    public List<JoinEvent> getByEventId(String aEventId);
    public List<JoinEvent> getByJoin(String aEventId, String aUserId);
};

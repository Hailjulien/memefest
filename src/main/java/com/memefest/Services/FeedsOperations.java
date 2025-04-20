package com.memefest.Services;


public interface FeedsOperations {

    public void sendToAll(Object message);

    public void sendToUser(Object message, String username);

    public void sendToAdmin(Object message, String username);

    public void sendToAdmins(Object message);

    public void sendToUsers(Object message);
}

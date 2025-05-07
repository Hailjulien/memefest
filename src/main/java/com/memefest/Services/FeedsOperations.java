package com.memefest.Services;

import jakarta.websocket.Session;

public interface FeedsOperations {

    public void sendToAll(Object message);

    public void sendToUser(Object message, String username);

    public void sendToAdmin(Object message, String username);

    public void sendToAdmins(Object message);

    public void sendToUsers(Object message);

    public void sendToSubscribers(Object message);

    public void addAdmin(Session session);

    public void removeAdmin(Session session);

    public void addClient(Session session);

    public void removeClient(Session session);
}

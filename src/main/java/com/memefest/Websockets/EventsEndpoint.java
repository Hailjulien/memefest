package com.memefest.Websockets;

import com.memefest.Services.Impl.EventService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

@Stateful(name = "eventsEndpoint")
public class EventsEndpoint extends Endpoint{

    @EJB
    private EventService eventService;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("New client connected: " + session.getId());
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        System
    }
    
}

package com.memefest.Services;

import java.time.LocalDateTime;
import java.util.Set;

import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.JSON.EventJSON;


import jakarta.websocket.Session;

public interface EventOperations {
    
    public void createScheduledEvent(Session session, EventJSON eventInfo, LocalDateTime dateTime);

    public void cancelScheduledEvent(EventJSON eventInfo);

    //public Set<EventJSON> getScheduledEvents(EventJSON eventJSON);
    //public void editScheduledEvent(EventJSON eventInfo);
    
    public void editEvent(EventJSON eventInfo);

    public void createEvent(EventJSON eventInfo);

    public void removeEvent(EventJSON eventInfo);

    //public Set<EventJSON> getEventsByUser(UserJSON user);

    public EventJSON getEventInfo(EventJSON event);

    //public Set<EventJSON> getEventsBetweenDates(LocalDateTime startDateTime, LocalDateTime lastDateTime);

    //public Set<PostJSON> getEventPosts(EventJSON eventInfo);

    public Event getEventEntity(EventJSON event);

    public Set<EventJSON> searchEvents(EventJSON event);
}

package com.memefest.Services.Impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.NoPermissionException;

import com.memefest.DataAccess.Category;
import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.Image;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.Video;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.ImageJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.DataAccess.JSON.VideoJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.FeedsOperations;
import com.memefest.Services.ImageOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Services.VideoOperations;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ejb.ScheduleExpression;
import jakarta.ejb.Stateless;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.ejb.TransactionRolledbackLocalException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;
import jakarta.websocket.Session;


@TransactionManagement(TransactionManagementType.CONTAINER)
//add event Categories in next iteration
@Stateless(name = "EventService")
public class EventService implements EventOperations{
    
    @EJB
    private FeedsOperations feedEndPointService;

    @EJB
    private UserOperations userOperations;

    @EJB
    private ImageOperations imageOps;

    @EJB
    private VideoOperations videoOps;

    @EJB 
    private PostOperations postOps;

    @EJB
    private NotificationOperations notOps;

    @Resource
    private TimerService timerService;

    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    
    public void createScheduledEvent(EventJSON eventInfo, LocalDateTime dueDate){
        ScheduleExpression schedule = new ScheduleExpression().year(dueDate.getYear()).dayOfMonth(dueDate.getDayOfMonth())
                                        .hour(dueDate.getHour()).minute(dueDate.getMinute());
        TimerConfig timerConf = new TimerConfig(eventInfo, true);       
        timerService.createCalendarTimer(schedule, timerConf);
    }

    
    @Timeout
    public void sendEvent(Timer timer) {
        if(timer.getInfo() instanceof  EventJSON){
            EventJSON eventInfo = (EventJSON) timer.getInfo();
            createEvent(eventInfo);                                            
        }
    }
    
    

    public void cancelScheduledEvent(EventJSON eventInfo) {
        timerService.getTimers().stream().filter(timer ->{
            if(timer.getInfo() instanceof EventJSON){
                EventJSON timerInfo = (EventJSON) timer.getInfo();
                if(timerInfo.getEventID() == eventInfo.getEventID() || timerInfo.getEventTitle().equalsIgnoreCase(eventInfo.getEventTitle())){
                    return true;
                }
                return false;
            }
            else return false;
        }).forEach(timer ->  timer.cancel());
    }

    public Map<EventJSON, LocalDateTime> getScheduledEvents(EventJSON event){
        Map<EventJSON, LocalDateTime> events = new HashMap<EventJSON, LocalDateTime>(); 
        for(Timer timer : timerService.getAllTimers()){
            if(timer.getInfo()instanceof EventJSON){
                EventJSON timerInfo = (EventJSON) timer.getInfo();
                if(timerInfo.getEventID() == event.getEventID() || timerInfo.getEventTitle().equalsIgnoreCase(event.getEventTitle())){
                    ScheduleExpression schedule = timer.getSchedule();
                    LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(schedule.getYear()), 
                                            Integer.parseInt(schedule.getMonth()), 
                                            Integer.parseInt(schedule.getDayOfMonth()),
                                            Integer.parseInt(schedule.getHour()),
                                            Integer.parseInt(schedule.getMinute()), 
                                            Integer.parseInt(schedule.getSecond()));
                    events.put(timerInfo, dateTime);   
                }
            }
        }
        return events;
    }

    public void editScheduledEvent(Map<EventJSON, LocalDateTime> scheduledEvents){
        scheduledEvents.entrySet().forEach(candidate -> {
            EventJSON event = candidate.getKey();
            if(event.isCanceled()){
                cancelScheduledEvent(event);
            }
            else{
                createScheduledEvent(event, candidate.getValue());
            }
            });
    }

    public Set<EventJSON> searchEvents(EventJSON event){
        Set<EventJSON> events = new HashSet<EventJSON>();

        if(event != null && event.getEventTitle() != null){
            Stream<Event> queryResults = entityManager.createNamedQuery("Event.searchEventsByTitle", Event.class)
                            .setParameter("title", "%" + event.getEventTitle() + "%").getResultStream();
            events.addAll(queryResults.map(eventEntity ->{
                    return getEventInfo(new EventJSON(eventEntity.getEvent_Id(),null, null,null,null,null,null,null,null,null,null,null,null));
                }).collect(Collectors.toSet()));
        }
        /* 
        if(event != null && event.getEventDescription() != null){
            Query query = entityManager.createNamedQuery("Event.searchEventsByEventDescription");
            query.setParameter("eventTitle", "%" + event.getEventTitle() + "%");
            Set<Event> queryResults = (Set<Event>)query.getResultList();
            events.addAll(queryResults.stream().map(eventEntity ->{
                    return getEventInfo(new EventJSON(eventEntity.getEvent_Id(),null, null,null,null,null,null,null,null,null,null,null));
                }).collect(Collectors.toSet()));    
        }
        */
        return events;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    //throw a custom exception to show object was not created
    //add logic to run as specified principals for changing some properties for events
    public void editEvent(EventJSON event){
        try{
            Event eventEntity = getEventEntity(event);
            LocalDateTime eventDate = LocalDateTime.ofInstant(eventEntity.getDate_Posted().toInstant(), ZoneId.systemDefault());
            if(event.getEventTitle() != null && !eventEntity.getEvent_Title().equalsIgnoreCase(event.getEventTitle()))
                eventEntity.setEvent_Title(event.getEventTitle()); 
            if(event.getEventPin() != null && !eventEntity.getEvent_Pin().equalsIgnoreCase(event.getEventPin()))
                eventEntity.setEvent_Pin(event.getEventPin());
            if(event.getEventDate() != null && !eventDate.equals(event.getEventDate())){
                eventEntity.setEvent_Date(Date.from(event.getEventDate().atZone(ZoneId.systemDefault()).toInstant()));
            }
            if(event.getEventDescription() != null && !eventEntity.getEvent_Description().equalsIgnoreCase(event.getEventDescription()))
                eventEntity.setEvent_Description(event.getEventDescription()); 
            if (event.getPosts() != null){
                for(EventPostJSON post : event.getPosts()){
                    postOps.editEventPost(post);
                }
            }
            /* 
            for(ImageJSON image : event.getPosters()){
                imageOps.editImage(image);
            }
            for(VideoJSON video : event.getClips()){
                videoOps.editVideo(video);
            }
            */

        }   
        catch(NoResultException | EJBException ex){
            createEvent(event);
        }
        removeEvent(event);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EventJSON getEventInfo(EventJSON event){
        Event eventEntity = getEventEntity(event);
        User postedByEntity = eventEntity.getPostedBy();
        UserJSON postedBy = new UserJSON(postedByEntity.getUsername());
        LocalDateTime eventDate = LocalDateTime.ofInstant(eventEntity.getEvent_Date().toInstant(), ZoneId.systemDefault());
        LocalDateTime datePosted = LocalDateTime.ofInstant(eventEntity.getDate_Posted().toInstant(), ZoneId.systemDefault());
        int eventId = eventEntity.getEvent_Id();
        String eventPin = eventEntity.getEvent_Pin();
        String eventTitle = eventEntity.getEvent_Title();
        String eventDesc = eventEntity.getEvent_Description();
        String eventVenue = eventEntity.getEvent_Venue();
        Set<ImageJSON> eventImages = new HashSet<ImageJSON>();
        eventImages = eventEntity.getImages().stream().map(imgInst  -> {
                    Image imageInfo = imgInst.getImage(); 
                    return new  ImageJSON(imgInst.getImg_Id(), 
                imageInfo.getImg_Path(), imageInfo.getImg_Title());
            }).collect(Collectors.toSet());
        Set<EventPostJSON> posts = new HashSet<EventPostJSON>();
        posts = eventEntity.getPosts().stream().map(postInst -> {
                return new EventPostJSON(postInst.getPost_Id(), null, null, 
                        0, 0, null, new EventJSON(postInst.getEvent().getEvent_Id(),
                             null, null, null,null,null,null, 
                                null, null,null, null,null, null));
            }).collect(Collectors.toSet());
        Set<VideoJSON> eventVideo = new HashSet<VideoJSON>();
        /* 
        eventVideo = eventEntity.getVideos().stream().map(vidInst -> {
                        Video vidInfo = vidInst.get
                        return new VideoJSON(vidInst.getVid_Id(), vidInst.getVid_Path(), vidInst.getVid_Title());
            }).collect(Collectors.toSet());
        */
        EventJSON eventJSON = new EventJSON(eventId, eventTitle, eventDesc, eventPin,eventDate,datePosted,eventVideo, eventImages,
                                        posts,null, null, eventVenue,postedBy); 
        return eventJSON;
    }   

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Event getEventEntity(EventJSON event) throws NoResultException{
        if(event == null)
            throw new NoResultException();
        /*if(event.getEventID() != 0){
            Event eventEntity = this.entityManager.find(Event.class, event.getEventID());
            if(eventEntity == null)
                throw new NoResultException();
            else
                return eventEntity;
        }
        else if(event.getEventTitle() !=  null){
            return this.entityManager.createNamedQuery("Event.getEventByTitle", Event.class)
                        .setParameter(1, event.getEventTitle()).getSingleResult();
        }
        else
            throw new NoResultException();
        */
        Event foundEvent = null;
        if(event.getEventID() != 0 ) {
            foundEvent = (Event)this.entityManager.find(Event.class, event.getEventID());
            if (foundEvent == null){
                throw new NoResultException();
            }
            return foundEvent;          
        }
        else if(event.getEventTitle() != null){   
            foundEvent = (Event) this.entityManager.createNamedQuery("Event.getEventByTitle", Event.class)
                                .setParameter(1,event.getEventTitle()).getSingleResult();
            return foundEvent; 
        }
        else throw new NoResultException();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //throw a custom exception to show object was not created
    public void createEvent(EventJSON event) {    
            User postedBy = userOperations.getUserEntity(event.getPostedBy());
            
            Event eventEntity = new Event();
            eventEntity.setPostedBy(postedBy);
            eventEntity.setEvent_Title(event.getEventTitle());
            eventEntity.setEvent_Description(event.getEventDescription());
            eventEntity.setEvent_Date(Date.from(event.getEventDate().atZone(ZoneId.systemDefault()).toInstant()));
            eventEntity.setEvent_Venue(event.getEventVenue());
            eventEntity.setDate_Posted(Date.from(Instant.now()));
            eventEntity.setEvent_Pin(event.getEventPin());
            entityManager.persist(eventEntity);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void removeEvent (EventJSON event){
        if(event.isCanceled()){
            try{
                Event eventEntity = getEventEntity(event);
                if(eventEntity!= null){
                    entityManager.remove(eventEntity);
                }
            }
            catch(NoResultException ex){
                return;
            }
        }
    }
    /*
    LocalDateTime.ofInstant(topicEntity.getCreated().toInstant(), ZoneId.systemDefault()) 
    private Set<EventImage> createEventImageEntities(EventJSON  event){
        Set<ImageJSON> eventImages = event.getPosters();
        Set<EventImage> eventImageEntities = new HashSet<EventImage>();

    }

    private EventImage createEventImageEntity(int eventId, int imageId){

    }



    public Set<EventJSON> getEventsByUser(UserJSON user){

    }

    public Set<EventJSON> getEventsBetweenDates(LocalDateTime startDateTime, LocalDateTime lastDateTime){
        return entityManager.createNamedQuery("EventJSON.findByDates", EventJSON.class)
                   .setParameter("startDateTime", startDateTime)
                   .setParameter("lastDateTime", lastDateTime)
                   .getResultList();
    }
    
    public EventJSON getEventInfo(EventJSON event){

    }
    
     public void getEventsByCategories(){
     }

     public void 
    */
}

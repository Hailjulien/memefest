package com.memefest.Services.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Future;

import org.bouncycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi.sha256WithSM2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.FeedsOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.NotificationJSON;
import com.memefest.Websockets.JSON.SearchJSON;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.MessageHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.EJB;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.ScheduleExpression;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.jms.Destination;
import jakarta.jms.JMSConsumer;
import jakarta.jms.Message;

/* 
@JMSDestinationDefinition(
    name = "java:comp/env/jms/FeedsPhysicalDest",
    interfaceName = "jakarta.jms.Topic",
    destinationName = "FeedsPhysicalDest"
)
@JMSConnectionFactoryDefinition(
    name = "java:module/jms/FeedsConFactory"
)
*/
@Singleton(name = "FeedsEndpointService")
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class FeedsEndPointService  implements FeedsOperations   {
    
    private volatile HashSet<Session> clientPeers = new HashSet<Session>();
    private volatile HashSet<Session> AdminPeers = new HashSet<Session>();



     /*
    private Destination destination;
    private JMSConsumer consumer;
   
    @Resource(name = "jms/FeedsPhysicalDest")
    private Topic topic;

    @Resource(name = "jms/FeedsConFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    @JMSConnectionFactory("java:module/jms/FeedsConFactory")
    private JMSContext context;
    */

    @EJB
    private TopicOperations topicOps;

    @PostActivate
    @PostConstruct
    public synchronized void init() {
        //consumer = context.createDurableConsumer(topic, "FeedsConsumer");
        //consumer.setMessageListener(this);
    }
    @PreDestroy
    @PrePassivate
    public synchronized void destroy() {

    }
    

/* 
    @Override
    public void onMessage(Message message){
        if(message instanceof ObjectMessage && message instanceof NotificationJSON){
            try {
                String username = message.getStringProperty("Receiver");
                this.peers.stream().filter( candidate -> {
                    return candidate.getUserPrincipal().getName().equalsIgnoreCase(username);
                }).forEach(result -> {
                    try {
                        NotificationJSON notification =(NotificationJSON) message.getBody(NotificationJSON.class);
                        sendToSession(result, notification);
                    } catch (JMSException ex) {
                    ex.printStackTrace();
                }    
                });
            } catch (JMSException ex) {
                ex.printStackTrace();
            } 
        }
        /* 
        else if(message instanceof ObjectMessage && message instanceof NotificationJSON){
            try {
                NotificationJSON notificationCommand = (NotificationJSON) message.getBody(NotificationJSON.class);
            } catch (JMSException ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }
        }
        
    }
    @Asynchronous
    public void send(Session session, Message message, Destination destination){

    }
    */


    @Asynchronous
    private void sendToSession(Session session, Object message){
        if(session.isOpen()) 
            session.getAsyncRemote().sendObject(message);
                    
    }

    @Asynchronous
    public void sendToAll(Object message){
        sendToAdmins(message);
        sendToUsers(message);
    }

    @Asynchronous
    public void sendToAdmins(Object message){
        for(Session session : this.AdminPeers){
            sendToSession(session, message);
        }
    }

    @Asynchronous
    public void sendToAdmin(Object message, String username){
        this.AdminPeers.stream().filter( candidate -> {
            return candidate.getUserPrincipal().getName().equalsIgnoreCase(username);
        }).forEach(result -> {
            sendToSession(result, message);
        });
    }

    @Asynchronous
    public void sendToUser(Object message, String username){
        this.clientPeers.stream().filter( candidate -> {
            return candidate.getUserPrincipal().getName().equalsIgnoreCase(username);
        }).forEach(result -> {
            sendToSession(result, message);
        });
    }

    @Asynchronous
    public void sendToUsers(Object message){
        for(Session session : this.clientPeers){
            sendToSession(session, message);
        }
    }


    public void addClientSession(Session sesion){
        this.clientPeers.add(sesion);
    }

    public void removeClientSession(Session sesion){
        this.clientPeers.remove(sesion);
    }

    public void addAdminSession(Session sesion){
        this.AdminPeers.add(sesion);
    }

    public void removeAdminSession(Session sesion){
        this.AdminPeers.remove(sesion);
    }



    /* 
    public void processCommand(){
        
    };

    @Asynchronous
    public Future<Boolean> followTopic(){

    }

    @Asynchronous
    public Future<Boolean> unfollowTopic(){

    }

    @Asynchronous
    public Future<Boolean> likePost(){

    }

    @Asynchronous
    public Future<Boolean> unlikePost(){

    }

    @Asynchronous
    public Future<Boolean> reportPost(){

    }

    @Asynchronous
    private Future<Boolean> updatePost(){

    }

    @Asynchronous
    private Future<Boolean> deletePost(){

    }

    @Asynchronous
    private Future<Boolean> searchPosts(){

    }

    @Asynchronous
    private Future<Boolean> createTopic(){

    }
    */
    //checks the json object passed in and checks it for commands to execute on the ejb
    //build encoders and decoders for json commands and responses from clientside
    public void processMessage(){
    
    }

    public synchronized void addPeer(Session session){
        clientPeers.add(session);
    }

    public synchronized void removePeer(Session session){
        clientPeers.remove(session);
    }
    
    public void notifyLogin(Object msg){
        if(msg instanceof PostJSON)
        clientPeers.forEach(peer -> peer.getAsyncRemote().sendObject(msg));
    }

}

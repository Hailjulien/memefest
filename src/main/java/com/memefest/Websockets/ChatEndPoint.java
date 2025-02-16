package com.memefest.Websockets;

import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
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
import jakarta.ejb.EJB;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSConnectionFactoryDefinition;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Topic;
import jakarta.websocket.CloseReason;
/* 
@JMSDestinationDefinition(
    name = "java:app/jms/TopicPhysicalDestination",
    interfaceName = "jakarta.jms.Topic",
    destinationName = "PhysicalAppTopic"
)
@JMSConnectionFactoryDefinition(
    name = "java:app/jms/PostDestinationFactory"
)
*/
@Stateful(name = "chatendpoint")
public class ChatEndPoint extends Endpoint implements MessageListener{
    
    private ArrayList<Session> peers = new ArrayList<Session>();
    private ObjectMapper mapper = new ObjectMapper();  
    private Destination destination;
    private JMSConsumer consumer;
    
    @Resource(lookup = "java:app/jms/TopicPhysicalDestination")
    private static Topic topic;

    @Resource(lookup = "jms/PostDestinationFactory")
    private static ConnectionFactory connectionFactory;

    @Inject
    @JMSConnectionFactory("jms/PostDestinationFactory")
    private JMSContext context;

    @EJB
    private TopicOperations topicOps;

    @PostActivate
    @PostConstruct
    public void init() {
        consumer = context.createDurableConsumer(topic, null);
        consumer.setMessageListener(this);
    }
    @PreDestroy
    @PrePassivate
    public void destroy() {

    }
    
    @Override
    public void onOpen(Session session, EndpointConfig config){
        //notifiacation about user joining room
        //timer to monitor time spent for each student in session
        //int topicId = Integer.valueOf(session.getPathParameters().get("topicId"));
        /*topic = new TopicJSON(topicId, null, null, null, null, null);
            topic = topicOps.getTopicInfo(topic);
        */
        peers.add(session);
        session.addMessageHandler(new MessageHandler.Whole<PostJSON>() {
            @Override
            public void onMessage(PostJSON message) {
                broadcast(session, message);
            }
        });

    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        peers.remove(session);
    }

    @Override
    public void onMessage(Message message){
        if(message instanceof ObjectMessage && message instanceof PostJSON){
            try {
                PostJSON postCommand =(PostJSON) message.getBody(PostJSON.class);
                
            } catch (JMSException ex) {
                ex.printStackTrace();
            }    
        }
        else if(message instanceof ObjectMessage && message instanceof NotificationJSON){
            try {
                NotificationJSON notificationCommand = (NotificationJSON) message.getBody(NotificationJSON.class);
            } catch (JMSException ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }
        }
    }

    public void send(){

    }

    public void broadcast(Session session, Object message){
        if(message instanceof PostJSON){
            try{
                String posted = mapper.writeValueAsString(message);
                peers.forEach(peer -> peer.getAsyncRemote().sendText(posted));
            }
            catch(JsonProcessingException exception){
                exception.printStackTrace();
            }
        }
    }

    public void processCommand(){
        
    };
    //checks the json object passed in and checks it for commands to execute on the ejb
    //build encoders and decoders for json commands and responses from clientside
    public void processMessage(){
    
    }
//create a notification message to show logged in user
    public void notifyLogin(Object msg){
        if(msg instanceof PostJSON)
        peers.forEach(peer -> peer.getAsyncRemote().sendObject(msg));
    }
}

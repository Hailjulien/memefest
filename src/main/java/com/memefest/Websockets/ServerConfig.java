package com.memefest.Websockets;

import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.Websockets.Decoders.*;
import com.memefest.Websockets.Encoders.AdminEncoder;
import com.memefest.Websockets.Encoders.CategoryEncoder;
import com.memefest.Websockets.Encoders.EditCategoryEncoder;
import com.memefest.Websockets.Encoders.EditEncoder;
import com.memefest.Websockets.Encoders.EditEventEncoder;
import com.memefest.Websockets.Encoders.EditEventNotificationEncoder;
import com.memefest.Websockets.Encoders.EditEventPostEncoder;
import com.memefest.Websockets.Encoders.EditEventPostNotificationEncoder;
import com.memefest.Websockets.Encoders.EditFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.EditPostEncoder;
import com.memefest.Websockets.Encoders.EditPostNotificationEncoder;
import com.memefest.Websockets.Encoders.EditPostWithReplyEncoder;
import com.memefest.Websockets.Encoders.EditRepostEncoder;
import com.memefest.Websockets.Encoders.EditResultCategoryEncoder;
import com.memefest.Websockets.Encoders.EditResultEventEncoder;
import com.memefest.Websockets.Encoders.EditResultEventNotificationEncoder;
import com.memefest.Websockets.Encoders.EditResultEventPostEncoder;
import com.memefest.Websockets.Encoders.EditResultEventPostNotificationEncoder;
import com.memefest.Websockets.Encoders.EditResultFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.EditResultPostEncoder;
import com.memefest.Websockets.Encoders.EditResultPostNotificationEncoder;
import com.memefest.Websockets.Encoders.EditResultPostWithReplyEncoder;
import com.memefest.Websockets.Encoders.EditResultRepostEncoder;
import com.memefest.Websockets.Encoders.EditResultScheduledEventEncoder;
import com.memefest.Websockets.Encoders.EditResultScheduledTopicEncoder;
import com.memefest.Websockets.Encoders.EditResultTopicEncoder;
import com.memefest.Websockets.Encoders.EditResultTopicFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.EditResultTopicPostEncoder;
import com.memefest.Websockets.Encoders.EditResultTopicPostNotificationEncoder;
import com.memefest.Websockets.Encoders.EditResultUserEncoder;
import com.memefest.Websockets.Encoders.EditResultUserFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.EditScheduledEventEncoder;
import com.memefest.Websockets.Encoders.EditScheduledTopicEncoder;
import com.memefest.Websockets.Encoders.EditTopicEncoder;
import com.memefest.Websockets.Encoders.EditTopicFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.EditTopicPostEncoder;
import com.memefest.Websockets.Encoders.EditTopicPostNotificationEncoder;
import com.memefest.Websockets.Encoders.EditUserEncoder;
import com.memefest.Websockets.Encoders.EditUserFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.EventEncoder;
import com.memefest.Websockets.Encoders.EventNotificationEncoder;
import com.memefest.Websockets.Encoders.EventPostNotificationEncoder;
import com.memefest.Websockets.Encoders.FollowNotificationEncoder;
import com.memefest.Websockets.Encoders.GetAdminEncoder;
import com.memefest.Websockets.Encoders.GetCategoryEncoder;
import com.memefest.Websockets.Encoders.GetEncoder;
import com.memefest.Websockets.Encoders.GetEventEncoder;
import com.memefest.Websockets.Encoders.GetEventNotificationEncoder;
import com.memefest.Websockets.Encoders.GetEventPostEncoder;
import com.memefest.Websockets.Encoders.GetEventPostNotificationEncoder;
import com.memefest.Websockets.Encoders.GetFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.GetNotificationEncoder;
import com.memefest.Websockets.Encoders.GetPostEncoder;
import com.memefest.Websockets.Encoders.GetPostNotificationEncoder;
import com.memefest.Websockets.Encoders.GetPostReplysEncoder;
import com.memefest.Websockets.Encoders.GetRepostEncoder;
import com.memefest.Websockets.Encoders.GetResultAdminEncoder;
import com.memefest.Websockets.Encoders.GetResultCategoryEncoder;
import com.memefest.Websockets.Encoders.GetResultEncoder;
import com.memefest.Websockets.Encoders.GetResultEventEncoder;
import com.memefest.Websockets.Encoders.GetResultEventNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultEventPostEncoder;
import com.memefest.Websockets.Encoders.GetResultEventPostNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultPostEncoder;
import com.memefest.Websockets.Encoders.GetResultPostNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultPostWithReplysEncoder;
import com.memefest.Websockets.Encoders.GetResultRepostEncoder;
import com.memefest.Websockets.Encoders.GetResultScheduledEventEncoder;
import com.memefest.Websockets.Encoders.GetResultScheduledTopicEncoder;
import com.memefest.Websockets.Encoders.GetResultTopicEncoder;
import com.memefest.Websockets.Encoders.GetResultTopicFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultTopicPostEncoder;
import com.memefest.Websockets.Encoders.GetResultTopicPostNotificationEncoder;
import com.memefest.Websockets.Encoders.GetResultUserFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.GetScheduledEventEncoder;
import com.memefest.Websockets.Encoders.GetScheduledTopicEncoder;
import com.memefest.Websockets.Encoders.GetTopicEncoder;
import com.memefest.Websockets.Encoders.GetTopicFollowNotificationEncoder;
import com.memefest.Websockets.Encoders.GetTopicPostEncoder;
import com.memefest.Websockets.Encoders.SearchCategoryEncoder;
import com.memefest.Websockets.Encoders.SearchEventEncoder;
import com.memefest.Websockets.Encoders.SearchPostEncoder;
import com.memefest.Websockets.Encoders.SearchResultCategoryEncoder;
import com.memefest.Websockets.Encoders.SearchResultEventEncoder;
import com.memefest.Websockets.Encoders.SearchResultPostEncoder;
import com.memefest.Websockets.Encoders.SearchResultTopicEncoder;
import com.memefest.Websockets.Encoders.SearchTopicEncoder;

import io.jsonwebtoken.lang.Collections;
import jakarta.websocket.Decoder;
import jakarta.websocket.Encoder;
import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerApplicationConfig;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerConfig implements ServerApplicationConfig {
  public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpoints) {
    Set<ServerEndpointConfig> configs = new HashSet<>();
    for (Class<? extends Endpoint> endpointClass : endpoints) {
      if (endpointClass.equals(FeedsEndpoint.class)) {
        Set<Class<? extends Encoder>> encoders = Collections.setOf(EditEventEncoder.class,        
                                                    EditResultEventEncoder.class, 
                                                    
                                                    SearchEventEncoder.class,
                                                    SearchResultEventEncoder.class,

                                                    GetEventEncoder.class,
                                                    GetResultEventEncoder.class,
                                                    



                                                    EditTopicEncoder.class,
                                                    EditResultTopicEncoder.class,

                                                    GetTopicEncoder.class,
                                                    GetResultTopicEncoder.class,
                                                    
                                                    SearchTopicEncoder.class,
                                                    SearchResultTopicEncoder.class,
        


                                                    
                                                    EditPostEncoder.class,
                                                    EditResultPostEncoder.class,

                                                    GetPostEncoder.class,
                                                    GetResultPostEncoder.class,

                                                    SearchPostEncoder.class,
                                                    SearchResultPostEncoder.class,                                                   
                                                    



                                                    EditPostWithReplyEncoder.class,
                                                    EditResultPostWithReplyEncoder.class,
                                                    
                                                    GetPostReplysEncoder.class,
                                                    GetResultPostWithReplysEncoder.class,
                                                    
                                              
                                                    EditEventPostEncoder.class,
                                                    EditResultEventPostEncoder.class,
                                                    
                                                    GetEventPostEncoder.class,
                                                    GetResultEventPostEncoder.class,                                                    
                                                    
                                                          
                                                    GetTopicPostEncoder.class,
                                                    GetResultTopicPostEncoder.class,
                                                    
                                                    EditTopicPostEncoder.class,
                                                    EditResultTopicPostEncoder.class,
                                                    

                                                    EditRepostEncoder.class,
                                                    EditResultRepostEncoder.class,

                                                    GetRepostEncoder.class,
                                                    GetResultRepostEncoder.class,
                                                    



                                                    EditCategoryEncoder.class,
                                                    EditResultCategoryEncoder.class,
                                                    
                                                    SearchCategoryEncoder.class,
                                                    SearchResultCategoryEncoder.class,

                                                    GetCategoryEncoder.class,
                                                    GetResultCategoryEncoder.class,


                                                    
                                                    
                                                    EditEventNotificationEncoder.class,
                                                    EditResultEventNotificationEncoder.class,
                                                    
                                                    GetEventNotificationEncoder.class,
                                                    GetResultEventNotificationEncoder.class,
                                                    



                                                    EditEventPostNotificationEncoder.class,
                                                    EditResultEventPostNotificationEncoder.class,

                                                    GetEventPostNotificationEncoder.class,
                                                    GetResultEventPostNotificationEncoder.class,


                                                    
                                                    EditFollowNotificationEncoder.class,
                                                    EditResultFollowNotificationEncoder.class,

                                                    GetFollowNotificationEncoder.class,
                                                    GetResultFollowNotificationEncoder.class,

                                                    


                                                    EditTopicFollowNotificationEncoder.class,
                                                    EditResultTopicFollowNotificationEncoder.class,
                                                    
                                                    GetTopicFollowNotificationEncoder.class,
                                                    GetResultTopicFollowNotificationEncoder.class,      




                                                    EditPostNotificationEncoder.class,
                                                    EditResultPostNotificationEncoder.class,
                                                    
                                                    GetPostNotificationEncoder.class,
                                                    GetResultPostNotificationEncoder.class,




                                                    EditResultTopicPostNotificationEncoder.class,
                                                    EditResultTopicPostNotificationEncoder.class,
                                                    
                                                    GetPostNotificationEncoder.class,
                                                    GetResultTopicPostNotificationEncoder.class);
        Set<Class<? extends Decoder>> decoders = Collections.setOf(
                                                    EditEventDecoder.class,
                                                    EditResultEventDecoder.class, 
                                                    
                                                    SearchEventDecoder.class,
                                                    SearchResultEventDecoder.class,

                                                    GetEventDecoder.class,
                                                    GetResultEventDecoder.class,
                                                    



                                                    EditTopicDecoder.class,
                                                    EditResultTopicDecoder.class,

                                                    GetTopicDecoder.class,
                                                    GetResultTopicDecoder.class,

                                                    SearchTopicDecoder.class,
                                                    SearchResultTopicDecoder.class,


                                                    

                                                    EditPostDecoder.class,
                                                    EditResultPostDecoder.class,

                                                    GetPostDecoder.class,
                                                    GetResultPostDecoder.class,
                                                    
                                                    SearchPostDecoder.class,
                                                    SearchResultPostDecoder.class,
                                                    
                            
                                                    EditPostWithReplyDecoder.class,
                                                    EditResultPostWithReplyDecoder.class,
                                                    
                                                    GetPostWithReplysDecoder.class,
                                                    GetResultPostWithReplysDecoder.class,
                                                    
                  
                                                    EditEventPostDecoder.class,
                                                    EditResultEventPostDecoder.class,
                                                    
                                                    GetEventPostDecoder.class,
                                                    GetResultEventPostDecoder.class,                                                    
                                                    
                                                          
                                                    GetTopicPostDecoder.class,
                                                    GetResultTopicPostDecoder.class,
                                                    
                                                    EditTopicPostDecoder.class,
                                                    EditResultTopicPostDecoder.class,
                                                    

                                                    EditRepostDecoder.class,
                                                    EditResultRepostDecoder.class,

                                                    GetRepostDecoder.class,
                                                    GetResultRepostDecoder.class,
                                                    



                                                    EditCategoryDecoder.class,
                                                    EditResultCategoryDecoder.class,
                                                    
                                                    SearchCategoryDecoder.class,
                                                    SearchResultCategoryDecoder.class,

                                                    GetCategoryDecoder.class,
                                                    GetResultCategoryDecoder.class,


                                                    
                                                    
                                                    EditEventNotificationDecoder.class,
                                                    EditResultEventNotificationDecoder.class,
                                                    
                                                    GetEventNotificationDecoder.class,
                                                    GetResultEventNotificationDecoder.class,
                                                    



                                                    EditEventPostNotificationDecoder.class,
                                                    EditResultEventPostNotificationDecoder.class,

                                                    GetEventPostNotificationDecoder.class,
                                                    GetResultEventPostNotificationDecoder.class,


                                                    
                                                    EditFollowNotificationDecoder.class,
                                                    EditResultFollowNotificationDecoder.class,

                                                    GetFollowNotificationDecoder.class,
                                                    GetResultFollowNotificationDecoder.class,

                                                    


                                                    EditTopicFollowNotificationDecoder.class,
                                                    EditResultTopicFollowNotificationDecoder.class,
                                                    
                                                    GetTopicFollowNotificationDecoder.class,
                                                    GetResultTopicFollowNotificationDecoder.class,      




                                                    EditPostNotificationDecoder.class,
                                                    EditResultPostNotificationDecoder.class,
                                                    
                                                    GetPostNotificationDecoder.class,
                                                    GetResultPostNotificationDecoder.class,




                                                    EditResultTopicPostDecoder.class,
                                                    EditResultTopicPostNotificationDecoder.class,
                                                    
                                                    GetPostNotificationDecoder.class,
                                                    GetResultTopicPostNotificationDecoder.class);

        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(endpointClass, "/feeds")
                  .encoders(encoders.stream().collect(Collectors.toList()))
                  .decoders(decoders.stream().collect(Collectors.toList())).build();
        configs.add(serverEndpointConfig);
      } 
    } 
    return configs;
  }
  
  public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> classes) {
    Set<Class<?>> configs = new HashSet<>();
    for (Class<?> endpointClass : classes) {
      if (endpointClass.isAnnotationPresent((Class)ServerEndpoint.class))
        configs.add(endpointClass); 
    } 
    return configs;
  }
}

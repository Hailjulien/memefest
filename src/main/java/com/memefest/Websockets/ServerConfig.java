package com.memefest.Websockets;

import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.Websockets.Decoders.AdminDecoder;
import com.memefest.Websockets.Decoders.CategoryDecoder;
import com.memefest.Websockets.Decoders.EditCategoryDecoder;
import com.memefest.Websockets.Decoders.EditDecoder;
import com.memefest.Websockets.Decoders.EditEventDecoder;
import com.memefest.Websockets.Decoders.EditEventNotificationDecoder;
import com.memefest.Websockets.Decoders.EditEventPostDecoder;
import com.memefest.Websockets.Decoders.EditEventPostNotificationDecoder;
import com.memefest.Websockets.Decoders.EditFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.EditPostDecoder;
import com.memefest.Websockets.Decoders.EditPostNotificationDecoder;
import com.memefest.Websockets.Decoders.EditPostWithReplyDecoder;
import com.memefest.Websockets.Decoders.EditRepostDecoder;
import com.memefest.Websockets.Decoders.EditResultCategoryDecoder;
import com.memefest.Websockets.Decoders.EditResultEventDecoder;
import com.memefest.Websockets.Decoders.EditResultEventNotificationDecoder;
import com.memefest.Websockets.Decoders.EditResultEventPostDecoder;
import com.memefest.Websockets.Decoders.EditResultEventPostNotificationDecoder;
import com.memefest.Websockets.Decoders.EditResultFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.EditResultPostDecoder;
import com.memefest.Websockets.Decoders.EditResultPostNotificationDecoder;
import com.memefest.Websockets.Decoders.EditResultPostWithReplyDecoder;
import com.memefest.Websockets.Decoders.EditResultRepostDecoder;
import com.memefest.Websockets.Decoders.EditResultScheduledEventDecoder;
import com.memefest.Websockets.Decoders.EditResultScheduledTopicDecoder;
import com.memefest.Websockets.Decoders.EditResultTopicDecoder;
import com.memefest.Websockets.Decoders.EditResultTopicFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.EditResultTopicPostDecoder;
import com.memefest.Websockets.Decoders.EditResultTopicPostNotificationDecoder;
import com.memefest.Websockets.Decoders.EditResultUserDecoder;
import com.memefest.Websockets.Decoders.EditResultUserFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.EditScheduledEventDecoder;
import com.memefest.Websockets.Decoders.EditScheduledTopicDecoder;
import com.memefest.Websockets.Decoders.EditTopicDecoder;
import com.memefest.Websockets.Decoders.EditTopicFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.EditTopicPostDecoder;
import com.memefest.Websockets.Decoders.EditTopicPostNotificationDecoder;
import com.memefest.Websockets.Decoders.EditUserDecoder;
import com.memefest.Websockets.Decoders.EditUserFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.EventDecoder;
import com.memefest.Websockets.Decoders.EventNotificationDecoder;
import com.memefest.Websockets.Decoders.EventPostNotificationDecoder;
import com.memefest.Websockets.Decoders.FollowNotificationDecoder;
import com.memefest.Websockets.Decoders.GetAdminDecoder;
import com.memefest.Websockets.Decoders.GetCategoryDecoder;
import com.memefest.Websockets.Decoders.GetDecoder;
import com.memefest.Websockets.Decoders.GetEventDecoder;
import com.memefest.Websockets.Decoders.GetEventNotificationDecoder;
import com.memefest.Websockets.Decoders.GetEventPostDecoder;
import com.memefest.Websockets.Decoders.GetEventPostNotificationDecoder;
import com.memefest.Websockets.Decoders.GetFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.GetNotificationDecoder;
import com.memefest.Websockets.Decoders.GetPostDecoder;
import com.memefest.Websockets.Decoders.GetPostNotificationDecoder;
import com.memefest.Websockets.Decoders.GetPostWithReplysDecoder;
import com.memefest.Websockets.Decoders.GetRepostDecoder;
import com.memefest.Websockets.Decoders.GetResultAdminDecoder;
import com.memefest.Websockets.Decoders.GetResultCategoryDecoder;
import com.memefest.Websockets.Decoders.GetResultDecoder;
import com.memefest.Websockets.Decoders.GetResultEventDecoder;
import com.memefest.Websockets.Decoders.GetResultEventNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultEventPostDecoder;
import com.memefest.Websockets.Decoders.GetResultEventPostNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultPostDecoder;
import com.memefest.Websockets.Decoders.GetResultPostNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultPostWithReplysDecoder;
import com.memefest.Websockets.Decoders.GetResultRepostDecoder;
import com.memefest.Websockets.Decoders.GetResultScheduledEventDecoder;
import com.memefest.Websockets.Decoders.GetResultScheduledTopicDecoder;
import com.memefest.Websockets.Decoders.GetResultTopicDecoder;
import com.memefest.Websockets.Decoders.GetResultTopicFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultTopicPostDecoder;
import com.memefest.Websockets.Decoders.GetResultTopicPostNotificationDecoder;
import com.memefest.Websockets.Decoders.GetResultUserDecoder;
import com.memefest.Websockets.Decoders.GetResultUserFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.GetScheduledEventDecoder;
import com.memefest.Websockets.Decoders.GetScheduledTopicDecoder;
import com.memefest.Websockets.Decoders.GetTopicDecoder;
import com.memefest.Websockets.Decoders.GetTopicFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.GetTopicPostDecoder;
import com.memefest.Websockets.Decoders.GetTopicPostNotificationDecoder;
import com.memefest.Websockets.Decoders.GetUserDecoder;
import com.memefest.Websockets.Decoders.GetUserFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.NotificationDecoder;
import com.memefest.Websockets.Decoders.PostDecoder;
import com.memefest.Websockets.Decoders.PostNotificationDecoder;
import com.memefest.Websockets.Decoders.PostWithReplyDecoder;
import com.memefest.Websockets.Decoders.SearchCategoryDecoder;
import com.memefest.Websockets.Decoders.SearchEventDecoder;
import com.memefest.Websockets.Decoders.SearchResultCategoryDecoder;
import com.memefest.Websockets.Decoders.SearchResultEventDecoder;
import com.memefest.Websockets.Decoders.TopicDecoder;
import com.memefest.Websockets.Decoders.TopicFollowNotificationDecoder;
import com.memefest.Websockets.Decoders.UserDecoder;
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
import com.memefest.Websockets.Encoders.SearchResultCategoryEncoder;
import com.memefest.Websockets.Encoders.SearchResultEventEncoder;
import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerApplicationConfig;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ServerConfig implements ServerApplicationConfig {
  public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpoints) {
    Set<ServerEndpointConfig> configs = new HashSet<>();
    for (Class<? extends Endpoint> endpointClass : endpoints) {
      if (endpointClass.equals(FeedsEndpoint.class)) {
        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(endpointClass, "/feeds")
                  .encoders(Arrays.asList(          
                                                    EditEventEncoder.class,
                                                    EditResultEventEncoder.class, 
                                                    
                                                    SearchEventEncoder.class,
                                                    SearchResultEventEncoder.class,

                                                    GetEventEncoder.class,
                                                    GetResultEventEncoder.class,
                                                    



                                                    EditTopicEncoder.class,
                                                    EditResultTopicEncoder.class,

                                                    GetTopicEncoder.class,
                                                    GetResultTopicEncoder.class,




                                                    
                                                    EditPostEncoder.class,
                                                    EditResultPostEncoder.class,

                                                    GetPostEncoder.class,
                                                    GetResultPostEncoder.class,
                                                    



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
                                                    GetResultTopicPostNotificationEncoder.class
                                          )
                                          

                                          )
                  .decoders(Arrays.asList(

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




                                                    
                                                    EditPostDecoder.class,
                                                    EditResultPostDecoder.class,

                                                    GetPostDecoder.class,
                                                    GetResultPostDecoder.class,
                                                    



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
                                                    GetResultTopicPostNotificationDecoder.class
                   )).build();
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

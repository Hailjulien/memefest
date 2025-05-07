package com.memefest.Websockets;

import com.memefest.Websockets.Decoders.AdminDecoder;
import com.memefest.Websockets.Decoders.CategoryDecoder;
import com.memefest.Websockets.Decoders.EditDecoder;
import com.memefest.Websockets.Decoders.EditEventDecoder;
import com.memefest.Websockets.Decoders.EditPostDecoder;
import com.memefest.Websockets.Decoders.EditTopicDecoder;
import com.memefest.Websockets.Decoders.GetEventDecoder;
import com.memefest.Websockets.Decoders.GetEventPostDecoder;
import com.memefest.Websockets.Decoders.GetPostDecoder;
import com.memefest.Websockets.Decoders.GetTopicDecoder;
import com.memefest.Websockets.Decoders.GetTopicPostDecoder;
import com.memefest.Websockets.Decoders.InteractionNotificationDecoder;
import com.memefest.Websockets.Decoders.NotificationDecoder;
import com.memefest.Websockets.Decoders.PostDecoder;
import com.memefest.Websockets.Decoders.PostNotificationDecoder;
import com.memefest.Websockets.Encoders.AdminEncoder;
import com.memefest.Websockets.Encoders.CategoryEncoder;
import com.memefest.Websockets.Encoders.EditEncoder;
import com.memefest.Websockets.Encoders.EditEventEncoder;
import com.memefest.Websockets.Encoders.EditPostEncoder;
import com.memefest.Websockets.Encoders.EditTopicEncoder;
import com.memefest.Websockets.Encoders.GetEventEncoder;
import com.memefest.Websockets.Encoders.GetEventPostEncoder;
import com.memefest.Websockets.Encoders.GetPostEncoder;
import com.memefest.Websockets.Encoders.GetTopicEncoder;
import com.memefest.Websockets.Encoders.GetTopicPostEncoder;
import com.memefest.Websockets.Encoders.InteractNotificationEncoder;
import com.memefest.Websockets.Encoders.NotificationEncoder;
import com.memefest.Websockets.Encoders.PostEncoder;
import com.memefest.Websockets.Encoders.PostNotificationEncoder;

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
                  .encoders(Arrays.asList(PostEncoder.class,
                                          PostNotificationEncoder.class,
                                          AdminEncoder.class,
                                          CategoryEncoder.class,
                                          EditPostEncoder.class,
                                          EditTopicEncoder.class,
                                          GetTopicEncoder.class,
                                          EditEventEncoder.class,
                                          GetEventEncoder.class,
                                          GetEventPostEncoder.class,
                                          EditEncoder.class,
                                          GetEventEncoder.class,
                                          GetPostEncoder.class,
                                          GetTopicPostEncoder.class,
                                          GetTopicEncoder.class,
                                          GetEventPostEncoder.class))
                  .decoders(Arrays.asList(PostDecoder.class,
                                              PostNotificationDecoder.class,
                                              GetEventPostDecoder.class,
                                              AdminDecoder.class,
                                              EditTopicDecoder.class,
                                              GetTopicDecoder.class,
                                              GetEventDecoder.class,
                                              EditEventDecoder.class,
                                              CategoryDecoder.class,
                                              EditPostDecoder.class,
                                              EditDecoder.class,
                                              GetEventDecoder.class,
                                              GetPostDecoder.class,
                                              GetTopicPostDecoder.class,
                                              GetTopicDecoder.class,
                                              GetEventPostDecoder.class
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

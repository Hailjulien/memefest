package com.memefest.Websockets;

import com.memefest.Websockets.Decoders.InteractionNotificationDecoder;
import com.memefest.Websockets.Decoders.NotificationDecoder;
import com.memefest.Websockets.Decoders.PostDecoder;
import com.memefest.Websockets.Encoders.InteractNotificationEncoder;
import com.memefest.Websockets.Encoders.NotificationEncoder;
import com.memefest.Websockets.Encoders.PostEncoder;
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
                  .encoders(Arrays.asList(PostEncoder.class /* ,InteractNotificationEncoder.class*/))
                  .decoders(Arrays.asList(PostDecoder.class /*,InteractionNotificationDecoder.class*/)).build();
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

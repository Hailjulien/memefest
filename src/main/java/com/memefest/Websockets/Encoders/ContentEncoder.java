package com.memefest.Websockets.Encoders;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public abstract class ContentEncoder {
     protected ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_DEFAULT);

    protected SimpleBeanPropertyFilter userPublicViewFilter(){
      SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.serializeAllExcept(
        "posts", "Posts",
            "contacts","Contacts",
            "firstName","FirstName",
                "lastName","LastName",
                    "email", "Email",
                        "userSecurity","UserSecurity", 
                            "topicsFollowing", "TopicsFollowing", 
                                "categoriesFollowing", "CategoriesFollowing");
        return userFilter;       
    }

    protected FilterProvider setFilters(Map<String,SimpleBeanPropertyFilter> filters){
        SimpleFilterProvider provider = new SimpleFilterProvider();
        for (Entry<String,SimpleBeanPropertyFilter> iterable: filters.entrySet()) {
            provider.addFilter(iterable.getKey(), iterable.getValue());
        }
        return provider;
    }

    protected SimpleBeanPropertyFilter eventPublicView(){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(
            "posts", "Posts",
                "clips","Clips",
                    "posters","Posters",
                        "postedBy");
    return filter;
  }

  protected SimpleBeanPropertyFilter topicPublicView(){
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(
        "Posts", "posts", "FollowedBy", "followedBy"
    );
    return filter;
  }

  protected SimpleBeanPropertyFilter categoryPublicView(){
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(
        "Topics", "topics",
            "followedBy", "FollowedBy"
    );
    return filter;
  }

}

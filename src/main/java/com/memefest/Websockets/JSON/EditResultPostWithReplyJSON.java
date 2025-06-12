package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;

@JsonRootName("EditResultPostWithReplies")
public class EditResultPostWithReplyJSON extends EditResultJSON {
    
    @JsonProperty("PostWithReplies")
    private Set<PostWithReplyJSON> postsWithReplies; 

    @JsonCreator
    public EditResultPostWithReplyJSON(@JsonProperty("ResultCode")int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("PostWithReplies") Set<PostWithReplyJSON> postsWithReplies){
        super(Editable.POST, resultCode, resultMessage);
        this.postsWithReplies = postsWithReplies;
    }

    public Set<PostWithReplyJSON> getPostWithReplies(){
        return this.postsWithReplies;
    }

    public void setPostWithReplies(Set<PostWithReplyJSON> postsWithReplys){
        this.postsWithReplies = postsWithReplys;
    }
}

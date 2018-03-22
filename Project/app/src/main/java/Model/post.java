package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class post {
    private List<comment> commentList;
    private List<user> followerList;
    private String image = "", postContent = "", postTime = "";

    public post() {
        commentList = new ArrayList<>();
        followerList = new ArrayList<>();
    }

    public post(List<comment> commentList, List<user> followerList, String image, String postContent, String postTime) {
        this.commentList = commentList;
        this.followerList = followerList;
        this.image = image;
        this.postContent = postContent;
        this.postTime = postTime;
    }

    public List<comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<comment> commentList) {
        this.commentList = commentList;
    }

    public List<user> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<user> followerList) {
        this.followerList = followerList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}

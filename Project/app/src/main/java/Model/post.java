package Model;

import java.util.List;

public class post {
    private List<String> comments;
    private String postContent = "", postTime = "";
    private Integer postImage = 0;

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
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

    public Integer getPostImage() {
        return postImage;
    }

    public void setPostImage(Integer postImage) {
        this.postImage = postImage;
    }

    public post(List<String> comments, String postContent, String postTime, Integer postImage) {

        this.comments = comments;
        this.postContent = postContent;
        this.postTime = postTime;
        this.postImage = postImage;
    }
}

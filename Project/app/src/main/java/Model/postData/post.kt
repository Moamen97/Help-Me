package Model.postData

import java.lang.reflect.Type

class post {
    var comments = arrayListOf<String>()
    var postContent: String = ""
    var postImage: String = ""
    var postTime: String = ""
    var postID: String = ""
    var postType: String = ""
    var postOwnerImage: String = ""
    var postOwnerUserName: String = ""
    var color: Int = -1
    var postRate=0
    constructor()

    constructor(postContent: String, postImage: String, postTime: String, postType: String, comments: ArrayList<String>, postOwnerImage: String = "", postOwnerUserName: String = "", color: Int = -1,postRate:Int =0) {
        this.comments = comments
        this.postContent = postContent
        this.postImage = postImage
        this.postTime = postTime
        this.postType = postType
        this.color = color
        this.postOwnerImage = postOwnerImage
        this.postOwnerUserName = postOwnerUserName
        this.postRate=postRate
    }

    constructor(Post: post) {
        this.comments = Post.comments
        this.postContent = Post.postContent
        this.postImage = Post.postImage
        this.postTime = Post.postTime
        this.postType = Post.postType
        this.postRate=postRate
    }
    fun edittype(type:String)
    {
        postType = type;
    }
    fun editID(ID:String)
    {
        this.postID = ID
    }


}
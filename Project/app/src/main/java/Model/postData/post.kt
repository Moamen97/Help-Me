package Model.postData

import java.lang.reflect.Type

class post {
    var comments = arrayListOf<String>()
    var OwnerFName: String = ""
    var postID: String = ""
    var postType: String = ""
    var postOwnerImage: String = ""
    var postOwnerUserName: String = ""
    var color: Int = -1
    var postRate=0
    constructor()

    constructor(OwnerFName:String , postType: String, comments: ArrayList<String>, postOwnerImage: String = "", postOwnerUserName: String = "", color: Int = -1,postRate:Int =0) {
        this.comments = comments
        this.postType = postType
        this.color = color
        this.postOwnerImage = postOwnerImage
        this.postOwnerUserName = postOwnerUserName
        this.OwnerFName = OwnerFName
        this.postRate=postRate
    }

    constructor(Post: post) {
        this.comments = Post.comments
        this.postType = Post.postType
        this.color = Post.color
        this.postOwnerImage = Post.postOwnerImage
        this.postOwnerUserName = Post.postOwnerUserName
        this.OwnerFName = Post.OwnerFName
        this.postRate=Post.postRate
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
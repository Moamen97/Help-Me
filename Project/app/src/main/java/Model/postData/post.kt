package Model.postData

class post {
    var comments = arrayListOf<String>()
    var postContent: String = ""
    var postImage: String = ""
    var postTime: String = ""
    var postType: String = ""
    var postOwnerImage: String = ""
    var postOwnerUserName: String = ""
    var color: Int = -1
    var postId: String = ""

    constructor()

    constructor(postContent: String, postImage: String, postTime: String, postType: String, comments: ArrayList<String>, postOwnerImage: String = "", postOwnerUserName: String = "", color: Int = -1, postId: String = "") {
        this.comments = comments
        this.postContent = postContent
        this.postImage = postImage
        this.postTime = postTime
        this.postType = postType
        this.color = color
        this.postOwnerImage = postOwnerImage
        this.postOwnerUserName = postOwnerUserName
        this.postId = postId

    }

    constructor(Post: post) {
        this.comments = Post.comments
        this.postContent = Post.postContent
        this.postImage = Post.postImage
        this.postTime = Post.postTime
        this.postType = Post.postType
        this.color = Post.color
        this.postOwnerImage = Post.postOwnerImage
        this.postOwnerUserName = Post.postOwnerUserName
        this.postId = Post.postId
    }


}
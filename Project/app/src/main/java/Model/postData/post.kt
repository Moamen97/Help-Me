package Model.postData

class post {
    var comments = arrayListOf<String>()
    var postContent: String = ""
    var postImage: String = ""
    var postTime: String = ""
    var postType: String = ""

    constructor()

    constructor(postContent: String, postImage: String, postTime: String, postType: String, comments: ArrayList<String>) {
        this.comments = comments
        this.postContent = postContent
        this.postImage = postImage
        this.postTime = postTime
        this.postType = postType
    }

    constructor(Post: post) {
        this.comments = Post.comments
        this.postContent = Post.postContent
        this.postImage = Post.postImage
        this.postTime = Post.postTime
        this.postType = Post.postType
    }


}
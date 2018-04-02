package Control
/**
 * Created by Mohamed Aaziz on 02/04/2018.
 */
import FireBase.fireStore
import Model.postData.post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.ArrayList
import java.util.HashMap


class PostControl private constructor() {
    private var dataBaseInstance = fireStore.fireStoreHandler

    companion object {
        private var instance: PostControl? = null
        fun getInstance(x:Int = 0/*Dummy*/): PostControl {
            if (instance == null)
                instance = PostControl()
            return instance!!
        }
    } //Singleton



    fun addPost(NewPost: post) {
        dataBaseInstance.collection("posts_user_map")
                .whereEqualTo("postContent", NewPost.postContent).
                whereEqualTo("postTime",NewPost.postTime)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                                var PostData = HashMap<String, Any>();
                                PostData.put("postContent", NewPost.postContent);
                                PostData.put("comments", NewPost.comments);
                                PostData.put("postImage", NewPost.postImage);
                                PostData.put("postTime", NewPost.postTime);
                                PostData.put("postType", NewPost.postType);
                                dataBaseInstance.collection("posts_user_map")
                                        .document(NewPost.postContent).set(PostData);
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }
    fun getPostsByType(PostType:String): ArrayList<post> {
        val posts = arrayListOf<post>()
        FirebaseFirestore.getInstance().collection("posts_user_map").
                whereEqualTo("postType", PostType)
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(p0: Task<QuerySnapshot>) {
                if (p0.isSuccessful) {
                    if (!p0.result.isEmpty)
                    for (document: QueryDocumentSnapshot in p0.result) {
                        println(document.id + " => " + document.data);
                        posts.add(document.toObject(post::class.java))
                    }
                } else {
                    println(p0.exception.toString())
                }
            }
        })
        return posts
    }
    fun editPost(Post:post , NewPost: post) {
        dataBaseInstance.collection("posts_user_map")
                .whereEqualTo("postContent", Post.postContent).
                whereEqualTo("postTime",Post.postTime)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            var PostData = HashMap<String, Any>();
                            PostData.put("postContent", NewPost.postContent);
                            PostData.put("comments", NewPost.comments);
                            PostData.put("postImage", NewPost.postImage);
                            PostData.put("postTime", NewPost.postTime);
                            PostData.put("postType", NewPost.postType);
                            dataBaseInstance.collection("posts_user_map")
                                    .document(NewPost.postContent).update(PostData);
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }
    fun SerchPostsByKeyWord(PostType: String,KeyWord:String): ArrayList<post> {
        val posts = arrayListOf<post>()
        FirebaseFirestore.getInstance().collection("posts_user_map").
                whereEqualTo("postType",PostType)
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(p0: Task<QuerySnapshot>) {
                if (p0.isSuccessful) {
                    if (!p0.result.isEmpty)
                        for (document: QueryDocumentSnapshot in p0.result) {
                            if (document.toObject(post::class.java).postContent.contains(KeyWord)) {
                                println(document.id + " => " + document.data);
                                posts.add(document.toObject(post::class.java))
                            }
                        }
                } else {
                    println(p0.exception.toString())
                }
            }
        })
        return posts
    }


}
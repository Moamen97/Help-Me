package Control

import Common.mySelf
import Utility.Utility
import Model.postData.post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.ArrayList
import java.util.HashMap


class PostControl  {
    private var dataBaseInstance = Utility.fireStoreHandler
    var Posttypemap = HashMap<String, ArrayList<post>>();
    var entered = 0
    private constructor()
    {
        Posttypemap.put("Doctor", ArrayList())
        getPostsByType("Doctor")
        Posttypemap.put("Engineer", ArrayList())
        getPostsByType("Engineer")
        Posttypemap.put("Cooking", ArrayList())
        getPostsByType("Cooking")
        Posttypemap.put("Carpenter", ArrayList())
        getPostsByType("Carpenter")
        Posttypemap.put("Mechanic", ArrayList())
        getPostsByType("Mechanic")
        Posttypemap.put("Plumber", ArrayList())
        getPostsByType("Plumber")
        Posttypemap.put("MyPosts", ArrayList())
        getMyPosts()
    }
    companion object {
        private var instance: PostControl? = null
        fun getInstance(x: Int = 0/*Dummy*/): PostControl {
            if (instance == null)
                instance = PostControl()

            return instance!!
        }
    }//Singleton

    fun getlist(posttype: String):ArrayList<post>
    {
        return Posttypemap.get(posttype)!!
    }
        fun addPost(NewPost: post) {
            var PostData = HashMap<String, Any>();
            PostData.put("color", "1");
            PostData.put("comments", NewPost.comments);
            PostData.put("postContent", NewPost.postContent);
            PostData.put("postImage", NewPost.postImage);
            PostData.put("postOwnerImage", NewPost.postOwnerImage);
            PostData.put("postOwnerUserName", NewPost.postOwnerUserName);
            PostData.put("postTime", NewPost.postTime);
            PostData.put("postType", NewPost.postType);
            PostData.put("postRate",NewPost.postRate);
            dataBaseInstance.collection("post").add(PostData);
        }
        fun getPostsByType(PostType:String) {
            FirebaseFirestore.getInstance().collection("post").
                    whereEqualTo("postType", PostType)
                    .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    if (p0.isSuccessful) {
                        Posttypemap.get(PostType)!!.clear();

                       for (document: QueryDocumentSnapshot in p0.result) {
                            println(document.id + " => " + document.data);
                            try {
                                var col = document.get("color").toString().toInt();
                                var comm = document.get("comments");
                                var con = document.get("postContent").toString();
                                var pimg = document.get("postImage").toString();
                                var oimg = document.get("postOwnerImage").toString();
                                var ponme = document.get("postOwnerUserName").toString();
                                var ptime = document.get("postTime").toString();
                                var ptype = document.get("postType").toString();
                                var pRate = document.get("postRate").toString().toInt();
                                var temp = post(con, pimg, ptime, ptype, ArrayList(), oimg, ponme, col,pRate)
                                temp.editID(document.id)
                                Posttypemap.get(PostType)!!.add(temp)

                            }catch (e:Exception)
                            {}
                        }

                    }
                }
            })
        }
    fun getMyPosts() {
        FirebaseFirestore.getInstance().collection("post").
                whereEqualTo("postOwnerUserName",mySelf.get_userName())
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(p0: Task<QuerySnapshot>) {
                if (p0.isSuccessful) {
                    Posttypemap.get("MyPosts")!!.clear();

                    for (document: QueryDocumentSnapshot in p0.result) {
                        println(document.id + " => " + document.data);
                        try {
                            var col = document.get("color").toString().toInt();
                            var comm = document.get("comments");
                            var con = document.get("postContent").toString();
                            var pimg = document.get("postImage").toString();
                            var oimg = document.get("postOwnerImage").toString();
                            var ponme = document.get("postOwnerUserName").toString();
                            var ptime = document.get("postTime").toString();
                            var ptype = document.get("postType").toString();
                            var temp = post(con, pimg, ptime, ptype, ArrayList(), oimg, ponme, col)
                            temp.editID(document.id)
                            Posttypemap.get("MyPosts")!!.add(temp)

                        }catch (e:Exception)
                        {}
                    }

                }
            }
        })
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
                                PostData.put("postRate",NewPost.postRate)
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

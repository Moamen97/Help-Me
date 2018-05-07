package Control

import Common.mySelf
import Utility.Utility
import Model.postData.post
import Model.user
import android.graphics.Bitmap
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.ArrayList
import java.util.HashMap


class PostControl {
    private var dataBaseInstance = Utility.fireStoreHandler
    var Posttypemap = HashMap<String, ArrayList<post>>();
    var Users = HashMap<String, user>();

    var entered = 0

    private constructor() {
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
        Posttypemap.put("Location", ArrayList())
        Posttypemap.put("WS_Name", ArrayList())
        Posttypemap.put("Owner_Name", ArrayList())
    }

    companion object {
        private var instance: PostControl? = null
        fun getInstance(x: Int = 0/*Dummy*/): PostControl {
            if (instance == null)
                instance = PostControl()

            return instance!!
        }
    }//Singleton

    fun getlist(posttype: String): ArrayList<post> {
        return Posttypemap.get(posttype)!!
    }

    fun addPost(NewPost: post) {
        dataBaseInstance.collection("post")
                .whereEqualTo("ID", NewPost.postID)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (p0.result.isEmpty) {
                                // getting empty documents ; here we should push our ws to db :V
                                println("No Document Data");
                                var PostData = HashMap<String, Any>();
                                PostData.put("color", "1");
                                PostData.put("comments", NewPost.comments);
                                PostData.put("postRate", NewPost.postRate);
                                PostData.put("postOwnerImage", "");
                                PostData.put("postOwnerUserName", NewPost.postOwnerUserName);
                                PostData.put("postOwnerFName", NewPost.OwnerFName);
                                PostData.put("postType", NewPost.postType);
                                PostData.put("postLocation", NewPost.postlocation);
                                PostData.put("postName", NewPost.postname);
                                PostData.put("postID", NewPost.postID);
                                var task = dataBaseInstance.collection("post").document(NewPost.postID)
                                        .set(PostData)
                            } else {
                                // here i found someone has the same userName :V so i can't add this user
                                println(p0.result.documents[0].data.toString());
                            }
                        }
                    }
                })
    }


    fun getPostsByType(PostType: String) {
        FirebaseFirestore.getInstance().collection("post").whereEqualTo("postType", PostType)
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            Posttypemap.get(PostType)!!.clear();

                            for (document: QueryDocumentSnapshot in p0.result) {
                                println(document.id + " => " + document.data);
                                try {
                                    var col = document.get("color").toString().toInt();
                                    var comm = document.get("comments");
                                    var orate = document.get("postRate").toString().toInt();
                                    var pofnme = document.get("postOwnerFName").toString();
                                    var pounme = document.get("postOwnerUserName").toString();
                                    var ptype = document.get("postType").toString();
                                    var ploc = document.get("postLocation").toString();
                                    var pnme = document.get("postName").toString();
                                    var pid = document.get("postID").toString();
                                    var User = user()
                                    User.CheckSet_userName(pounme)
                                    try {
                                        User.downloadProfileImage()
                                        var oimg =  User.get_ProfileImage()!!.imageData
                                        var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                        Users.put(temp.postID,User)
                                        Posttypemap.get(PostType)!!.add(temp)
                                    }catch (e:Exception){var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                        Posttypemap.get(PostType)!!.add(temp)
                                        Users.put(temp.postID,User)
                                    }

                                } catch (e: Exception) {
                                }
                            }


                        }
                    }
                })
    }

    fun getMyPosts() {
        FirebaseFirestore.getInstance().collection("post").whereEqualTo("postOwnerUserName", mySelf.get_userName())
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            Posttypemap.get("MyPosts")!!.clear();

                            for (document: QueryDocumentSnapshot in p0.result) {
                                println(document.id + " => " + document.data);
                                try {
                                    var col = document.get("color").toString().toInt();
                                    var comm = document.get("comments");
                                    var orate = document.get("postRate").toString().toInt();
                                    var pofnme = document.get("postOwnerFName").toString();
                                    var pounme = document.get("postOwnerUserName").toString();
                                    var ptype = document.get("postType").toString();
                                    var ploc = document.get("postLocation").toString();
                                    var pnme = document.get("postName").toString();
                                    var pid = document.get("postID").toString();
                                    var User = user()
                                    User.CheckSet_userName(pounme)
                                    try {
                                        var oimg =  User.get_ProfileImage()!!.imageData

                                        var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                        Users.put(temp.postID,User)
                                        Posttypemap.get("MyPosts")!!.add(temp)
                                    }catch (e:Exception){var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                        Users.put(temp.postID,User)
                                        Posttypemap.get("MyPosts")!!.add(temp)}


                                } catch (e: Exception) {
                                }
                            }

                        }
                    }
                })

    }
/*
    fun getPostOfWS() {
        var WorkShopController = WorkShopControl.getInstance(null)
        FirebaseFirestore.getInstance().collection("post").whereEqualTo("postID", WorkShopController.WorkShop!!.workshopid)
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            try {
                                Posttypemap.get(WorkShopController.WorkShop!!.workshopid)!!.clear();
                            } catch (e: Exception) {
                                Posttypemap.put(WorkShopController.WorkShop!!.workshopid, ArrayList())
                            }
                            for (document: QueryDocumentSnapshot in p0.result) {
                                println(document.id + " => " + document.data);
                                try {
                                    var col = document.get("color").toString().toInt();
                                    var comm = document.get("comments");
                                    var orate = document.get("postRate").toString().toInt();
                                    var pofnme = document.get("postOwnerFName").toString();
                                    var oimg = document.get("postOwnerImage").toString()
                                    var pounme = document.get("postOwnerUserName").toString();
                                    var ptype = document.get("postType").toString();
                                    var ploc = document.get("postLocation").toString();
                                    var pnme = document.get("postName").toString();
                                    var pid = document.get("postID").toString();
                                    var temp = post(pofnme, ptype, ArrayList(), oimg, pounme, col, orate, ploc, pnme, pid)
                                    Posttypemap.get(WorkShopController.WorkShop!!.workshopid)!!.add(temp)

                                } catch (e: Exception) {
                                }
                            }

                        }
                    }
                })
    }*/
    fun getPostBylocation(Location:String) {
        FirebaseFirestore.getInstance().collection("post")
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(p0: Task<QuerySnapshot>) {
                if (p0.isSuccessful) {
                    try {
                        Posttypemap.get("Location")!!.clear();
                    }catch (e:Exception)
                    {
                        Posttypemap.put("Location", ArrayList())
                    }
                    for (document: QueryDocumentSnapshot in p0.result) {
                        println(document.id + " => " + document.data);
                        try {
                            var col = document.get("color").toString().toInt();
                            var comm = document.get("comments");
                            var orate = document.get("postRate").toString().toInt();
                            var pofnme = document.get("postOwnerFName").toString();
                            var oimg = document.get("postOwnerImage").toString()
                            var pounme = document.get("postOwnerUserName").toString();
                            var ptype = document.get("postType").toString();
                            var ploc = document.get("postLocation").toString();
                            var pnme = document.get("postName").toString();
                            var pid = document.get("postID").toString();
                            if (ploc.contains(Location)||Location.contains(ploc))
                            {
                                var User = user()
                                User.CheckSet_userName(pounme)
                                try {
                                    var oimg =  User.get_ProfileImage()!!.imageData
                                    var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                    Users.put(temp.postID,User)
                                    Posttypemap.get("Location")!!.add(temp)
                                }catch (e:Exception){var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                    Users.put(temp.postID,User)
                                    Posttypemap.get("Location")!!.add(temp)}
                            }
                        } catch (e: Exception) {
                        }
                    }

                }
            }
        })
    }
    fun getPostByOwnerName(Owner_Name:String)  {
        FirebaseFirestore.getInstance().collection("post")
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(p0: Task<QuerySnapshot>) {
                if (p0.isSuccessful) {
                    try {
                        Posttypemap.get("Owner_Name")!!.clear();
                    }catch (e:Exception)
                    {
                        Posttypemap.put("Owner_Name", ArrayList())
                    }
                    for (document: QueryDocumentSnapshot in p0.result) {
                        println(document.id + " => " + document.data);
                        try {
                            var col = document.get("color").toString().toInt();
                            var comm = document.get("comments");
                            var orate = document.get("postRate").toString().toInt();
                            var pofnme = document.get("postOwnerFName").toString();
                            var oimg = document.get("postOwnerImage").toString()
                            var pounme = document.get("postOwnerUserName").toString();
                            var ptype = document.get("postType").toString();
                            var ploc = document.get("postLocation").toString();
                            var pnme = document.get("postName").toString();
                            var pid = document.get("postID").toString();
                            if (pofnme.contains(Owner_Name)||Owner_Name.contains(pofnme))
                            {
                                var User = user()
                                User.CheckSet_userName(pounme)
                                try {
                                    var oimg =  User.get_ProfileImage()!!.imageData
                                    var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                    Users.put(temp.postID,User)
                                    Posttypemap.get("Owner_Name")!!.add(temp)
                                }catch (e:Exception){var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                    Users.put(temp.postID,User)
                                    Posttypemap.get("Owner_Name")!!.add(temp)}
                            }
                        } catch (e: Exception) {
                        }
                    }

                }
            }
        })
    }
    fun getPostByWS_Name(WS_Name:String)  {
        FirebaseFirestore.getInstance().collection("post")
                .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(p0: Task<QuerySnapshot>) {
                if (p0.isSuccessful) {
                    try {
                        Posttypemap.get("WS_Name")!!.clear();
                    }catch (e:Exception)
                    {
                        Posttypemap.put("WS_Name", ArrayList())
                    }
                    for (document: QueryDocumentSnapshot in p0.result) {
                        println(document.id + " => " + document.data);
                        try {
                            var col = document.get("color").toString().toInt();
                            var comm = document.get("comments");
                            var orate = document.get("postRate").toString().toInt();
                            var pofnme = document.get("postOwnerFName").toString();
                            var oimg = document.get("postOwnerImage").toString()
                            var pounme = document.get("postOwnerUserName").toString();
                            var ptype = document.get("postType").toString();
                            var ploc = document.get("postLocation").toString();
                            var pnme = document.get("postName").toString();
                            var pid = document.get("postID").toString();
                            if (pnme.contains(WS_Name))
                            {
                                var User = user()
                                User.CheckSet_userName(pounme)
                                try {
                                    var oimg =  User.get_ProfileImage()!!.imageData
                                    var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                    Users.put(temp.postID,User)
                                    Posttypemap.get("WS_Name")!!.add(temp)
                                }catch (e:Exception){var temp = post(pofnme, ptype, ArrayList(), null, pounme, col, orate, ploc, pnme, pid)
                                    Users.put(temp.postID,User)
                                    Posttypemap.get("WS_Name")!!.add(temp)}
                            }
                        } catch (e: Exception) {
                        }
                    }

                }
            }
        })
    }

/*
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
    }*/
}
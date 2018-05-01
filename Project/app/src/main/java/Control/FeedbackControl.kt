package Control

/**
 * Created by Mohamed Aaziz on 02/04/2018.
 */
import Model.postData.Feedback.Feedback
import Utility.Utility
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import java.util.HashMap


class FeedbackControl private constructor() {


    companion object {
        private var instance: FeedbackControl? = null
        private var dataBaseInstance = Utility.fireStoreHandler
        fun getInstance(x: Int = 0/*Dummy*/): FeedbackControl {
            if (instance == null)
                instance = FeedbackControl()
            return instance!!
        }


        fun addFeedback(feedback: Feedback) {
            FeedbackControl.dataBaseInstance.collection("posts_user_map")
                    .whereEqualTo("postContent", NewPost.postContent).whereEqualTo("postTime", NewPost.postTime)
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
                                PostControl.dataBaseInstance.collection("posts_user_map")
                                        .document(NewPost.postContent).set(PostData);
                            } else {
                                // Task is not Successfull ,, should be throw an exception
                                println(p0.exception.toString());
                            }
                        }
                    })

        }


    } //Singleton


}
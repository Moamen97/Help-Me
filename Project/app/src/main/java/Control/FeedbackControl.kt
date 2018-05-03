package Control

/**
 * Created by Mohamed Aaziz on 02/04/2018.
 */
import Model.postData.Feedback.Feedback
import Model.postData.post
import Utility.Utility
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot


class FeedbackControl private constructor() {
    var feedbacks = arrayListOf<Feedback>()

    companion object {
        private var instance: FeedbackControl? = null
        private var dataBaseInstance = Utility.fireStoreHandler
        private var feedbacks = arrayListOf<Feedback>()
        fun getInstance(x: Int = 0/*Dummy*/): FeedbackControl {
            if (instance == null)
                instance = FeedbackControl()
            return instance!!
        }


        fun addFeedback(feedback: Feedback, postId: Int) {
            FeedbackControl.dataBaseInstance.collection("post").document(postId.toString()).collection("feedbacks")
                    .add(feedback)
                    .addOnSuccessListener {
                        print("Success")
                    }.addOnFailureListener { print("Failure") }
        }


        fun prepareFeedback(postId: Int) {
            val ayKalam = FeedbackControl.dataBaseInstance.collection("post").document(postId.toString()).collection("feedbacks")
                    .get()
                    .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                        override fun onComplete(p0: Task<QuerySnapshot>) {
                            if (p0.isSuccessful) {
                                getFeedbacksList().clear()
                                if (!p0.result.isEmpty) {
                                    for (document in p0.result) {
                                        try {
                                            val feedback = Feedback(
                                                    document.get("userImage") as String,
                                                    document.get("from") as String,
                                                    document.get("message") as String,
                                                    document.get("timestamp") as String,
                                                    document.get("deleteIt") as Boolean,
                                                    document.get("rate") as String)
                                            getFeedbacksList().add(feedback)
                                            println("Document whose data => " + document.data.toString())
                                        } catch (e: Exception) { }
                                    }
                                }
                            } else {
                                println(p0.exception.toString())
                            }
                        }
                    })

        }

        fun getFeedbacksList(): ArrayList<Feedback> {
            println(this.feedbacks.size)
            return this.feedbacks
        }

    } //Singleton

}
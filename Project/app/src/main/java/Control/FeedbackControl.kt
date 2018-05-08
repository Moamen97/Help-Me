package Control

/**
 * Created by Mohamed Aaziz on 02/04/2018.
 */
import Common.mySelf
import Model.postData.Feedback.Feedback
import Model.postData.post
import Utility.Utility
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.helpme.Comment.ShowFeedbacks


class FeedbackControl private constructor() {
    var feedbacks = arrayListOf<Feedback>()

    companion object {
        private var instance: FeedbackControl? = null
        private var dataBaseInstance = Utility.fireStoreHandler
        private var feedbacks = arrayListOf<Feedback>()
        var flag = arrayListOf<Boolean>(false)
        fun getInstance(x: Int = 0/*Dummy*/): FeedbackControl {
            if (instance == null)
                instance = FeedbackControl()
            return instance!!
        }

        fun addFeedback(feedback: Feedback, showFeedbacks: ShowFeedbacks) {
            FeedbackControl.dataBaseInstance.collection("post").document(mySelf.currentPostId).collection("feedbacks")
                    .add(feedback)
                    .addOnSuccessListener {
                        print("Success")
                        showFeedbacks.toastMessage("feedback has been added successfully")
                        prepareFeedback(showFeedbacks)
                    }.addOnFailureListener { print("Failure") }
        }

        fun prepareFeedback(showFeedbacks: ShowFeedbacks) {
            flag[0] = false
            FeedbackControl.dataBaseInstance.collection("post").document(mySelf.currentPostId).collection("feedbacks")
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
                                                    document.get("rate") as String,
                                                    document.get("firstName") as String,
                                                    document.get("midName") as String,
                                                    document.get("lastName") as String)
                                            feedback.feedbackId = document.id
                                            getFeedbacksList().add(feedback)
                                            println("Document whose data => " + document.data.toString())
                                            flag[0] = true
                                        } catch (e: Exception) {
                                            println(e.toString())
                                        }
                                    }
                                    showFeedbacks.refreshYBasha()
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

        fun getSortedFeedbacksList(): ArrayList<Feedback> {
            val sortedList = feedbacks.sortedWith(compareBy({ it.timestamp }))
            feedbacks.clear()
            for (i in (0..sortedList.size - 1)) feedbacks.add(sortedList[i])
            return feedbacks
        }

        fun returnHashMapOfFeedbackList(): ArrayList<Feedback> {
            val postID = mySelf.currentPostId
            if (feedbacks.size == 0)
                for (i in 0..(mySelf.hashMap[postID]!!.size - 1))
                    feedbacks.add(mySelf.hashMap[postID]!![i])
            return feedbacks
        }

        fun clearHashMap() {
            mySelf.hashMap.clear()
        }

        fun printHashMapOfThisFragment() {
            mySelf.hashMap.keys.forEach {
                for (i in (0..mySelf.hashMap[it]!!.size - 1)) {
                    println(mySelf.hashMap[it]!![i])
                }
            }
        }
    } //Singleton

    fun removeFeedback(feedbackId: String, showFeedbacks: ShowFeedbacks) {
        FeedbackControl
                .dataBaseInstance
                .collection("post")
                .document(mySelf.currentPostId)
                .collection("feedbacks")
                .document(feedbackId)
                .delete()
                .addOnSuccessListener {
                    showFeedbacks.toastMessage("feedback/s have been deleted")
                }
                .addOnFailureListener { showFeedbacks.toastMessage("error while deleting feedback") }
    }
}
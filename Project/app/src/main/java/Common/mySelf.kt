package Common
import  FireBase.fireStore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception
import java.util.*
import Model.postData.post

object mySelf : Model.user() {
    var uPath: String = "" // this variable will change whole object if assigned to another user
        set(value) {
            val doc = fireStore.fireStoreHandler.document("${usersCollectionName}/${uPath}")
            doc.get().addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
                override fun onSuccess(docSnapshot: DocumentSnapshot) {
                    userName = docSnapshot.getString(userNameKey)
                    password = docSnapshot.getString(passwordKey)
                    email = docSnapshot.getString(emailKey)
                    firstName = docSnapshot.getString(firstNameKey)
                    lastName = docSnapshot.getString(lastNameKey)
                    gender = docSnapshot.getString(genderKey)[0]
                    phoneNum = docSnapshot.getString(phoneNumKey)
                    behaveRate = docSnapshot.getLong(behaveRateKey) as Int
                    birthDate = docSnapshot.getDate(birthDateKey).toString()
                    field = value
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    //debugger.showMess("failed at fetching userdata")
                }

            })
        }
    var myNotifications: MutableList<Pair<String, Date>> = mutableListOf<Pair<String, Date>>()
    fun addNotification(p: Pair<String, Date>) {
        myNotifications.add(p)
    }

    var myPosts: MutableList<post> = mutableListOf<post>()
}


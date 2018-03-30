package Common
import  FireBase.fireStore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception
import java.util.*
import Model.postData.post
import Model.user

object mySelf : Model.user() {
    fun loadMyself(uName:String):Boolean {
        userName = uName
        var task = fireStore.fireStoreHandler.document("${user.usersCollectionName}/$uName").get()
        while (true) {
            if(task.isComplete) {
                if (task.isSuccessful) {
                    var docSnapshot = task.result
                    this@mySelf.password = docSnapshot.getString(user.passwordKey)
                    this@mySelf.email = docSnapshot.getString(user.emailKey)
                    this@mySelf.firstName = docSnapshot.getString(user.firstNameKey)
                    this@mySelf.lastName = docSnapshot.getString(user.lastNameKey)
                    //this@mySelf.gender = docSnapshot.getString(user.genderKey)[0]
                    //this@mySelf. phoneNum = docSnapshot.getString(user.phoneNumKey)
                    //this@mySelf.behaveRate = docSnapshot.getLong(user.behaveRateKey) as Int
                    //this@mySelf.birthDate = docSnapshot.getDate(user.birthDateKey).toString()
                    return true
                } else
                    return false
            }
        }

        return false
    }
    var myNotifications: MutableList<Pair<String, Date>> = mutableListOf<Pair<String, Date>>()
    fun addNotification(p: Pair<String, Date>) { myNotifications.add(p) }
    var myPosts: MutableList<post> = mutableListOf<post>()
}


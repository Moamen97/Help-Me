package Common
import Utility.Utility
import java.util.*
import Model.postData.post
import Model.user

object mySelf : Model.user() {
    fun loadMyself(uName:String):Boolean {
        userName = uName
        var task = Utility.fireStoreHandler.document("${user.usersCollectionName}/$uName").get()
        while (true) {
            if(task.isComplete) {
                if (task.isSuccessful) {
                    var docSnapshot = task.result
                    this@mySelf.password = docSnapshot.getString(user.passwordKey)
                    this@mySelf.eMail = docSnapshot.getString(user.emailKey)
                    this@mySelf.firstName = docSnapshot.getString(user.firstNameKey)
                    this@mySelf.lastName = docSnapshot.getString(user.lastNameKey)
                    this@mySelf.midName = docSnapshot.getString(user.midNameKey)
                    this@mySelf.gender = docSnapshot.getString(user.genderKey)
                    this@mySelf. phoneNum = docSnapshot.getString(user.phoneNumKey)
                    //this@mySelf.behaveRate = docSnapshot.getLong(user.behaveRateKey) as Int
                    this@mySelf.birthDate = docSnapshot.getString(user.birthDateKey).toString()
                    return true
                } else
                    return false
            }
        }
        return false
    }
    fun uploadMyself(){
        var userData = HashMap<String, Any>();
        userData.put(user.userNameKey,userName);
        userData.put(user.firstNameKey, firstName);
        userData.put(user.midNameKey, midName);
        userData.put(user.lastNameKey, lastName);
        userData.put(user.imageKey,imageID);
        userData.put(user.passwordKey, password);
        userData.put(user.emailKey, eMail);
        userData.put(user.genderKey, gender);
        userData.put(user.phoneNumKey, phoneNum);
        userData.put(user.behaveRateKey, behaveRate);
        userData.put(user.birthDateKey, birthDate);
        Utility.fireStoreHandler.collection("user").document(userName).
                update(userData);
    }
    var myNotifications: MutableList<Pair<String, Date>> = mutableListOf<Pair<String, Date>>()
    fun addNotification(p: Pair<String, Date>) { myNotifications.add(p) }
    var myPosts: MutableList<post> = mutableListOf<post>()
}


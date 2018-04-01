package Model
import java.util.*

/**
 * Created by shehab on 3/29/18.
 */

    open class user() {

        constructor(eMail:String, firstName:String, midName:String ,
                    lastName:String ,gender :String ,image:String ,password: String ,
                    phoneNum:String , userName:String , behav_rate:Int, birthDate:String ,
                    imageID:String) : this() {
            this.userName=userName;this.password=password;this.email=eMail;this.phoneNum=phoneNum
            this.firstName=firstName;this.midName=midName;this.lastName=lastName;this.birthDate=birthDate
            this.gender=gender
            this.behaveRate=behaveRate;this.imageID=imageID
        }
        var userName: String = ""
            set(value) {
                field = value
            }
        var password: String = ""
            set(value) {
                field = value
            }
        var email: String = ""
            set(value) {
                field = value
            }
        var phoneNum: String = ""
            set(value) {
                field = value
            }
        var firstName: String = ""
            set(value) {
                field = value
            }
        var midName: String = ""
            set(value) {
                field = value
            }
        var lastName: String = ""
            set(value) {
                field = value
            }
        var birthDate: String = ""
            set(value) {
                field = value
            }

        var gender: String = ""
            set(value) {
                field = value
            }
        var behaveRate: Int = 5
            set(value) {
                field = when {
                    value > 10 -> 10
                    value < 0 -> 0
                    else -> value }
            }
        var imageID: String = ""
        companion object {
            val userNameKey: String = "userName"
            val passwordKey: String = "password"
            val firstNameKey: String = "firstName"
            val midNameKey: String = "midName"
            val lastNameKey: String = "lastName"
            val birthDateKey: String = "birthDate"
            val emailKey: String = "eMail"
            val genderKey: String = "gender"
            val imageKey: String = "image"
            val behaveRateKey: String = "behaveRate"
            val phoneNumKey: String = "phoneNumber"
            val professionCollectionKey: String = "profession"
            val notificationsCollectionKey: String = "notifications"
            val postsCollectionKey:String = "posts"
            val usersCollectionName:String = "user"
        }
    }


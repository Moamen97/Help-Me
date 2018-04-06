package Model
import FireBase.fireStore
import com.google.android.gms.tasks.Task
import com.helpme.R.id.*
import java.util.*

/**
 * Created by shehab on 3/29/18.
 */
open class user() {
    protected var userName: String = ""
    protected var password: String = ""
    protected var eMail: String = ""
    protected var phoneNum: String = ""
    protected var firstName: String = ""
    protected var midName: String = ""
    protected var lastName: String = ""
    protected var birthDate: String = ""
    protected var gender: String = ""
    protected var imageID: String = ""
    protected var behaveRate: Int = 5
        set(value) {
            field = when {
                value > 10 -> 10
                value < 0 -> 0
                else -> value
            }
        }

    fun CheckSet_userName(uname:String){
        require(uname.length>=3){"Error: username is too short 3 chars minimum"}
        require(uname.matches(Regex("\\w*"))){"Error: username can be alphabet and digits only"}
        userName=uname
    }
    fun CheckSet_password(pass1:String,pass2:String){
        require(pass1.length>=5 ){"Error: password length less than five chars"}
        require(pass1==pass2){"Error: password confirmation mismatch"}
        password=pass1
    }
    fun CheckSet_email(em:String) {
        require(em.matches(Regex("\\w{1,20}@[a-z]{1,7}\\.com"))){"wrong email format"}
        eMail=em
    }
    fun CheckSet_phoneNum(phNum:String){
        val regex=Regex("01[0-2][0-9]{8}")
        require(phNum.matches(regex)) {"Error:wrong phone number format"}
        phoneNum=phNum
    }
    fun CheckSet_firstName(fName:String){
        require(!fName.matches(Regex(".*\\d.*")) && fName.length >1)
        {"Error: firstname can't contain numbers or it's too short"}
        firstName=fName
    }
    fun CheckSet_midName(mName:String){
        require(!mName.matches(Regex(".*\\d.*"))){"Error: midname contains numbers"}
        midName=mName
    }
    fun CheckSet_lastName(lName:String){
        require(!lName.matches(Regex(".*\\d.*"))){"Error: midname contains numbers"}
        lastName=lName
    }
    fun CheckSet_birthDate(bDate:String){
        require(((Calendar.getInstance().get(Calendar.YEAR)
                -
                bDate.split("/")[2].toInt())
                > 12)
                ) {"Error:you should at least 12 years old"}
        birthDate=bDate
    }
    fun CheckSet_gender(g:String){
        if(g=="male"|| g=="female")
            gender=g
        else
            throw IllegalArgumentException("Error: only male or female accepted")
    }
    fun Checkset_imageID(imgid:String){

    }
    fun get_userName()=userName
    fun get_password()=password
    fun get_email()= eMail
    fun get_phoneNum()=phoneNum
    fun get_firstName()=firstName
    fun get_midName()=midName
    fun get_lastName()=lastName
    fun get_birthDate()=birthDate
    fun get_gender()=gender
    fun get_imageID()=imageID
    fun get_behaveRate()=behaveRate
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
        val behaveRateKey: String = "behav_rate"
        val phoneNumKey: String = "phoneNum"
        val professionCollectionKey: String = "profession"
        val notificationsCollectionKey: String = "notifications"
        val postsCollectionKey: String = "posts"
        val usersCollectionName: String = "user"
    }
}


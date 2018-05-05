package Model

import Control.UserControl
import Utility.Utility
import android.graphics.BitmapFactory
import com.google.android.gms.tasks.OnSuccessListener
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
    protected var profileimage: Image? = null
    protected var worksImages:MutableList<Image?> = mutableListOf()
    protected var isProfessional: Boolean = false
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
        password=Utility.hashString(pass1)
    }
    fun CheckSet_email(em:String) {
        require(em.matches(Regex("[0-9A-Za-z._]{1,20}@[a-z]{1,7}\\.com"))){"wrong email format"}
        eMail=em
    }
    fun CheckSet_phoneNum(phNum:String){
        val regex=Regex("01[0-2][0-9]{8}")
        if (!phNum.isEmpty())require(phNum.matches(regex)) {"Error:wrong phone number format"}
        phoneNum=phNum
    }
    fun CheckSet_firstName(fName:String){
        require(!fName.matches(Regex(".*\\d.*")) && fName.length >1)
        {"Error: firstname can't contain numbers or it's too short"}
        firstName=fName
    }
    fun CheckSet_midName(mName:String){
        require(!mName.matches(Regex(".*\\d.*"))){"Error: middle name contains numbers"}
        midName=mName
    }
    fun CheckSet_lastName(lName:String){
        require(!lName.matches(Regex(".*\\d.*"))){"Error: last name contains numbers"}
        lastName=lName
    }
    fun CheckSet_birthDate(bDate:String){
        if (bDate!="") {
            require(((Calendar.getInstance().get(Calendar.YEAR)
                    -
                    bDate.split("/")[2].toInt())
                    > 12)
            ) { "Error:you should at least 12 years old" }
        }
        birthDate = bDate
    }


    fun CheckSet_isProfistional(b:Boolean){
        isProfessional=b
    }
    fun CheckSet_gender(g:String){
        gender=g
    }
    fun Checkset_image(im:Image?){
        profileimage=im
    }
    fun addWorkImage(im:Image?){
        worksImages.add(im)
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
    fun get_ProfileImage()=profileimage
    fun get_imageID()="" // this func will be removed later
    fun get_behaveRate()=behaveRate
    fun get_worksImages()=worksImages
    fun get_isProfessional()=isProfessional
    fun downloadProfileImage() {
        Utility.storageHandler.reference.child("images/$userName/profile/$userName.jpg").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(object : OnSuccessListener<ByteArray> {
                    override fun onSuccess(p0: ByteArray) {
                        var im=Image("$userName.jpg",BitmapFactory.decodeByteArray(p0, 0, p0.size))
                        this@user.Checkset_image(im)
                        UserControl.getUnChangedInstance().updateProfileImage(this@user)
                    }
                })
    }
    fun downloadWorksImages(list:List<String>){
        var count=list.size
        list.forEach{
        Utility.storageHandler.reference
                .child("images/$userName/works/$it").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(object : OnSuccessListener<ByteArray> {
                    override fun onSuccess(p0: ByteArray) {
                        var im=Image(it,BitmapFactory.decodeByteArray(p0, 0, p0.size))
                        this@user.addWorkImage(im)
                        count--
                        if(count==0)
                            UserControl.getUnChangedInstance().updateWorksImage(this@user)
                    }
                })
        }

    }
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
        val userStorageImageFolder:String ="users_images"
        val worksImagesNamesKey:String="worksImagesNames"
        var isProfessionalKey:String="isProfessional"
    }
}

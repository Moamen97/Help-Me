package Control


import com.google.android.gms.tasks.*
import Common.mySelf
import Utility.*
import Model.*
import Model.postData.post
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import com.helpme.Authentication.SignIn
import com.helpme.Authentication.SignUp
import com.helpme.EditProfile.MyProfile
import com.helpme.Home.home
import com.helpme.UploadImages.UploadImage
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_upload_image.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class UserControl private constructor() {
    private var User_Model: mySelf? = null
    private var newUser: user = user()
    private var SignIn_View: SignIn? = null
    private var SignUp_View: SignUp? = null
    private var Home_View: home? = null
    private var myProfileView: MyProfile? = null
    private var dataBaseInstance = Utility.fireStoreHandler
    private var toasmsg: String = ""
    companion object {
        private var instance: UserControl? = null
        fun getInstance(SignIn_View: SignIn? = null, SignUp_View: SignUp? = null // mainly for views to talk with controller
                        , Home_View: home? = null, myProfileView: MyProfile? = null
        ): UserControl {
            if (instance == null)
                instance = UserControl()
            instance!!.setcurrentview(SignIn_View, SignUp_View, Home_View, myProfileView)
            return instance!!
        }

        fun getUnChangedInstance(): UserControl { // for data to talk with controller with change view bindings
            if (instance == null)
                instance = UserControl()
            return instance!!
        }
    } //Singleton

    fun updateBehaviourRate(behav_rate: Int) {
        dataBaseInstance.collection("user").document(mySelf.postOwner)
                .update("behav_rate", behav_rate)
                .addOnSuccessListener { println("behave rate Successfully updated") }
                .addOnFailureListener { println("Error while updating rate") }
    }


    fun GetUserRate(Post: post) {
        dataBaseInstance.collection(user.usersCollectionName)
                .whereEqualTo(user.userNameKey, Post.postOwnerUserName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                println(p0.result.documents[0].data.toString())
                                Post?.postRate = p0.result.documents[0].get("behav_rate").toString().toInt()
                            }
                        } else {
                            println(p0.exception.toString());
                        }
                    }
                })


    }

    fun loadUser(u: user): Boolean {
        var task = Utility.fireStoreHandler.document("${user.usersCollectionName}/${u.get_userName()}").get()
        while (true) {
            if (task.isComplete) {
                if (task.isSuccessful) {
                    var docSnapshot = task.result


                    u.Checkset_image(null)
                    u.downloadProfileImage()
                    u.downloadWorksImages(docSnapshot.data!![user.worksImagesNamesKey] as MutableList<String>?)

                    return true
                } else
                    return false
            }
        }
    }

    fun getUserByUserName(userName: String): StringBuilder {
        val s = StringBuilder()

        var task = Utility.fireStoreHandler.document("${user.usersCollectionName}/$userName").get()
        while (true) {
            if (task.isComplete) {
                if (task.isSuccessful) {
                    val docSnapshot = task.result
                    s.append(docSnapshot.getString(user.firstNameKey)!! + " ").append(docSnapshot.getString(user.midNameKey)!! + " ")
                            .append(docSnapshot.getString(user.lastNameKey)!!)
                    return s
                } else
                    return s
            }
        }
    }

    private fun setcurrentview(SignIn_View: SignIn?, SignUp_View: SignUp?, Home_View: home?,
                               myProfileView: MyProfile?) {
        this.SignIn_View = SignIn_View
        this.SignUp_View = SignUp_View
        this.Home_View = Home_View
        this.myProfileView = myProfileView
    }

    fun getUser(): mySelf? {
        return this.User_Model
    }


    fun Login(userName: String, password: String) {
        if (!Utility.isNetworkAvailable(SignIn_View!!.baseContext)) {
            toasmsg = ("You can't Log In if you offline")
            SignIn_View!!.ShowToast(toasmsg)
            SignIn_View!!.signInBtn.isEnabled = true
            SignIn_View!!.signUpBtn.isEnabled = true
            SignIn_View!!.login_button.isEnabled = true
            return
        }
        dataBaseInstance.collection(user.usersCollectionName)
                .whereEqualTo(user.userNameKey, userName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                println(p0.result.documents[0].data.toString())
                                if (p0.result.documents[0].get(user.passwordKey)
                                        == Utility.hashString(password)) {
                                    if (mySelf.loadMyself(userName)) {
                                        User_Model = mySelf
                                        toasmsg = "Sign In Successfully " +
                                                User_Model!!.get_firstName() + " " +
                                                User_Model!!.get_midName() +
                                                " " + User_Model!!.get_lastName() + " "
                                        SignIn_View!!.ShowToast(toasmsg)
                                        SignIn_View!!.LogIn()
                                    } else {
                                        SignIn_View!!.ShowToast("Loginfalied:check your internet connection")
                                        SignIn_View!!.signInBtn.isEnabled = true
                                        SignIn_View!!.signUpBtn.isEnabled = true
                                        SignIn_View!!.login_button.isEnabled = true
                                    }
                                } else {
                                    toasmsg = "Wrong user name of password"
                                    SignIn_View!!.ShowToast(toasmsg)
                                    SignIn_View!!.signInBtn.isEnabled = true
                                    SignIn_View!!.signUpBtn.isEnabled = true
                                    SignIn_View!!.login_button.isEnabled = true
                                }
                            } else {
                                toasmsg = "Wrong user name of password"
                                SignIn_View!!.ShowToast(toasmsg)
                                SignIn_View!!.signInBtn.isEnabled = true
                                SignIn_View!!.signUpBtn.isEnabled = true
                                SignIn_View!!.login_button.isEnabled = true
                            }
                        } else {
                            println(p0.exception.toString());
                        }
                    }
                })


    }


    fun signUp() {
        if (!Utility.isNetworkAvailable(SignUp_View!!.baseContext)) {
            toasmsg = ("You can't sign up if you offline")
            SignUp_View!!.ShowToast(toasmsg)
            return
        }
        dataBaseInstance.collection(user.usersCollectionName)
                .whereEqualTo(user.userNameKey, newUser.get_userName())
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (p0.result.isEmpty) {
                                // getting empty documents ; here we should push our user to db :V
                                println("No Document Data");
                                var userData = HashMap<String, Any>();
                                userData.put(user.userNameKey, newUser.get_userName());
                                userData.put(user.passwordKey, newUser.get_password());
                                userData.put(user.emailKey, newUser.get_email());
                                userData.put(user.firstNameKey, newUser.get_firstName());
                                userData.put(user.midNameKey, newUser.get_midName());
                                userData.put(user.lastNameKey, newUser.get_lastName());
                                userData.put(user.genderKey, newUser.get_gender());
                                userData.put(user.phoneNumKey, newUser.get_phoneNum());
                                userData.put(user.behaveRateKey, 0);
                                userData.put(user.birthDateKey, newUser.get_birthDate());
                                userData.put(user.isProfessionalKey, false);
                                toasmsg = "data will be uploaded in seconds please wait"
                                SignUp_View!!.ShowToast(toasmsg)
                                SignUp_View!!.Signup()
                                var task = dataBaseInstance.collection("user").document(newUser.get_userName())
                                        .set(userData)

                                TimeUnit.SECONDS.sleep(1)
                                toasmsg = "Your data uploaded you can sign in now and enjoy our service"
                                SignUp_View!!.ShowToast(toasmsg)
                                SignUp_View!!.Signup()
                            } else {
                                // here i found someone has the same userName :V so i can't add this user
                                println(p0.result.documents[0].data.toString());
                                toasmsg = "Sign up is not success user name is already exist"
                                SignUp_View!!.ShowToast(toasmsg)
                            }
                        }
                    }
                })
    }

    fun signUpFacebook(unhashedpass: String) {
        if (!Utility.isNetworkAvailable(SignIn_View!!.baseContext)) {
            toasmsg = ("You can't sign up if you offline")
            SignUp_View!!.ShowToast(toasmsg)
            return
        }
        dataBaseInstance.collection(user.usersCollectionName)
                .whereEqualTo(user.userNameKey, newUser.get_userName())
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (p0.result.isEmpty) {
                                // getting empty documents ; here we should push our user to db :V
                                println("No Document Data");
                                var userData = HashMap<String, Any>();
                                userData.put(user.userNameKey, newUser.get_userName());
                                userData.put(user.imageKey, newUser.get_imageID());
                                userData.put(user.passwordKey, newUser.get_password());
                                userData.put(user.emailKey, newUser.get_email());
                                userData.put(user.firstNameKey, newUser.get_firstName());
                                userData.put(user.midNameKey, newUser.get_midName());
                                userData.put(user.lastNameKey, newUser.get_lastName());
                                userData.put(user.genderKey, newUser.get_gender());
                                userData.put(user.phoneNumKey, newUser.get_phoneNum());
                                userData.put(user.behaveRateKey, 0);
                                userData.put(user.birthDateKey, newUser.get_birthDate());
                                userData.put(user.isProfessionalKey, false);
                                toasmsg = "data will be uploaded in seconds please wait"
                                SignIn_View!!.ShowToast(toasmsg)
                                //SignIn_View!!.Signup()
                                var task = dataBaseInstance.collection("user").document(newUser.get_userName())
                                        .set(userData)
                                TimeUnit.SECONDS.sleep(1)
                                Login(newUser.get_userName(), unhashedpass)
                            } else {
                                println(p0.result.documents[0].data.toString());
                                Login(newUser.get_userName(), unhashedpass)
                            }
                        }
                    }
                })
    }

    fun CreateNewUserFB(userName: String, eMail: String, password: String, passwordconfirm: String,
                        firstName: String, midName: String, lastName: String, phoneNumber: String
                        , birthDate: String, gender: String, unhashedpass: String) {
        try {
            newUser.CheckSet_userName(userName)
            newUser.eMail = eMail
            newUser.CheckSet_password(password
                    , passwordconfirm)
            newUser.CheckSet_firstName(firstName)
            newUser.CheckSet_midName(midName)
            newUser.CheckSet_lastName(lastName)
            newUser.CheckSet_phoneNum(phoneNumber)
            newUser.CheckSet_birthDate(birthDate)
            newUser.CheckSet_gender(gender)
            if (!Utility.isNetworkAvailable(SignIn_View!!.baseContext))
                throw Exception("You can't sign up if you offline")

            this.signUpFacebook(unhashedpass)
        } catch (e: Exception) {
            SignIn_View!!.ShowToast(e.message!!)
        }
    }
    ///////////////////////////////////////////////////////////////////////////


    fun checkBeforUpdate(NewFistName: String, NewMidName: String, NewLastName: String, NewGender: String,
                         NewEmail: String, NewPassword: String, NewMobile: String, NewBirthDate: String
    ) {

        try {
            newUser.CheckSet_email(NewEmail)
            newUser.CheckSet_password(NewPassword, NewPassword)
            newUser.CheckSet_firstName(NewFistName)
            newUser.CheckSet_midName(NewMidName)
            newUser.CheckSet_lastName(NewLastName)
            newUser.CheckSet_phoneNum(NewMobile)
            newUser.CheckSet_birthDate(NewBirthDate)
            newUser.CheckSet_gender(NewGender)
            if (!Utility.isNetworkAvailable(myProfileView!!.applicationContext))
                throw Exception("You can't edit profile if you offline")
            this.UpdateUserInfo(NewFistName, NewMidName, NewLastName, NewGender, NewEmail
                    , NewPassword, NewMobile, NewBirthDate)
        } catch (e: Exception) {
            myProfileView!!.ShowToast(e.message!!)
        }
    }

    fun UpdateUserInfo(NewFirstName: String, NewMidName: String, NewLastName: String, NewGender: String
                       , NewEmail: String, NewPassword: String, NewMobile: String, NewBdate: String) {
        if (!Utility.isNetworkAvailable(myProfileView!!.baseContext)) {
            toasmsg = ("You can't Edit Your Profile if you are offline")
            myProfileView!!.ShowToast(toasmsg)
            return
        }
        toasmsg = ""
        var newemail: String = NewEmail //To Config.
        /*   if (User_Model!!.get_email() != NewEmail) {
                   // checks if the new Email exists
                   dataBaseInstance.collection("user")
                           .whereEqualTo("eMail", NewEmail)
                           .get()
                           .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                               override fun onComplete(p0: Task<QuerySnapshot>) {
                                   println("Entered Complete Listener");
                                   if (p0.isSuccessful) {
                                       if (!p0.result.isEmpty) {
                                           toasmsg += "Email Error, "
                                           newemail = User_Model!!.get_email()
                                       }
                                   }
                               }
                           })
               }
        */
        dataBaseInstance.collection("user")
                .whereEqualTo("userName", User_Model!!.get_userName())
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                // getting non empty documents ; here we should update our user to db :V
                                println("Found Document Data");
                                User_Model!!.CheckSet_email(newemail)
                                User_Model!!.CheckSet_password(NewPassword, NewPassword)
                                User_Model!!.CheckSet_phoneNum(NewMobile)
                                User_Model!!.CheckSet_firstName(NewFirstName)
                                User_Model!!.CheckSet_midName(NewMidName)
                                User_Model!!.CheckSet_lastName(NewLastName)
                                User_Model!!.CheckSet_gender(NewGender)
                                User_Model!!.CheckSet_birthDate(NewBdate)

                                toasmsg = "DONE!"
                                myProfileView!!.ShowToast(toasmsg)
                                User_Model!!.uploadMyself()
                                myProfileView!!.updateinfoviewer()

                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun GetCurrentUserProfile(): user {
        return this.User_Model!!
    }

    fun CreateNewUser(userName: String, eMail: String, password: String, passwordconfirm: String,
                      firstName: String, midName: String, lastName: String, phoneNumber: String
                      , birthDate: String, gender: String) {
        try {
            newUser.CheckSet_userName(userName)
            newUser.CheckSet_email(eMail)
            newUser.CheckSet_password(password
                    , passwordconfirm)
            newUser.CheckSet_firstName(firstName)
            newUser.CheckSet_midName(midName)
            newUser.CheckSet_lastName(lastName)
            newUser.CheckSet_phoneNum(phoneNumber)
            newUser.CheckSet_birthDate(birthDate)
            newUser.CheckSet_gender(gender)
            if (!Utility.isNetworkAvailable(SignUp_View!!.baseContext))
                throw Exception("You can't sign up if you offline")
            this.signUp()
        } catch (e: Exception) {
            SignUp_View!!.ShowToast(e.message!!)
        }
    }

    fun uploadImage(con: UploadImage, bitmap: Bitmap?) {
        con.uploadProgressbar.progress = 1

        lateinit var imageName:String
        if(UploadImage.imageInfo.kind==imageKind.profile)
            imageName=mySelf.get_userName()+".jpg"
        else {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS")
            val date = Date()
            imageName = dateFormat.format(date).replace('/', '-') + ".jpg"
        }


        if (bitmap != null) {
            var Images = Utility.storageHandler.reference
                    .child("images/${mySelf.get_userName()}/${UploadImage.imageInfo.kind}/$imageName")

            val baos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var data = baos.toByteArray()
            var tmp = data.size / (1024.0 * 1024.0)
            if (tmp > 2.5) {
                con.showMessage("Image size is too big")
                con.uploadProgressbar.progress = 0
                con.selectNewImageBtn.isEnabled = true
                con.uploadImageBtn.isEnabled = true
                return
            } else {
                val b = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, minOf((1 / tmp * 100).toInt(), 100), b)
                data = b.toByteArray()
            }

            var im = Image(imageName, BitmapFactory.decodeByteArray(data, 0, data.size))
            if (UploadImage.imageInfo.kind == imageKind.profile)
                mySelf.Checkset_image(im)
            else if (UploadImage.imageInfo.kind == imageKind.works)
                mySelf.addWorkImage(im)


            val uploadTask = Images.putBytes(data)
            con.showMessage("Uploading...")

// Listen for state changes, errors, and completion of the upload.
            uploadTask.addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot> {
                override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    if (progress.toInt() != 0)
                        con.uploadProgressbar.progress = progress.toInt()
                }
            }).addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
                con.showMessage("Image Uploading Failed")
                mySelf.Checkset_image(null)
                mySelf.lastWorkImageFailed()
                con.selectNewImageBtn.isEnabled = true
                con.uploadImageBtn.isEnabled = true

            }).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(p0: UploadTask.TaskSnapshot) {
                    con.uploadProgressbar.setProgress(0)
                    con.selectNewImageBtn.isEnabled = true
                    con.uploadImageBtn.isEnabled = true
                    mySelf.uploadMyself()
                    con.showMessage("Image Uploading Done")
                }
            })
        } else
            con.showMessage("No new image selected")
    }

    fun deleteImage(pos:Int) {
         Utility.storageHandler.reference
                 .child("images/${mySelf.get_userName()}/works/${mySelf.get_worksImages()[pos]!!.imageName}")
                 .delete().addOnSuccessListener(object:OnSuccessListener<Void>{
                     override fun onSuccess(p0: Void?) {
                         mySelf.rmWorkImage(pos)
                         mySelf.uploadMyself()
                         if(myProfileView!=null) {
                             myProfileView!!.updateWorksImage(mySelf)
                             myProfileView!!.ShowToast("Image Deleted")
                             myProfileView!!.worksImagesGV.isClickable=true
                         }
                     }
                 }).addOnFailureListener(object:OnFailureListener{
                     override fun onFailure(p0: Exception) {
                         myProfileView!!.ShowToast("Failed to Delete Image Please Try again Later")
                         myProfileView!!.worksImagesGV.isClickable=true
                     }
                 })
    }

    fun updateProfileImage(u: user) {
        myProfileView?.updateProfileImage(u)
    }

    fun updateWorksImage(u: user) {
        myProfileView?.updateWorksImage(u)
    }

}


/*var t = object : Thread() {
    override fun run() {
        try {
            Tasks.await(task,1500, TimeUnit.MILLISECONDS);
            toasmsg = "Your data uploaded you can sign in now and enjoy our service"
            SignUp_View!!.ShowToast(toasmsg)
            SignUp_View!!.Signup()
        }
        catch (e:ExecutionException){

            toasmsg = "Error:Your data isn't uploaded correctly please try again"
            SignUp_View!!.ShowToast(toasmsg)
            SignUp_View!!.Signup()
        }
        catch (e:InterruptedException) {
                toasmsg = "Error:Your data isn't uploaded correctly please try again"
                SignUp_View!!.ShowToast(toasmsg)
                SignUp_View!!.Signup()

        }
        catch (e:Exception){
            toasmsg = e.message!!+"Your data uploaded you can sign in now and enjoy our service"
            SignUp_View!!.ShowToast(toasmsg)
            SignUp_View!!.Signup()
        }
    }
}
t.run()
*/

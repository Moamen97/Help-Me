package Control

import Common.mySelf
import FireBase.fireStore
import Model.user
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.helpme.Authentication.SignIn
import com.helpme.Authentication.SignUp
import com.helpme.EditProfile.EditProfile
import com.helpme.EditProfile.MyProfile
import com.helpme.Home.home
import java.lang.Exception
import java.util.*

class UserControl private constructor() {
    private var User_Model: mySelf? = null
    private var newUser: user = user()
    private var SignIn_View: SignIn? = null
    private var SignUp_View: SignUp? = null
    private var Home_View: home? = null
    private var myProfileView: MyProfile? = null
    private var dataBaseInstance = fireStore.fireStoreHandler
    private var toasmsg: String = ""

    companion object {
        private var instance: UserControl? = null
        fun getInstance(SignIn_View: SignIn? = null, SignUp_View: SignUp? = null
                        , Home_View: home? = null, myProfileView: MyProfile? = null): UserControl {
            if (instance == null)
                instance = UserControl()
            instance!!.setcurrentview(SignIn_View, SignUp_View, Home_View, myProfileView)
            return instance!!
        }
    } //Singleton

    private fun setcurrentview(SignIn_View: SignIn?, SignUp_View: SignUp?, Home_View: home?,
                               myProfileView: MyProfile?) {
        this.SignIn_View = SignIn_View
        this.SignUp_View = SignUp_View
        this.Home_View = Home_View
        this.myProfileView = myProfileView
    }

    fun Login(userName: String, password: String) {
        dataBaseInstance.collection(user.usersCollectionName)
                .whereEqualTo(user.userNameKey, userName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                println(p0.result.documents[0].data.toString())
                                if (p0.result.documents[0].get(user.passwordKey).equals(password)) {
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
                                    }
                                } else {
                                    toasmsg = "Wrong user name of password"
                                    SignIn_View!!.ShowToast(toasmsg)
                                }
                            } else {
                                toasmsg = "Wrong user name of password or you are offline"
                                SignIn_View!!.ShowToast(toasmsg)
                            }
                        } else {
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun signUp() {
        if (!fireStore.isNetworkAvailable(SignUp_View!!.baseContext)) {
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
                                userData.put(user.behaveRateKey, newUser.get_behaveRate());
                                userData.put(user.birthDateKey, newUser.get_birthDate());
                                toasmsg = "Congratz your data will be uploaded in seconds"
                                SignUp_View!!.ShowToast(toasmsg)
                                SignUp_View!!.Signup()
                                var task = dataBaseInstance.collection("user").document(newUser.get_userName()).set(userData)
                                while (true) {
                                    if (task.isComplete) {
                                        if (task.isSuccessful) {
                                            toasmsg = "Your data uploaded you can sign in now and enjoy our service"
                                            SignUp_View!!.ShowToast(toasmsg)
                                            SignUp_View!!.Signup()
                                            break
                                        } else {
                                            toasmsg = "Error:Your data isn't uploaded correctly please try again"
                                            SignUp_View!!.ShowToast(toasmsg)
                                            SignUp_View!!.Signup()
                                            break
                                        }
                                    }
                                }
                            } else {
                                // here i found someone has the same userName :V so i can't add this user
                                println(p0.result.documents[0].data.toString());
                                toasmsg = "Sign up is not success user name is already exist or you are offline"
                                SignUp_View!!.ShowToast(toasmsg)
                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun UpdateUserInfo(NewFistName: String, NewMidName: String, NewLastName: String, NewGender: String,
                       NewEmail: String, NewPassword: String, NewMobile: String, NewBirthDate: String) {
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
                                User_Model!!.CheckSet_firstName(NewFistName)
                                User_Model!!.CheckSet_midName(NewMidName)
                                User_Model!!.CheckSet_lastName(NewLastName)
                                User_Model!!.CheckSet_gender(NewGender)
                                User_Model!!.CheckSet_email(newemail)
                                User_Model!!.CheckSet_password(NewPassword, NewPassword)
                                User_Model!!.CheckSet_phoneNum(NewMobile)
                                User_Model!!.CheckSet_birthDate(NewBirthDate)
                                //User_Model!!.Checkset_imageID()
                                toasmsg = "DONE!"
                                myProfileView!!.ShowToast(toasmsg)
                                User_Model!!.uploadMyself()

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

    fun CreateNewUser() {
        try {
            newUser.CheckSet_userName(SignUp_View!!.get_userName())
            newUser.CheckSet_email(SignUp_View!!.get_eMail())
            newUser.CheckSet_password(SignUp_View!!.get_password()
                    , SignUp_View!!.get_passwordconfirm())
            newUser.CheckSet_firstName(SignUp_View!!.get_firstName())
            newUser.CheckSet_midName(SignUp_View!!.get_midName())
            newUser.CheckSet_lastName(SignUp_View!!.get_lastName())
            newUser.CheckSet_phoneNum(SignUp_View!!.get_phoneNumber())
            newUser.CheckSet_birthDate(SignUp_View!!.get_birthDate())
            newUser.CheckSet_gender(SignUp_View!!.get_gender())
            if (!fireStore.isNetworkAvailable(SignUp_View!!.baseContext))
                throw Exception("You can't sign up if you offline")
            this.signUp()
        } catch (e: Exception) {
            SignUp_View!!.ShowToast(e.message!!)
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////


/*
    fun UpdatePassword(NewPassword:String ,Oldpassword: String /*check*/)
    {
        if(User_Model!!.password != Oldpassword)
        {
            Toast.makeText(Current_Context, "Re-enter Old Password "
                    , Toast.LENGTH_SHORT).show()
            return
        }
        dataBaseInstance.collection("user")
                .whereEqualTo("userName", User_Model!!.userName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                // getting non empty documents ; here we should update our user to db :V
                                println("Found Document Data");
                                var userData = HashMap<String, Any>();
                                userData.put("password", NewPassword);

                                dataBaseInstance.collection("user").document(User_Model!!.userName).
                                        set(userData);
                                Toast.makeText(Current_Context, "Password is updated successfully"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun UpdateImage(NewImage: String,password: String)
    {
        if(User_Model!!.password != password)
        {
            Toast.makeText(Current_Context, "Wrong Password"
                    , Toast.LENGTH_SHORT).show()
            return
        }
        dataBaseInstance.collection("user")
                .whereEqualTo("userName", User_Model!!.userName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                // getting non empty documents ; here we should update our user to db :V
                                println("Found Document Data");
                                var userData = HashMap<String, Any>();
                                userData.put("image", NewImage);

                                dataBaseInstance.collection("user").document(User_Model!!.userName).
                                        set(userData);
                                Toast.makeText(Current_Context, "Image is updated successfully"
                                        , Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }
*/
/*
fun UpdateRate(RatedUserName:String,NewRate:Integer) {
    dataBaseInstance.collection("user")
            .whereEqualTo("userName", RatedUserName)
            .get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    println("Entered Complete Listener");
                    if (p0.isSuccessful) {
                        if (!p0.result.isEmpty) {
                            // getting non empty documents ; here we should update our user to db :V
                            println("Found Document Data");
                            var userData = HashMap<String, Any>();
                            var Rate = p0.result.documents[0].get("behav_rate").toString().toLong()
                            Rate = (Rate + NewRate.toLong())/5 //todo
                            userData.put("behav_rate",Rate);

                            dataBaseInstance.collection("user").document(RatedUserName).
                                    set(userData);
                            Toast.makeText(Current_Context, "Rate is updated successfully"
                                    , Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        // Task is not Successfull ,, should be throw an exception
                        println(p0.exception.toString());
                    }
                }
            })
}*/
}


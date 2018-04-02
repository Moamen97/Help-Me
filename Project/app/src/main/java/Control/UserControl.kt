package Control

import Common.mySelf
import FireBase.fireStore
import Model.postData.post
import Model.user
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.helpme.Authentication.SignIn
import com.helpme.Authentication.SignUp
import com.helpme.EditProfile.EditProfile
import com.helpme.Home.home
import java.util.*


class UserControl private constructor() {
    private var User_Model: user? = null
    private var SignIn_View: SignIn? = null
    private var SignUp_View: SignUp? = null
    private var Home_View: home? = null
    private var EditProfile_View: EditProfile? = null
    private var dataBaseInstance = fireStore.fireStoreHandler
    private var toasmsg: String = ""

    companion object {
        private var instance: UserControl? = null
        fun getInstance(SignIn_View: SignIn? = null, SignUp_View: SignUp? = null
                        , Home_View: home? = null, EditProfile_View: EditProfile? = null): UserControl {
            if (instance == null)
                instance = UserControl()
            instance!!.setcurrentview(SignIn_View, SignUp_View, Home_View, EditProfile_View)
            return instance!!
        }
    } //Singleton

    private fun setcurrentview(SignIn_View: SignIn?, SignUp_View: SignUp?, Home_View: home?,
                               EditProfile_View: EditProfile?) {
        this.SignIn_View = SignIn_View
        this.SignUp_View = SignUp_View
        this.Home_View = Home_View
        this.EditProfile_View = EditProfile_View
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
                                                User_Model!!.firstName + " " +
                                                User_Model!!.midName +
                                                " " + User_Model!!.lastName + " "
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

    fun signUp(newUser: user) {
        dataBaseInstance.collection("user")
                .whereEqualTo("userName", newUser.userName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (p0.result.isEmpty) {
                                // getting empty documents ; here we should push our user to db :V
                                println("No Document Data");
                                var userData = HashMap<String, Any>();
                                userData.put("userName", newUser.userName);
                                userData.put("image", newUser.imageID);
                                userData.put("password", newUser.password);
                                userData.put("eMail", newUser.email);
                                userData.put("firstName", newUser.firstName);
                                userData.put("midName", newUser.midName);
                                userData.put("lastName", newUser.lastName);
                                userData.put("gender", newUser.gender);
                                userData.put("phoneNum", newUser.phoneNum);
                                userData.put("behav_rate", newUser.behaveRate);
                                userData.put("birthDate", newUser.birthDate);
                                dataBaseInstance.collection("user").document(newUser.userName).set(userData);
                                toasmsg = "Sign up successfully"
                                SignUp_View!!.ShowToast(toasmsg)
                                SignUp_View!!.Signup()

                            } else {
                                // here i found someone has the same userName :V so i can't add this user
                                println(p0.result.documents[0].data.toString());
                                toasmsg = "Sign up is not success user name is already exist"
                                SignUp_View!!.ShowToast(toasmsg)
                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun UpdateUserInfo(NewEmail: String, NewMobile: String,NewImageID:String,NewPassword:String)
    {
        toasmsg = ""
        var newemail:String = NewEmail //To Config.
        if (User_Model!!.email != NewEmail) {
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
                                    newemail = User_Model!!.email
                                }
                                else {
                                    toasmsg += "Email Changed, "
                                }
                            }
                        }
                    })
        }
        if (NewMobile != User_Model!!.phoneNum) {
            toasmsg += "Phone Changed, "
        }
        if (NewImageID != User_Model!!.imageID) {
            toasmsg += "Image Changed, "
        }
        if (NewPassword != User_Model!!.password) {
            toasmsg += "Password Changed. "
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
                                userData.put("userName", User_Model!!.userName);
                                userData.put("image", NewImageID);
                                userData.put("password", NewPassword);
                                userData.put("eMail", newemail);
                                userData.put("firstName", User_Model!!.firstName);
                                userData.put("midName", User_Model!!.midName);
                                userData.put("lastName", User_Model!!.lastName);
                                userData.put("gender", User_Model!!.gender);
                                userData.put("phoneNum", NewMobile);
                                userData.put("behav_rate", User_Model!!.behaveRate);
                                userData.put("birthDate", User_Model!!.birthDate);
                                dataBaseInstance.collection("user").document(User_Model!!.userName).
                                        update(userData);
                                User_Model!!.email = newemail as String
                                User_Model!!.phoneNum = NewMobile
                                User_Model!!.imageID = NewImageID
                                User_Model!!.password = NewPassword
                                EditProfile_View!!.ShowToast(toasmsg)
                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun GetCurrentUserProfile():user
    {
        return  this.User_Model!!
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


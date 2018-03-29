package Control
/**
 * Created by Mohamed Aaziz on 25/03/2018.
 */

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import Common.Common
import com.helpme.home
import com.helpme.Authentication.SignIn
import com.helpme.Authentication.SignUp
import Model.user
import com.helpme.EditProfile


class UserControl private constructor() {
    private var User_Model: user? = null
    private var SignIn_View: SignIn? = null
    private var SignUp_View: SignUp? = null
    private var Home_View: home? = null
    private var EditProfile_View:EditProfile? =null
    private var dataBaseInstance = FirebaseFirestore.getInstance()
    private var toasmsg:String = ""


    companion object {
        private var instance : UserControl? = null

        fun  getInstance(SignIn_View: SignIn? = null , SignUp_View: SignUp? = null
                         , Home_View: home? = null,EditProfile_View: EditProfile? = null): UserControl {
            if (instance == null)
                instance = UserControl()
            instance!!.setcurrentview(SignIn_View,SignUp_View,Home_View,EditProfile_View)
            return instance!!
        }
    } //Singleton

    private fun setcurrentview(SignIn_View: SignIn?, SignUp_View: SignUp?, Home_View: home?,
                               EditProfile_View :EditProfile?)
    {
        this.SignIn_View = SignIn_View
        this.SignUp_View = SignUp_View
        this.Home_View = Home_View
        this.EditProfile_View = EditProfile_View
    }



    fun Login(userName: String, password: String) {
        dataBaseInstance.collection("user")
                .whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                println(p0.result.documents[0].data.toString())
                                if (p0.result.documents[0].get("password").equals(password)) {
                                    Common.currentUser = p0.result.documents[0].toObject(user::class.java);
                                    User_Model = Common.currentUser
                                    toasmsg = "Sign In Successfully " +
                                            Common.currentUser.getFirstName() + " " +
                                            Common.currentUser.getMidName() +
                                            " " + Common.currentUser.getLastName() + " "
                                    SignIn_View!!.ShowToast(toasmsg)
                                    SignIn_View!!.LogIn()
                                } else {
                                    toasmsg = "Wrong user name of password"
                                    SignIn_View!!.ShowToast(toasmsg)
                                }
                            } else {
                                println("No Document Data")
                                toasmsg = "Wrong user name of password"
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
                .whereEqualTo("userName", newUser.getUserName())
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
                                userData.put("image", newUser.image);
                                userData.put("password", newUser.password);
                                userData.put("eMail", newUser.geteMail());
                                userData.put("firstName", newUser.firstName);
                                userData.put("midName", newUser.midName);
                                userData.put("lastName", newUser.lastName);
                                userData.put("gender", newUser.gender);
                                userData.put("phoneNum", newUser.phoneNum);
                                userData.put("behav_rate", newUser.behav_rate);
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

    // todo update values in current model
    fun UpdateUserInfo(NewUserName: String?,NewEmail: String?,NewMobile:String?,NewBirthdate:String?)
    {
        //config of inputs//////////////////////////////////////
        var newusername = NewUserName
        var newemail = NewEmail
        var newbirthdate = NewBirthdate
        var newmobile = NewMobile
        if (NewBirthdate == null)
        {
            newbirthdate = User_Model!!.birthDate
        }
        if (NewUserName == null)
        {
            newusername = User_Model!!.userName
        }
        if (NewEmail == null)
        {
            newemail = User_Model!!.geteMail()
        }
        if (NewMobile == null)
        {
            newmobile = User_Model!!.phoneNum
        }
        ////////////////////////////////////////////////////
        toasmsg = ""
         if(User_Model!!.userName != newusername)
        {
            // checks if the new user name exists
            dataBaseInstance.collection("user")
                    .whereEqualTo("userName", newusername)
                    .get()
                    .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                        override fun onComplete(p0: Task<QuerySnapshot>) {
                            println("Entered Complete Listener");
                            if (p0.isSuccessful) {
                                if (!p0.result.isEmpty){
                                        toasmsg = "UserName Error, "
                                    newusername = User_Model!!.userName
                                }
                            }
                            else
                            {
                                toasmsg = "UserName Changed, "
                            }
                        }
                    })
        }
        if(User_Model!!.geteMail() != newemail)
        {
            // checks if the new Email exists
            dataBaseInstance.collection("user")
                    .whereEqualTo("eMail", newemail)
                    .get()
                    .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                        override fun onComplete(p0: Task<QuerySnapshot>) {
                            println("Entered Complete Listener");
                            if (p0.isSuccessful) {
                                if (!p0.result.isEmpty){
                                    toasmsg += "Email Error, "
                                    newemail = User_Model!!.geteMail()
                                }
                            }
                            else
                            {
                                toasmsg += "Email Changed, "
                            }
                        }
                    })
        }
        if (newmobile != User_Model!!.phoneNum)
        {
            toasmsg += "Phone Changed, "
        }
        if (newbirthdate != User_Model!!.birthDate)
        {
            toasmsg += "Bdate Changed"
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
                                userData.put("userName", newusername!!);
                                userData.put("eMail", newemail!!);
                                userData.put("phoneNum", newmobile!!);
                                userData.put("birthDate", newbirthdate!!);
                                dataBaseInstance.collection("user").document(User_Model!!.userName).
                                        set(userData);
                                User_Model!!.userName = newusername
                                User_Model!!.seteMail(newemail)
                                User_Model!!.phoneNum = newmobile
                                User_Model!!.birthDate = newbirthdate
                                EditProfile_View!!.ShowToast(toasmsg)
                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }
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


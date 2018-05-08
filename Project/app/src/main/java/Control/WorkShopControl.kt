package Control

import Common.mySelf
import Model.Workshop
import Model.postData.post
import Model.user
import Utility.Utility
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.google.android.gms.tasks.*
import com.google.firebase.firestore.QuerySnapshot
import com.helpme.EditProfile.AddWorkShop
import com.helpme.EditProfile.MyProfile
import com.helpme.Home.home
import com.helpme.LoadingActivity
import com.helpme.UserProfile
import java.util.*
import kotlin.collections.ArrayList

class WorkShopControl private constructor() {
    private var AddWShop_View: AddWorkShop? = null
    private var home_View: home? = null
    private var myprofile:MyProfile?=null
    private var dataBaseInstance = Utility.fireStoreHandler
    private var PostController = PostControl.getInstance()
    var MyWorkShop: Workshop? = null
    var UWorkShop: Workshop? = null
    var USER:user? = null


    companion object {
        private var instance: WorkShopControl? = null
        fun getInstance(AddWShop: AddWorkShop?,myProfile: MyProfile?=null,hv:home?=null): WorkShopControl {
            if (instance == null)
                instance = WorkShopControl()
            instance!!.setcurrentview(AddWShop,myProfile,hv)
            return instance!!
        }
    } //Singleton

    private fun setcurrentview(AddWShop: AddWorkShop?,myProfile: MyProfile?,hv:home?=null) {
        this.AddWShop_View = AddWShop
        this.home_View=hv
        if(myProfile!=null)
        {
            this.myprofile = myProfile
        }
    }


    fun AddWorkShop(NewWS: Workshop) {
        dataBaseInstance.collection("WorkShop")
                .whereEqualTo("ID", NewWS.workshopid)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (p0.result.isEmpty) {
                                // getting empty documents ; here we should push our ws to db :V
                                println("No Document Data");
                                var wshopData = HashMap<String, Any>();
                                wshopData.put("Owner", NewWS.OwnerName);
                                wshopData.put("OwnerFullName", NewWS.OwnerFullName)
                                wshopData.put("Name", NewWS.workshopName);
                                wshopData.put("Location", NewWS.location);
                                wshopData.put("Profession", NewWS.profession);
                                wshopData.put("Phone", NewWS.workshopPhoneNum);
                                wshopData.put("ID", NewWS.workshopid);
                                var task = dataBaseInstance.collection("WorkShop").document(NewWS.workshopid)
                                        .set(wshopData)
                                AddWShop_View!!.ShowToast("Done")
                                mySelf.CheckSet_isProfistional(true)
                                mySelf.uploadMyself()
                                MyWorkShop = NewWS
                                try {
                                    myprofile!!.hidebu()
                                }

                                catch (e:Exception){}
                                var NewPost = post(mySelf.get_firstName() + " " + mySelf.get_midName() + " " + mySelf.get_lastName(), NewWS.profession, ArrayList(), null
                                        , mySelf.get_userName(), -1, 0, NewWS.location, NewWS.workshopid + "\n" + NewWS.workshopName
                                        , NewWS.workshopid)

                                PostController.addPost(NewPost)
                            } else {
                                // here i found someone has the same userName :V so i can't add this user
                                println(p0.result.documents[0].data.toString());
                                AddWShop_View!!.ShowToast("Invalid ID")
                            }
                        }
                    }
                })
    }

    fun GetWorkShopByusername(uname: String) {
        dataBaseInstance.collection("WorkShop")
                .whereEqualTo("Owner", uname)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                println(p0.result.documents[0].data.toString());
                                MyWorkShop = Workshop(p0.result.documents[0].get("Location").toString(),
                                        p0.result.documents[0].get("Name").toString(),
                                        p0.result.documents[0].get("Phone").toString(),
                                        p0.result.documents[0].get("Profession").toString(),
                                        p0.result.documents[0].get("Owner").toString(),
                                        p0.result.documents[0].get("ID").toString(),
                                        p0.result.documents[0].get("OwnerFullName").toString());

                            } else {
                                println("No Document Data");

                            }
                        }
                    }
                })
    }
    fun GetWorkShopByID(ID: String) {
        dataBaseInstance.collection("WorkShop")
                .whereEqualTo("ID", ID)
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                USER = user()
                                try {
                                    USER!!.CheckSet_userName(p0.result.documents[0].get("Owner").toString())
                                    USER!!.downloadProfileImage()
                                }catch (e:Exception){}
                                println(p0.result.documents[0].data.toString());
                                UWorkShop = Workshop(p0.result.documents[0].get("Location").toString(),
                                        p0.result.documents[0].get("Name").toString(),
                                        p0.result.documents[0].get("Phone").toString(),
                                        p0.result.documents[0].get("Profession").toString(),
                                        p0.result.documents[0].get("Owner").toString(),
                                        p0.result.documents[0].get("ID").toString(),
                                        p0.result.documents[0].get("OwnerFullName").toString());

                                var UserController:UserControl = UserControl.getInstance()
                                UserController.loadUser(USER!!)
                                LoadingActivity.progress.p=100.0
                                val intent = Intent(home_View, UserProfile::class.java)
                                home_View?.startActivity(intent)
                            } else {
                                println("No Document Data");

                            }
                        }
                    }
                })
    }


}





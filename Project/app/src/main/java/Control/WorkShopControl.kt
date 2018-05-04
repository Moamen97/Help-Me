package Control

import Common.mySelf
import Model.Workshop
import Model.postData.post
import Utility.Utility
import com.google.android.gms.tasks.*
import com.google.firebase.firestore.QuerySnapshot
import com.helpme.EditProfile.AddWorkShop
import java.util.*
import kotlin.collections.ArrayList

class WorkShopControl private constructor() {
    private var AddWShop_View: AddWorkShop? = null
    private var dataBaseInstance = Utility.fireStoreHandler
    private var PostController = PostControl.getInstance()
    companion object {
        private var instance: WorkShopControl? = null
        fun getInstance(AddWShop: AddWorkShop? = null): WorkShopControl {
            if (instance == null)
                instance = WorkShopControl()
            instance!!.setcurrentview(AddWShop)
            return instance!!
        }
    } //Singleton

    private fun setcurrentview(AddWShop: AddWorkShop?) {
        this.AddWShop_View = AddWShop_View
    }


    fun AddWorkShop(NewWS: Workshop) {
        var wshopData = HashMap<String, Any>();
        wshopData.put("Owner", NewWS.OwnerName);
        wshopData.put("Name", NewWS.workshopName);
        wshopData.put("Location", NewWS.location);
        wshopData.put("Profession", NewWS.profession);
        wshopData.put("Phone", NewWS.workshopPhoneNum);
        dataBaseInstance.collection("WorkShop").add(wshopData);
        var NewPost = post(mySelf.get_firstName()+" "+mySelf.get_midName()+" "+mySelf.get_lastName(),NewWS.profession, ArrayList(),""
        ,mySelf.get_userName(),-1,0,NewWS.location,NewWS.workshopName)
        PostController.addPost(NewPost)
    }

}


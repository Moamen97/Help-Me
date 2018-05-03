package Control

import Model.Workshop
import Utility.Utility
import com.google.android.gms.tasks.*
import com.google.firebase.firestore.QuerySnapshot
import com.helpme.EditProfile.AddWorkShop
import java.util.*

class WorkShopControl private constructor() {
    private var AddWShop_View: AddWorkShop? = null
    private var dataBaseInstance = Utility.fireStoreHandler
    private var toasmsg: String = ""

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
         }

}


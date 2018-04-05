package Model

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.helpme.R

/**
 * Created by Lenovo on 3/27/2018.
 */
class workshopViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    var WorkShopName: EditText? = null
    var WorkShopLocation: EditText? = null
    var WorkShopPhoneNumber: EditText? = null
    var WorkShopWorkingHours: EditText? = null
    var addWorkShop: FloatingActionButton? = null

    // Ta7ya Masr :V
    init {
        WorkShopName = view.findViewById(R.id.WorkShopNameEditText)
        WorkShopLocation = view.findViewById(R.id.WorkshopLocationEditText)
        WorkShopPhoneNumber = view.findViewById(R.id.WorkshopPhoneNumberEditText)
        WorkShopWorkingHours = view.findViewById(R.id.WorkshopWorkingHoursEditText)
        addWorkShop = view.findViewById(R.id.fab)
    }

}
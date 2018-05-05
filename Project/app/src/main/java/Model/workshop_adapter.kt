package Model

import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpme.R

/**
 * Created by Lenovo on 3/27/2018.
 */
class workshopAdapter(var mContext: Context, var workshopList: ArrayList<Workshop>,var listener: workshopListener) : RecyclerView.Adapter<workshopViewHolder>() {


    override fun onBindViewHolder(holder: workshopViewHolder, position: Int) {
        var workshopToBind: Workshop = workshopList[position];

        holder.WorkShopName?.setText(workshopToBind.workshopName);
        holder.WorkShopLocation?.setText(workshopToBind.location);
        holder.WorkShopPhoneNumber?.setText(workshopToBind.workshopPhoneNum);
        holder.addWorkShop?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                listener.onClicK(position)
            }
        })
        // dh by3mel load ll post cover using Glide library gamed f45
        //Glide.with(mContext).load(workshopToBind.postImage).into(holder?.postImage);
    }


    override fun getItemCount(): Int {
        return workshopList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): workshopViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.workshop_card, parent, false)
        return workshopViewHolder(itemView)
    }

}
package Model.postData

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.helpme.R
import kotlinx.android.synthetic.main.post_card.view.*

class postViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    var postname: TextView? = null
    var postImage: ImageView? = null
    var postType: ImageView? = null
    var showCommentsButton: Button? = null
    var postOwnerImage: ImageView? = null
    var postOwnerFName: TextView? = null
    var postlocation: TextView? = null

    // Ta7ya Masr :V
    init {
        postname = view.findViewById(R.id.post_content)
        postImage = view.findViewById(R.id.post_image)
        postType = view.findViewById(R.id.post_type)
        showCommentsButton = view.findViewById(R.id.showComments)
        postOwnerImage = view.findViewById(R.id.postOwnerImage)
        postOwnerFName = view.findViewById(R.id.postOwner)
        postlocation = view.findViewById(R.id.postlocation)
    }

}
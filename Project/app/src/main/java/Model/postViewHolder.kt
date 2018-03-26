package Model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.helpme.R
import kotlinx.android.synthetic.main.post_card.view.*

class postViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    var postContent: TextView? = null
    var postImage: ImageView? = null
    var postType: ImageView? = null

    // Ta7ya Masr :V
    init {
        postContent = view.findViewById(R.id.post_content)
        postImage = view.findViewById(R.id.post_image)
        postType = view.findViewById(R.id.post_type)
    }
}
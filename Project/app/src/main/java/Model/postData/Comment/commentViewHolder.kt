package Model.postData.Comment

import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.helpme.Comment.commentAdapterListener

import com.helpme.R

class commentViewHolder(var view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

    var from: TextView? = null
    var subject: TextView? = null
    var message: TextView? = null
    var iconText: TextView? = null
    var timestamp: TextView? = null
    var iconImp: ImageView? = null
    var imgProfile: ImageView? = null
    var messageContainer: LinearLayout? = null
    var iconContainer: RelativeLayout? = null
    var iconBack: RelativeLayout? = null
    var iconFront: RelativeLayout? = null
    var listener: commentAdapterListener? = null
    var moodSad: ImageView? = null

    init {
        from = view.findViewById(R.id.from)
        subject = view.findViewById(R.id.txt_primary)
        iconText = view.findViewById(R.id.icon_text)
        timestamp = view.findViewById(R.id.timestamp)
        iconBack = view.findViewById(R.id.icon_back)
        iconFront = view.findViewById(R.id.icon_front)
        iconImp = view.findViewById(R.id.icon_star)
        imgProfile = view.findViewById(R.id.icon_profile)
        messageContainer = view.findViewById(R.id.message_container)
        iconContainer = view.findViewById(R.id.icon_container)
        moodSad = view.findViewById(R.id.icon_bad_mood)
        view.setOnLongClickListener(this)
    }

    override fun onLongClick(p0: View?): Boolean {
        listener?.onRowLongClicked(adapterPosition)
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        return true
    }

}
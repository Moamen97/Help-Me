package Model.postData

import android.support.design.widget.Snackbar;
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.helpme.R

class postAdapter(var mContext: Context, var postList: ArrayList<post>, var postType: Int) : RecyclerView.Adapter<postViewHolder>() {

    override fun getItemCount(): Int {
        return postList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): postViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.post_card, parent, false)
        return postViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: postViewHolder?, position: Int) {
        var postToBind: post = postList[position];
        holder?.postContent?.text = postToBind.postContent;
        holder?.postType?.setImageResource(postType);
        holder?.itemView?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Snackbar.make(holder.view, postToBind.postContent, Snackbar.LENGTH_SHORT).show()
            }
        })
        holder?.postImage?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Snackbar.make(holder.view, postToBind.postContent, Snackbar.LENGTH_SHORT).show()
            }
        })

        // dh by3mel load ll post cover using Glide library gamed f45
        Glide.with(mContext).load(postToBind.postImage).into(holder?.postImage);
    }

}
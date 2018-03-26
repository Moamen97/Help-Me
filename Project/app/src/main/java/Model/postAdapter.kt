package Model

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.helpme.R

class postAdapter(var mContext: Context, var postList: ArrayList<post>) : RecyclerView.Adapter<Model.postViewHolder>() {


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
        // dh by3mel load ll post cover using Glide library gamed f45
        Glide.with(mContext).load(postToBind.postImage).into(holder?.postImage);
    }

}
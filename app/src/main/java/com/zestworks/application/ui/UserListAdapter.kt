package com.zestworks.application.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zestworks.application.R
import com.zestworks.application.model.Data

class UserListAdapter(
    private val users: List<Data>,
    private val adapterCallbacks: AdapterClickCallback
):RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_info,parent,false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = users[position]
        Picasso.get().load(user.image).into(holder.userImage)
        val userName = user.firstName+" "+user.lastName.trim()
        holder.userName.text = userName
        holder.userId = user.id
    }


    inner class UserListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val userImage: ImageView = itemView.findViewById(R.id.user_image)
        val userName: TextView = itemView.findViewById(R.id.user_name_text)
        var userId : Int = 0
        private val parentContainer : CardView = itemView.findViewById(R.id.parentContainer)

        init {
            parentContainer.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            adapterCallbacks.onUserItemClicked(userId)
        }
    }
}


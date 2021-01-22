package com.damars.damarbuildsubmissionke3.settingadapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.damars.damarbuildsubmissionke3.CustomizeOnItemsClickListen
import com.damars.damarbuildsubmissionke3.DetailOfUsers
import com.damars.damarbuildsubmissionke3.R
import com.damars.damarbuildsubmissionke3.linksource.ItsFavorite
import kotlinx.android.synthetic.main.implicit_card_user.view.*
import java.util.ArrayList

class MainFavoriteAdapter(private val onActivity: Activity) : RecyclerView.Adapter<MainFavoriteAdapter.OnNotToVwHolder>() {
    var lstItFvRite = ArrayList<ItsFavorite>()
        set(lstItFvRite) {
            if(lstItFvRite.size > 0){
                this.lstItFvRite.clear()
            }
            this.lstItFvRite.addAll(lstItFvRite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(itsParented: ViewGroup, viewType: Int): MainFavoriteAdapter.OnNotToVwHolder {
        val view = LayoutInflater.from(itsParented.context).inflate(R.layout.implicit_card_user, itsParented, false)
        return OnNotToVwHolder(view)
    }

    override fun onBindViewHolder(hld: MainFavoriteAdapter.OnNotToVwHolder, position: Int) {
        hld.onBind(lstItFvRite[position])
    }

    inner class OnNotToVwHolder(itmVw: View) : RecyclerView.ViewHolder(itmVw) {
        fun onBind(itsFv: ItsFavorite) {
            with(itemView){
                Glide.with(itemView.context)
                        .load(itsFv.itsAvatar)
                        .apply(RequestOptions().override(200,200))
                        .into(itemView.card_imgOnsAvatar)
                card_onsUsername.text = itsFv.itsUsername
                card_onsName.text = itsFv.itsName
                card_onsLocation.text = itsFv.itsLocation.toString()
                itemView.setOnClickListener(
                        CustomizeOnItemsClickListen(
                                adapterPosition,
                                object : CustomizeOnItemsClickListen.OnItemClicksCallback{
                                    override fun onsItmIsClicks(view: View, position: Int) {
                                        val onMvInt = Intent(onActivity, DetailOfUsers::class.java)
                                        onMvInt.putExtra(DetailOfUsers.MountOnPos,position)
                                        onMvInt.putExtra(DetailOfUsers.MountOnNot,itsFv)
                                        onActivity.startActivity(onMvInt)
                                    }
                                }
                        )
                )
            }
        }
    }
    override fun getItemCount(): Int = this.lstItFvRite.size
}
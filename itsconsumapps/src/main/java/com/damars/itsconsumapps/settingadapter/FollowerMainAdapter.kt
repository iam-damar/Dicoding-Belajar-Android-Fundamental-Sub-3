package com.damars.itsconsumapps.settingadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.damars.itsconsumapps.R
import com.damars.itsconsumapps.linksource.ItsDataUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.implicit_card_user.view.*

var lstOnsFollowerFill = ArrayList<ItsDataUser>()
class FollowerMainAdapter(lstUsr: ArrayList<ItsDataUser>) : RecyclerView.Adapter<FollowerMainAdapter.OnLstVwHolder>() {
    init{
        lstOnsFollowerFill = lstUsr }

    private var onItmClicksCallBck: OnItemClickCallback? = null

    fun setItmClicksCallBck(onItmClicksCallBck: OnItemClickCallback) {
        this.onItmClicksCallBck = onItmClicksCallBck
    }

    interface OnItemClickCallback {
        fun onItmClicks(itsDataUser: ItsDataUser)
    }

    inner class OnLstVwHolder(itmVw: View) : RecyclerView.ViewHolder(itmVw) {
        var imaAvaTr: CircleImageView = itmVw.card_imgOnsAvatar
        var cardFmUsername: TextView = itmVw.card_onsUsername
        var cardFmName: TextView = itmVw.card_onsName
        var cardFmLocation: TextView = itmVw.card_onsLocation
    }
    override fun onCreateViewHolder(vGroups: ViewGroup, i: Int): FollowerMainAdapter.OnLstVwHolder {
        val vw: View = LayoutInflater.from(vGroups.context).inflate(R.layout.implicit_card_user, vGroups, false)
        val usrHld = OnLstVwHolder(vw)
        vConTxt = vGroups.context
        return usrHld
    }

    override fun onBindViewHolder(hld: OnLstVwHolder, position: Int) {
        val onDt = lstOnsFollowerFill[position]
        Glide.with(hld.itemView.context)
                .load(onDt.itsAvatar)
                .apply(RequestOptions().override(200,200))
                .into(hld.imaAvaTr)
        hld.cardFmUsername.text = onDt.itsUsername
        hld.cardFmName.text = onDt.itsName
        hld.cardFmLocation.text = onDt.itsLocation
        hld.itemView.setOnClickListener {
        }
    }
    override fun getItemCount(): Int = lstOnsFollowerFill.size
}
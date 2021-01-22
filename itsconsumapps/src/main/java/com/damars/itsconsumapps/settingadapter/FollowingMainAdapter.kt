package com.damars.itsconsumapps.settingadapter

import android.content.Context
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

lateinit var vConTxt: Context
var lstOnsFollowingsFill = ArrayList<ItsDataUser>()
class FollowingMainAdapter(lstUsr: ArrayList<ItsDataUser>) : RecyclerView.Adapter<FollowingMainAdapter.OnLstVwHolder>() {
    init {
        lstOnsFollowingsFill = lstUsr }
    override fun onCreateViewHolder(vGroups: ViewGroup, i: Int): FollowingMainAdapter.OnLstVwHolder {
        val mainVw: View = LayoutInflater.from(vGroups.context).inflate(R.layout.implicit_card_user, vGroups, false)
        val usrHld = OnLstVwHolder(mainVw)
        vConTxt = vGroups.context
        return usrHld
    }

    override fun onBindViewHolder(hld: OnLstVwHolder, position: Int) {
        val onDt = lstOnsFollowingsFill[position]
        Glide.with(hld.itemView.context)
            .load(onDt.itsAvatar)
            .apply(RequestOptions().override(200,200))
            .into(hld.imgOnsAvaTr)
        hld.onsUsername.text = onDt.itsUsername
        hld.onsName.text = onDt.itsName
        hld.onsLocateS.text = onDt.itsLocation
        hld.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int = lstOnsFollowingsFill.size

    inner class OnLstVwHolder(itmVw: View) : RecyclerView.ViewHolder(itmVw) {
        var imgOnsAvaTr: CircleImageView = itmVw.card_imgOnsAvatar
        var onsUsername: TextView = itmVw.card_onsUsername
        var onsName: TextView = itmVw.card_onsName
        var onsLocateS: TextView = itmVw.card_onsLocation
    }

    private var onItmClicksCallBck: OnItemIsClicksCallBack? = null

    fun setIsItmClicksCallBck(itemIsClicksCallBck: OnItemIsClicksCallBack) {
        this.onItmClicksCallBck = itemIsClicksCallBck
    }

    interface OnItemIsClicksCallBack {
        fun onItmIsClicks(itsDataUser: ItsDataUser)
    }
}
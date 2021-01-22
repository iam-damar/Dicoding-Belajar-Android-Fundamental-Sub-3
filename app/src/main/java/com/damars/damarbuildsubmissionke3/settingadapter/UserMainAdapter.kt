package com.damars.damarbuildsubmissionke3.settingadapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.damars.damarbuildsubmissionke3.DetailOfUsers
import com.damars.damarbuildsubmissionke3.R
import com.damars.damarbuildsubmissionke3.linksource.ItsDataUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.implicit_card_user.view.*
import java.util.*
import kotlin.collections.ArrayList

var lstOnUsrFill = ArrayList<ItsDataUser>()
lateinit var vConTxt: Context

@Suppress("UNCHECKED_CAST")
class UserMainAdapter(private var lstDt: ArrayList<ItsDataUser>) : RecyclerView.Adapter<UserMainAdapter.OnLstUsrVwHolder>(), Filterable {

    init {
        lstOnUsrFill = lstDt }
    private var onItemItsClicksCallback: OnItemItsClickCallback? = null

    fun setOnItsItemsClickCallback(onItemItsClicksCallback: OnItemItsClickCallback){
        this.onItemItsClicksCallback = onItemItsClicksCallback
    }

    override fun onBindViewHolder(onHld: OnLstUsrVwHolder, pst: Int) {
        val itsDtSrc = lstOnUsrFill[pst]
        Glide.with(onHld.itemView.context)
            .load(itsDtSrc.itsAvatar)
            .apply(RequestOptions().override(200, 200))
            .into(onHld.imgOnsAvatar)
        onHld.onsUsername.text = itsDtSrc.itsUsername
        onHld.onsName.text = itsDtSrc.itsName
        onHld.onsLocation.text = itsDtSrc.itsLocation
        onHld.itemView.setOnClickListener {
            val itsDtOfUsr = ItsDataUser(
                itsDtSrc.itsUsername,
                itsDtSrc.itsName,
                itsDtSrc.itsAvatar,
                itsDtSrc.itsLocation,
                itsDtSrc.itsCompany,
                itsDtSrc.itsRepository,
                itsDtSrc.itsFollower,
                itsDtSrc.itsFollowing,
                itsDtSrc.itsFavor
            )
            val intOfDtl = Intent(vConTxt, DetailOfUsers::class.java)
            intOfDtl.putExtra(DetailOfUsers.MountOnData, itsDtOfUsr)
            intOfDtl.putExtra(DetailOfUsers.MountOnFv, itsDtOfUsr)
            vConTxt.startActivity(intOfDtl)
        }
    }

    inner class OnLstUsrVwHolder(itmVw: View) : RecyclerView.ViewHolder(itmVw) {
        var imgOnsAvatar: CircleImageView = itmVw.card_imgOnsAvatar
        var onsUsername: TextView = itmVw.card_onsUsername
        var onsName: TextView = itmVw.card_onsName
        var onsLocation: TextView = itmVw.card_onsLocation
    }
    interface OnItemItsClickCallback {fun onItemItsClicked(itsDataUser: ItsDataUser)}

    override fun onCreateViewHolder(vGroups: ViewGroup, i: Int): OnLstUsrVwHolder {
        val onMainV: View = LayoutInflater.from(vGroups.context).inflate(R.layout.implicit_card_user, vGroups, false)
        val usrHld = OnLstUsrVwHolder(onMainV)
        vConTxt = vGroups.context
        return usrHld
    }

    override fun getItemCount(): Int = lstOnUsrFill.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val schOnUsr = constraint.toString()
                lstOnUsrFill = if(schOnUsr.isEmpty()) {
                    lstDt
                } else {
                    val listOfResult = ArrayList<ItsDataUser>()
                    for(onRows in lstOnUsrFill) {
                        if((onRows.itsUsername.toString().toLowerCase(Locale.ROOT).contains(schOnUsr.toLowerCase(Locale.ROOT)))) {
                            listOfResult.add(
                                ItsDataUser(
                                    onRows.itsUsername,
                                    onRows.itsName,
                                    onRows.itsAvatar,
                                    onRows.itsLocation,
                                    onRows.itsCompany,
                                    onRows.itsRepository,
                                    onRows.itsFollower,
                                    onRows.itsFollowing
                                )
                            )
                        }
                    }
                    listOfResult
                }
                val itsFlTrOfResult = FilterResults()
                itsFlTrOfResult.values = lstOnUsrFill
                return itsFlTrOfResult
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                lstOnUsrFill = results.values as ArrayList<ItsDataUser>
                notifyDataSetChanged()
            }

        }
    }
}
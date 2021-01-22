package com.damars.itsconsumapps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.damars.itsconsumapps.linksource.ItsDataUser
import com.damars.itsconsumapps.linksource.ItsFavorite
import com.damars.itsconsumapps.settingadapter.FollowerMainAdapter
import com.damars.itsconsumapps.settingadapter.lstOnsFollowerFill
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_on_followers.*
import org.json.JSONArray
import org.json.JSONObject

class FragmentOnFollowers : Fragment() {

    companion object {
        private val iTagCls = FragmentOnFollowers::class.java.simpleName
        const val MountOnData = "mount_data"
        const val MountOnNot = "mount_note"
    }

    private var lstOnUsr: ArrayList<ItsDataUser> = ArrayList()
    private lateinit var isAdPtr: FollowerMainAdapter
    private var itsFv: ItsFavorite? = null
    private lateinit var dtFavS: ItsFavorite
    private lateinit var dtUsr: ItsDataUser

    override fun onCreateView(onInfl: LayoutInflater, cnt: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onInfl.inflate(R.layout.fragment_on_followers, cnt, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAdPtr = FollowerMainAdapter(lstOnUsr)
        lstOnUsr.clear()
        itsFv = activity!!.intent.getParcelableExtra(DetailOfUsers.MountOnNot)
        if(itsFv != null){
            dtFavS = activity!!.intent.getParcelableExtra<ItsFavorite>(MountOnNot) as ItsFavorite
            onGetFollowerOnUsr(dtFavS.itsUsername.toString())
        } else {
            dtUsr = activity!!.intent.getParcelableExtra<ItsDataUser>(MountOnData) as ItsDataUser
            onGetFollowerOnUsr(dtUsr.itsUsername.toString())
        }
    }

    private fun onGetFollowerOnUsr(id: String) {
        onBarProgressToFollower.visibility = View.VISIBLE
        val ownCli = AsyncHttpClient()
        ownCli.addHeader("User-Agent", "request")
        ownCli.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        ownCli.get("https://api.github.com/users/$id/followers", object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBd: ByteArray) {
                onBarProgressToFollower.visibility = View.INVISIBLE
                val onRl = String(responseBd)
                Log.d(iTagCls, onRl)
                try{
                    val arrayJs = JSONArray(onRl)
                    for(i in 0 until arrayJs.length()){
                        val onJsObc = arrayJs.getJSONObject(i)
                        val username: String = onJsObc.getString("login")
                        usrGetOnDtl(username) }
                }catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace() }
            }
            override fun onFailure(codeStats: Int, headers: Array<Header>, responseBd: ByteArray, error: Throwable) {
                onBarProgressToFollower.visibility = View.INVISIBLE
                val mErr = when (codeStats) {
                    401 -> "$codeStats : ops.."
                    else -> "$codeStats : $(error.message)"
                }
                Toast.makeText(activity, mErr, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun usrGetOnDtl(id: String) {
        onBarProgressToFollower.visibility = View.VISIBLE
        val ownCl = AsyncHttpClient()
        ownCl.addHeader("User-Agent", "request")
        ownCl.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        ownCl.get("https://api.github.com/users/$id", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                onBarProgressToFollower.visibility = View.INVISIBLE
                val onRl = String(responseBody)
                Log.d(iTagCls, onRl)
                try {
                    val onJsObc = JSONObject(onRl)
                    val dlUsername: String = onJsObc.getString("login").toString()
                    val dlName: String = onJsObc.getString("name").toString()
                    val dlAvaTr: String = onJsObc.getString("avatar_url").toString()
                    val dlCompany: String = onJsObc.getString("company").toString()
                    val dlLocate: String = onJsObc.getString("location").toString()
                    val dlRepo: String = onJsObc.getString("public_repos")
                    val dlFollower: String = onJsObc.getString("followers")
                    val dlFollowing: String = onJsObc.getString("following")
                    lstOnUsr.add(
                            ItsDataUser(
                                    dlUsername, dlName, dlAvaTr,
                                    dlLocate, dlCompany, dlRepo,
                                    dlFollower, dlFollowing)
                    )
                    rLstShow()
                }catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    e.printStackTrace() }
            }

            override fun onFailure(codeStats: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                onBarProgressToFollower.visibility = View.INVISIBLE
                val mErr = when(codeStats){
                    401 -> "$codeStats : Ops.."
                    else -> "$codeStats : $(error.message)"
                }
                Toast.makeText(activity, mErr, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun rLstShow() {
        viewRecycleFollower.layoutManager = LinearLayoutManager(activity)
        val lstDtAdPtr = FollowerMainAdapter(lstOnsFollowerFill)
        viewRecycleFollower.adapter = isAdPtr

        lstDtAdPtr.setItmClicksCallBck(object : FollowerMainAdapter.OnItemClickCallback{
            override fun onItmClicks(itsDataUser: ItsDataUser) {
            }
        })
    }
}
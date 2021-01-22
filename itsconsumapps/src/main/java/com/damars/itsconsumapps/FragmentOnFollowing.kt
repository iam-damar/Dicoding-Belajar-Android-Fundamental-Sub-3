package com.damars.itsconsumapps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.damars.itsconsumapps.linksource.ItsDataUser
import com.damars.itsconsumapps.linksource.ItsFavorite
import com.damars.itsconsumapps.settingadapter.FollowingMainAdapter
import com.damars.itsconsumapps.settingadapter.lstOnsFollowingsFill
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_on_following.*
import org.json.JSONArray
import org.json.JSONObject

class FragmentOnFollowing : Fragment() {
    companion object {
        private val iTagCls = FragmentOnFollowing::class.java.simpleName
    }

    private var lstOfsUsr: ArrayList<ItsDataUser> = ArrayList()
    private lateinit var isAdPt: FollowingMainAdapter
    private var tsFv: ItsFavorite? = null
    private lateinit var dtFavS: ItsFavorite
    private lateinit var dtUsr: ItsDataUser

    override fun onCreateView(onInfl: LayoutInflater, cnt: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onInfl.inflate(R.layout.fragment_on_following, cnt, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAdPt = FollowingMainAdapter(lstOfsUsr)
        lstOfsUsr.clear()

        tsFv = activity!!.intent.getParcelableExtra(DetailOfUsers.MountOnNot)
        if(tsFv != null) {
            dtFavS = activity!!.intent.getParcelableExtra<ItsFavorite>(FragmentOnFollowers.MountOnNot) as ItsFavorite
            onGetFollowingOnUsr(dtFavS.itsUsername.toString())
        } else {
            dtUsr = activity!!.intent.getParcelableExtra<ItsDataUser>(FragmentOnFollowers.MountOnData) as ItsDataUser
            onGetFollowingOnUsr(dtUsr.itsUsername.toString())
        }
    }

    private fun onGetFollowingOnUsr(id: String) {
        onBarProgressToFollowing.visibility = View.VISIBLE
        val ownCl = AsyncHttpClient()
        ownCl.addHeader("User-Agent", "request")
        ownCl.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        ownCl.get("https://api.github.com/users/$id/following", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                onBarProgressToFollowing.visibility = View.INVISIBLE
                val onRl = String(responseBody)
                Log.d(iTagCls, onRl)
                try {
                    val jsonArray = JSONArray(onRl)
                    for(i in 0 until jsonArray.length()){
                        val onJsObc = jsonArray.getJSONObject(i)
                        val username: String = onJsObc.getString("login")
                        usrGetOnDtl(username)
                    }
                }catch (e:Exception){
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(codeStats: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                onBarProgressToFollowing.visibility = View.INVISIBLE
                val mErr = when(codeStats) {
                    401 -> "$codeStats : Ops.."
                    else -> "$codeStats : $(error.message)"
                }
                Toast.makeText(activity, mErr, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun usrGetOnDtl(id: String) {
        onBarProgressToFollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        client.get("https://api.github.com/users/$id", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                onBarProgressToFollowing.visibility = View.INVISIBLE
                val onRl = String(responseBody)
                Log.d(iTagCls, onRl)
                try {
                    val onJsObc = JSONObject(onRl)
                    val dlUsername: String = onJsObc.getString("login").toString()
                    val dlName: String = onJsObc.getString("name").toString()
                    val dlAvaTr: String = onJsObc.getString("avatar_url").toString()
                    val dlCompany: String = onJsObc.getString("company").toString()
                    val dlLocate: String = onJsObc.getString("location").toString()
                    val dlRepos: String = onJsObc.getString("public_repos")
                    val dlFollower: String = onJsObc.getString("followers")
                    val dlFollowing: String = onJsObc.getString("following")
                    lstOfsUsr.add(
                            ItsDataUser(
                                    dlUsername, dlName, dlAvaTr,
                                    dlLocate, dlCompany, dlRepos,
                                    dlFollower, dlFollowing
                            )
                    )
                    rLstShow()
                }catch (e: java.lang.Exception){
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                onBarProgressToFollowing.visibility = View.INVISIBLE
                val mErr = when(statusCode) {
                    401 -> "$statusCode : ops.."
                    else -> "$statusCode : $(error.message)"
                }
                Toast.makeText(activity, mErr, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun rLstShow() {
        viewRecycleFollowing.layoutManager = LinearLayoutManager(activity)
        val lstDtAdPtr = FollowingMainAdapter(lstOnsFollowingsFill)

        viewRecycleFollowing.adapter = isAdPt
        lstDtAdPtr.setIsItmClicksCallBck(object : FollowingMainAdapter.OnItemIsClicksCallBack{
            override fun onItmIsClicks(itsDataUser: ItsDataUser) {
            }
        })
    }
}
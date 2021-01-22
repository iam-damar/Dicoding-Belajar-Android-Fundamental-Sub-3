package com.damars.damarbuildsubmissionke3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.damars.damarbuildsubmissionke3.linksource.ItsDataUser
import com.damars.damarbuildsubmissionke3.settingadapter.UserMainAdapter
import com.damars.damarbuildsubmissionke3.settingadapter.lstOnUsrFill
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var lstOnsUsr: ArrayList<ItsDataUser> = ArrayList()
    private lateinit var isAdPtr: UserMainAdapter

    companion object { private val iTagCls = MainActivity::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title ="List GitUser"

        isAdPtr = UserMainAdapter(lstOnsUsr)

        onRecyViewsConfig()
        onsSearchUsr()
        getIntoUsr()
    }

    private fun onsSearchUsr() {
        searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(onQry: String): Boolean {
                if(onQry.isEmpty()){
                    return true
                } else {
                    lstOnsUsr.clear()
                    getUserSearch(onQry)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun getUserSearch(id: String) {
        barProgressToMain.visibility = View.INVISIBLE
        val onsMainOwnCli = AsyncHttpClient()
        onsMainOwnCli.addHeader("User-Agent", "request")
        onsMainOwnCli.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        onsMainOwnCli.get("https://api.github.com/search/users?q=$id", object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBd: ByteArray) {
                barProgressToMain.visibility = View.INVISIBLE
                val onRl = String(responseBd)
                Log.d(iTagCls, onRl)
                try {
                    val jsonArray = JSONObject(onRl)
                    val itsItm = jsonArray.getJSONArray("items")
                    for(i in 0 until itsItm.length()) {
                        val onJsObc = itsItm.getJSONObject(i)
                        val username: String = onJsObc.getString("login")
                        getUserDetail(username)
                    }
                }catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show() }
            }

            override fun onFailure(codeStats: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                barProgressToMain.visibility = View.INVISIBLE
                val mErr = when(codeStats){
                    401 -> "$codeStats : Ops.."
                    else -> "$(error.message)"
                }
                Toast.makeText(this@MainActivity, mErr, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getIntoUsr(){
        barProgressToMain.visibility = View.VISIBLE
        val mainOwnCli = AsyncHttpClient()
        mainOwnCli.addHeader("User-Agent","request")
        mainOwnCli.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        mainOwnCli.get("https://api.github.com/users", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                barProgressToMain.visibility = View.INVISIBLE
                val onRl = String(responseBody)
                Log.d(iTagCls, onRl)
                try {
                    val arraysJs = JSONArray(onRl)
                    for(i in 0 until arraysJs.length()){
                        val onJsObc = arraysJs.getJSONObject(i)
                        val username: String = onJsObc.getString("login")
                        getUserDetail(username)
                    }
                }catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(codeStats: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                barProgressToMain.visibility = View.INVISIBLE
                val mErr = when(codeStats) {
                    401 -> "$codeStats : Ops.."
                    else -> "$codeStats : $(error.message)"
                }
                Toast.makeText(this@MainActivity, mErr, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun getUserDetail(id: String) {
        barProgressToMain.visibility = View.VISIBLE
        val ownCli = AsyncHttpClient()
        ownCli.addHeader("User-Agent", "request")
        ownCli.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        ownCli.get("https://api.github.com/users/$id", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                barProgressToMain.visibility = View.INVISIBLE
                val onRl = String(responseBody)
                Log.d(iTagCls, onRl)
                try {
                    val onJsObc = JSONObject(onRl)
                    val mainOnsUsername: String = onJsObc.getString("login").toString()
                    val mainOnsName: String = onJsObc.getString("name").toString()
                    val mainOnAvaTr: String = onJsObc.getString("avatar_url").toString()
                    val mainOnCompany: String = onJsObc.getString("company").toString()
                    val mainOnsLocate: String = onJsObc.getString("location").toString()
                    val mainOnsRepo: String = onJsObc.getString("public_repos")
                    val mainOnsFollower: String = onJsObc.getString("followers")
                    val mainOnsFollowing: String = onJsObc.getString("following")
                    lstOnsUsr.add(
                            ItsDataUser(
                                    mainOnsUsername, mainOnsName,
                                    mainOnAvaTr, mainOnsLocate,
                                    mainOnCompany, mainOnsRepo,
                                    mainOnsFollower, mainOnsFollowing)
                    )
                    showRecyclerList()
                }catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace() }
            }

            override fun onFailure(codeStats: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                barProgressToMain.visibility = View.INVISIBLE
                val mErrs = when(codeStats) {
                    401 -> "$codeStats : Ops"
                    else -> "$codeStats : $(error.message)"
                }
                Toast.makeText(this@MainActivity, mErrs, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun showRecyclerList() {
        mainRecOnView.layoutManager = LinearLayoutManager(this)
        val lstDtAdPtr = UserMainAdapter(lstOnUsrFill)
        mainRecOnView.adapter = isAdPtr
        lstDtAdPtr.setOnItsItemsClickCallback(object : UserMainAdapter.OnItemItsClickCallback{
            override fun onItemItsClicked(itsDataUser: ItsDataUser) {
                showSelectedUser(itsDataUser) }
        })
    }

    private fun showSelectedUser(itsDtUsr: ItsDataUser) {
        ItsDataUser(
                itsDtUsr.itsUsername,
                itsDtUsr.itsName,
                itsDtUsr.itsAvatar,
                itsDtUsr.itsLocation,
                itsDtUsr.itsCompany,
                itsDtUsr.itsRepository,
                itsDtUsr.itsFollower,
                itsDtUsr.itsFollowing
        )
        val mvIntDtl = Intent(this@MainActivity, DetailOfUsers:: class.java)
        mvIntDtl.putExtra(DetailOfUsers.MountOnData, itsDtUsr)

        this@MainActivity.startActivity(mvIntDtl)
        Toast.makeText(this, "${itsDtUsr.itsName}", Toast.LENGTH_SHORT).show()
    }

    private fun onRecyViewsConfig() {
        mainRecOnView.layoutManager = LinearLayoutManager(mainRecOnView.context)
        mainRecOnView.setHasFixedSize(true)
        mainRecOnView.addItemDecoration(
                DividerItemDecoration(mainRecOnView.context, DividerItemDecoration.VERTICAL)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.implicit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(isTem: MenuItem): Boolean {
        when(isTem.itemId){
            R.id.ActionOnFavor -> {
                val favorInt = Intent(this, MainPageOnFavorite::class.java)
                startActivity(favorInt)
            }
            R.id.ItsMe -> {
                val itsMe = Intent(this, PageOfMe::class.java)
                startActivity(itsMe)
            }
            R.id.setOnLanguage_change -> {
                val langInt = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(langInt)
            }
            R.id.setOnMainAlarm -> {
                val isAlmInt = Intent(this, SettingsNotify::class.java)
                startActivity(isAlmInt)
            }
        }
        return super.onOptionsItemSelected(isTem)
    }
}
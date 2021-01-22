package com.damars.itsconsumapps

import android.view.View

class CustomizeOnItemsClickListen(private val position: Int, private val onItemClicksCallBack: OnItemClicksCallback) : View.OnClickListener {
    override fun onClick(view: View) {
        onItemClicksCallBack.onsItmIsClicks(view, position)
    }
    interface OnItemClicksCallback {
        fun onsItmIsClicks(view: View, position: Int)
    }
}
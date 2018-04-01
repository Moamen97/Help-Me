package com.helpme.Comment

interface commentAdapterListener {
    fun onIconClicked(position: Int)

    fun onIconImportantClicked(position: Int)

    fun onMessageRowClicked(position: Int)

    fun onRowLongClicked(position: Int)
}

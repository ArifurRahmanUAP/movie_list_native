package com.arif.movielistnative.Utill.listener

import com.arif.movielistnative.dataBase.AppTable


interface DeleteListener {
    fun onDelete(appTable: AppTable)
}
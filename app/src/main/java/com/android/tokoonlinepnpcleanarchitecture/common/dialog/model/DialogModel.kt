package com.android.tokoonlinepnpcleanarchitecture.common.dialog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DialogModel(
    var title:String,
    var message:String,
    var img:Int
):Parcelable
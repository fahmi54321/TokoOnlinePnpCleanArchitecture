package com.android.tokoonlinepnpcleanarchitecture.common.dialog

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.android.tokoonlinepnpcleanarchitecture.common.dialog.model.DialogModel

class ShowDialog(
    private val fragmentManager: FragmentManager,
) {

    private var dialogNavigator: DialogNavigator
    private var dialogModel: DialogModel
    private var bundle: Bundle

    init {
        dialogNavigator = DialogNavigator()
        dialogModel = DialogModel("", "", 0)
        bundle = Bundle()
    }

    fun showDialogSukses(
        title: String,
        message: String,
        img: Int
    ) {
        dialogModel.title = title
        dialogModel.message = message
        dialogModel.img = img
        bundle.putParcelable(DialogNavigator.KEY_SUKSES_FROM_LOGIN, dialogModel)
        dialogNavigator.arguments = bundle
        dialogNavigator.show(fragmentManager, dialogNavigator.tag)
    }

    fun showDialogError(
        title: String,
        message: String,
        img: Int
    ) {
        dialogModel.title = title
        dialogModel.message = message
        dialogModel.img = img
        bundle.putParcelable(DialogNavigator.KEY_ERROR_FROM_LOGIN, dialogModel)
        dialogNavigator.arguments = bundle
        dialogNavigator.show(fragmentManager, dialogNavigator.tag)
    }

    fun showDialogSuksesRegister(title: String, message: String, img: Int) {
        dialogModel.title = title
        dialogModel.message = message
        dialogModel.img = img
        bundle.putParcelable(DialogNavigator.KEY_SUKSES_FROM_REGISTER, dialogModel)
        dialogNavigator.arguments = bundle
        dialogNavigator.show(fragmentManager, dialogNavigator.tag)
    }

    fun showDialogErrorRegister(title: String, message: String, img: Int) {
        dialogModel.title = title
        dialogModel.message = message
        dialogModel.img = img
        bundle.putParcelable(DialogNavigator.KEY_ERROR_FROM_REGISTER, dialogModel)
        dialogNavigator.arguments = bundle
        dialogNavigator.show(fragmentManager, dialogNavigator.tag)
    }

}
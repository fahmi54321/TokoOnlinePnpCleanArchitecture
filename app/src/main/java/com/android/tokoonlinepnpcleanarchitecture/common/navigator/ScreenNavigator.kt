package com.android.tokoonlinepnpcleanarchitecture.common.navigator

import android.content.Context
import android.content.Intent
import com.android.tokoonlinepnpcleanarchitecture.HomeActivity
import com.android.tokoonlinepnpcleanarchitecture.login.LoginActivity
import com.android.tokoonlinepnpcleanarchitecture.register.RegisterActivity

class ScreenNavigator(
    private val context: Context
) {

    fun toRegisterActivity() {
        context.startActivity(Intent(context, RegisterActivity::class.java))
    }

    fun toLoginActivity() {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    fun toHomeActivity() {
        context.startActivity(Intent(context, HomeActivity::class.java))
    }

}
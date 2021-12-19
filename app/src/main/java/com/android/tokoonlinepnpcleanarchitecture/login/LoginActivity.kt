package com.android.tokoonlinepnpcleanarchitecture.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.tokoonlinepnpcleanarchitecture.BuildConfig
import com.android.tokoonlinepnpcleanarchitecture.R
import com.android.tokoonlinepnpcleanarchitecture.common.animation.AnimationLoader
import com.android.tokoonlinepnpcleanarchitecture.constants.Constant.URL
import com.android.tokoonlinepnpcleanarchitecture.common.dialog.ShowDialog
import com.android.tokoonlinepnpcleanarchitecture.common.navigator.ScreenNavigator
import com.android.tokoonlinepnpcleanarchitecture.databinding.ActivityLoginBinding
import com.android.tokoonlinepnpcleanarchitecture.login.model.ResponseUsers
import com.android.tokoonlinepnpcleanarchitecture.network.RestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var restApi: RestApi
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var showDialog: ShowDialog
    lateinit var animationLoader: AnimationLoader
    lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restApi = provideHttpAdapter().create(RestApi::class.java)
        compositeDisposable = CompositeDisposable()
        showDialog = ShowDialog(supportFragmentManager)
        animationLoader = AnimationLoader(this)
        screenNavigator = ScreenNavigator(this)

        implementLeftToRightAnimate()

        binding.btnLogin.setOnClickListener {
            loading()
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            if (username.isNullOrEmpty()) {
                loaded()
                showMessageDialogLogin("Username tidak boleh kosong")
            } else if (password.isNullOrEmpty()) {
                loaded()
                showMessageDialogLogin("Password tidak boleh kosong")
            } else {
                login(username, password)
            }
        }

        binding.txtSignup.setOnClickListener {
            screenNavigator.toRegisterActivity()
        }
    }

    private fun login(username: String, password: String) {
        compositeDisposable.add(
            restApi.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loaded()
                    showDialogLoginWhenResponse(it)
                }, {
                    loaded()
                    showDialogLoginWhenThrowable(it)
                })
        )
    }


    private fun showMessageDialogLogin(message: String) {
        showDialog.showDialogError(
            message,
            resources.getString(R.string.error_message),
            R.drawable.ic_baseline_error_24
        )
    }

    private fun showDialogLoginWhenThrowable(it: Throwable?) {
        showDialog.showDialogError(
            it?.message.toString(),
            resources.getString(R.string.error_message),
            R.drawable.ic_baseline_error_24
        )
    }

    private fun showDialogLoginWhenResponse(it: ResponseUsers?) {
        if (it?.status == true) {
            showDialog.showDialogSukses(
                it?.message.toString(),
                resources.getString(R.string.selamat_menikmati),
                R.drawable.ic_done_24
            )
        } else {
            showDialog.showDialogError(
                it?.message.toString(),
                resources.getString(R.string.error_message),
                R.drawable.ic_baseline_error_24
            )
        }
    }

    private fun implementLeftToRightAnimate() {
        animationLoader.leftToRight(binding.layoutLogin)
    }

    private fun loading() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = "Loading"
    }

    private fun loaded() {
        binding.btnLogin.isEnabled = true
        binding.btnLogin.text = "Login"
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(120, TimeUnit.SECONDS);
            connectTimeout(120, TimeUnit.SECONDS);
            writeTimeout(120, TimeUnit.SECONDS);
            addInterceptor(provideHttpLoggingInterceptor())
        }.build()
    }

    private fun provideHttpAdapter(): Retrofit {
        return Retrofit.Builder().apply {
            client(provideHttpClient())
            baseUrl(URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
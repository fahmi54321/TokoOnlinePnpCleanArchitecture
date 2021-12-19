package com.android.tokoonlinepnpcleanarchitecture.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.tokoonlinepnpcleanarchitecture.BuildConfig
import com.android.tokoonlinepnpcleanarchitecture.R
import com.android.tokoonlinepnpcleanarchitecture.common.animation.AnimationLoader
import com.android.tokoonlinepnpcleanarchitecture.common.dialog.ShowDialog
import com.android.tokoonlinepnpcleanarchitecture.common.navigator.ScreenNavigator
import com.android.tokoonlinepnpcleanarchitecture.constants.Constant
import com.android.tokoonlinepnpcleanarchitecture.databinding.ActivityRegisterBinding
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

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var restApi: RestApi
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var showDialog: ShowDialog
    lateinit var animationLoader: AnimationLoader
    lateinit var screenNavigator: ScreenNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restApi = provideHttpAdapter().create(RestApi::class.java)
        compositeDisposable = CompositeDisposable()
        showDialog = ShowDialog(supportFragmentManager)
        animationLoader = AnimationLoader(this)
        screenNavigator = ScreenNavigator(this)

        implementLeftToRightAnimate()

        binding.btnRegister.setOnClickListener {

            loading()

            var namaLengkap = binding.edtNamaLengkap.text.toString()
            var noIdentitas = binding.edtNoIdentitas.text.toString()
            var noHp = binding.edtNoHp.text.toString()
            var alamat = binding.edtAlamat.text.toString()
            var email = binding.edtEmail.text.toString()
            var pass = binding.edtPassword.text.toString()
            var confPass = binding.edtConfirmPass.text.toString()

            if (namaLengkap.isNullOrEmpty()) {
                loaded()
                showMessageDialog("Nama lengkap tidak boleh kosong")
            } else if (noIdentitas.isNullOrEmpty()) {
                loaded()
                showMessageDialog("No identitas tidak boleh kosong")
            } else if (noHp.isNullOrEmpty()) {
                loaded()
                showMessageDialog("No hp tidak boleh kosong")
            } else if (alamat.isNullOrEmpty()) {
                loaded()
                showMessageDialog("Alamat tidak boleh kosong")
            } else if (email.isNullOrEmpty()) {
                loaded()
                showMessageDialog("Email tidak boleh kosong")
            } else if (pass.isNullOrEmpty()) {
                loaded()
                showMessageDialog("Password tidak boleh kosong")
            } else if (!confPass.equals(pass)) {
                loaded()
                showMessageDialog("Password tidak sama")
            } else {
                register(namaLengkap, noIdentitas, noHp, alamat, email, pass)
            }
        }
    }

    private fun register(
        namaLengkap: String,
        noIdentitas: String,
        noHp: String,
        alamat: String,
        email: String,
        pass: String
    ) {
        restApi.register("uid", namaLengkap, email, pass, noIdentitas, noHp, alamat, "token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loaded()
                showDialogLoginWhenResponse(it)
            }, {
                loaded()
                showDialogLoginWhenThrowable(it)
            })
    }

    private fun implementLeftToRightAnimate() {
        animationLoader.leftToRight(binding.linearLayout)
    }

    private fun showMessageDialog(message: String) {
        showDialog.showDialogErrorRegister(
            message,
            resources.getString(R.string.error_message),
            R.drawable.ic_baseline_error_24
        )
    }

    private fun showDialogLoginWhenThrowable(it: Throwable?) {
        showDialog.showDialogErrorRegister(
            it?.message.toString(),
            resources.getString(R.string.error_message),
            R.drawable.ic_baseline_error_24
        )
    }

    private fun showDialogLoginWhenResponse(it: ResponseUsers?) {
        if (it?.status == true) {
            showDialog.showDialogSuksesRegister(
                it?.message.toString(),
                resources.getString(R.string.selamat_menikmati),
                R.drawable.ic_done_24
            )
        } else {
            showDialog.showDialogErrorRegister(
                it?.message.toString(),
                resources.getString(R.string.error_message),
                R.drawable.ic_baseline_error_24
            )
        }
    }

    private fun loading() {
        binding.btnRegister.isEnabled = false
        binding.btnRegister.text = "Loading"
    }

    private fun loaded() {
        binding.btnRegister.isEnabled = true
        binding.btnRegister.text = "Register"
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
            baseUrl(Constant.URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
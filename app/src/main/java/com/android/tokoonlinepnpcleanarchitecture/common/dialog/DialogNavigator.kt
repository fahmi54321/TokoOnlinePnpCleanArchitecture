package com.android.tokoonlinepnpcleanarchitecture.common.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.android.tokoonlinepnpcleanarchitecture.R
import com.android.tokoonlinepnpcleanarchitecture.common.ImageLoader
import com.android.tokoonlinepnpcleanarchitecture.common.animation.AnimationLoader
import com.android.tokoonlinepnpcleanarchitecture.common.dialog.model.DialogModel
import com.android.tokoonlinepnpcleanarchitecture.common.navigator.ScreenNavigator
import com.android.tokoonlinepnpcleanarchitecture.databinding.DialogLayoutBinding

class DialogNavigator : DialogFragment() {

    companion object {
        var KEY_SUKSES_FROM_LOGIN = "KEY_SUKSES_FROM_LOGIN"
        var KEY_ERROR_FROM_LOGIN = "KEY_ERROR_FROM_LOGIN"
        var KEY_SUKSES_FROM_REGISTER = "KEY_SUKSES_FROM_REGISTER"
        var KEY_ERROR_FROM_REGISTER = "KEY_ERROR_FROM_REGISTER"
    }

    private var getDataSuksesLogin: DialogModel? = null
    private var getDataErrorLogin: DialogModel? = null
    private var getDataSuksesRegister: DialogModel? = null
    private var getDataErrorRegister: DialogModel? = null
    private lateinit var binding: DialogLayoutBinding
    private lateinit var imageLoader: ImageLoader
    private lateinit var animationLoader: AnimationLoader
    private lateinit var screenNavigator: ScreenNavigator

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.custom_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        binding = DialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataSuksesLogin = arguments?.getParcelable(KEY_SUKSES_FROM_LOGIN)
        getDataErrorLogin = arguments?.getParcelable(KEY_ERROR_FROM_LOGIN)
        getDataSuksesRegister = arguments?.getParcelable(KEY_SUKSES_FROM_REGISTER)
        getDataErrorRegister = arguments?.getParcelable(KEY_ERROR_FROM_REGISTER)

        imageLoader = ImageLoader(requireContext())
        animationLoader = AnimationLoader(requireContext())
        screenNavigator = ScreenNavigator(requireContext())

        implementTopToBottomAnimate()
        implementBottomToTopAnimate()

        if (getDataSuksesLogin != null) {
            modifDialog(getDataSuksesLogin!!)
            enableButtonLanjut()
        } else if (getDataErrorLogin != null) {
            modifDialog(getDataErrorLogin!!)
            disableButtonLanjut()
        } else if (getDataSuksesRegister != null) {
            modifDialog(getDataSuksesRegister!!)
            enableButtonLanjut()
            changeNameButtonLanjutToLogin()
        } else if (getDataErrorRegister != null) {
            modifDialog(getDataErrorRegister!!)
            disableButtonLanjut()
        }

        binding.btnTutup.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnLanjut.setOnClickListener {
            when (binding.btnLanjut.text) {
                "Login" -> {
                    screenNavigator.toLoginActivity()
                }
                else -> {
                    screenNavigator.toHomeActivity()
                }
            }
        }
    }

    private fun implementTopToBottomAnimate() {
        animationLoader.topToBottom(binding.imgDialog)
        animationLoader.topToBottom(binding.txtDialog)
    }

    private fun implementBottomToTopAnimate() {
        animationLoader.bottomToTop(binding.txtMessage)
        animationLoader.bottomToTop(binding.btnTutup)
        animationLoader.bottomToTop(binding.btnLanjut)
    }

    private fun modifDialog(data: DialogModel) {
        binding.txtDialog.text = data.title
        binding.txtMessage.text = data.message
        imageLoader.loadWithGlide(data.img, binding.imgDialog)
    }

    private fun enableButtonLanjut() {
        binding.btnLanjut.visibility = View.VISIBLE
    }

    private fun disableButtonLanjut() {
        binding.btnLanjut.visibility = View.GONE
    }

    private fun changeNameButtonLanjutToLogin() {
        binding.btnLanjut.text = "Login"
    }

}
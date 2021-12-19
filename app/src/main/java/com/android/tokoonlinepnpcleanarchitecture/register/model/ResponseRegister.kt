package com.android.tokoonlinepnpcleanarchitecture.register.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

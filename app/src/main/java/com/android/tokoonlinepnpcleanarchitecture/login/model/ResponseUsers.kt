package com.android.tokoonlinepnpcleanarchitecture.login.model

import com.google.gson.annotations.SerializedName

data class ResponseUsers(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("foto")
	val foto: String? = null,
)

data class ResultItem(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("no_identitas")
	val noIdentitas: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("user_level")
	val userLevel: String? = null,

	@field:SerializedName("profile_photo_path")
	val profilePhotoPath: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

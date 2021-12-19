package com.android.tokoonlinepnpcleanarchitecture.network

import com.android.tokoonlinepnpcleanarchitecture.login.model.ResponseUsers
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RestApi {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Single<ResponseUsers>

    @FormUrlEncoded
    @POST("registerOtomatis.php")
    fun register(
        @Field("uid") uid: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("no_identitas") no_identitas: String,
        @Field("no_hp") no_hp: String,
        @Field("alamat") alamat: String,
        @Field("remember_token") remember_token: String,
    ): Single<ResponseUsers>

}
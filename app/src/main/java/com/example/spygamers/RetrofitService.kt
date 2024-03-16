package com.example.spygamers

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

data class RetrofitResponse(
    val status: String,
    val session_token: String? = null,
    val account_id: Int? = null,
    val timezone_code: String? = null
)

data class UserRegistration(
    val username: String,
    val password: String,
    val email: String
)

data class UserLogin(
    val username: String,
    val password: String

)

data class GetDM(
    val auth_token: String,
    val target_account_id: Int
)

data class newUsername(
    val auth_token: String,
    val new_username: String
)


interface AuthenticationService {
    @POST("http://spygamers.servehttp.com:44414/app-api/account/register")
    suspend fun registerUser(
        @Body user: UserRegistration
    ): Response<RetrofitResponse>

    @POST("http://spygamers.servehttp.com:44414/app-api/account/login")
    suspend fun userLogin(
        @Body user: UserLogin
    ): Response<RetrofitResponse>

    @POST("http://spygamers.servehttp.com:44414/app-api/account/get-direct-messages")
    suspend fun getDM(
        @Body user: GetDM
    ): Response<RetrofitResponse>

    @PUT("http://spygamers.servehttp.com:44414/app-api/account/change-username")
    suspend fun changeUsername(
        @Body user: newUsername
    ): Response<RetrofitResponse>

}


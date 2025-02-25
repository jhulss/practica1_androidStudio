package com.calyr.network

import retrofit2.http.GET
import retrofit2.http.Path

interface IApiService {
    @GET("/users/{githubLogin}")
    suspend fun getInfoAvatar(@Path("githubLogin") githubLogin: String): AvatarResponseDto
}

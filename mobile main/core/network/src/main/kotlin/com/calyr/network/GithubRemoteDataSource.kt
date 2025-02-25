package com.calyr.network

class GithubRemoteDataSource(
    val retrofiService: RetrofitBuilder
) {
    suspend fun getAvatarInfo(githubLogin: String): AvatarResponseDto {
        return retrofiService.apiService.getInfoAvatar(githubLogin)
    }
}

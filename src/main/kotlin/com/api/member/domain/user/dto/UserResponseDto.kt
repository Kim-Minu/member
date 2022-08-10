package com.api.member.domain.user.dto

import com.api.member.domain.user.domain.User

data class UserResponseDto (
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val phoneNumber: String,
){
    constructor(user: User) : this(
        user.id,
        user.name,
        user.nickname,
        user.email,
        user.phoneNumber
    )
}

data class UserSignInResponseDto(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val phoneNumber: String,
    val token: String,
) {
    constructor(user: User, token: String) : this(
        user.id,
        user.name,
        user.nickname,
        user.email,
        user.phoneNumber,
        token
    )
}

data class UserSignUpResponseDto (
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val phoneNumber: String,
    val token: String,
) {
    constructor(user: User, token: String) : this(
        user.id,
        user.name,
        user.nickname,
        user.email,
        user.phoneNumber,
        token
    )
}

data class UserFindPasswordResponseDto (
    val password: String
)
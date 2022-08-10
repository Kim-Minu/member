package com.api.member.domain.user.dto

import com.api.member.domain.user.domain.Role
import com.api.member.domain.user.domain.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class UserRequestDto {
}

class UserSignInRequestDto(
    @field:NotBlank
    val id: String,

    @field:NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용하세요.")
    val password: String
)

class UserSignUpRequestDto(
    @field:NotBlank
    @field:Pattern(regexp = "[가-힣]{1,10}", message = "올바른 형식의 이름이어야 합니다")
    val name: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    val nickname: String,

    @field:Pattern(regexp = "010\\d{4}\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    val phoneNumber: String,

    @field:NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용하세요.")
    val password: String,

    @field:NotBlank
    val authenticationCode: String,
) {

    fun toEntity(password: String): User {
        return User(email = email,
            name = name,
            nickname = nickname,
            phoneNumber = phoneNumber,
            password = password,
            role = Role.USER
        )
    }
}

class UserFindPasswordRequestDto(
    @field:Pattern(regexp = "010\\d{4}\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    val phoneNumber: String,

    @field:NotBlank
    val authenticationCode: String,
)
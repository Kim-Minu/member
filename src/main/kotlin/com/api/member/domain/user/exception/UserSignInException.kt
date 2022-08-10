package com.api.member.domain.user.exception

import com.api.member.global.exception.ErrorCode
import com.api.member.global.exception.InvalidValueException

class UserSignInException(message: String): InvalidValueException(
    message, ErrorCode.LOGIN_INPUT_INVALID
)
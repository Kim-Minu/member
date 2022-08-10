package com.api.member.domain.user.exception

import com.api.member.global.exception.ErrorCode
import com.api.member.global.exception.InvalidValueException

class UserNotAuthPhoneNumberException(phoneNumber: String): InvalidValueException(
    phoneNumber,
    ErrorCode.NOT_AUTH_SMS
)
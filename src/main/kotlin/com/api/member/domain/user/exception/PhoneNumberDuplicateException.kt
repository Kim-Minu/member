package com.api.member.domain.user.exception

import com.api.member.global.exception.ErrorCode
import com.api.member.global.exception.InvalidValueException

class PhoneNumberDuplicateException(phoneNumber: String) : InvalidValueException(
    phoneNumber,
    ErrorCode.PHONE_DUPLICATION
)
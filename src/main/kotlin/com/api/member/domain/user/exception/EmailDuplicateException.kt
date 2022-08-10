package com.api.member.domain.user.exception

import com.api.member.global.exception.ErrorCode
import com.api.member.global.exception.InvalidValueException

class EmailDuplicateException(email: String) : InvalidValueException(
    email,
    ErrorCode.EMAIL_DUPLICATION
)
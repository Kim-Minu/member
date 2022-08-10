package com.api.member.domain.user.exception

import com.api.member.global.exception.EntityNotFoundException
import com.api.member.global.exception.ErrorCode

class UserNotFoundException(): EntityNotFoundException(
    message = ErrorCode.NOT_FOUND_MEMBER.message
)
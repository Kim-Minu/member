package com.api.member.global.exception

class FieldErrorResponse (
    val field: String? = null,
    val value: String? = null,
    val reason: String? = null
)
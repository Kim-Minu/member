package com.api.member.global.exception

class ErrorResponse (
    val message: String?,
    val status: Int,
    val errors: MutableList<FieldErrorResponse>? = mutableListOf(),
    val code: String?
){
}
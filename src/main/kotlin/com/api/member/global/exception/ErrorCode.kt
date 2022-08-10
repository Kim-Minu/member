package com.api.member.global.exception

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    HANDLE_ACCESS_UNAUTHORIZED(401, "C006", "Access is UNAUTHORIZED"),
    NOT_FOUND_URL(404, "C006", "invalid URL"),

    // Member
    EMAIL_DUPLICATION(409, "M001", "이미 사용 중인 이메일입니다."),
    PHONE_DUPLICATION(409, "M002", "이미 사용 중인 휴대폰 번호입니다."),
    LOGIN_INPUT_INVALID(400, "M003", "아이디 또는 비밀번호를 확인해주세요."),
    NOT_AUTH_SMS(400, "M004", "휴대폰 번호 인증을 완료해주세요."),
    NOT_FOUND_MEMBER(404, "M004", "등록된 사용자 정보가 없습니다."),

    //SMS
    AUTH_INVALID(400, "SMS002", "휴대폰 번호 또는 인증 번호를 확인해주세요."),
    AUTH_EXPIRED_TIME(400, "SMS003", "인증 시간이 만료 되었습니다."),
}
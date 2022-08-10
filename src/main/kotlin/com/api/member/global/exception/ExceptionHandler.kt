package com.api.member.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class ExceptionHandler(
) {
    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse?>? {

        val fieldErrorResponse: MutableList<FieldErrorResponse> = mutableListOf()

        e.bindingResult.fieldErrors.map {
            fieldErrorResponse.add(
                FieldErrorResponse(
                    it.field,
                    it.rejectedValue.toString(),
                    it.defaultMessage
                )
            )
        }

        val response = ErrorResponse(
            message = ErrorCode.INVALID_INPUT_VALUE.message,
            status = ErrorCode.INVALID_INPUT_VALUE.status,
            errors = fieldErrorResponse,
            code = ErrorCode.INVALID_INPUT_VALUE.code
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): ResponseEntity<ErrorResponse?>? {

        val fieldErrorResponse: MutableList<FieldErrorResponse> = mutableListOf()

        e.bindingResult.fieldErrors.map {
            fieldErrorResponse.add(
                FieldErrorResponse(
                    it.field,
                    it.rejectedValue.toString(),
                    it.defaultMessage
                )
            )
        }

        val response = ErrorResponse(
            message = ErrorCode.INVALID_INPUT_VALUE.message,
            status = ErrorCode.INVALID_INPUT_VALUE.status,
            errors = fieldErrorResponse,
            code = ErrorCode.INVALID_INPUT_VALUE.code,
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException?): ResponseEntity<ErrorResponse?>? {

        val response = ErrorResponse(
            message = e?.message,
            status = HttpStatus.BAD_REQUEST.value(),
            code = e?.errorCode
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException?): ResponseEntity<ErrorResponse?>? {

        val response = ErrorResponse(
            message = ErrorCode.METHOD_NOT_ALLOWED.message,
            status = ErrorCode.METHOD_NOT_ALLOWED.status,
            code = ErrorCode.METHOD_NOT_ALLOWED.code
        )
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(e: AccessDeniedException?): ResponseEntity<ErrorResponse?>? {

        val response = ErrorResponse(
            message = ErrorCode.HANDLE_ACCESS_DENIED.message,
            status = ErrorCode.HANDLE_ACCESS_DENIED.status,
            code = ErrorCode.HANDLE_ACCESS_DENIED.code
        )

        return ResponseEntity(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.status))
    }

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse?>? {

        val response = ErrorResponse(
            message = e.errorCode.message,
            status = e.errorCode.status,
            code = e.errorCode.code
        )

        return ResponseEntity(response, HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception?): ResponseEntity<ErrorResponse?>? {

        val response = ErrorResponse(
            message = e?.message,
            status = ErrorCode.INTERNAL_SERVER_ERROR.status,
            code = ErrorCode.INTERNAL_SERVER_ERROR.code
        )

        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    protected fun handle404(e: NoHandlerFoundException): ResponseEntity<ErrorResponse?>? {

        val message = "존재하지 않는 URL입니다. : " + e.requestURL

        val response = ErrorResponse(
            message = message,
            status = ErrorCode.NOT_FOUND_URL.status,
            code = ErrorCode.NOT_FOUND_URL.code
        )

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}
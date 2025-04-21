package com.example.miniBank.config

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class ExceptionConfig: DefaultErrorAttributes() {

    override fun getErrorAttributes(
        webRequest: WebRequest,
        options: ErrorAttributeOptions
    ): MutableMap<String, Any> {
        val errorAttributes = super.getErrorAttributes(webRequest, options)

        val exception = getError(webRequest)

        if (exception is RuntimeException) {
            errorAttributes["message"] = exception.message ?: "Unexpected error"
        }

        return errorAttributes
    }
}

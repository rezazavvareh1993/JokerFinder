package com.example.jokerfinder.utils.response

sealed class GeneralResponse<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : GeneralResponse<T>(data = data)

    class Loading<T>(data: T? = null) : GeneralResponse<T>(data = data)

    class Error<T>(message: String) : GeneralResponse<T>(message = message)
}

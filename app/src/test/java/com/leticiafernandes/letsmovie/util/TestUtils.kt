package com.leticiafernandes.letsmovie.util

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun buildHttpException(code: Int): HttpException =
    HttpException(Response.error<Unit>(code, "".toResponseBody(null)))

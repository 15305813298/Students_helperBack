package cn.lintyone.out.helper.common

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.lang.RuntimeException

class MyException(@get:JvmName("getMessage") private val msg: String?) : GraphQLError, RuntimeException() {

    override fun getMessage(): String? = msg
    override fun getErrorType(): ErrorType? = null
    override fun getLocations(): MutableList<SourceLocation>? = null

    override fun getExtensions(): MutableMap<String, Any>? = null
    override fun getStackTrace(): Array<StackTraceElement>? = null
    override fun getLocalizedMessage(): String? = null
    override fun getPath(): MutableList<Any>? = null
}
package cn.lintyone.out.helper.model.response

class Response<T> constructor(val code: Int = 1, val message: String? = "", data: T? = null) {
    constructor(message: String?) : this(0, message, null)
    constructor(data: T) : this(1, "", data)
}
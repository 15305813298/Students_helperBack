package cn.lintyone.out.helper.common

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Claim
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

object JwtToken {

    private const val HOUR = Calendar.HOUR

    var expireTime = 24

    var secret = ""

    private val map = HashMap<String, Any>(2)

    init {
        map["alg"] = "HS256"
        map["typ"] = "JWT"
    }

    /**
     * 根据用户ID[userID]，时间单位（选填）[unit]，时间数值（选填）[expireTime] 创建一个用户Token
     */
    fun createToken(userID: String, unit: Int = HOUR, num: Int = expireTime): String {
        val iatDate = Date()
        val nowTime = Calendar.getInstance()
        nowTime.add(unit, num)
        val expiresDate = nowTime.time
        return JWT.create()
                .withHeader(map)
                .withClaim("user_id", userID)
                .withExpiresAt(expiresDate)
                .withIssuedAt(iatDate)
                .sign(Algorithm.HMAC256(secret))
    }

    /**
     * 对传入的[token]进行验证，如果验证失败，抛出[MyException]，验证成功返回[Map]
     */
    private fun verifyToken(token: String): Map<String, Claim> {
        return try {
            (JWT.require(Algorithm.HMAC256(secret)).build().verify(token)).claims
        } catch (e: Exception) {
            throw MyException("登录信息已过期，请重新登录")
        }
    }

    /**
     * 对传入的[token]进行验证并解析，如果失败则抛出[MyException]，成功则返回用户ID
     */
    fun getUserId(token: String): String {
        val claims = verifyToken(token)
        return try {
            (claims["user_id"] ?: error("token错误")).asString()
        } catch (e: Exception) {
            throw MyException("登录信息已过期，请重新登录")
        }
    }

}
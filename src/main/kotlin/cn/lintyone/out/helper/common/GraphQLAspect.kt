package cn.lintyone.out.helper.common

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class GraphQLAspect {

    companion object {
        var userId: String? = null
    }

    @Pointcut("execution(public * cn.lintyone.out.helper.resolver.*.*.*(..))")
    fun cut() {
    }

    @Before("cut()")
    fun before(joinPoint: JoinPoint) {
        val sra = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val token = sra.request.getHeader("token")
        userId = if (token != null) {
            try {
                JwtToken.getUserId(token)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

}
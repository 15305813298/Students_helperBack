package cn.lintyone.out.helper.resolver

import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class DateScalarType : GraphQLScalarType("DateTime", "日期时间", object : Coercing<Timestamp, String> {
    override fun parseValue(input: Any): Timestamp? {
        val time = input as String? ?: return null
        return Timestamp.valueOf(time)
    }

    override fun parseLiteral(input: Any?): Timestamp? {
        val time = input as String? ?: return null
        return Timestamp.valueOf(time)
    }

    override fun serialize(dataFetcherResult: Any?): String {
        val time = (dataFetcherResult as Timestamp).time
        val interval = (System.currentTimeMillis() - time) / 1000
        if (interval < 60 * 60 * 24) {
            return when {
                interval < 60 * 60 -> {
                    (interval / 60).toString() + " 分钟前"
                }
                interval > 60 -> {
                    (interval / 60 / 60).toString() + " 小时前"
                }
                else -> {
                    (interval / 60).toString() + " 秒前"
                }
            }
        }
        val timeString = Timestamp(time).toString()
        return timeString.substring(0, timeString.lastIndexOf("."))
    }

})
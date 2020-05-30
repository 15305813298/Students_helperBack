package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.School
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class SchoolQuery : BaseQuery() {

    fun schools(filter: String): List<School> {
        return schoolService.list(filter)
    }

    fun school(): School {
        return getUser().school
    }
}
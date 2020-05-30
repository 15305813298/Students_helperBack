package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.School
import org.springframework.data.domain.Page

interface SchoolService {

    fun get(id: Int): School

    fun getByName(name : String) : School

    fun list(filter: String): List<School>

    fun paginate(page: Int, filter: String): Page<School>
}
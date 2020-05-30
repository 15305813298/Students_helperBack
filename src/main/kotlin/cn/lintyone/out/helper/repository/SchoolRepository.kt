package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.School
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface SchoolRepository : JpaRepository<School, Int>, JpaSpecificationExecutor<School> {

    @Query("select id, name from School where name like :filter")
    fun searchSchool(filter: String): List<School>

    fun findByName(name : String) : School?
}
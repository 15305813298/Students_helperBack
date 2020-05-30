package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<Post, Int>, JpaSpecificationExecutor<Post> {

    @Query("select count(id) from Post where createdAt > current_date ")
    fun countPost(): Int
}
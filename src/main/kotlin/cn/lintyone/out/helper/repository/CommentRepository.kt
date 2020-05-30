package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CommentRepository : JpaRepository<Comment, Int>, JpaSpecificationExecutor<Comment> {
}
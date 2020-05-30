package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Feedback
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface FeedbackRepository : JpaRepository<Feedback, Int>, JpaSpecificationExecutor<Feedback> {
}
package cn.lintyone.out.helper.common

import com.github.wenhao.jpa.PredicateBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

fun <T> PredicateBuilder<T>.defaultPage(page: Int, repository: JpaSpecificationExecutor<T>): Page<T> {
    val sort = Sort.Order(Sort.Direction.DESC, "id")
    val pageRequest = PageRequest.of(page, 15, Sort.by(sort))
    return repository.findAll(this.build(), pageRequest)
}

fun <T> PredicateBuilder<T>.defaultPage(page: Int, repository: JpaSpecificationExecutor<T>, size : Int): Page<T> {
    val sort = Sort.Order(Sort.Direction.DESC, "id")
    val pageRequest = PageRequest.of(page, size, Sort.by(sort))
    return repository.findAll(this.build(), pageRequest)
}
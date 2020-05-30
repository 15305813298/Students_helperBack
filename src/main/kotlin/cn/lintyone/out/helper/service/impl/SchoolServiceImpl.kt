package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.common.defaultPage
import cn.lintyone.out.helper.model.School
import cn.lintyone.out.helper.repository.SchoolRepository
import cn.lintyone.out.helper.service.SchoolService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SchoolServiceImpl : SchoolService {

    @Autowired
    lateinit var schoolRepository: SchoolRepository

    override fun get(id: Int): School {
        val optional = schoolRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw MyException("学校不存在")
        }
    }

    override fun getByName(name: String): School {
        return schoolRepository.findByName(name) ?: throw MyException("请输入正确的学校名称")
    }

    override fun list(filter: String): List<School> {
        val pre = Specifications.and<School>()
        pre.like("name", "$filter%")
        val sort = Sort.Order(Sort.Direction.DESC, "id")
        val pageRequest = PageRequest.of(0, 10, Sort.by(sort))
        return schoolRepository.findAll(pre.build(), pageRequest).content
    }

    override fun paginate(page: Int, filter: String): Page<School> {
        val pre = Specifications.and<School>()
        if (filter.isNotEmpty()) {
            pre.like("name", "%$filter%")
        }
        return pre.defaultPage(page, schoolRepository)
    }
}
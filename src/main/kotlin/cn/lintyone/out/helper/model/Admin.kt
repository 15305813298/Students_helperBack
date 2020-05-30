package cn.lintyone.out.helper.model

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var avatar: String? = null

    var nickName: String? = null

    @Column(unique = true, nullable = false)
    lateinit var userName: String

    @Column(nullable = false)
    lateinit var password: String

}
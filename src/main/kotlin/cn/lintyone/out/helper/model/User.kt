package cn.lintyone.out.helper.model

import cn.lintyone.out.helper.common.JwtToken
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = true, unique = true, length = 10)
    lateinit var userName: String

    var nickName: String? = null

    var avatar: String? = null

    @Column(nullable = false, unique = true)
    lateinit var studentId: String

    @Column(nullable = false, unique = true, length = 11)
    lateinit var phone: String

    var city: String? = null

    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal(0)

    @Column(nullable = true)
    var lastLoginTime: Timestamp? = null

    @Column(nullable = false)
    lateinit var password: String

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var school: School

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    var address: Address? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: Timestamp

    @Transient
    var token: String? = null

    /**
     * 对当前用户设置一个新的Token
     */
    fun setToken() {
        this.token = JwtToken.createToken(this.id.toString())
    }
}
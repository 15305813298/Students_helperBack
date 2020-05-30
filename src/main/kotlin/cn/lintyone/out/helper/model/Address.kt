package cn.lintyone.out.helper.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false)
    lateinit var description: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var user: User

    @CreatedDate
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: Timestamp
}
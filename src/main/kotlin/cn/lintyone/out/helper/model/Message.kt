package cn.lintyone.out.helper.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*


@Entity
@EntityListeners(AuditingEntityListener::class)
class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var user:User

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    lateinit var type: Type

    @Column(nullable = false)
    lateinit var content: String

    var targetId: Int? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: Timestamp

    enum class Type {
        SYSTEM,
        ORDER,
        COMMENT
    }
}
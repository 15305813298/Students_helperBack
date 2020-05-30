package cn.lintyone.out.helper.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var user: User

    var image: String? = null

    @Column(nullable = false, length = 20)
    lateinit var title: String

    @Column(nullable = false, columnDefinition = "longtext")
    lateinit var content: String

    @Column(nullable = false, columnDefinition = "int default 0")
    var countComment = 0

    @Column(nullable = false, columnDefinition = "int default 0")
    var countView = 0

    @CreatedDate
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: Timestamp

    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updatedAt: Timestamp
}
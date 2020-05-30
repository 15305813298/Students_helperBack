package cn.lintyone.out.helper.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var user: User

    @Column(nullable = false)
    lateinit var content: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var post: Post

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    var comment: Comment? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: Timestamp

}
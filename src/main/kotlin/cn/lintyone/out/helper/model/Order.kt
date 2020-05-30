package cn.lintyone.out.helper.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "orders", indexes = [Index(name = "ORDERS_ADDRESS_INDEX", columnList = "address_id", unique = false)])
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = ForeignKey(name = "none"), name = "post_user_id" ,nullable = true)
    lateinit var postUser: User

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "none"), name = "pick_user_id", nullable = true)
    var pickUser: User ?= null

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    lateinit var type: Type

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    lateinit var status: Status

    var successCode: String? = null

    var expressCode: String? = null

    var expressName: String? = null

    var pickAddress: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var school: School

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = ForeignKey(name = "none"))
    lateinit var address: Address

    @Column(nullable = false)
    lateinit var description: String

    @Column(nullable = false)
    var offer: BigDecimal = BigDecimal(0)

    @CreatedDate
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: Timestamp

    enum class Type {
        EXPRESS,
        EAT,
        BUY,
        RUN
    }

    enum class Status {
        UNRECEIVED,
        RECEIVED,
        SUCCESS
    }

    fun getTypeName(): String {
        return when (this.type) {
            Type.EXPRESS -> "代取快递"
            Type.BUY -> "代购"
            Type.RUN -> "代跑腿"
            Type.EAT -> "代取外卖"
        }
    }
}
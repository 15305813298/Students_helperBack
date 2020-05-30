package cn.lintyone.out.helper.model

import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "SCHOOL_NAME_INDEX", columnList = "name", unique = true)])
class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false,length = 30)
    lateinit var name: String
}
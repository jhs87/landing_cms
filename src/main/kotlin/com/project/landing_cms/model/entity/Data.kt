package com.project.landing_cms.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
class Data {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var dNo: Long? = null

    @Comment("JSON 데이터")
    @Column(columnDefinition = "LONGTEXT")
    private var data: String? = null

    @Comment("참여 일시")
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
    private var regDate: LocalDateTime? = null

    @ManyToOne
    @Comment("연계 랜딩페이지")
    @JoinColumn(name = "LANDING_KEY")
    private var landingKey: LandingInfo? = null

    @Comment("삭제 여부")
    private var isDeleted: Boolean? = false

    fun getDNo(): Long? {
        return dNo
    }

    fun getData(): String? {
        return data
    }

    fun getRegDate(): LocalDateTime? {
        return regDate
    }

    fun getLandingKey(): LandingInfo? {
        return landingKey
    }

    fun deleted(): Boolean? {
        return isDeleted
    }
}
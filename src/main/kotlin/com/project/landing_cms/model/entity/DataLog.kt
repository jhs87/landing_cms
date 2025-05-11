package com.project.landing_cms.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
class DataLog {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var logNo: Long? = null

    @Comment("로그 데이터(등록 데이터, 수정 이전 데이터, 에러 등)")
    @Column(columnDefinition = "LONGTEXT")
    private var logData: String? = null

    @Comment("로그 타입(등록, 수정, 삭제 등)")
    private var logType: String? = null

    @Comment("로그 메시지")
    private var logMsg: String? = null

    @Comment("등록 일시")
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
    private var createDate: LocalDateTime? = null
}
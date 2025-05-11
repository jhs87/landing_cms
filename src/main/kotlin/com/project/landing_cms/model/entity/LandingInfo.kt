package com.project.landing_cms.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.project.landing_cms.model.dto.LandingInfoDTO
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Entity
class LandingInfo(landingInfoReg: LandingInfoDTO.LandingInfoReg?) {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var lNo: Long? = null

    @Comment("랜딩 제목")
    private var title: String? = null

    @Comment("랜딩 타입")
    private var type: String? = null

    @Comment("랜딩 주소")
    private var url: String? = null

    @Comment("사용 여부")
    private var isUsed: Boolean? = false

    @Comment("시작일")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private var startDate: LocalDateTime? = null

    @Comment("종료일")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private var endDate: LocalDateTime? = null

    @Comment("등록한 사용자")
    @ManyToOne
    @JoinColumn(name = "REG_MEMBER")
    private var regMember: Member? = null

    @Comment("등록 일시")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private var regDate: LocalDateTime? = null

    init {
        lNo = landingInfoReg?.lNo
        title = landingInfoReg?.title
        type = landingInfoReg?.type
        url = landingInfoReg?.url
        isUsed = landingInfoReg?.isUsed
        startDate = landingInfoReg?.startDate
        endDate = landingInfoReg?.endDate
        regMember = landingInfoReg?.regMember
        regDate = landingInfoReg?.regDate
    }

    fun getLNo(): Long? {
        return lNo
    }

    fun getTitle(): String? {
        return title
    }

    fun getType(): String? {
        return type
    }

    fun getUrl(): String? {
        return url
    }

    fun getUsed(): Boolean? {
        return isUsed
    }

    fun getRegMember(): Member? {
        return regMember
    }

    fun getStartDate(): LocalDateTime? {
        return startDate
    }

    fun getEndDate(): LocalDateTime? {
        return endDate
    }

    fun getRegDate(): LocalDateTime? {
        return regDate
    }
}
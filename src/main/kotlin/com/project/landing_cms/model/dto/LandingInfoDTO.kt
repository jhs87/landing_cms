package com.project.landing_cms.model.dto

import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class LandingInfoDTO {

    @Data
    class LandingSideBar(landingInfo: LandingInfo?) {
        var lNo: Long? = landingInfo?.getLNo()
        var title: String? = landingInfo?.getTitle()
    }

    @Data
    class BoardSideBar(landingInfo: LandingInfo?) {
        var lNo: Long? = landingInfo?.getLNo()
        var title: String? = landingInfo?.getTitle()
    }

    @Data
    class LandingInfoReg {
        var lNo: Long? = null
        var title: String? = null
        var type: String? = null
        var url: String? = null
        var isUsed = true
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var startDate: LocalDateTime? = null
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var endDate: LocalDateTime? = null
        var regMember: Member? = null
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        var regDate: LocalDateTime? = LocalDateTime.now()
    }

    @Data
    open class LandingInfoView {
        var lNo: Long? = null
        var title: String? = null
        var type: String? = null
        var url: String? = null
        var isUsed: Boolean? = true
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        var startDate: LocalDateTime? = null
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        var endDate: LocalDateTime? = null
        private var regMember: Member? = null
        var regDate: LocalDateTime? = null

        fun toDto(landingInfo: LandingInfo): LandingInfoView {
            this.lNo = landingInfo.getLNo()
            this.title = landingInfo.getTitle()
            this.type = landingInfo.getType()
            this.url = landingInfo.getUrl()
            this.isUsed = landingInfo.getUsed()
            this.startDate = landingInfo.getStartDate()
            this.endDate = landingInfo.getEndDate()
            this.regMember = landingInfo.getRegMember()
            this.regDate = landingInfo.getRegDate()
            return this
        }
    }
}
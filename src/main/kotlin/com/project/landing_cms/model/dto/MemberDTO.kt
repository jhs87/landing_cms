package com.project.landing_cms.model.dto

import lombok.Data
import com.project.landing_cms.model.entity.Member
import java.time.LocalDateTime
import java.util.*

class MemberDTO {

    @Data
    class MemberReg {
        var mNo: Long? = null
        var memberId: String? = null
        var password: String? = null
        var name: String? = null
        var phone: String? = null
        var birth: Date? = null
        var authRole: String? = null
        var status: Int? = 1
        var regDate: LocalDateTime? = LocalDateTime.now()
        var lastIp: String? = null
        var lastDate: LocalDateTime? = null
    }

    @Data
    open class MemberInfo {
        var mNo: Long? = null
        var memberId: String? = null
        var name: String? = null
        var phone: String? = null
        var birth: Date? = null
        var authRole: String? = null
        var status: Int? = null
        var regDate: LocalDateTime? = null
        var lastIp: String? = null
        var lastDate: LocalDateTime? = null

        fun toDto(member: Member): MemberInfo {
            this.mNo = member.getMNo()
            this.memberId = member.username
            this.name = member.getName()
            this.phone = member.getPhone()
            this.birth = member.getBirth()
            this.authRole = member.getAuthRole()
            this.status = member.getStatus()
            this.regDate = member.getRegDate()
            this.lastIp = member.getLastIP()
            this.lastDate = member.getLastDate()
            return this
        }
    }

    @Data
    class MemberEdit {
        var mNo: Long? = null
        var memberId: String? = null
        var password: String? = null
        var name: String? = null
        var phone: String? = null
        var authRole: String? = null
        var landingKey: List<Long>? = null
        var memberIdYn = false
    }

}
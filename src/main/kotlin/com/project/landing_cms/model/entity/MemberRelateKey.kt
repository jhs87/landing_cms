package com.project.landing_cms.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
class MemberRelateKey(landingInfo: LandingInfo, member: Member) {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var mrNo: Long? = null

    @Comment("연계 랜딩 키")
    @ManyToOne
    @JoinColumn(name = "LANDING_KEY")
    private var landingKey: LandingInfo? = null

    @Comment("연계 사용자 키")
    @ManyToOne
    @JoinColumn(name = "MEMBER_KEY")
    private var memberKey: Member? = null

    fun getMrNo(): Long? {
        return mrNo
    }

    fun getLandingKey(): LandingInfo? {
        return landingKey
    }

    fun getMemberKey(): Member? {
        return memberKey
    }

    init {
        this.landingKey = landingInfo
        this.memberKey = member
    }
}
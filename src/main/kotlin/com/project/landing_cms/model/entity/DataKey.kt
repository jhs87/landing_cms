package com.project.landing_cms.model.entity

import com.project.landing_cms.model.dto.DataKeyDTO
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
class DataKey(dataKeyInfo: DataKeyDTO.DataKeyInfo) {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var dkNo: Long? = null

    @Comment("랜딩페이지 데이터키")
    private var keyName: String? = null

    @Comment("랜딩페이지 데이터키 한글명")
    private var korName: String? = null

    @ManyToOne
    @Comment("연계 랜딩페이지")
    @JoinColumn(name = "LANDING_KEY")
    private var landingKey: LandingInfo? = null

    init {
        this.keyName = dataKeyInfo.keyName
        this.korName = dataKeyInfo.korName
        this.landingKey = dataKeyInfo.landingKey
    }

    fun getDkNo(): Long? {
        return this.dkNo
    }

    fun getKeyName(): String? {
        return this.keyName
    }

    fun getKorName(): String? {
        return this.korName
    }

    fun getLandingKey(): LandingInfo? {
        return this.landingKey
    }
}
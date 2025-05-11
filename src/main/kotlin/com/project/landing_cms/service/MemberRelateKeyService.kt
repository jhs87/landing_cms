package com.project.landing_cms.service

import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey

interface MemberRelateKeyService {

    fun getMemberRelateKeyList(member: Member): List<MemberRelateKey>?

    fun save(landingInfo: LandingInfo, member: Member)

    fun saveAll(memberRelateKey: ArrayList<MemberRelateKey>)

    fun deleteAll(member: Member)

    fun getMyLandingCount(member: Member): Int
}
package com.project.landing_cms.service.impl

import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey
import com.project.landing_cms.persistence.MemberRelateKeyRepository
import com.project.landing_cms.service.MemberRelateKeyService
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service

@Service
class MemberRelateKeyServiceImpl(private val memberRelateKeyRepository: MemberRelateKeyRepository): MemberRelateKeyService {
    override fun getMemberRelateKeyList(member: Member): List<MemberRelateKey>? {
        return memberRelateKeyRepository.findByMemberKeyOrderByMrNoDesc(member)
    }

    override fun save(landingInfo: LandingInfo, member: Member) {
        memberRelateKeyRepository.save(MemberRelateKey(landingInfo, member))
    }

    override fun saveAll(memberRelateKey: ArrayList<MemberRelateKey>) {
        memberRelateKeyRepository.saveAll(memberRelateKey)
    }

    override fun deleteAll(member: Member) {
        memberRelateKeyRepository.deleteByMemberKey(member)
    }

    override fun getMyLandingCount(member: Member): Int {
        return memberRelateKeyRepository.countByMemberKey(member)
    }
}
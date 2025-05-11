package com.project.landing_cms.persistence

import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface MemberRelateKeyRepository: JpaRepository<MemberRelateKey, Long> {

    fun findByMemberKeyOrderByMrNoDesc(member: Member): List<MemberRelateKey>?

    @Modifying
    @Transactional
    fun deleteByMemberKey(member: Member)

    fun countByMemberKey(member: Member): Int
}
package com.project.landing_cms.persistence

import com.project.landing_cms.model.dto.MemberDTO
import com.project.landing_cms.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
    fun findByMemberId(memberId: String): Member?
    fun countByMemberId(memberId: String): Long
    fun countByPhone(phone: String): Long
}
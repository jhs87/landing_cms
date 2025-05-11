package com.project.landing_cms.service.impl

import com.project.landing_cms.model.dto.MemberDTO
import jakarta.persistence.EntityNotFoundException
import com.project.landing_cms.model.dto.MemberDTO.MemberInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.persistence.MemberRepository
import com.project.landing_cms.service.MemberService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(private val memberRepository: MemberRepository): MemberService, UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        return memberRepository.findByMemberId(username)
    }
    override fun list(): List<MemberInfo> {
        return memberRepository.findAll().map { member -> MemberInfo().toDto(member) }
    }

    override fun getMember(mNo: Long): Member {
        return memberRepository.findById(mNo).orElseThrow { EntityNotFoundException() }
    }

    override fun save(member: Member): Member {
        return memberRepository.save(member)
    }

    override fun idCheck(memberId: String): Boolean {
        return memberRepository.countByMemberId(memberId) > 0
    }

    override fun phoneCheck(phone: String): Boolean {
        return memberRepository.countByPhone(phone) > 0
    }

    override fun changePassword(mNo: Long, password: String) {
        val member = memberRepository.findById(mNo).orElseThrow { EntityNotFoundException() }
        member.setPassword(password, BCryptPasswordEncoder())
        memberRepository.save(member)
    }
}
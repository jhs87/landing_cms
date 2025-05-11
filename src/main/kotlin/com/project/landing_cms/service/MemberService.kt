package com.project.landing_cms.service

import com.project.landing_cms.model.dto.MemberDTO.MemberReg
import com.project.landing_cms.model.dto.MemberDTO.MemberInfo
import com.project.landing_cms.model.entity.Member

interface MemberService {

    fun list(): List<MemberInfo>
    fun getMember(mNo: Long): Member
    fun save(member: Member): Member
    fun idCheck(memberId: String): Boolean
    fun phoneCheck(phone: String): Boolean
    fun changePassword(mNo: Long, password: String)
}
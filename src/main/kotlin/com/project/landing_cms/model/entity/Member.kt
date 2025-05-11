package com.project.landing_cms.model.entity

import jakarta.annotation.*
import jakarta.persistence.*
import com.project.landing_cms.model.dto.MemberDTO
import com.project.landing_cms.model.dto.MemberDTO.MemberInfo
import io.ktor.util.*
import org.hibernate.annotations.Comment
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.*

@Entity
class Member(memberDTO: MemberDTO.MemberReg?, passwordEncoder: BCryptPasswordEncoder): UserDetails {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var mNo: Long? = null

    @Comment("아이디")
    @Nonnull
    @Column(unique = true)
    private var memberId: String? = null

    @Comment("패스워드")
    @Nonnull
    @Column
    private var password: String? = null

    @Comment("이름")
    @Nonnull
    @Column
    private var name: String? = null

    @Comment("연락처")
    @Column
    private var phone: String? = null

    @Comment("생년월일")
    @Column(columnDefinition = "DATE")
    private var birth: Date? = null

    @Comment("권한")
    @Nonnull
    @Column
    private var authRole: String? = null

    @Comment("상태")
    private var status: Int? = null

    @Comment("가입일자")
    @Nonnull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var regDate: LocalDateTime? = null

    @Comment("마지막 로그인 IP")
    @Column
    @Nullable
    private var lastIp: String? = null

    @Comment("마지막 로그인 일자")
    @Column
    @Nullable
    @Temporal(TemporalType.TIMESTAMP)
    private var lastDate: LocalDateTime? = null

    init {
        this.mNo = memberDTO?.mNo
        this.memberId = memberDTO?.memberId
        this.password = passwordEncoder.encode(memberDTO?.password)
        this.name = memberDTO?.name
        if(memberDTO?.phone != null) this.phone = memberDTO.phone
        this.authRole = memberDTO?.authRole
        this.status = memberDTO?.status
        if(memberDTO?.regDate != null) this.regDate = memberDTO.regDate
        else this.regDate = LocalDateTime.now()
    }

    fun setPassword(password: String, passwordEncoder: BCryptPasswordEncoder) {
        this.password = passwordEncoder.encode(password)
    }

    fun edit(m: MemberDTO.MemberEdit): Member {
        this.mNo = m.mNo
        this.memberId
        this.name = m.name
        this.phone = m.phone
        if(m.authRole != null) this.authRole = m.authRole
        return this
    }

    fun getMNo(): Long? {
        return mNo
    }

    override fun getUsername(): String? {
        return this.memberId
    }

    override fun getPassword(): String? {
        return this.password
    }

    fun getName(): String? {
        return name
    }

    fun getPhone(): String? {
        return phone
    }

    fun getBirth(): Date? {
        return birth
    }

    fun getAuthRole(): String? {
        return authRole
    }

    fun getStatus(): Int? {
        return status
    }

    fun getRegDate(): LocalDateTime? {
        return regDate
    }

    fun getLastIP(): String? {
        return lastIp
    }

    fun getLastDate(): LocalDateTime? {
        return lastDate
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val roles: MutableSet<GrantedAuthority> = HashSet()
        for (role in authRole?.split(",")?.toTypedArray()!!) {
            roles.add(SimpleGrantedAuthority("ROLE_$role"))
        }
        return roles
    }

    override fun isAccountNonLocked(): Boolean { return true }

    override fun isCredentialsNonExpired(): Boolean { return true }

    override fun isEnabled(): Boolean { return true }

    override fun isAccountNonExpired(): Boolean { return true }
}
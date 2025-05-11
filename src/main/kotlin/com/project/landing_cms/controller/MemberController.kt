package com.project.landing_cms.controller

import com.project.landing_cms.model.dto.MemberDTO
import com.project.landing_cms.model.dto.MemberDTO.MemberInfo
import com.project.landing_cms.model.dto.MemberDTO.MemberReg
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey
import com.project.landing_cms.service.LandingInfoService
import com.project.landing_cms.service.MemberRelateKeyService
import com.project.landing_cms.service.MemberService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Member controller
 *
 * @property memberService
 * @property memberRelateKeyService
 * @property landingInfoService
 * @constructor Create empty Member controller
 */
@Controller
class MemberController(private val memberService: MemberService,
                       private val memberRelateKeyService: MemberRelateKeyService,
                       private val landingInfoService: LandingInfoService) {

    /**
     * Member index
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/members")
    fun memberIndex(model: Model, request: HttpServletRequest): ModelAndView {
        val memberList: List<MemberInfo> = memberService.list()
        model.addAttribute("list", memberList)
        return ModelAndView("member/index")
    }

    /**
     * Member reg page
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/members/reg")
    fun memberRegPage(model: Model, request: HttpServletRequest): ModelAndView {
        return ModelAndView("member/reg")
    }

    /**
     * Member reg
     *
     * @param memberReg
     * @param model
     * @param principal
     * @param request
     * @return
     */
    @PostMapping("/members")
    fun memberReg(@ModelAttribute memberReg: MemberReg, model: Model,
                  @AuthenticationPrincipal principal: Member,
                  request: HttpServletRequest): ModelAndView {
        memberService.save(Member(memberReg, BCryptPasswordEncoder()))
        return ModelAndView("redirect:/members")
    }

    /**
     * Member get
     *
     * @param mNo
     * @return
     */
    @GetMapping("/members/get")
    fun memberGet(@RequestParam mNo: Long, @AuthenticationPrincipal principal: Member): ResponseEntity<Map<String, List<Any?>?>> {
        if(principal.getAuthRole() == "ADMIN" || mNo == principal.getMNo()) {
            val result = HashMap<String, List<Any?>?>()
            val memberList = ArrayList<MemberInfo>()
            val member: Member = memberService.getMember(mNo)
            val memberInfo = MemberInfo().toDto(member)
            memberList.add(memberInfo)
            val memberKey: List<MemberRelateKey>? = memberRelateKeyService.getMemberRelateKeyList(member)
            val landingKey: MutableList<Long?> = ArrayList()
            val landingList: List<LandingInfo>? = landingInfoService.getAllLanding()
            memberKey?.forEach { x ->
                landingKey.add(x.getLandingKey()?.getLNo())
            }

            result["member"] = memberList
            result["memberKey"] = memberKey
            result["landingList"] = landingList
            result["landingKey"] = landingKey
            return ResponseEntity(result, HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    /**
     * Member check
     *
     * @param memberEdit
     * @return
     */
    @GetMapping("/members/check")
    fun memberCheck(@ModelAttribute memberEdit: MemberDTO.MemberEdit): ResponseEntity<String> {
        var result = "success"
        val member = memberService.getMember(memberEdit.mNo!!)
        if(member.username.contentEquals(memberEdit.memberId)) {
            return ResponseEntity(result, HttpStatus.OK)
        }
        // id 체크
        val idCheck: Boolean = memberService.idCheck(memberEdit.memberId!!)
        if(idCheck) {
            result = "idCheck"
            return ResponseEntity(result, HttpStatus.OK)
        }
        // phone 체크
        /*val phoneCheck: Boolean? = memberService.phoneCheck(memberEdit.phone!!)
        if(phoneCheck == false) {
            result = "phoneCheck"
            return ResponseEntity(result, HttpStatus.OK)
        }*/
        return ResponseEntity(result, HttpStatus.OK)
    }

    /**
     * Member check
     *
     * @param memberEdit
     * @return
     */
    @GetMapping("/members/idCheck")
    fun idCheck(@RequestParam memberId: String): ResponseEntity<String> {
        var result = "success"
        // id 체크
        val idCheck: Boolean = memberService.idCheck(memberId)
        if(idCheck) {
            result = "idCheck"
            return ResponseEntity(result, HttpStatus.OK)
        }
        // phone 체크
        /*val phoneCheck: Boolean? = memberService.phoneCheck(memberEdit.phone!!)
        if(phoneCheck == false) {
            result = "phoneCheck"
            return ResponseEntity(result, HttpStatus.OK)
        }*/
        return ResponseEntity(result, HttpStatus.OK)
    }

    /**
     * Member edit
     *
     * @param memberEdit
     * @return
     */
    @ResponseBody
    @PostMapping("/members/edit")
    fun memberEdit(@ModelAttribute memberEdit: MemberDTO.MemberEdit, request: HttpServletRequest,
                   @AuthenticationPrincipal principal: Member): String {
        // 사용자 정보 수정 처리
        val member: Member = memberService.getMember(memberEdit.mNo!!)
        if(principal.getAuthRole() == "ADMIN") {
            memberEdit.memberIdYn = true
        }
        memberService.save(member.edit(memberEdit))

        // 해당 사용자와 연계된 키 삭제 후 재 등록
        if(memberEdit.authRole != null) {
            val memberRelateList = ArrayList<MemberRelateKey>()
            memberRelateKeyService.deleteAll(member)
            if(memberEdit.landingKey?.isNotEmpty() == true) {
                for(key: Long in memberEdit.landingKey!!) {
                    val landingInfo = landingInfoService.getLandingInfo(key)
                    val memberRelateKey = MemberRelateKey(landingInfo, member)
                    memberRelateList.add(memberRelateKey)
                }
                memberRelateKeyService.saveAll(memberRelateList)
            }
        }
        val result = "<script>alert('본인 정보 수정일 경우 로그아웃 후 다시 로그인 해주셔야 반영이 됩니다.'); history.back();</script>"
        return result
    }

    @PutMapping("/members/pwd")
    fun memberPasswdChange(@RequestParam mNo: Long, @RequestParam password: String): ResponseEntity<String> {
        println("사용자 비밀번호 변경 : $mNo : $password")
        memberService.changePassword(mNo, password)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/join")
    fun adminJoin(): ModelAndView {
        val memberReg = MemberReg()
        memberReg.memberId = "admin"
        memberReg.name = "관리자"
        memberReg.password = "123qwe"
        memberReg.authRole = "ADMIN"
        memberReg.birth = Date(1987315)
        memberReg.phone = "01092101480"
        memberService.save(Member(memberReg, BCryptPasswordEncoder()))
        return ModelAndView("redirect:/")
    }

}
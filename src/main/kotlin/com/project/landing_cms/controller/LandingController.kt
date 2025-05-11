package com.project.landing_cms.controller

import com.project.landing_cms.model.dto.LandingInfoDTO
import com.project.landing_cms.model.dto.LandingInfoDTO.LandingInfoReg
import com.project.landing_cms.model.dto.MemberDTO
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey
import com.project.landing_cms.service.LandingInfoService
import com.project.landing_cms.service.MemberRelateKeyService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

/**
 * Landing controller
 *
 * @property landingInfoService
 * @property memberRelateKeyService
 * @constructor Create empty Landing controller
 */
@Controller
class LandingController(private val landingInfoService: LandingInfoService,
                        private val memberRelateKeyService: MemberRelateKeyService) {

    /**
     * 랜딩페이지 목록
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/landing")
    fun landingIndex(model: Model, request: HttpServletRequest): ModelAndView {
        val list = landingInfoService.getAllLanding()
        model.addAttribute("list", list)
        return ModelAndView("landing/index")
    }

    /**
     * 랜딩페이지 등록
     *
     * @param member
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/landing/reg")
    fun landingRegPage(@AuthenticationPrincipal member: Member,
                     model: Model, request: HttpServletRequest): ModelAndView {
        return ModelAndView("landing/reg")
    }

    /**
     * 랜딩페이지 등록 Action
     *
     * @param landingInfoReg
     * @param model
     * @param principal
     * @return
     */
    @PostMapping("/landing")
    fun landingReg(@ModelAttribute landingInfoReg: LandingInfoReg, model: Model,
                   @AuthenticationPrincipal principal: Member): ModelAndView {
        landingInfoReg.regMember = principal
        val landing: LandingInfo = landingInfoService.save(landingInfoReg)
        if(landingInfoReg.lNo == null) {
            memberRelateKeyService.save(landing, principal)
        }
        return ModelAndView("redirect:/landing")
    }

    /**
     * 랜딩페이지 상세정보
     *
     * @param lNo
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/landing/info")
    fun landingInfo(@RequestParam lNo: Long, model: Model,
                    request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        val map = HashMap<String, Any>()
        val landing: LandingInfoDTO.LandingInfoView = LandingInfoDTO.LandingInfoView().toDto(landingInfoService.getLandingInfo(lNo))
        map["landing"] = landing
        return ResponseEntity(map, HttpStatus.OK)
    }

    @ResponseBody
    @PatchMapping("/landing/used")
    fun landingToggle(@RequestParam id: Long, @RequestParam used: Boolean): ResponseEntity<String> {
        landingInfoService.usedToggle(id, used)
        return ResponseEntity(HttpStatus.OK)
    }
}
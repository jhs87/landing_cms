package com.project.landing_cms.controller

import com.project.landing_cms.model.dto.Dashboard
import com.project.landing_cms.model.entity.Data
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey
import com.project.landing_cms.service.DataService
import com.project.landing_cms.service.MemberRelateKeyService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView

/**
 * Main controller
 *
 * @property dataService
 * @property memberRelateKeyService
 * @constructor Create empty Main controller
 */
@Controller
class MainController(private val dataService: DataService,
                     private val memberRelateKeyService: MemberRelateKeyService) {

//    private val privateKey = "0123456789abcdef"

    /**
     * Main
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/")
    fun main(@AuthenticationPrincipal principal: Member, model: Model): ModelAndView {
        val maxCount = 300
        val dashBoardList = ArrayList<Dashboard>()
//        val dateMonthMap = HashMap<String, List<Any>>()
        var dashboard: Dashboard?
        val memberRelateKeyList: List<MemberRelateKey>? = memberRelateKeyService.getMemberRelateKeyList(principal)
        var landingPageCount = 0
        var dataCount: Int
        if (memberRelateKeyList != null) {
            landingPageCount = memberRelateKeyList.size
            for((i, key) in memberRelateKeyList.withIndex()) {
                if(key.getLandingKey()?.getUsed() == true) {
                    dataCount = dataService.getDataCount(key.getLandingKey()!!)
                    dashboard = Dashboard(key.getLandingKey()!!.getTitle(), dataCount)
                    dashBoardList.add(dashboard)
                }
            }
        }

        model.addAttribute("maxCount", maxCount)
        model.addAttribute("list", dashBoardList)
        model.addAttribute("landingCount", landingPageCount)
        return ModelAndView("index")
    }

    /**
     * Login
     *
     * @return
     */
    @GetMapping("/login")
    fun login(): ModelAndView {
        return ModelAndView("login")
    }

}
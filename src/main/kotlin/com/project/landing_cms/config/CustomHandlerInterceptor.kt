package com.project.landing_cms.config

import com.project.landing_cms.model.dto.BoardDTO
import com.project.landing_cms.model.dto.LandingInfoDTO
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.model.entity.MemberRelateKey
import com.project.landing_cms.service.BoardService
import com.project.landing_cms.service.MemberRelateKeyService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class CustomHandlerInterceptor(
    private val memberRelateKeyService: MemberRelateKeyService,
    private val boardService: BoardService
    ): HandlerInterceptor {

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse,
                            handler: Any, modelAndView: ModelAndView?) {
        try {
            if ("/login" != request.requestURI) {
                val context = SecurityContextHolder.getContext()
                val member: Member = context.authentication.principal as Member
                val currentPage = request.requestURI
                val memberRelateKeyList: List<MemberRelateKey>? = memberRelateKeyService.getMemberRelateKeyList(member)
                val landingList = ArrayList<LandingInfoDTO.LandingSideBar>()
                val boardList = ArrayList<LandingInfoDTO.BoardSideBar>()
                if (memberRelateKeyList != null) {
                    for(key: MemberRelateKey in memberRelateKeyList) {
                        if(key.getLandingKey()?.getUsed() == true) {
                            if(key.getLandingKey()?.getType() == "DATA") {
                                landingList.add(LandingInfoDTO.LandingSideBar(key.getLandingKey()))
                            } else {
                                boardList.add(LandingInfoDTO.BoardSideBar(key.getLandingKey()))
                            }
                        }
                    }
                }
                modelAndView?.addObject("myId", member.getMNo())
                modelAndView?.addObject("currentPage", currentPage)
                modelAndView?.addObject("landingList", landingList)
                modelAndView?.addObject("boardList", boardList)
            }
        } catch (e: Exception) {
            modelAndView?.addObject("error", e.message)
        }
    }
}
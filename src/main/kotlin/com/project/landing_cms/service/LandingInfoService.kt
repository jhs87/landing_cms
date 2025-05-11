package com.project.landing_cms.service

import com.project.landing_cms.model.dto.LandingInfoDTO.LandingInfoReg
import com.project.landing_cms.model.entity.LandingInfo

interface LandingInfoService {

    fun save(landingInfoReg: LandingInfoReg): LandingInfo

    fun getLandingInfo(lNo: Long): LandingInfo

    fun getAllLanding(): List<LandingInfo>?

    fun usedToggle(id: Long, used: Boolean)
}
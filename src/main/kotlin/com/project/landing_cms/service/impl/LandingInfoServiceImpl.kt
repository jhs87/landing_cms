package com.project.landing_cms.service.impl

import com.project.landing_cms.model.dto.LandingInfoDTO
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.persistence.LandingInfoRepository
import com.project.landing_cms.service.LandingInfoService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class LandingInfoServiceImpl(private val landingInfoRepository: LandingInfoRepository): LandingInfoService {

    override fun save(landingInfoReg: LandingInfoDTO.LandingInfoReg): LandingInfo {
        return landingInfoRepository.save(LandingInfo(landingInfoReg))
    }

    override fun getLandingInfo(lNo: Long): LandingInfo {
        return landingInfoRepository.findById(lNo).orElseThrow { EntityNotFoundException() }
    }

    override fun getAllLanding(): List<LandingInfo>? {
        return landingInfoRepository.findAll()
    }

    override fun usedToggle(id: Long, used: Boolean) {
        landingInfoRepository.setUsed(id, used)
    }
}
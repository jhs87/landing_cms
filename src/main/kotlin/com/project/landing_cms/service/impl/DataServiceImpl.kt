package com.project.landing_cms.service.impl

import com.project.landing_cms.model.entity.Data
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.persistence.DataRepository
import com.project.landing_cms.service.DataService
import org.springframework.stereotype.Service

@Service
class DataServiceImpl(private val dataRepository: DataRepository): DataService {

    override fun getDataList(landingInfo: LandingInfo): List<Data>? {
        return dataRepository.findByLandingKeyAndIsDeletedFalse(landingInfo)
    }

    override fun getDataCount(landingInfo: LandingInfo): Int {
        return dataRepository.countByLandingKey(landingInfo)
    }
}
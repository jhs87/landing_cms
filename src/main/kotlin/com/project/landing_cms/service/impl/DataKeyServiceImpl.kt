package com.project.landing_cms.service.impl

import com.project.landing_cms.model.dto.DataKeyDTO
import com.project.landing_cms.model.entity.DataKey
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.persistence.DataKeyRepository
import com.project.landing_cms.service.DataKeyService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DataKeyServiceImpl(private val dataKeyRepository: DataKeyRepository): DataKeyService {

    override fun getDataKeyList(landingInfo: LandingInfo): List<DataKey>? {
        return dataKeyRepository.findByLandingKey(landingInfo)
    }

    override fun save(dataKeyList: ArrayList<DataKeyDTO.DataKeyInfo>) {
        val keyList = ArrayList<DataKey>()
        for(dataKeyInfo: DataKeyDTO.DataKeyInfo in dataKeyList) {
            keyList.add(DataKey(dataKeyInfo))
        }
        dataKeyRepository.saveAll(keyList)
    }

    override fun getDataKey(dkNo: Long): DataKey {
        return dataKeyRepository.findById(dkNo).orElseThrow { EntityNotFoundException() }
    }

    override fun delete(dkNo: Long) {
        dataKeyRepository.deleteById(dkNo)
    }

    override fun deleteAll(landingInfo: LandingInfo) {
        dataKeyRepository.deleteByLandingKey(landingInfo)
    }
}
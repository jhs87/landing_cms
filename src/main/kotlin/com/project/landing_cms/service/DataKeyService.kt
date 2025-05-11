package com.project.landing_cms.service

import com.project.landing_cms.model.dto.DataKeyDTO
import com.project.landing_cms.model.entity.DataKey
import com.project.landing_cms.model.entity.LandingInfo

interface DataKeyService {

    fun getDataKeyList(landingInfo: LandingInfo): List<DataKey>?

    fun save(dataKeyList: ArrayList<DataKeyDTO.DataKeyInfo>)

    fun getDataKey(dkNo: Long): DataKey

    fun delete(dkNo: Long)

    fun deleteAll(landingInfo: LandingInfo)
}
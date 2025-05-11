package com.project.landing_cms.service

import com.project.landing_cms.model.entity.Data
import com.project.landing_cms.model.entity.LandingInfo

interface DataService {

    fun getDataList(landingInfo: LandingInfo): List<Data>?

    fun getDataCount(landingInfo: LandingInfo): Int

}
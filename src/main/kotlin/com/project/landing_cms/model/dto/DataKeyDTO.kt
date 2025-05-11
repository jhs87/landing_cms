package com.project.landing_cms.model.dto

import com.project.landing_cms.model.entity.DataKey
import com.project.landing_cms.model.entity.LandingInfo
import lombok.Data

class DataKeyDTO {

    @Data
    class DataKeyInfo {
        var dkNo: Long? = null

        var keyName: String? = null

        var korName: String? = null

        var landingKey: LandingInfo? = null

        fun toDTO(dataKey: DataKey): DataKeyInfo {
            this.dkNo = dataKey.getDkNo()
            this.keyName = dataKey.getKeyName()
            this.korName = dataKey.getKorName()
            this.landingKey = dataKey.getLandingKey()
            return this
        }
    }

    @Data
    class DataKeySave {
        var lNo: Long? = null
        var keyNameList: List<String>? = null
        var korNameList: List<String>? = null
    }

    @Data
    class DataKeyDel {
        var lNo: Long? = null
        var dkNo: Long? = null
    }
}
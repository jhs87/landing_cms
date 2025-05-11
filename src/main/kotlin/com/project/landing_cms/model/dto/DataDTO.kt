package com.project.landing_cms.model.dto

import com.project.landing_cms.model.entity.LandingInfo
import lombok.Data
import java.time.LocalDateTime

@Data
class DataDTO(data: com.project.landing_cms.model.entity.Data) {
    var dNo: Long? = data.getDNo()
    var data: String? = data.getData()
    var regDate: LocalDateTime? = data.getRegDate()
    var landingKey: LandingInfo? = data.getLandingKey()
}
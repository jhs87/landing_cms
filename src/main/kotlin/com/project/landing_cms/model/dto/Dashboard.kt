package com.project.landing_cms.model.dto

import lombok.Data
import java.time.LocalDateTime

@Data
class Dashboard(landingTitle: String?, dataCount: Int?) {
    var landingTitle: String? = landingTitle
    var dataCount: Int? = dataCount
}
package com.project.landing_cms.model.dto

import lombok.Data
import org.json.JSONObject

@Data
class JsonDataDTO {

    var jsonData = HashMap<String, Any>()

    fun toJsonData(data: DataDTO): JsonDataDTO {
        val strData: String = data.data!!
        val json = JSONObject(strData)
        for(key: String in json.keys()) {
            this.jsonData[key] = json.getString(key)
        }
        return this
    }
}
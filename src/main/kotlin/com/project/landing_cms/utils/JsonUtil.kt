package com.project.landing_cms.utils

import com.project.landing_cms.model.dto.DataDTO
import com.project.landing_cms.model.dto.DataKeyDTO
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONObject
import org.springframework.stereotype.Component
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@Component
class JsonUtil {

    fun parseData(key: Any, value: String): String {
        val map: Map<String, String> = jsonMapper().readValue(value)
        return map[key].toString()
    }

    //Json 데이터 파싱 로직
    fun parseJsonData(dataKeyList: ArrayList<DataKeyDTO.DataKeyInfo>,
                      jsonDataList: ArrayList<DataDTO>,
                      dataIdList: ArrayList<Long?>?): ArrayList<List<String>> {
        val parseData: ArrayList<List<String>> = ArrayList()
        jsonDataList.forEach { x: DataDTO ->
            dataIdList?.add(x.dNo)
            val jObject = JSONObject(x.data)
            val data: ArrayList<String> = ArrayList()
            val keys: ArrayList<String> = ArrayList()
            val i: Iterator<Any> = jObject.keys()
            while (i.hasNext()) {
                keys.add(i.next().toString())
            }
            dataKeyList.forEach { y: DataKeyDTO.DataKeyInfo ->
                if(keys.contains(y.keyName!!)) {
                    data.add(jObject.getString(y.keyName))
                } else {
                    data.add("")
                }
            }
            data.add(x.regDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            parseData.add(data)
        }
        return parseData
    }

    //날짜 형식 인지 확인
    fun validationDate(date: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            dateFormat.isLenient = false
            dateFormat.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }
}
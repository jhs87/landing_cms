package com.project.landing_cms.controller

import com.project.landing_cms.model.dto.DataKeyDTO
import com.project.landing_cms.model.dto.DataKeyDTO.DataKeyInfo
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.service.DataKeyService
import com.project.landing_cms.service.LandingInfoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * Data key controller
 *
 * @property dataKeyService
 * @property landingInfoService
 * @constructor Create empty Data key controller
 */
@Controller
class DataKeyController(private val dataKeyService: DataKeyService,
                        private val landingInfoService: LandingInfoService) {

    /**
     * Get data key
     *
     * @param lNo
     * @return
     */
    @GetMapping("/dataKey")
    fun getDataKey(@RequestParam lNo: Long): ResponseEntity<List<DataKeyInfo>> {
        val landingInfo = landingInfoService.getLandingInfo(lNo)
        val dataKeyList = dataKeyService.getDataKeyList(landingInfo)?.map { dataKey -> DataKeyInfo().toDTO(dataKey) }
        return ResponseEntity(dataKeyList, HttpStatus.OK )
    }

    /**
     * Save data key
     *
     * @param lNo
     * @param keyNameList
     * @param korNameList
     * @return
     */
    @ResponseBody
    @PostMapping("/dataKey/save")
    fun saveDataKey(@ModelAttribute keyList : DataKeyDTO.DataKeySave): ResponseEntity<Any> {
        var landingInfo: LandingInfo = LandingInfo(null)
        if(keyList.lNo != null) {
            landingInfo = landingInfoService.getLandingInfo(keyList.lNo!!)
            // 기존 키 삭제
            dataKeyService.deleteAll(landingInfo)
        }
        val dataKeyList = ArrayList<DataKeyInfo>()
        if(keyList.keyNameList != null) {
            for((i, key) in keyList.keyNameList!!.withIndex()) {
                val dataKeyInfo = DataKeyInfo()
                dataKeyInfo.keyName = key
                dataKeyInfo.korName = keyList.korNameList?.get(i)
                dataKeyInfo.landingKey = landingInfo
                dataKeyList.add(dataKeyInfo)
            }
            dataKeyService.save(dataKeyList)
        }

        val getDataKeyList = dataKeyService.getDataKeyList(landingInfo)?.map { dataKey -> DataKeyInfo().toDTO(dataKey) }

        return ResponseEntity(getDataKeyList, HttpStatus.OK)
    }

    /**
     * Del data key
     *
     * @param lNo
     * @param dkNo
     * @return
     */
    @ResponseBody
    @DeleteMapping("/dataKey/del")
    fun delDataKey(@ModelAttribute dataKeyNo : DataKeyDTO.DataKeyDel): ResponseEntity<Any> {
        dataKeyNo.dkNo?.let { dataKeyService.delete(it) }
        val landingInfo = dataKeyNo.lNo?.let { landingInfoService.getLandingInfo(it) }
        val dataKeyList = landingInfo?.let { dataKeyService.getDataKeyList(it)?.map { dataKey -> DataKeyInfo().toDTO(dataKey) } }
        return ResponseEntity(dataKeyList, HttpStatus.OK)
    }
}
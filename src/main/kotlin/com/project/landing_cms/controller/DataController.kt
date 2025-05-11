package com.project.landing_cms.controller

import com.project.landing_cms.config.ExcelDownload
import com.project.landing_cms.model.dto.DataDTO
import com.project.landing_cms.model.dto.DataKeyDTO.DataKeyInfo
import com.project.landing_cms.model.dto.JsonDataDTO
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.service.DataKeyService
import com.project.landing_cms.service.DataService
import com.project.landing_cms.service.LandingInfoService
import com.project.landing_cms.utils.JsonUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

/**
 * Data controller
 *
 * @property dataService
 * @property dataKeyService
 * @property landingInfoService
 * @property excelDownload
 * @constructor Create empty Data controller
 */
@Controller
class DataController(private val dataService: DataService,
                     private val dataKeyService: DataKeyService,
                     private val landingInfoService: LandingInfoService,
                     private val excelDownload: ExcelDownload) {

    /**
     * Data index
     *
     * @param model
     * @param member
     * @param request
     * @param lNo
     * @return
     */
    @GetMapping("/data/{lNo}")
    fun dataIndex(model: Model, @AuthenticationPrincipal member: Member,
                  request: HttpServletRequest, @PathVariable("lNo") lNo: Long): ModelAndView {
        val landingInfo = landingInfoService.getLandingInfo(lNo)
        val dataKeyList: List<DataKeyInfo>? = dataKeyService.getDataKeyList(landingInfo)?.map { dataKey -> DataKeyInfo().toDTO(dataKey) }
        val dataList: List<DataDTO>? = dataService.getDataList(landingInfo)?.map { data -> DataDTO(data) }
        val jsonDataList:ArrayList<HashMap<String, Any>> = ArrayList()
        if (dataList != null) {
            var jsonData: JsonDataDTO
            for((i, data) in dataList.withIndex()) {
                jsonData = JsonDataDTO().toJsonData(data)
                jsonDataList.add(jsonData.jsonData)
            }
        }
        model.addAttribute("landingTitle", landingInfo.getTitle())
        model.addAttribute("landingNo", lNo)
        model.addAttribute("headList", dataKeyList)
        model.addAttribute("dataList", dataList)
        return ModelAndView("data/index")
    }

    /**
     * Data excel download
     *
     * @param lNo
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/excelDownload", produces = ["application/vnd.ms-excel"])
    fun dataExcelDownload(@RequestParam lNo: Long, model: Model, request: HttpServletRequest,
                          response: HttpServletResponse): String {
        excelDownload.addStaticAttribute("lNo", lNo)
        return "downloadExcel"
    }
}
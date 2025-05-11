package com.project.landing_cms.config

import com.project.landing_cms.model.dto.BoardDTO
import com.project.landing_cms.model.dto.DataDTO
import com.project.landing_cms.model.dto.DataKeyDTO
import com.project.landing_cms.service.BoardService
import com.project.landing_cms.service.DataKeyService
import com.project.landing_cms.service.DataService
import com.project.landing_cms.service.LandingInfoService
import com.project.landing_cms.utils.JsonUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import org.springframework.stereotype.Component
import org.springframework.web.servlet.view.document.AbstractXlsView
import java.io.IOException
import java.lang.reflect.Field
import java.net.URLEncoder
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Excel download
 *
 * @constructor Create empty Excel download
 */
@Component("downloadExcel")
class ExcelDownload(private val dataService: DataService,
                    private val dataKeyService: DataKeyService,
                    private val boardService: BoardService,
                    private val landingInfoService: LandingInfoService): AbstractXlsView() {

    @Throws(Exception::class, IOException::class)
    override fun buildExcelDocument(model: Map<String, Any>, wb: Workbook,
                                    request: HttpServletRequest, response: HttpServletResponse) {
        println("========Excel Download Start=========")

        val landingInfo = landingInfoService.getLandingInfo(model["lNo"] as Long)
        val dataKeyList: List<DataKeyDTO.DataKeyInfo>?
        val boardList: List<BoardDTO.BoardExcel>?
        val sheet = wb.createSheet()
        sheet.defaultColumnWidth = 15
        val headRow = sheet.createRow(0);
        val cellStyle = wb.createCellStyle()
        if(landingInfo.getType() == "BOARD") {
            boardList = boardService.getBoardListRegDateDesc(landingInfo)?.map {
                    board -> BoardDTO.BoardExcel().toDto(board) }

            // 셀 스타일
            cellStyle.borderTop = BorderStyle.THIN
            cellStyle.borderBottom = BorderStyle.THIN
            cellStyle.borderLeft = BorderStyle.THIN
            cellStyle.borderRight = BorderStyle.THIN

            // 셀 배경색
            cellStyle.fillForegroundColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.index
            cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            // 헤더 입력
            for((i, title: Field) in BoardDTO.BoardExcel().javaClass.declaredFields.withIndex()) {
                val cell = headRow.createCell(i)
                cell.cellStyle = cellStyle
                cell.setCellValue(title.name)
            }

            if (boardList != null) {
                for ((i, board) in boardList.withIndex()) {
                    val row = sheet.createRow(i + 1)
                    var cell = row.createCell(0)
                    cell.setCellValue(board.title)
                    cell = row.createCell(1)
                    cell.setCellValue(board.contents)
                    cell = row.createCell(2)
                    cell.setCellValue(board.regDate?.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")))
                    cell = row.createCell(3)
                    cell.setCellValue(board.udtDate?.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")))
                    cell = row.createCell(4)
                    cell.setCellValue(board.regMember)
                }
            }
        } else if(landingInfo.getType() == "DATA") {
            dataKeyList = dataKeyService.getDataKeyList(landingInfo)?.map{
                    dataKey -> DataKeyDTO.DataKeyInfo().toDTO(dataKey) }
            val dataList: List<DataDTO>? = dataService.getDataList(landingInfo)?.map { data -> DataDTO(data) }

            // 셀 스타일
            cellStyle.borderTop = BorderStyle.THIN
            cellStyle.borderBottom = BorderStyle.THIN
            cellStyle.borderLeft = BorderStyle.THIN
            cellStyle.borderRight = BorderStyle.THIN

            // 셀 배경색
            cellStyle.fillForegroundColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.index
            cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            // 헤더 입력
            dataKeyList?.forEach { x: DataKeyDTO.DataKeyInfo ->
                val cell = headRow.createCell(dataKeyList.indexOf(x))
                cell.cellStyle = cellStyle
                cell.setCellValue(x.korName)
            }
            val regDate = headRow.createCell(dataKeyList!!.size)
            regDate.cellStyle = cellStyle
            regDate.setCellValue("참여일시")

            //랜딩데이터 Json 파싱후 데이터를 담을 개체
            val parseData: ArrayList<List<String>> = JsonUtil().parseJsonData(dataKeyList as ArrayList, dataList as ArrayList, null)
            for ((i, list) in dataList.withIndex()) {
                val row = sheet.createRow(i + 1)
                val data = parseData[i]
                for((j, str) in data.withIndex()) {
                    val cell = row.createCell(j)
                    if (JsonUtil().validationDate(data[j])) {
                        cell.setCellValue(data[j].format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")))
                    } else {
                        cell.setCellValue(data[j])
                    }
                }
            }
        }

        val fileName = URLEncoder.encode(landingInfo.getTitle(), "UTF-8") + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        response.setHeader("Content-Disposition", "attachment; filename=\"$fileName.xls\"")
        wb.write(response.outputStream)
        println("========Excel Download End=========")
    }
}
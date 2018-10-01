package com.rongxingyn.xyf.web.backstage.goods.datum

import com.rongxingyn.xyf.config.Workbook
import com.rongxingyn.xyf.config.XYFProperties
import com.rongxingyn.xyf.service.backstage.goods.classify.GoodsClassifyService
import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsDatumService
import com.rongxingyn.xyf.service.common.UploadService
import com.rongxingyn.xyf.service.utils.RequestUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.Resource

@RestController
@RequestMapping("/web/backstage/goods")
open class GoodsDatumRestController {

    @Resource
    open lateinit var goodsDatumService: GoodsDatumService

    @Resource
    open lateinit var goodsClassifyService: GoodsClassifyService

    @Resource
    open lateinit var uploadService: UploadService

    @Resource
    open lateinit var xyfProperties: XYFProperties

    /**
     * 商品列表数据
     *
     * @param serverWebExchange 请求数据
     * @return list data
     */
    @GetMapping("/datum/data")
    fun data(serverWebExchange: ServerWebExchange): Mono<DataTablesUtils<GoodsBean>> {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        val headers = ArrayList<String>()
        headers.add("select")
        headers.add("goods_name")
        headers.add("goods_price")
        headers.add("goods_recommend")
        headers.add("classify_name")
        headers.add("goods_serial")
        headers.add("goods_is_del")
        headers.add("operator")
        val dataTablesUtils = DataTablesUtils<GoodsBean>(serverWebExchange.request, headers)
        val records = goodsDatumService.findAllByPage(dataTablesUtils)
        var goodsBean: List<GoodsBean> = ArrayList()
        if (!ObjectUtils.isEmpty(records) && records.isNotEmpty) {
            goodsBean = records.into(GoodsBean::class.java)
        }
        dataTablesUtils.data = goodsBean
        dataTablesUtils.setiTotalRecords(goodsDatumService.countAll().toLong())
        dataTablesUtils.setiTotalDisplayRecords(goodsDatumService.countByCondition(dataTablesUtils).toLong())
        return Mono.just(dataTablesUtils)
    }

    /**
     * 获取类别数据
     *
     * @return 数据
     */
    @GetMapping("/datum/classifies")
    fun classifies(): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").listData(goodsClassifyService.findByState(0))
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    @PostMapping("/datum/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") filePart: FilePart): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val path =   xyfProperties.getConstants().staticImages + Workbook.DIRECTORY_SPLIT
        val fileData = uploadService.upload(filePart, path, Workbook.GOODS_DATUM_FILE)
        if (fileData.isPresent) {
            ajaxUtils.success().msg("上传文件成功").put("info", fileData.get())
                    .put("picPath", xyfProperties.getConstants().staticImages!!)
        } else {
            ajaxUtils.fail().msg("上传文件失败")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}
package com.rongxingyn.xyf.web.backstage.goods.datum

import com.rongxingyn.xyf.config.Workbook
import com.rongxingyn.xyf.config.XYFProperties
import com.rongxingyn.xyf.domain.tables.pojos.Goods
import com.rongxingyn.xyf.domain.tables.pojos.GoodsPics
import com.rongxingyn.xyf.domain.tables.pojos.TableTime
import com.rongxingyn.xyf.service.backstage.TableTimeService
import com.rongxingyn.xyf.service.backstage.goods.classify.GoodsClassifyService
import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsDatumService
import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsPicsService
import com.rongxingyn.xyf.service.common.FileSystemService
import com.rongxingyn.xyf.service.utils.UUIDUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import com.rongxingyn.xyf.web.utils.SmallPropsUtils
import com.rongxingyn.xyf.web.vo.backstage.goods.datum.DatumAddVo
import com.rongxingyn.xyf.web.vo.backstage.goods.datum.DatumEditVo
import com.rongxingyn.xyf.web.vo.backstage.goods.datum.DatumStateVo
import com.rongxingyn.xyf.web.vo.backstage.goods.datum.DatumValidVo
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.util.CollectionUtils
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.sql.Timestamp
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/web/backstage/goods")
open class GoodsDatumRestController {

    @Resource
    open lateinit var goodsDatumService: GoodsDatumService

    @Resource
    open lateinit var goodsClassifyService: GoodsClassifyService

    @Resource
    open lateinit var goodsPicsService: GoodsPicsService

    @Resource
    open lateinit var tableTimeService: TableTimeService

    @Resource
    open lateinit var fileSystemService: FileSystemService

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

    /**
     * 上传图片
     *
     * @param filePart 数据
     * @return 文件信息
     */
    @PostMapping("/datum/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") filePart: FilePart): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val path = xyfProperties.getConstants().staticImages + Workbook.DIRECTORY_SPLIT
        val fileData = fileSystemService.upload(filePart, path, Workbook.GOODS_DATUM_FILE)
        if (fileData.isPresent) {
            ajaxUtils.success().msg("上传文件成功").put("info", fileData.get())
                    .put("picPath", xyfProperties.getConstants().staticImages!!)
        } else {
            ajaxUtils.fail().msg("上传文件失败")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 删除图片
     *
     * @param goodsPic 路径
     * @return true or false
     */
    @GetMapping("/datum/del_pic")
    fun delPic(@PathParam("goodsPic") goodsPic: String): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of().success().msg("删除成功")
        fileSystemService.delete(goodsPic)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 商品校验
     *
     * @param datumValidVo 请求数据
     * @param bindingResult 校验
     * @return true or false
     */
    @GetMapping("/datum/valid")
    fun valid(@Valid datumValidVo: DatumValidVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            if (0 == datumValidVo.type) {
                val goods = goodsDatumService.findByGoodsName(datumValidVo.goodsName!!)
                if (CollectionUtils.isEmpty(goods)) {
                    ajaxUtils.success().msg("商品名不存在")
                } else {
                    ajaxUtils.fail().msg("商品名已存在")
                }
            } else if (1 == datumValidVo.type) {
                if (ObjectUtils.isEmpty(datumValidVo.goodsId)) {
                    ajaxUtils.fail().msg("商品ID不能为空")
                } else {
                    val goods = goodsDatumService.findByGoodsNameNeGoodsId(datumValidVo.goodsName!!, datumValidVo.goodsId!!)
                    if (goods.isEmpty()) {
                        ajaxUtils.success().msg("商品名不存在")
                    } else {
                        ajaxUtils.fail().msg("商品名已存在")
                    }
                }
            } else {
                ajaxUtils.fail().msg("未知的校验类型")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 商品添加
     *
     * @param datumAddVo 请求数据
     * @param bindingResult 校验
     * @return true or false
     */
    @PostMapping("/datum/save")
    fun add(@Valid datumAddVo: DatumAddVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val goods = Goods()
            val goodsId = UUIDUtils.getUUID()
            goods.goodsId = goodsId
            goods.goodsName = datumAddVo.goodsName
            goods.goodsPrice = datumAddVo.goodsPrice
            goods.goodsBrief = datumAddVo.goodsBrief
            goods.goodsRecommend = datumAddVo.goodsRecommend
            goods.goodsSerial = datumAddVo.goodsSerial
            goods.goodsIsDel = datumAddVo.goodsIsDel
            goods.classifyId = datumAddVo.classifyId
            goodsDatumService.save(goods)

            val goodsPics = GoodsPics()
            goodsPics.goodsId = goodsId
            goodsPics.picUrl = datumAddVo.goodsPic
            goodsPicsService.save(goodsPics)
            saveTableTime()
            ajaxUtils.success().msg("保存数据成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }

        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 商品添加
     *
     * @param datumEditVo 请求数据
     * @param bindingResult 校验
     * @return true or false
     */
    @PostMapping("/datum/update")
    fun edit(@Valid datumEditVo: DatumEditVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val goods = goodsDatumService.findById(datumEditVo.goodsId!!)
            goods.goodsName = datumEditVo.goodsName
            goods.goodsPrice = datumEditVo.goodsPrice
            goods.goodsBrief = datumEditVo.goodsBrief
            goods.goodsRecommend = datumEditVo.goodsRecommend
            goods.goodsSerial = datumEditVo.goodsSerial
            goods.goodsIsDel = datumEditVo.goodsIsDel
            goods.classifyId = datumEditVo.classifyId
            goodsDatumService.update(goods)

            // 先删除图片
            goodsPicsService.deleteByGoodsId(datumEditVo.goodsId!!)
            val goodsPics = GoodsPics()
            goodsPics.goodsId = datumEditVo.goodsId
            goodsPics.picUrl = datumEditVo.goodsPic
            goodsPicsService.save(goodsPics)
            saveTableTime()
            ajaxUtils.success().msg("更新数据成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }

        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 批量更改状态
     *
     * @param datumStateVo 数据
     * @return true注销成功
     */
    @PutMapping("/datum/state")
    fun state(@Valid datumStateVo: DatumStateVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            if (StringUtils.hasLength(datumStateVo.goodsIds)) {
                goodsDatumService.updateState(SmallPropsUtils.StringIdsToStringList(datumStateVo.goodsIds!!), datumStateVo.goodsIsDel!!)
                saveTableTime()
                ajaxUtils.success().msg("更新状态成功")
            } else {
                ajaxUtils.fail().msg("更新状态失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 保存时间到tableTime
     */
    private fun saveTableTime() {
        val tableTime = TableTime()
        tableTime.tableName = "GOODS"
        tableTime.dealTime = Timestamp(System.currentTimeMillis())
        tableTimeService.save(tableTime)
    }
}
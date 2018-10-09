package com.rongxingyn.xyf.web.reception

import com.rongxingyn.xyf.domain.tables.pojos.Feedback
import com.rongxingyn.xyf.service.backstage.feedback.FeedbackService
import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsDatumService
import com.rongxingyn.xyf.service.utils.DateTimeUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.bean.reception.PaginationBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import com.rongxingyn.xyf.web.vo.reception.ContactVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@RequestMapping("/data")
open class ReceptionRestController {

    @Resource
    open lateinit var goodsDatumService: GoodsDatumService

    @Resource
    open lateinit var feedbackService: FeedbackService

    /**
     * 商品列表数据
     *
     * @param paginationBean 数据
     * @return true or false
     */
    @GetMapping("/goods/list")
    fun goodsList(paginationBean: PaginationBean): Mono<DataTablesUtils<GoodsBean>> {
        val dataTablesUtils = DataTablesUtils<GoodsBean>(paginationBean)
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
     * 获取商品详情
     *
     * @param goodsId 商品id
     * @return 数据
     */
    @GetMapping("/goods/item/{goodsId}")
    fun goodsItem(@PathVariable("goodsId") goodsId: String): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val data = goodsDatumService.findByIdRelation(goodsId)
        if (data.isPresent) {
            ajaxUtils.success().msg("获取数据成功").put("goods", data.get().into(GoodsBean::class.java))
        } else {
            ajaxUtils.fail().msg("获取数据失败")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 保存反馈问题
     *
     * @param contactVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @GetMapping("/contact/save")
    fun contactSave(@Valid contactVo: ContactVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val feedback = Feedback()
            feedback.customerName = contactVo.customerName
            feedback.customerContact = contactVo.customerContact
            feedback.content = contactVo.content
            feedback.createDate = DateTimeUtils.getNow()
            feedbackService.save(feedback)
            ajaxUtils.success().msg("保存数据成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

}
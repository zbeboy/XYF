package com.rongxingyn.xyf.web.backstage.feedback

import com.rongxingyn.xyf.domain.tables.pojos.TableTime
import com.rongxingyn.xyf.service.backstage.TableTimeService
import com.rongxingyn.xyf.service.backstage.feedback.FeedbackService
import com.rongxingyn.xyf.service.utils.DateTimeUtils
import com.rongxingyn.xyf.web.bean.backstage.feedback.FeedbackBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import com.rongxingyn.xyf.web.utils.SmallPropsUtils
import com.rongxingyn.xyf.web.vo.backstage.feedback.FeedbackDealVo
import com.rongxingyn.xyf.web.vo.backstage.feedback.FeedbackRemarkVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.sql.Timestamp
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@RequestMapping("/web/backstage/feedback")
open class FeedbackRestController {

    @Resource
    open lateinit var feedbackService: FeedbackService

    @Resource
    open lateinit var tableTimeService: TableTimeService

    /**
     * 反馈列表数据
     *
     * @param serverWebExchange 请求数据
     * @return list data
     */
    @GetMapping("/data")
    fun data(serverWebExchange: ServerWebExchange): Mono<DataTablesUtils<FeedbackBean>> {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        val headers = ArrayList<String>()
        headers.add("select")
        headers.add("customer_name")
        headers.add("customer_contact")
        headers.add("content")
        headers.add("create_date")
        headers.add("has_deal")
        headers.add("remark")
        headers.add("operator")
        val dataTablesUtils = DataTablesUtils<FeedbackBean>(serverWebExchange.request, headers)
        val records = feedbackService.findAllByPage(dataTablesUtils)
        var feedbackBean: List<FeedbackBean> = ArrayList()
        if (!ObjectUtils.isEmpty(records) && records.isNotEmpty) {
            feedbackBean = records.into(FeedbackBean::class.java)
            feedbackBean.forEach { i -> i.createDateStr = DateTimeUtils.formatDate(i.createDate) }
        }
        dataTablesUtils.data = feedbackBean
        dataTablesUtils.setiTotalRecords(feedbackService.countAll().toLong())
        dataTablesUtils.setiTotalDisplayRecords(feedbackService.countByCondition(dataTablesUtils).toLong())
        return Mono.just(dataTablesUtils)
    }

    /**
     * 更新状态
     *
     * @param feedbackDealVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @PutMapping("/state")
    fun state(@Valid feedbackDealVo: FeedbackDealVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            feedbackService.updateState(feedbackDealVo.feedbackId!!, feedbackDealVo.hasDeal!!)
            saveTableTime()
            ajaxUtils.success().msg("更新成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 更新状态
     *
     * @param feedbackRemarkVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @PutMapping("/remark")
    fun state(@Valid feedbackRemarkVo: FeedbackRemarkVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            feedbackService.updateRemark(feedbackRemarkVo.feedbackId!!, feedbackRemarkVo.remark!!)
            saveTableTime()
            ajaxUtils.success().msg("更新成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 删除
     *
     * @param feedbackIds 数据
     * @return true or false
     */
    @DeleteMapping("/delete/{feedbackIds}")
    fun delete(@PathVariable("feedbackIds") feedbackIds: String): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (SmallPropsUtils.StringIdsIsNumber(feedbackIds)) {
            val ids = SmallPropsUtils.StringIdsToList(feedbackIds)
            ids.forEach { id -> feedbackService.deleteById(id) }
            saveTableTime()
            ajaxUtils.success().msg("删除成功")
        } else {
            ajaxUtils.fail().msg("删除失败")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 保存时间到tableTime
     */
    private fun saveTableTime() {
        val tableTime = TableTime()
        tableTime.tableName = "FEEDBACK"
        tableTime.dealTime = Timestamp(System.currentTimeMillis())
        tableTimeService.save(tableTime)
    }
}
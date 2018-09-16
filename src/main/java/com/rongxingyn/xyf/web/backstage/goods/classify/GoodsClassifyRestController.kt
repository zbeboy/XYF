package com.rongxingyn.xyf.web.backstage.goods.classify

import com.rongxingyn.xyf.service.backstage.goods.classify.GoodsClassifyService
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.ClassifyBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.ArrayList
import javax.annotation.Resource

@RestController
@RequestMapping("/web/goods")
open class GoodsClassifyRestController {

    @Resource
    open lateinit var goodsClassifyService: GoodsClassifyService

    @GetMapping("/classify/data")
    fun data(serverWebExchange: ServerWebExchange): Mono<DataTablesUtils<ClassifyBean>> {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        val headers = ArrayList<String>()
        headers.add("classify_id")
        headers.add("classify_name")
        headers.add("operator")
        val dataTablesUtils = DataTablesUtils<ClassifyBean>(serverWebExchange.request, headers)
        val records = goodsClassifyService.findAllByPage(dataTablesUtils)
        var classifyBean: List<ClassifyBean> = ArrayList()
        if (!ObjectUtils.isEmpty(records) && records.isNotEmpty) {
            classifyBean = records.into(ClassifyBean::class.java)
        }
        dataTablesUtils.data = classifyBean
        dataTablesUtils.setiTotalRecords(goodsClassifyService.countAll().toLong())
        dataTablesUtils.setiTotalDisplayRecords(goodsClassifyService.countByCondition(dataTablesUtils).toLong())
        return Mono.just(dataTablesUtils)
    }
}
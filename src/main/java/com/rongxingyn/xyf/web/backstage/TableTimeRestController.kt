package com.rongxingyn.xyf.web.backstage

import com.rongxingyn.xyf.service.backstage.TableTimeService
import com.rongxingyn.xyf.service.utils.DateTimeUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.TableTimeBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.annotation.Resource

@RestController
@RequestMapping("/web/backstage/table")
open class TableTimeRestController {

    @Resource
    open lateinit var tableTimeService: TableTimeService

    /**
     * 查询表
     *
     * @param table id
     * @return 数据
     */
    @GetMapping("/{table}")
    fun getTable(@PathVariable table: String): Mono<TableTimeBean> {
        var tableTime = TableTimeBean()
        val data = tableTimeService.findById(table)
        if (data.isPresent) {
            tableTime = data.get().into(TableTimeBean::class.java)
            tableTime.dealTimeStr = DateTimeUtils.formatDate(tableTime.dealTime)
        }
        return Mono.just(tableTime)
    }
}
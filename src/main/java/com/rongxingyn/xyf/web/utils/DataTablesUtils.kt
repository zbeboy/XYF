package com.rongxingyn.xyf.web.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.ObjectUtils

/**
 * Created by zbeboy 2017-11-26 .
 **/
open class DataTablesUtils<T> {
    /*
    返回的数据
     */
    var data: List<T>? = null

    var draw: Int = 0

    /*
    数据总数
     */
    private var iTotalRecords: Long = 0

    /*
    过滤条件下的总数
     */
    private var iTotalDisplayRecords: Long = 0

    /*
    从哪页开始
     */
    var start: Int = 0

    /*
    页大小
     */
    var length: Int = 0

    /*
    哪列排序 是索引
     */
    var orderColumn: Int = 0

    /*
    列
     */
    var headers: List<String>? = null

    /*
    哪列排序 是数据库对应列名
     */
    var orderColumnName: String? = null

    /*
    asc or desc
     */
    var orderDir: String? = "asc"

    /*
    当开启过滤时，过滤参数
     */
    var searchValue: String? = null

    /*
    额外搜索参数
     */
    var extraSearch: String? = null

    /*
    当前页号
     */
    var extraPage: Int = 0

    /*
    object extraSearch
     */
    var search: JSONObject? = null

    companion object {
        @JvmStatic
        fun <T> of(): DataTablesUtils<T> {
            return DataTablesUtils()
        }
    }

    constructor()

    constructor(request: ServerHttpRequest, headers: List<String>) {
        val startParam = request.queryParams.getFirst("start")
        val lengthParam = request.queryParams.getFirst("length")
        val orderColumnParam = request.queryParams.getFirst("order[0][column]")
        val orderDirParam = request.queryParams.getFirst("order[0][dir]")
        val searchValueParam = request.queryParams.getFirst("search[value]")
        val extraSearchParam = request.queryParams.getFirst("extra_search")
        val extraPage = request.queryParams.getFirst("extra_page")
        val dramParam = request.queryParams.getFirst("draw")

        if (NumberUtils.isDigits(startParam)) {
            this.start = NumberUtils.toInt(startParam)
        }

        if (NumberUtils.isDigits(lengthParam)) {
            this.length = NumberUtils.toInt(lengthParam)
        }

        if (NumberUtils.isDigits(orderColumnParam)) {
            this.orderColumn = NumberUtils.toInt(orderColumnParam)
        }

        if (!ObjectUtils.isEmpty(headers) && !headers.isEmpty() && headers.size > this.orderColumn) {
            this.orderColumnName = headers[this.orderColumn]
            this.headers = headers
        }

        if (StringUtils.isNotBlank(orderDirParam)) {
            this.orderDir = orderDirParam
        }

        if (StringUtils.isNotBlank(searchValueParam)) {
            this.searchValue = searchValueParam
        }

        if (StringUtils.isNotBlank(extraSearchParam)) {
            this.extraSearch = extraSearchParam
            this.search = JSON.parseObject(extraSearchParam)
        }

        if (NumberUtils.isDigits(extraPage)) {
            this.extraPage = NumberUtils.toInt(extraPage)
        }

        if (NumberUtils.isDigits(dramParam)) {
            this.draw = NumberUtils.toInt(dramParam)
        }

    }

    fun getiTotalRecords(): Long {
        return iTotalRecords
    }

    fun setiTotalRecords(iTotalRecords: Long) {
        this.iTotalRecords = iTotalRecords
    }

    fun getiTotalDisplayRecords(): Long {
        return iTotalDisplayRecords
    }

    fun setiTotalDisplayRecords(iTotalDisplayRecords: Long) {
        this.iTotalDisplayRecords = iTotalDisplayRecords
    }
}
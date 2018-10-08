package com.rongxingyn.xyf.web.bean.reception

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.apache.commons.lang3.StringUtils

class PaginationBean {
    var pageNumber: Int? = null
    var pageSize: Int? = null
    var sortName: String? = null
    var sortOrder: String? = null
    var extraSearch: String? = null
    var search: JSONObject? = null

    fun buildSearch(): JSONObject? {
        if (StringUtils.isNotBlank(extraSearch)) {
            this.search = JSON.parseObject(extraSearch)
        }
        return this.search
    }
}
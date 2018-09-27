package com.rongxingyn.xyf.web.utils

/**
 * ajax 数据
 *
 * @version 1.3
 */
open class AjaxUtils() {

    var state: Boolean = false// 消息状态
    var msg: String = ""// 消息
    var mapResult: Map<String, Any> = HashMap()// map数据
    var listResult: List<Any> = ArrayList()// list数据
    var objectResult: Any = String()// 单个对象数据
    val data = HashMap<String, Any>()   // 数据
    var paginationUtils: PaginationUtils = PaginationUtils()// 分页数据

    companion object {
        @JvmStatic
        fun of(): AjaxUtils {
            return AjaxUtils()
        }
    }

    fun success(): AjaxUtils {
        this.state = true
        return this
    }

    fun fail(): AjaxUtils {
        this.state = false
        return this
    }

    fun msg(msg: String): AjaxUtils {
        this.msg = msg
        return this
    }

    fun obj(obj: Any): AjaxUtils {
        this.objectResult = obj
        return this
    }

    fun mapData(map: Map<String, Any>): AjaxUtils {
        this.mapResult = map
        return this
    }

    fun listData(list: List<Any>): AjaxUtils {
        this.listResult = list
        return this
    }

    fun paginationUtils(paginationUtils: PaginationUtils): AjaxUtils {
        this.paginationUtils = paginationUtils
        return this
    }

    fun put(attribute: String, any: Any): AjaxUtils {
        this.data[attribute] = any
        return this
    }

    fun send(): Map<String, Any> {
        this.data["state"] = this.state
        this.data["msg"] = this.msg
        this.data["mapResult"] = this.mapResult
        this.data["listResult"] = this.listResult
        this.data["obj"] = this.objectResult
        this.data["pagination"] = this.paginationUtils
        return this.data
    }
}
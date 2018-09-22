package com.rongxingyn.xyf.web.utils

open class AjaxUtils() {

    var state: Boolean = false//消息状态
    var msg: String = ""//消息
    val data = HashMap<String, Any>()   // 数据
    var paginationUtils: PaginationUtils = PaginationUtils()//分页数据

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

    fun paginationUtils(paginationUtils: PaginationUtils): AjaxUtils {
        this.paginationUtils = paginationUtils
        return this
    }

    fun put(attribute: String, any: Any): AjaxUtils {
        this.data.put(attribute, any)
        return this
    }

    fun send(): Map<String, Any> {
        this.data.put("state", this.state)
        this.data.put("msg", this.msg)
        this.data.put("pagination", this.paginationUtils)
        return this.data
    }
}
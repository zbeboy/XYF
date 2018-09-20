package com.rongxingyn.xyf.web.backstage.goods.classify

import com.rongxingyn.xyf.domain.tables.pojos.Classify
import com.rongxingyn.xyf.service.backstage.goods.classify.GoodsClassifyService
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.ClassifyBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import com.rongxingyn.xyf.web.vo.backstage.goods.classify.ClassifyAddVo
import com.rongxingyn.xyf.web.vo.backstage.goods.classify.ClassifyValidVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.CollectionUtils
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid
import kotlin.collections.HashMap

@RestController
@RequestMapping("/web/goods")
open class GoodsClassifyRestController {

    @Resource
    open lateinit var goodsClassifyService: GoodsClassifyService

    /**
     * 商品分类列表数据
     *
     * @param serverWebExchange 请求数据
     * @return list data
     */
    @GetMapping("/classify/data")
    fun data(serverWebExchange: ServerWebExchange): Mono<DataTablesUtils<ClassifyBean>> {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        val headers = ArrayList<String>()
        headers.add("select")
        headers.add("classify_id")
        headers.add("classify_name")
        headers.add("classify_is_del")
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

    /**
     * 商品分类名校验
     *
     * @param classifyValidVo 请求数据
     * @param bindingResult 校验
     * @return true or false
     */
    @GetMapping("/classify/valid")
    fun valid(@Valid classifyValidVo: ClassifyValidVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val data = HashMap<String, Any>()
        if (!bindingResult.hasErrors()) {
            if (0 == classifyValidVo.type) {
                val classify = goodsClassifyService.findByClassifyName(classifyValidVo.classifyName!!)
                if (CollectionUtils.isEmpty(classify)) {
                    data["state"] = true
                    data["msg"] = "类别名不存在"
                } else {
                    data["state"] = false
                    data["msg"] = "类别名已存在"
                }
            } else if (1 == classifyValidVo.type) {
                val classify = goodsClassifyService.findByClassifyNameNeClassifyId(classifyValidVo.classifyName!!, classifyValidVo.classifyId!!)
                if (classify.isEmpty()) {
                    data["state"] = true
                    data["msg"] = "类别名不存在"
                } else {
                    data["state"] = false
                    data["msg"] = "类别名已存在"
                }
            } else {
                data["state"] = false
                data["msg"] = "未知的校验类型"
            }
        } else {
            data["state"] = false
            data["msg"] = bindingResult.fieldError!!.defaultMessage!!
        }
        return Mono.just(ResponseEntity<Map<String, Any>>(data, HttpStatus.OK))
    }

    /**
     * 商品分类添加
     *
     * @param classifyAddVo 请求数据
     * @param bindingResult 校验
     * @return true or false
     */
    @PostMapping("/classify/add")
    fun add(@Valid classifyAddVo: ClassifyAddVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val data = HashMap<String, Any>()
        if (!bindingResult.hasErrors()) {
            val classify = Classify()
            classify.classifyName = classifyAddVo.classifyName
            classify.classifyIsDel = classifyAddVo.classifyIsDel
            goodsClassifyService.save(classify)
            data["state"] = true
            data["msg"] = "保存数据成功"
        } else {
            data["state"] = false
            data["msg"] = bindingResult.fieldError!!.defaultMessage!!
        }

        return Mono.just(ResponseEntity<Map<String, Any>>(data, HttpStatus.OK))
    }
}
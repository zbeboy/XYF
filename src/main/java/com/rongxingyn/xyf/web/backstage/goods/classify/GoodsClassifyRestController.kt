package com.rongxingyn.xyf.web.backstage.goods.classify

import com.rongxingyn.xyf.domain.tables.pojos.Classify
import com.rongxingyn.xyf.domain.tables.pojos.TableTime
import com.rongxingyn.xyf.service.backstage.TableTimeService
import com.rongxingyn.xyf.service.backstage.goods.classify.GoodsClassifyService
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.ClassifyBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import com.rongxingyn.xyf.web.utils.SmallPropsUtils
import com.rongxingyn.xyf.web.vo.backstage.goods.classify.ClassifyAddVo
import com.rongxingyn.xyf.web.vo.backstage.goods.classify.ClassifyEditVo
import com.rongxingyn.xyf.web.vo.backstage.goods.classify.ClassifyStateVo
import com.rongxingyn.xyf.web.vo.backstage.goods.classify.ClassifyValidVo
import org.jooq.util.derby.sys.Sys
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

@RestController
@RequestMapping("/web/goods")
open class GoodsClassifyRestController {

    @Resource
    open lateinit var goodsClassifyService: GoodsClassifyService

    @Resource
    open lateinit var tableTimeService: TableTimeService

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
     * 查询类别
     *
     * @param classify 类别id
     * @return 数据
     */
    @GetMapping("/classify/query/{classify}")
    fun getClassify(@PathVariable classify: Int): Mono<Classify> {
        return Mono.just(goodsClassifyService.findById(classify))
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
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            if (0 == classifyValidVo.type) {
                val classify = goodsClassifyService.findByClassifyName(classifyValidVo.classifyName!!)
                if (CollectionUtils.isEmpty(classify)) {
                    ajaxUtils.success().msg("类别名不存在")
                } else {
                    ajaxUtils.fail().msg("类别名已存在")
                }
            } else if (1 == classifyValidVo.type) {
                val classify = goodsClassifyService.findByClassifyNameNeClassifyId(classifyValidVo.classifyName!!, classifyValidVo.classifyId!!)
                if (classify.isEmpty()) {
                    ajaxUtils.success().msg("类别名不存在")
                } else {
                    ajaxUtils.fail().msg("类别名已存在")
                }
            } else {
                ajaxUtils.fail().msg("未知的校验类型")
            }
        } else {
            ajaxUtils.fail().msg("bindingResult.fieldError!!.defaultMessage!!")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
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
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val classify = Classify()
            classify.classifyName = classifyAddVo.classifyName
            classify.classifyIsDel = classifyAddVo.classifyIsDel
            goodsClassifyService.save(classify)
            saveTableTime()
            ajaxUtils.success().msg("保存数据成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }

        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 商品分类更新
     *
     * @param classifyEditVo 请求数据
     * @param bindingResult 校验
     * @return true or false
     */
    @PutMapping("/classify/edit")
    fun edit(@Valid classifyEditVo: ClassifyEditVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val classify = goodsClassifyService.findById(classifyEditVo.classifyId!!)
            classify.classifyName = classifyEditVo.classifyName
            classify.classifyIsDel = classifyEditVo.classifyIsDel
            goodsClassifyService.update(classify)
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
     * @param classifyStateVo 数据
     * @return true注销成功
     */
    @PutMapping("/classify/state")
    fun state(@Valid classifyStateVo: ClassifyStateVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            if (StringUtils.hasLength(classifyStateVo.classifyIds) && SmallPropsUtils.StringIdsIsNumber(classifyStateVo.classifyIds!!)) {
                goodsClassifyService.updateState(SmallPropsUtils.StringIdsToList(classifyStateVo.classifyIds!!), classifyStateVo.classifyIsDel!!)
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
        tableTime.tableName = "CLASSIFY"
        tableTime.dealTime = Timestamp(System.currentTimeMillis())
        tableTimeService.save(tableTime)
    }
}
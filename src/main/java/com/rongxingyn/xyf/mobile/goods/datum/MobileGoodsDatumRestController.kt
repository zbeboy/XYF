package com.rongxingyn.xyf.mobile.goods.datum

import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsDatumService
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.Resource

@RestController
@RequestMapping("/mobile/backstage/goods")
open class MobileGoodsDatumRestController {

    @Resource
    open lateinit var goodsDatumService: GoodsDatumService

    /**
     * 热销商品列表数据
     *
     * @return list data
     */
    @GetMapping("/datum/stick")
    fun stick(): Mono<ResponseEntity<Map<String, Any>>> {
        val list = goodsDatumService.findByGoodsIsStickRelation(1, 1)
        val goodsBeans = if (list.isNotEmpty) {
            list.into(GoodsBean::class.java)
        } else {
            ArrayList()
        }
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").listData(goodsBeans)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 商品列表数据
     *
     * @return list data
     */
    @GetMapping("/datum/data")
    fun data(goodsBean: GoodsBean): Mono<ResponseEntity<Map<String, Any>>> {
        val list = goodsDatumService.findByGoodsItemAndClassifyIdAndGoodsName(1, goodsBean.classifyId, goodsBean.goodsName)
        val goodsBeans = if (list.isNotEmpty) {
            list.into(GoodsBean::class.java)
        } else {
            ArrayList()
        }
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").listData(goodsBeans)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 商品数据
     *
     * @return list data
     */
    @GetMapping("/datum/one/{goodsId}")
    fun one(@PathVariable goodsId: String): Mono<ResponseEntity<Map<String, Any>>> {
        val data = goodsDatumService.findByIdRelation(goodsId)
        val goodsBeans = if (data.isPresent) {
            data.get().into(GoodsBean::class.java)
        } else {
            GoodsBean()
        }
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").put("goodsBeans", goodsBeans)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}
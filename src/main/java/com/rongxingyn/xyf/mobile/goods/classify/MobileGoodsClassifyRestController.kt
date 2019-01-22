package com.rongxingyn.xyf.mobile.goods.classify

import com.rongxingyn.xyf.service.backstage.goods.classify.GoodsClassifyService
import com.rongxingyn.xyf.web.utils.AjaxUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.annotation.Resource

@RestController
@RequestMapping("/mobile/backstage/goods")
open class MobileGoodsClassifyRestController {

    @Resource
    open lateinit var goodsClassifyService: GoodsClassifyService

    /**
     * 类别列表数据
     *
     * @return list data
     */
    @GetMapping("/classify/data")
    fun data(): Mono<ResponseEntity<Map<String, Any>>> {
        val list = goodsClassifyService.findByState(0)
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").listData(list)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}
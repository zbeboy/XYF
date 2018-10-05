package com.rongxingyn.xyf.web.backstage.goods.datum

import com.rongxingyn.xyf.domain.tables.pojos.GoodsPics
import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsDatumService
import com.rongxingyn.xyf.service.backstage.goods.datum.GoodsPicsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import javax.annotation.Resource

@Controller
@RequestMapping("/web/backstage/goods")
open class GoodsDatumViewController {

    @Resource
    open lateinit var goodsDatumService: GoodsDatumService

    @Resource
    open lateinit var goodsPicsService: GoodsPicsService

    /**
     * 商品列表页
     *
     * @return list page
     */
    @GetMapping("/datum/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/goods/datum/datum_list")
    }

    /**
     * 商品添加页
     *
     * @return add page
     */
    @GetMapping("/datum/add")
    fun add(): Mono<String> {
        return Mono.just("backstage/goods/datum/datum_add")
    }

    /**
     * 商品编辑页
     *
     * @return add page
     */
    @GetMapping("/datum/edit/{goodsId}")
    fun edit(@PathVariable("goodsId") goodsId: String, model: Model): Mono<String> {
        model.addAttribute("goods", goodsDatumService.findById(goodsId))
        val goodsPics = goodsPicsService.findByGoodsId(goodsId)
        if (goodsPics.isNotEmpty()) {
            model.addAttribute("goodsPic", goodsPics.first())
        } else {
            model.addAttribute("goodsPic", GoodsPics())
        }
        return Mono.just("backstage/goods/datum/datum_edit")
    }
}
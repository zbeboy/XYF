package com.rongxingyn.xyf.mobile.goods.banner

import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.service.backstage.goods.banner.GoodsBannerService
import com.rongxingyn.xyf.web.utils.AjaxUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.ArrayList
import javax.annotation.Resource

@RestController
@RequestMapping("/mobile/backstage/goods")
open class MobileGoodsBannerRestController {

    @Resource
    open lateinit var goodsBannerService: GoodsBannerService

    /**
     * 商品banner列表数据
     *
     * @return list data
     */
    @GetMapping("/banner/data")
    fun data(): Mono<ResponseEntity<Map<String, Any>>> {
        val list = goodsBannerService.findByBannerItemAndBannerIsHide(1, 0)
        val banners = if (list.isNotEmpty) {
            list.into(Banner::class.java)
        } else {
            ArrayList()
        }
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").listData(banners)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}
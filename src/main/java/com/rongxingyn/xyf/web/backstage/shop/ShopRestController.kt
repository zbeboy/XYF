package com.rongxingyn.xyf.web.backstage.shop

import com.rongxingyn.xyf.domain.tables.pojos.DataInfo
import com.rongxingyn.xyf.service.backstage.data.DataInfoService
import com.rongxingyn.xyf.service.backstage.data.DataKey
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.vo.backstage.shop.ShopEditVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.annotation.Resource

@RestController
@RequestMapping("/web/backstage/shop")
open class ShopRestController {

    @Resource
    open lateinit var dataInfoService: DataInfoService

    @PostMapping("/edit")
    fun edit(shopEditVo: ShopEditVo): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val list = ArrayList<DataInfo>()
        val data1 = DataInfo()
        data1.dataKey = DataKey.SHOP_NAME.name
        data1.dataValue = shopEditVo.shopName
        list.add(data1)

        val data2 = DataInfo()
        data2.dataKey = DataKey.SHOP_BRIEF.name
        data2.dataValue = shopEditVo.shopBrief
        list.add(data2)
        dataInfoService.save(list)
        ajaxUtils.success().msg("保存成功")
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}
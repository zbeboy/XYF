package com.rongxingyn.xyf.web.backstage.website

import com.rongxingyn.xyf.domain.tables.pojos.DataInfo
import com.rongxingyn.xyf.service.backstage.data.DataInfoService
import com.rongxingyn.xyf.service.backstage.data.DataKey
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.vo.backstage.website.WebsiteEditVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.annotation.Resource

@RestController
@RequestMapping("/web/backstage/website")
open class WebsiteRestController {

    @Resource
    open lateinit var dataInfoService: DataInfoService

    @PostMapping("/edit")
    fun edit(websiteEditVo: WebsiteEditVo): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val list = ArrayList<DataInfo>()
        val data1 = DataInfo()
        data1.dataKey = DataKey.WEBSITE_CONTACTS.name
        data1.dataValue = websiteEditVo.websiteContacts
        list.add(data1)

        val data2 = DataInfo()
        data2.dataKey = DataKey.WEBSITE_PHONE.name
        data2.dataValue = websiteEditVo.websitePhone
        list.add(data2)

        val data3 = DataInfo()
        data3.dataKey = DataKey.WEBSITE_EMAIL.name
        data3.dataValue = websiteEditVo.websiteEmail
        list.add(data3)

        val data4 = DataInfo()
        data4.dataKey = DataKey.WEBSITE_ADDRESS.name
        data4.dataValue = websiteEditVo.websiteAddress
        list.add(data4)

        dataInfoService.save(list)
        ajaxUtils.success().msg("保存成功")
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}
package com.rongxingyn.xyf.web.backstage.website

import com.rongxingyn.xyf.service.backstage.data.DataInfoService
import com.rongxingyn.xyf.service.backstage.data.DataKey
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import javax.annotation.Resource

@Controller
@RequestMapping("/web/backstage/website")
open class WebsiteViewController {

    @Resource
    open lateinit var dataInfoService: DataInfoService

    /**
     * 网站设置页
     *
     * @return list page
     */
    @GetMapping("/list")
    fun list(model: Model): Mono<String> {
        val record = dataInfoService.findByPrefix(DataKey.WEBSITE.name)
        if (record.isNotEmpty) {
            record.forEach { r ->
                model.addAttribute(r.dataKey, r.dataValue)
            }
        }
        return Mono.just("backstage/website/website_list")
    }
}
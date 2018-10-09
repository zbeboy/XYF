package com.rongxingyn.xyf.service.reception

import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.domain.tables.pojos.Classify
import org.springframework.ui.Model

interface ReceptionService {

    /**
     * 获取banner
     */
    fun getBanners(): List<Banner>

    /**
     * 获取各类
     */
    fun getClassifies(): List<Classify>

    /**
     * 获取网站信息
     */
    fun getWebsiteInfo(model: Model)

    /**
     * 获取店铺信息
     */
    fun getShopInfo(model: Model)
}
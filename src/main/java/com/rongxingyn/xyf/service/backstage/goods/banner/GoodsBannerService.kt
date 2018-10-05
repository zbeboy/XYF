package com.rongxingyn.xyf.service.backstage.goods.banner

import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.domain.tables.records.BannerRecord
import org.jooq.Result

interface GoodsBannerService {

    /**
     * 查询全部
     *
     * @return 数据
     */
    fun findAll(): Result<BannerRecord>

    /**
     * 保存
     *
     * @param banner 数据
     */
    fun save(banner: Banner)
}
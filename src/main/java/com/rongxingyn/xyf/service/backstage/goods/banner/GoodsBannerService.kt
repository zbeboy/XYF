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

    /**
     * 更新隐藏状态
     *
     * @param bannerId id
     * @param bannerIsHide 是否隐藏
     */
    fun updateHide(bannerId: String, bannerIsHide: Byte)

    /**
     * 更新序号
     *
     * @param bannerId id
     * @param bannerSerial 序号
     */
    fun updateSerial(bannerId: String, bannerSerial: Int)
}
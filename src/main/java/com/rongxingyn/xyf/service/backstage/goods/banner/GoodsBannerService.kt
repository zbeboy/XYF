package com.rongxingyn.xyf.service.backstage.goods.banner

import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.domain.tables.records.BannerRecord
import org.jooq.Result

interface GoodsBannerService {

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return 数据
     */
    fun findById(id: String): Banner

    /**
     * 查询全部
     *
     * @return 数据
     */
    fun findAll(): Result<BannerRecord>

    /**
     * 根据渠道取数据
     *
     * @param bannerItem 渠道
     * @param bannerIsHide 是否隐藏
     * @return 数据
     */
    fun findByBannerItemAndBannerIsHide(bannerItem: Int, bannerIsHide: Byte): Result<BannerRecord>

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

    /**
     * 更新商品端
     *
     * @param bannerId id
     * @param bannerItem 商品端
     */
    fun updateItem(bannerId: String, bannerItem: Int)

    /**
     * 通过ID删除
     *
     * @param bannerId id
     */
    fun deleteById(bannerId: String)
}
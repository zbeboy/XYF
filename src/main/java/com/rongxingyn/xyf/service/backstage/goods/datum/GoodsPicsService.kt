package com.rongxingyn.xyf.service.backstage.goods.datum

import com.rongxingyn.xyf.domain.tables.pojos.GoodsPics

interface GoodsPicsService {

    /**
     * 通过商品ID查询
     *
     * @param goodsId 商品ID
     * @return 数据
     */
    fun findByGoodsId(goodsId: String): List<GoodsPics>

    /**
     * 通过商品ID删除
     *
     * @param goodsId 数据
     */
    fun deleteByGoodsId(goodsId: String)

    /**
     * 保存
     *
     * @param goodsPics 数据
     */
    fun save(goodsPics: GoodsPics)
}
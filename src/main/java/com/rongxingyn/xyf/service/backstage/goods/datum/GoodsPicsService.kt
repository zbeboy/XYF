package com.rongxingyn.xyf.service.backstage.goods.datum

import com.rongxingyn.xyf.domain.tables.pojos.GoodsPics

interface GoodsPicsService {

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
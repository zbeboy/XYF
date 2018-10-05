package com.rongxingyn.xyf.service.backstage.goods.datum

import com.rongxingyn.xyf.domain.tables.pojos.Goods
import com.rongxingyn.xyf.domain.tables.records.GoodsRecord
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.Record
import org.jooq.Result

interface GoodsDatumService {

    /**
     * 根据商品名查询
     *
     * @param goodsName 商品名
     * @return 数据
     */
    fun findByGoodsName(goodsName: String): List<Goods>

    /**
     * 根据商品名查询 注:不等于类别ID
     *
     * @param goodsName 商品名
     * @param goodsId 商品ID
     * @return 数据
     */
    fun findByGoodsNameNeGoodsId(goodsName: String, goodsId: String): Result<GoodsRecord>

    /**
     * 分页查询
     *
     * @param dataTablesUtils datatables工具类
     * @return 分页数据
     */
    fun findAllByPage(dataTablesUtils: DataTablesUtils<GoodsBean>): Result<Record>

    /**
     * 总数
     *
     * @return 总数
     */
    fun countAll(): Int

    /**
     * 根据条件查询总数
     *
     * @return 条件查询总数
     */
    fun countByCondition(dataTablesUtils: DataTablesUtils<GoodsBean>): Int

    /**
     * 保存
     *
     * @param goods 数据
     */
    fun save(goods: Goods)

    /**
     * 更新
     *
     * @param goods 数据
     */
    fun update(goods: Goods)

    /**
     * 通过id更新状态
     *
     * @param ids   ids
     * @param isDel is_del
     */
    fun updateState(ids: List<String>, isDel: Byte)
}
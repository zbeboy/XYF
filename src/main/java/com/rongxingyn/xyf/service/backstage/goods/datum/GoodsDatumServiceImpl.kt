package com.rongxingyn.xyf.service.backstage.goods.datum

import com.rongxingyn.xyf.domain.Tables.*
import com.rongxingyn.xyf.domain.tables.daos.GoodsDao
import com.rongxingyn.xyf.domain.tables.pojos.Goods
import com.rongxingyn.xyf.domain.tables.records.GoodsRecord
import com.rongxingyn.xyf.service.plugin.DataTablesPlugin
import com.rongxingyn.xyf.service.utils.SQLQueryUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.apache.commons.lang3.math.NumberUtils
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import java.util.*
import javax.annotation.Resource

@Service("goodsDatumService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class GoodsDatumServiceImpl @Autowired constructor(dslContext: DSLContext) : DataTablesPlugin<GoodsBean>(), GoodsDatumService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var goodsDao: GoodsDao

    override fun findById(id: String): Goods {
        return goodsDao.findById(id)
    }

    override fun findByIdRelation(id: String): Optional<Record> {
        return create.select()
                .from(GOODS)
                .join(CLASSIFY)
                .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                .join(GOODS_PICS)
                .on(GOODS.GOODS_ID.eq(GOODS_PICS.GOODS_ID))
                .where(GOODS.GOODS_ID.eq(id))
                .fetchOptional()
    }

    override fun findByGoodsIsStickRelation(goodsIsStick: Byte, goodsItem: Int): Result<Record> {
        return create.select()
                .from(GOODS)
                .join(CLASSIFY)
                .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                .join(GOODS_PICS)
                .on(GOODS.GOODS_ID.eq(GOODS_PICS.GOODS_ID))
                .where(GOODS.GOODS_IS_STICK.eq(goodsIsStick).and(GOODS.GOODS_IS_DEL.eq(0)).and(GOODS.GOODS_ITEM.eq(goodsItem)))
                .orderBy(GOODS.GOODS_SERIAL.asc())
                .fetch()
    }

    override fun findByGoodsName(goodsName: String): List<Goods> {
        return goodsDao.fetchByGoodsName(goodsName)
    }

    override fun findByGoodsNameNeGoodsId(goodsName: String, goodsId: String): Result<GoodsRecord> {
        return create.selectFrom<GoodsRecord>(GOODS)
                .where(GOODS.GOODS_NAME.eq(goodsName).and(GOODS.GOODS_ID.ne(goodsId)))
                .fetch()
    }

    override fun findByGoodsItemAndClassifyIdAndGoodsName(goodsItem: Int, classifyId: Int, goodsName: String): Result<Record> {
        val a = create.select()
                .from(GOODS)
                .join(CLASSIFY)
                .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                .join(GOODS_PICS)
                .on(GOODS.GOODS_ID.eq(GOODS_PICS.GOODS_ID))
                .where(GOODS.GOODS_IS_DEL.eq(0).and(GOODS.GOODS_ITEM.eq(goodsItem)))
        if (classifyId > 0) {
            a.and(GOODS.CLASSIFY_ID.eq(classifyId))
        }

        if (StringUtils.hasLength(goodsName)) {
            a.and(GOODS.GOODS_NAME.like(SQLQueryUtils.likeAllParam(goodsName)))
        }

        return a.orderBy(GOODS.GOODS_SERIAL.asc()).fetch()
    }

    override fun findAllByPage(dataTablesUtils: DataTablesUtils<GoodsBean>): Result<Record> {
        val a = searchCondition(dataTablesUtils)
        return if (ObjectUtils.isEmpty(a)) {
            val selectJoinStep = create.select()
                    .from(GOODS)
                    .join(CLASSIFY)
                    .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                    .join(GOODS_PICS)
                    .on(GOODS.GOODS_ID.eq(GOODS_PICS.GOODS_ID))
            sortCondition(dataTablesUtils, null, selectJoinStep, DataTablesPlugin.JOIN_TYPE)
            pagination(dataTablesUtils, null, selectJoinStep, DataTablesPlugin.JOIN_TYPE)
            selectJoinStep.fetch()
        } else {
            val selectConditionStep = create.select()
                    .from(GOODS)
                    .join(CLASSIFY)
                    .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                    .join(GOODS_PICS)
                    .on(GOODS.GOODS_ID.eq(GOODS_PICS.GOODS_ID))
                    .where(a)
            sortCondition(dataTablesUtils, selectConditionStep, null, DataTablesPlugin.CONDITION_TYPE)
            pagination(dataTablesUtils, selectConditionStep, null, DataTablesPlugin.CONDITION_TYPE)
            selectConditionStep.fetch()
        }
    }

    override fun countAll(): Int {
        return statisticsAll(create, GOODS)
    }

    override fun countByCondition(dataTablesUtils: DataTablesUtils<GoodsBean>): Int {
        val count: Record1<Int>
        val a = searchCondition(dataTablesUtils)
        count = if (ObjectUtils.isEmpty(a)) {
            create.selectCount()
                    .from(GOODS).fetchOne()
        } else {
            create.selectCount()
                    .from(GOODS)
                    .join(CLASSIFY)
                    .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                    .join(GOODS_PICS)
                    .on(GOODS.GOODS_ID.eq(GOODS_PICS.GOODS_ID))
                    .where(a).fetchOne()
        }
        return count.value1()
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    override fun save(goods: Goods) {
        goodsDao.insert(goods)
    }

    override fun update(goods: Goods) {
        goodsDao.update(goods)
    }

    override fun updateState(ids: List<String>, isDel: Byte) {
        for (id in ids) {
            create.update<GoodsRecord>(GOODS).set<Byte>(GOODS.GOODS_IS_DEL, isDel).where(GOODS.GOODS_ID.eq(id)).execute()
        }
    }

    override fun updateStick(ids: List<String>, stick: Byte) {
        for (id in ids) {
            create.update<GoodsRecord>(GOODS).set<Byte>(GOODS.GOODS_IS_STICK, stick).where(GOODS.GOODS_ID.eq(id)).execute()
        }
    }

    /**
     * 数据全局搜索条件
     *
     * @param dataTablesUtils datatables工具类
     * @return 搜索条件
     */
    override fun searchCondition(dataTablesUtils: DataTablesUtils<GoodsBean>): Condition? {
        var a: Condition? = null
        val search = dataTablesUtils.search
        if (!ObjectUtils.isEmpty(search)) {
            val goodsName = StringUtils.trimWhitespace(search!!.getString("goodsName"))
            val goodsItem = StringUtils.trimWhitespace(search.getString("goodsItem"))
            val goodsIsDel = StringUtils.trimWhitespace(search.getString("goodsIsDel"))
            val classifyId = StringUtils.trimWhitespace(search.getString("classifyId"))
            if (StringUtils.hasLength(goodsName)) {
                a = GOODS.GOODS_NAME.like(SQLQueryUtils.likeAllParam(goodsName))
            }

            if (StringUtils.hasLength(goodsItem)) {
                a = if (ObjectUtils.isEmpty(a)) {
                    GOODS.GOODS_ITEM.eq(NumberUtils.toInt(goodsItem))
                } else {
                    a!!.and(GOODS.GOODS_ITEM.eq(NumberUtils.toInt(goodsItem)))
                }
            }

            if (StringUtils.hasLength(goodsIsDel)) {
                a = if (ObjectUtils.isEmpty(a)) {
                    GOODS.GOODS_IS_DEL.eq(NumberUtils.toByte(goodsIsDel))
                } else {
                    a!!.and(GOODS.GOODS_IS_DEL.eq(NumberUtils.toByte(goodsIsDel)))
                }
            }

            if (StringUtils.hasLength(classifyId)) {
                a = if (ObjectUtils.isEmpty(a)) {
                    CLASSIFY.CLASSIFY_ID.eq(NumberUtils.toInt(classifyId))
                } else {
                    a!!.and(CLASSIFY.CLASSIFY_ID.eq(NumberUtils.toInt(classifyId)))
                }
            }
        }
        return a
    }

    /**
     * 数据排序
     *
     * @param dataTablesUtils     datatables工具类
     * @param selectConditionStep 条件
     */
    override fun sortCondition(dataTablesUtils: DataTablesUtils<GoodsBean>, selectConditionStep: SelectConditionStep<Record>?, selectJoinStep: SelectJoinStep<Record>?, type: Int) {
        val orderColumnName = dataTablesUtils.orderColumnName
        val orderDir = dataTablesUtils.orderDir
        val isAsc = "asc".equals(orderDir, ignoreCase = true)
        var sortField: Array<SortField<*>?>? = null
        if (StringUtils.hasLength(orderColumnName)) {

            if ("goods_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_NAME.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_NAME.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_price".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_PRICE.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_PRICE.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_recommend".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_RECOMMEND.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_RECOMMEND.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("classify_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = CLASSIFY.CLASSIFY_NAME.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = CLASSIFY.CLASSIFY_NAME.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_item".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_ITEM.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_ITEM.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_serial".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_SERIAL.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_SERIAL.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_is_del".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_IS_DEL.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_IS_DEL.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_is_stick".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_IS_STICK.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_IS_STICK.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

        }

        sortToFinish(selectConditionStep, selectJoinStep, type, *sortField!!)
    }
}
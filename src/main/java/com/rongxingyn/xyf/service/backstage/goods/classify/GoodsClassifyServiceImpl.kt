package com.rongxingyn.xyf.service.backstage.goods.classify

import com.rongxingyn.xyf.domain.Tables.CLASSIFY
import com.rongxingyn.xyf.domain.tables.daos.ClassifyDao
import com.rongxingyn.xyf.domain.tables.pojos.Classify
import com.rongxingyn.xyf.domain.tables.records.ClassifyRecord
import com.rongxingyn.xyf.service.plugin.DataTablesPlugin
import com.rongxingyn.xyf.service.utils.SQLQueryUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.ClassifyBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import javax.annotation.Resource

@Service("goodsClassifyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class GoodsClassifyServiceImpl @Autowired constructor(dslContext: DSLContext) : DataTablesPlugin<ClassifyBean>(), GoodsClassifyService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var classifyDao: ClassifyDao

    override fun findById(id: Int): Classify {
        return classifyDao.findById(id)
    }

    override fun findByClassifyName(classifyName: String): List<Classify> {
        return classifyDao.fetchByClassifyName(classifyName)
    }

    override fun findByClassifyNameNeClassifyId(classifyName: String, classifyId: Int): Result<ClassifyRecord>{
        return create.selectFrom<ClassifyRecord>(CLASSIFY)
                .where(CLASSIFY.CLASSIFY_NAME.eq(classifyName).and(CLASSIFY.CLASSIFY_ID.ne(classifyId)))
                .fetch()
    }

    override fun findAllByPage(dataTablesUtils: DataTablesUtils<ClassifyBean>): Result<Record> {
        return dataPagingQueryAll(dataTablesUtils, create, CLASSIFY)
    }

    override fun countAll(): Int {
        return statisticsAll(create, CLASSIFY)
    }

    override fun countByCondition(dataTablesUtils: DataTablesUtils<ClassifyBean>): Int {
        return statisticsWithCondition(dataTablesUtils, create, CLASSIFY)
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    override fun save(classify: Classify) {
        classifyDao.insert(classify)
    }

    override fun update(classify: Classify) {
        classifyDao.update(classify)
    }

    override fun updateState(ids: List<Int>, isDel: Byte?) {
        for (id in ids) {
            create.update<ClassifyRecord>(CLASSIFY).set<Byte>(CLASSIFY.CLASSIFY_IS_DEL, isDel).where(CLASSIFY.CLASSIFY_ID.eq(id)).execute()
        }
    }

    /**
     * 数据全局搜索条件
     *
     * @param dataTablesUtils datatables工具类
     * @return 搜索条件
     */
    override fun searchCondition(dataTablesUtils: DataTablesUtils<ClassifyBean>): Condition? {
        var a: Condition? = null

        val search = dataTablesUtils.search
        if (!ObjectUtils.isEmpty(search)) {
            val classifyName = StringUtils.trimWhitespace(search!!.getString("classifyName"))
            if (StringUtils.hasLength(classifyName)) {
                a = CLASSIFY.CLASSIFY_NAME.like(SQLQueryUtils.likeAllParam(classifyName))
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
    override fun sortCondition(dataTablesUtils: DataTablesUtils<ClassifyBean>, selectConditionStep: SelectConditionStep<Record>?, selectJoinStep: SelectJoinStep<Record>?, type: Int) {
        val orderColumnName = dataTablesUtils.orderColumnName
        val orderDir = dataTablesUtils.orderDir
        val isAsc = "asc".equals(orderDir, ignoreCase = true)
        var sortField: Array<SortField<*>?>? = null
        if (StringUtils.hasLength(orderColumnName)) {
            if ("classify_id".equals(orderColumnName!!, ignoreCase = true)) {
                sortField = arrayOfNulls(1)
                if (isAsc) {
                    sortField[0] = CLASSIFY.CLASSIFY_ID.asc()
                } else {
                    sortField[0] = CLASSIFY.CLASSIFY_ID.desc()
                }
            }

            if ("classify_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = CLASSIFY.CLASSIFY_NAME.asc()
                    sortField[1] = CLASSIFY.CLASSIFY_ID.asc()
                } else {
                    sortField[0] = CLASSIFY.CLASSIFY_NAME.desc()
                    sortField[1] = CLASSIFY.CLASSIFY_ID.desc()
                }
            }

            if ("classify_is_del".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = CLASSIFY.CLASSIFY_IS_DEL.asc()
                    sortField[1] = CLASSIFY.CLASSIFY_ID.asc()
                } else {
                    sortField[0] = CLASSIFY.CLASSIFY_IS_DEL.desc()
                    sortField[1] = CLASSIFY.CLASSIFY_ID.desc()
                }
            }

        }

        sortToFinish(selectConditionStep, selectJoinStep, type, *sortField!!)
    }
}
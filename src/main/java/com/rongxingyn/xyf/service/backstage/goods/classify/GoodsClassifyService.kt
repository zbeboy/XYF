package com.rongxingyn.xyf.service.backstage.goods.classify

import com.rongxingyn.xyf.domain.tables.pojos.Classify
import com.rongxingyn.xyf.domain.tables.records.ClassifyRecord
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.ClassifyBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.Record
import org.jooq.Result

interface GoodsClassifyService {

    /**
     * 根据类别名查询
     *
     * @param classifyName 类别名
     * @return 数据
     */
    fun findByClassifyName(classifyName: String): List<Classify>

    /**
     * 根据类别名查询 注:不等于类别ID
     *
     * @param classifyName 类别名
     * @param classifyId 类别ID
     * @return 数据
     */
    fun findByClassifyNameNeClassifyId(classifyName: String, classifyId: Int): Result<ClassifyRecord>

    /**
     * 分页查询
     *
     * @param dataTablesUtils datatables工具类
     * @return 分页数据
     */
    fun findAllByPage(dataTablesUtils: DataTablesUtils<ClassifyBean>): Result<Record>

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
    fun countByCondition(dataTablesUtils: DataTablesUtils<ClassifyBean>): Int

    /**
     * 保存
     *
     * @param classify 数据
     */
    fun save(classify: Classify)
}
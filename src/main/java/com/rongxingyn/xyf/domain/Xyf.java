/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain;


import com.rongxingyn.xyf.domain.tables.Authorities;
import com.rongxingyn.xyf.domain.tables.Banner;
import com.rongxingyn.xyf.domain.tables.Classify;
import com.rongxingyn.xyf.domain.tables.DataInfo;
import com.rongxingyn.xyf.domain.tables.Feedback;
import com.rongxingyn.xyf.domain.tables.Goods;
import com.rongxingyn.xyf.domain.tables.GoodsPics;
import com.rongxingyn.xyf.domain.tables.TableTime;
import com.rongxingyn.xyf.domain.tables.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Xyf extends SchemaImpl {

    private static final long serialVersionUID = 1658675779;

    /**
     * The reference instance of <code>xyf</code>
     */
    public static final Xyf XYF = new Xyf();

    /**
     * The table <code>xyf.authorities</code>.
     */
    public final Authorities AUTHORITIES = com.rongxingyn.xyf.domain.tables.Authorities.AUTHORITIES;

    /**
     * The table <code>xyf.banner</code>.
     */
    public final Banner BANNER = com.rongxingyn.xyf.domain.tables.Banner.BANNER;

    /**
     * The table <code>xyf.classify</code>.
     */
    public final Classify CLASSIFY = com.rongxingyn.xyf.domain.tables.Classify.CLASSIFY;

    /**
     * The table <code>xyf.data_info</code>.
     */
    public final DataInfo DATA_INFO = com.rongxingyn.xyf.domain.tables.DataInfo.DATA_INFO;

    /**
     * The table <code>xyf.feedback</code>.
     */
    public final Feedback FEEDBACK = com.rongxingyn.xyf.domain.tables.Feedback.FEEDBACK;

    /**
     * The table <code>xyf.goods</code>.
     */
    public final Goods GOODS = com.rongxingyn.xyf.domain.tables.Goods.GOODS;

    /**
     * The table <code>xyf.goods_pics</code>.
     */
    public final GoodsPics GOODS_PICS = com.rongxingyn.xyf.domain.tables.GoodsPics.GOODS_PICS;

    /**
     * The table <code>xyf.table_time</code>.
     */
    public final TableTime TABLE_TIME = com.rongxingyn.xyf.domain.tables.TableTime.TABLE_TIME;

    /**
     * The table <code>xyf.users</code>.
     */
    public final Users USERS = com.rongxingyn.xyf.domain.tables.Users.USERS;

    /**
     * No further instances allowed
     */
    private Xyf() {
        super("xyf", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Authorities.AUTHORITIES,
            Banner.BANNER,
            Classify.CLASSIFY,
            DataInfo.DATA_INFO,
            Feedback.FEEDBACK,
            Goods.GOODS,
            GoodsPics.GOODS_PICS,
            TableTime.TABLE_TIME,
            Users.USERS);
    }
}

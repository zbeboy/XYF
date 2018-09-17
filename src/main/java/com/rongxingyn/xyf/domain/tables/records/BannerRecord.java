/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.Banner;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class BannerRecord extends UpdatableRecordImpl<BannerRecord> implements Record5<String, Integer, String, Byte, Integer> {

    private static final long serialVersionUID = 1894177768;

    /**
     * Setter for <code>xyf.banner.banner_id</code>.
     */
    public void setBannerId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.banner.banner_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getBannerId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>xyf.banner.banner_serial</code>.
     */
    public void setBannerSerial(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.banner.banner_serial</code>.
     */
    public Integer getBannerSerial() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>xyf.banner.banner_url</code>.
     */
    public void setBannerUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.banner.banner_url</code>.
     */
    @NotNull
    @Size(max = 500)
    public String getBannerUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>xyf.banner.is_hide</code>.
     */
    public void setIsHide(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>xyf.banner.is_hide</code>.
     */
    public Byte getIsHide() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>xyf.banner.classify_id</code>.
     */
    public void setClassifyId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>xyf.banner.classify_id</code>.
     */
    @NotNull
    public Integer getClassifyId() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, Integer, String, Byte, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, Integer, String, Byte, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Banner.BANNER.BANNER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Banner.BANNER.BANNER_SERIAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Banner.BANNER.BANNER_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return Banner.BANNER.IS_HIDE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Banner.BANNER.CLASSIFY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getBannerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getBannerSerial();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getBannerUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component4() {
        return getIsHide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getClassifyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getBannerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getBannerSerial();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getBannerUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getIsHide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getClassifyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BannerRecord value1(String value) {
        setBannerId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BannerRecord value2(Integer value) {
        setBannerSerial(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BannerRecord value3(String value) {
        setBannerUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BannerRecord value4(Byte value) {
        setIsHide(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BannerRecord value5(Integer value) {
        setClassifyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BannerRecord values(String value1, Integer value2, String value3, Byte value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BannerRecord
     */
    public BannerRecord() {
        super(Banner.BANNER);
    }

    /**
     * Create a detached, initialised BannerRecord
     */
    public BannerRecord(String bannerId, Integer bannerSerial, String bannerUrl, Byte isHide, Integer classifyId) {
        super(Banner.BANNER);

        set(0, bannerId);
        set(1, bannerSerial);
        set(2, bannerUrl);
        set(3, isHide);
        set(4, classifyId);
    }
}

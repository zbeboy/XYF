/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain;


import com.rongxingyn.xyf.domain.tables.Authorities;
import com.rongxingyn.xyf.domain.tables.PersistentLogins;
import com.rongxingyn.xyf.domain.tables.Users;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>xyf</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index AUTHORITIES_PRIMARY = Indexes0.AUTHORITIES_PRIMARY;
    public static final Index PERSISTENT_LOGINS_PRIMARY = Indexes0.PERSISTENT_LOGINS_PRIMARY;
    public static final Index USERS_PRIMARY = Indexes0.USERS_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index AUTHORITIES_PRIMARY = Internal.createIndex("PRIMARY", Authorities.AUTHORITIES, new OrderField[] { Authorities.AUTHORITIES.USERNAME, Authorities.AUTHORITIES.AUTHORITY }, true);
        public static Index PERSISTENT_LOGINS_PRIMARY = Internal.createIndex("PRIMARY", PersistentLogins.PERSISTENT_LOGINS, new OrderField[] { PersistentLogins.PERSISTENT_LOGINS.SERIES }, true);
        public static Index USERS_PRIMARY = Internal.createIndex("PRIMARY", Users.USERS, new OrderField[] { Users.USERS.USERNAME }, true);
    }
}

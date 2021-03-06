/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
public class Authorities implements Serializable {

    private static final long serialVersionUID = 663792583;

    private String username;
    private String authority;

    public Authorities() {}

    public Authorities(Authorities value) {
        this.username = value.username;
        this.authority = value.authority;
    }

    public Authorities(
        String username,
        String authority
    ) {
        this.username = username;
        this.authority = authority;
    }

    @NotNull
    @Size(max = 64)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(max = 50)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Authorities (");

        sb.append(username);
        sb.append(", ").append(authority);

        sb.append(")");
        return sb.toString();
    }
}

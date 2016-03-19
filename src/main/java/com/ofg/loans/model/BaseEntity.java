package com.ofg.loans.model;

import com.ofg.loans.utils.Utils;

/**
 * Created by zhabba on 18.03.16.
 */
public class BaseEntity {
    protected Long id;

    public BaseEntity() {
        this.id = Utils.getNextId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

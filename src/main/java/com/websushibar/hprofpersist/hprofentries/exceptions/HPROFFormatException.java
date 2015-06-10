package com.websushibar.hprofpersist.hprofentries.exceptions;

import com.websushibar.hprofpersist.hprofentries.HPROFEntity;

public class HPROFFormatException extends RuntimeException {
    private HPROFEntity badEntity;

    public HPROFFormatException(HPROFEntity badEntity) {
        super();
        this.badEntity = badEntity;
    }

    public HPROFFormatException(HPROFEntity badEntity, String msg) {
        super(msg);
        this.badEntity = badEntity;
    }

    public HPROFFormatException(HPROFEntity badEntity, Throwable cause) {
        super(cause);
        this.badEntity = badEntity;
    }

    public HPROFFormatException(HPROFEntity badEntity, String msg, Throwable cause) {
        super(msg, cause);
        this.badEntity = badEntity;
    }

    public HPROFFormatException(HPROFEntity badEntity, String msg, Throwable cause,
                                boolean f1, boolean f2) {
        super(msg, cause, f1, f2);
        this.badEntity = badEntity;

    }

    public HPROFFormatException(String msg) {
        super(msg);
    }

    public HPROFFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public HPROFEntity getBadEntity() {
        return badEntity;
    }

}

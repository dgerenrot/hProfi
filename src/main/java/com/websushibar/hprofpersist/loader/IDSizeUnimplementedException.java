package com.websushibar.hprofpersist.loader;

/**
 * Created by Dmitry on 1/25/2015.
 */
public class IDSizeUnimplementedException extends RuntimeException{
    public IDSizeUnimplementedException(int idSize) {
        super("ID size unimplemented " + idSize);
        this.idSize = idSize;
    }

    int idSize;
}

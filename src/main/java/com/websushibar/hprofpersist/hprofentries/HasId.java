package com.websushibar.hprofpersist.hprofentries;

public interface HasId {
    //@Id // TODO : this is NOT what we want! (& doesn't work anyway)
    IDField getId();
}

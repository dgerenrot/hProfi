package com.websushibar.hprofpersist.hprofentries;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="hprofHeader")
public class HPROFHeaderInfo {

    private String fmtAndVersion;
    private int idSize;

    private long timestamp;

    public HPROFHeaderInfo(String fmtAndVersion, int idSize, long timestamp) {
        this.fmtAndVersion = fmtAndVersion;
        this.idSize = idSize;
        this.timestamp = timestamp;
    }

    public String getFmtAndVersion() {
        return fmtAndVersion;
    }

    public int getIdSize() {
        return idSize;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getMillisHigh() {
        return (int)((timestamp >> 32) & 0xFFFFFFFF);
    }

    public int getMillisLow() {
        return (int)((timestamp) & 0xFFFFFFFF);
    }
}

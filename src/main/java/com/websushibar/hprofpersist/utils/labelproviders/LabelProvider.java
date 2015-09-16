package com.websushibar.hprofpersist.utils.labelproviders;

public interface LabelProvider {
    String getLabel();
    void advanceLabel();
    public void setPrefix(String prefix);
    public void setSuffix(String prefix);
}

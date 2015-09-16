package com.websushibar.hprofpersist.utils.labelproviders;

public class DefaultLabelProvider implements LabelProvider {
    private String label;

    private String prefix;
    private String suffix;

    @Override
    public String getLabel() {
        if (label == null) {
            label = "0";
        }

        return label;
    }

    @Override
    public void advanceLabel() {
        int l = Integer.parseInt(getLabel()) + 1;
        label = l + "";
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

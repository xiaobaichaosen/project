package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;

/**
 * 其他福利
 */
public class ProtectBean implements Serializable {
    //五险
    private String fiveInsurance;
    //公积金
    private String providentFund;
    //补充医疗保险
    private String  SuppMedInsurance;

    public String getFiveInsurance() {
        return fiveInsurance;
    }

    public void setFiveInsurance(String fiveInsurance) {
        this.fiveInsurance = fiveInsurance;
    }

    public String getProvidentFund() {
        return providentFund;
    }

    public void setProvidentFund(String providentFund) {
        this.providentFund = providentFund;
    }

    public String getSuppMedInsurance() {
        return SuppMedInsurance;
    }

    public void setSuppMedInsurance(String suppMedInsurance) {
        SuppMedInsurance = suppMedInsurance;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    //其他
    private String other;

}

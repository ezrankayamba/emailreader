/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tz.co.nezatech.systems.api.emailreader.data;

import java.util.Date;

/**
 *
 * @author godfred.nkayamba
 */
public class Payment {
private String txnSource;
    private String txnId;
    private double amount;
    private String msisdn;
    private Date recordDate;
    private String recordedBy;
    private Date transDate;
    private Date lastUpdate;
    private int status;
    private String distributor;
    private int regionId;
    private String payeeMsisdn;
    

    public Payment(String txnSource,String txnId,String payeeMsisdn, double amount, String msisdn, String recordedBy, Date transDate) {
        this.txnSource=txnSource;
        this.txnId = txnId;
        this.amount = amount;
        this.msisdn = msisdn;
        this.recordedBy = recordedBy;
        this.transDate = transDate;
        this.recordDate = new Date();
        this.lastUpdate = new Date();
        this.status = 0;
        this.payeeMsisdn=payeeMsisdn;
    }

    public Payment() {
    }

    public String getPayeeMsisdn() {
        return payeeMsisdn;
    }

    public void setPayeeMsisdn(String payeeMsisdn) {
        this.payeeMsisdn = payeeMsisdn;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getTxnSource() {
        return txnSource;
    }

    public void setTxnSource(String txnSource) {
        this.txnSource = txnSource;
    }

}

package com.fufang.bi.entity;

import java.util.*;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class StorageItem {
    private Integer id;

    private String matcode;

    private String matname;

    private String batchnum;

    private String manufname;

    private String unitname;

    private String spec;

    private String dosage;

    private Double beginstroage;

    private Double instorage;

    private Double instroagemoney;

    private Double outsotrage;

    private Double outstoragemoney;

    private Double endstorage;

    private Double qc;

    private Double qcje;

    private Double rk;

    private Double rkje;

    private Double qcrk;

    private Double qcrkje;

    private Double cg;

    private Double cgje;

    private Double xt;

    private Double xtje;

    private Double py;

    private Double pyje;

    private Double clrk;

    private Double clrkje;

    private Double qtrk;

    private Double qtrkje;

    private Double xs;

    private Double xsje;

    private Double ct;

    private Double ctje;

    private Double pk;

    private Double pkje;

    private Double xh;

    private Double xhje;

    private Double clck;

    private Double clckje;

    private Double qd;

    private Double qdje;

    private Double qtck;

    private Double qtckje;

    private Double ck;

    private Double ckje;

    private Double qm;

    private Double qmje;

    private Date statTime;
    

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatcode() {
        return matcode;
    }

    public void setMatcode(String matcode) {
        this.matcode = matcode == null ? null : matcode.trim();
    }

    public String getMatname() {
        return matname;
    }

    public void setMatname(String matname) {
        this.matname = matname == null ? null : matname.trim();
    }

    public String getBatchnum() {
        return batchnum;
    }

    public void setBatchnum(String batchnum) {
        this.batchnum = batchnum == null ? null : batchnum.trim();
    }

    public String getManufname() {
        return manufname;
    }

    public void setManufname(String manufname) {
        this.manufname = manufname == null ? null : manufname.trim();
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage == null ? null : dosage.trim();
    }

    public Double getBeginstroage() {
        return beginstroage;
    }

    public void setBeginstroage(Double beginstroage) {
        this.beginstroage = beginstroage;
    }

    public Double getInstorage() {
        return instorage;
    }

    public void setInstorage(Double instorage) {
        this.instorage = instorage;
    }

    public Double getInstroagemoney() {
        return instroagemoney;
    }

    public void setInstroagemoney(Double instroagemoney) {
        this.instroagemoney = instroagemoney;
    }

    public Double getOutsotrage() {
        return outsotrage;
    }

    public void setOutsotrage(Double outsotrage) {
        this.outsotrage = outsotrage;
    }

    public Double getOutstoragemoney() {
        return outstoragemoney;
    }

    public void setOutstoragemoney(Double outstoragemoney) {
        this.outstoragemoney = outstoragemoney;
    }

    public Double getEndstorage() {
        return endstorage;
    }

    public void setEndstorage(Double endstorage) {
        this.endstorage = endstorage;
    }

    public Double getQc() {
        return qc;
    }

    public void setQc(Double qc) {
        this.qc = qc;
    }

    public Double getQcje() {
        return qcje;
    }

    public void setQcje(Double qcje) {
        this.qcje = qcje;
    }

    public Double getRk() {
        return rk;
    }

    public void setRk(Double rk) {
        this.rk = rk;
    }

    public Double getRkje() {
        return rkje;
    }

    public void setRkje(Double rkje) {
        this.rkje = rkje;
    }

    public Double getQcrk() {
        return qcrk;
    }

    public void setQcrk(Double qcrk) {
        this.qcrk = qcrk;
    }

    public Double getQcrkje() {
        return qcrkje;
    }

    public void setQcrkje(Double qcrkje) {
        this.qcrkje = qcrkje;
    }

    public Double getCg() {
        return cg;
    }

    public void setCg(Double cg) {
        this.cg = cg;
    }

    public Double getCgje() {
        return cgje;
    }

    public void setCgje(Double cgje) {
        this.cgje = cgje;
    }

    public Double getXt() {
        return xt;
    }

    public void setXt(Double xt) {
        this.xt = xt;
    }

    public Double getXtje() {
        return xtje;
    }

    public void setXtje(Double xtje) {
        this.xtje = xtje;
    }

    public Double getPy() {
        return py;
    }

    public void setPy(Double py) {
        this.py = py;
    }

    public Double getPyje() {
        return pyje;
    }

    public void setPyje(Double pyje) {
        this.pyje = pyje;
    }

    public Double getClrk() {
        return clrk;
    }

    public void setClrk(Double clrk) {
        this.clrk = clrk;
    }

    public Double getClrkje() {
        return clrkje;
    }

    public void setClrkje(Double clrkje) {
        this.clrkje = clrkje;
    }

    public Double getQtrk() {
        return qtrk;
    }

    public void setQtrk(Double qtrk) {
        this.qtrk = qtrk;
    }

    public Double getQtrkje() {
        return qtrkje;
    }

    public void setQtrkje(Double qtrkje) {
        this.qtrkje = qtrkje;
    }

    public Double getXs() {
        return xs;
    }

    public void setXs(Double xs) {
        this.xs = xs;
    }

    public Double getXsje() {
        return xsje;
    }

    public void setXsje(Double xsje) {
        this.xsje = xsje;
    }

    public Double getCt() {
        return ct;
    }

    public void setCt(Double ct) {
        this.ct = ct;
    }

    public Double getCtje() {
        return ctje;
    }

    public void setCtje(Double ctje) {
        this.ctje = ctje;
    }

    public Double getPk() {
        return pk;
    }

    public void setPk(Double pk) {
        this.pk = pk;
    }

    public Double getPkje() {
        return pkje;
    }

    public void setPkje(Double pkje) {
        this.pkje = pkje;
    }

    public Double getXh() {
        return xh;
    }

    public void setXh(Double xh) {
        this.xh = xh;
    }

    public Double getXhje() {
        return xhje;
    }

    public void setXhje(Double xhje) {
        this.xhje = xhje;
    }

    public Double getClck() {
        return clck;
    }

    public void setClck(Double clck) {
        this.clck = clck;
    }

    public Double getClckje() {
        return clckje;
    }

    public void setClckje(Double clckje) {
        this.clckje = clckje;
    }

    public Double getQd() {
        return qd;
    }

    public void setQd(Double qd) {
        this.qd = qd;
    }

    public Double getQdje() {
        return qdje;
    }

    public void setQdje(Double qdje) {
        this.qdje = qdje;
    }

    public Double getQtck() {
        return qtck;
    }

    public void setQtck(Double qtck) {
        this.qtck = qtck;
    }

    public Double getQtckje() {
        return qtckje;
    }

    public void setQtckje(Double qtckje) {
        this.qtckje = qtckje;
    }

    public Double getCk() {
        return ck;
    }

    public void setCk(Double ck) {
        this.ck = ck;
    }

    public Double getCkje() {
        return ckje;
    }

    public void setCkje(Double ckje) {
        this.ckje = ckje;
    }

    public Double getQm() {
        return qm;
    }

    public void setQm(Double qm) {
        this.qm = qm;
    }

    public Double getQmje() {
        return qmje;
    }

    public void setQmje(Double qmje) {
        this.qmje = qmje;
    }

    public Date getStatTime() {
        return statTime;
    }

    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }
    
    public String toString() {
    	  return ToStringBuilder.reflectionToString(this,
    	  ToStringStyle.MULTI_LINE_STYLE);
    	  }

    public int hashCode() {
    	  return HashCodeBuilder.reflectionHashCode(this);
    	  }


}
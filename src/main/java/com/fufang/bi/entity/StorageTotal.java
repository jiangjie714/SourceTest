package com.fufang.bi.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class StorageTotal {
	
	private Integer id=-1;
	
	private String pharmacycode="";

    private String name="";

    private Integer pharmacyid=-1;

    private Integer qckc=0;

    private Double qckcje=0.00;

    private Integer qjrk=0;

    private Double qjrkje=0.00;

    private Integer qjck=0;

    private Double qjckje=0.00;

    private Integer qmkc=0;

    private Double qmkcje=0.00;
    
   
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    public String getPharmacycode() {
        return pharmacycode;
    }

    public void setPharmacycode(String pharmacycode) {
        this.pharmacycode = pharmacycode == null ? null : pharmacycode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPharmacyid() {
        return pharmacyid;
    }

    public void setPharmacyid(Integer pharmacyid) {
        this.pharmacyid = pharmacyid;
    }

    public Integer getQckc() {
        return qckc;
    }

    public void setQckc(Integer qckc) {
        this.qckc = qckc;
    }

    public Double getQckcje() {
        return qckcje;
    }

    public void setQckcje(Double qckcje) {
        this.qckcje = qckcje;
    }

    public Integer getQjrk() {
        return qjrk;
    }

    public void setQjrk(Integer qjrk) {
        this.qjrk = qjrk;
    }

    public Double getQjrkje() {
        return qjrkje;
    }

    public void setQjrkje(Double qjrkje) {
        this.qjrkje = qjrkje;
    }

    public Integer getQjck() {
        return qjck;
    }

    public void setQjck(Integer qjck) {
        this.qjck = qjck;
    }

    public Double getQjckje() {
        return qjckje;
    }

    public void setQjckje(Double qjckje) {
        this.qjckje = qjckje;
    }

    public Integer getQmkc() {
        return qmkc;
    }

    public void setQmkc(Integer qmkc) {
        this.qmkc = qmkc;
    }

    public Double getQmkcje() {
        return qmkcje;
    }

    public void setQmkcje(Double qmkcje) {
        this.qmkcje = qmkcje;
    }
    
    public String toString() {
    	  return ToStringBuilder.reflectionToString(this,
    	  ToStringStyle.MULTI_LINE_STYLE);
    	  }

    public int hashCode() {
    	  return HashCodeBuilder.reflectionHashCode(this);
    	  }


}
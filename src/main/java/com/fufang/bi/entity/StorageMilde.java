package com.fufang.bi.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class StorageMilde {
	private Integer id=-1;
	
	private Integer pharmacyid=-1;

    private String pharmacycode="";

    private String name="";

    private Integer materialid=-1;

    private String matcode="";

    private String barcode="";

    private String matname="";

    private String commonname="";

    private String dosage="";

    private String spec="";

    private String unitname="";

    private String manufname="";

    private String productarea="";

    private String licensenum="";

    private Double retail=0.00;

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
	
    public Integer getPharmacyid() {
        return pharmacyid;
    }

    public void setPharmacyid(Integer pharmacyid) {
        this.pharmacyid = pharmacyid;
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

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public String getMatcode() {
        return matcode;
    }

    public void setMatcode(String matcode) {
        this.matcode = matcode == null ? null : matcode.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getMatname() {
        return matname;
    }

    public void setMatname(String matname) {
        this.matname = matname == null ? null : matname.trim();
    }

    public String getCommonname() {
        return commonname;
    }

    public void setCommonname(String commonname) {
        this.commonname = commonname == null ? null : commonname.trim();
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage == null ? null : dosage.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    public String getManufname() {
        return manufname;
    }

    public void setManufname(String manufname) {
        this.manufname = manufname == null ? null : manufname.trim();
    }

    public String getProductarea() {
        return productarea;
    }

    public void setProductarea(String productarea) {
        this.productarea = productarea == null ? null : productarea.trim();
    }

    public String getLicensenum() {
        return licensenum;
    }

    public void setLicensenum(String licensenum) {
        this.licensenum = licensenum == null ? null : licensenum.trim();
    }

    public Double getRetail() {
        return retail;
    }

    public void setRetail(Double retail) {
        this.retail = retail;
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
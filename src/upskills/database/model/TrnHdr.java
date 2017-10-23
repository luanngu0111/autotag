package upskills.database.model;

import java.io.Serializable;

public class TrnHdr implements Serializable {


	private static final long serialVersionUID = 1L;
	private String trnFmly;
    private String trnType;
    private String trnGrp;
    private String description;

    public TrnHdr() {
    }

	public TrnHdr(String trnFmly, String trnType, String trnGrp, String description) {
		super();
		this.trnFmly = trnFmly;
		this.trnType = trnType;
		this.trnGrp = trnGrp;
		this.description = description;
	}

	public String getTrnFmly() {
		return trnFmly;
	}

	public void setTrnFmly(String trnFmly) {
		this.trnFmly = trnFmly;
	}

	public String getTrnType() {
		return trnType;
	}

	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}

	public String getTrnGrp() {
		return trnGrp;
	}

	public void setTrnGrp(String trnGrp) {
		this.trnGrp = trnGrp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	    
}



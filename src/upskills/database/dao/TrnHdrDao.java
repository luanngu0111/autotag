package upskills.database.dao;

import java.util.List;

import upskills.database.model.TrnHdr;

public interface TrnHdrDao extends Dao<TrnHdr> {
	//Add specified method for Issue
	public List<TrnHdr> getDataByFmly(String fmly);
}

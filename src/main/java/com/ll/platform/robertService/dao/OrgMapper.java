package com.ll.platform.robertService.dao;

import com.ll.platform.robertService.model.OrgQueryParam;
import com.ll.platform.robertService.model.Organization;

import java.util.List;

public interface OrgMapper {
	
	/** 查找指定机构 **/
	public Organization findBkOrg(OrgQueryParam param);

	/** 查机构下属校区id **/
	public List<Integer> getSchoolIdsByOrgCode(String orgCode);
}

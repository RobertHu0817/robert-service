package com.ll.platform.robertService.component;

import com.alibaba.fastjson.JSON;
import com.ll.platform.robertService.common.Result;
import com.ll.platform.robertService.common.ResultCodeEnum;
import com.ll.platform.robertService.dao.OrgMapper;
import com.ll.platform.robertService.model.CommonParam;
import com.ll.platform.robertService.model.CommonRequiredParam;
import com.ll.platform.robertService.model.OrgQueryParam;
import com.ll.platform.robertService.model.Organization;
import com.ll.platform.robertService.util.WebUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Map.Entry;

/**
 * @Title WebMvcConfig.java
 * @Description 接口认证拦截器
 * @author Robert
 * @date 2019年11月29日
 */
@Component
public class AuthValidateInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthValidateInterceptor.class);
	
	@Autowired
	private OrgMapper orgMapper;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, Object> params;
		String reqMethod = request.getMethod();
		if(HttpMethod.GET.name().equalsIgnoreCase(reqMethod)) {
			params = new HashMap<>();
			Map<String, String[]>paramsArr = request.getParameterMap();
			for (Entry<String, String[]> entry : paramsArr.entrySet()) {
				params.put(entry.getKey(), entry.getValue()[0]);
			}
		} else if(HttpMethod.POST.name().equalsIgnoreCase(reqMethod)) {
			RequestWrapper requestWrapper = new RequestWrapper(request);
			params = JSON.parseObject(requestWrapper.getBodyString(), Map.class);
		} else {
			WebUtil.responseResult(response, Result.failure(ResultCodeEnum.METHOD_NOT_ALLOWED));
			return false;
		}
		
		if (params == null || !params.containsKey(CommonRequiredParam.KEY_ORG_CODE) || !params.containsKey(CommonRequiredParam.KEY_SIGN)) {
			WebUtil.responseResult(response, Result.failure(ResultCodeEnum.UNAUTHORIZED));
			return false; 
		}
		
		// 由机构编码获取数据库机构信息-后续考虑放入redis维护 TODO
		OrgQueryParam orgParam = new OrgQueryParam();
		orgParam.setOrgCode(params.get(CommonRequiredParam.KEY_ORG_CODE).toString());
		orgParam.setServiceOpen(1);
		Organization org = orgMapper.findBkOrg(orgParam);
		if (org == null) {
			WebUtil.responseResult(response, Result.failure(ResultCodeEnum.UNAUTHORIZED));
			return false;
		}

		// 参数中存在校区id时，校区id需验证合法性
		if (params.containsKey(CommonParam.KEY_SCHOOL_ID)) {
			int reqSchoolId;
			try {
				reqSchoolId = (Integer) params.get(CommonParam.KEY_SCHOOL_ID);
			} catch (ClassCastException e) {
				WebUtil.responseResult(response, Result.failure(ResultCodeEnum.BAD_REQUEST));
				return false;
			}
			if (!org.getSchoolIds().contains(reqSchoolId)) {
				WebUtil.responseResult(response, Result.failure(ResultCodeEnum.FORBIDDEN));
				return false;
			}
		}

		// 验签
		if (validateSign(params, org.getServiceSecret())) {
			LOGGER.info("validate sign sucess, uri:{}, IP:{}, params：{}", request.getRequestURI(), WebUtil.getIpAddress(request), params);
			return true;
		} else {
			LOGGER.warn("validate sign fail, uri:{}, IP:{}, params：{}", request.getRequestURI(), WebUtil.getIpAddress(request), params);
			WebUtil.responseResult(response, Result.failure(ResultCodeEnum.UNAUTHORIZED));
			return false;
		}
	}
	
	/**
	 * 签名认证规则： 1. 将请求参数按ascii码排序 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
	 * 3.混合密钥进行md5获得签名，与请求的签名进行比较
	 */
	private boolean validateSign(Map<String, Object> params, String secret) {
		List<String> keys = new ArrayList<>(params.keySet());
		keys.remove(CommonRequiredParam.KEY_SIGN);
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key).append("=").append(params.get(key).toString()).append("&");
		}
		String linkString = sb.substring(0, sb.length() - 1);// 去除最后一个'&'
		String serverSign = DigestUtils.md5Hex(linkString + secret);
		LOGGER.info("Server linkString:{}, sign:{}", linkString, serverSign);
		return StringUtils.equals(serverSign, params.get(CommonRequiredParam.KEY_SIGN).toString());
	}

}
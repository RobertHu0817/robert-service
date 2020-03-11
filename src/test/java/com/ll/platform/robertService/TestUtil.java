package com.ll.platform.robertService;

import com.alibaba.fastjson.JSON;
import com.ll.platform.robertService.util.TransUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class TestUtil {

	/** 秘钥，各机构不同**/
	private static final String DEFAULT_SECRET = "abcDEFghiJkL1";// orgCode:TEST_ORG,内网测试

	/**
	 * 发送携带签名的POST请求
	 * @param mockMvc
	 * @param url
	 * @param reqObj
	 * @throws Exception
	 */
	public static void postWithSign(MockMvc mockMvc, String url, Object reqObj) throws Exception {
		TreeMap<String, Object> reqMap = new TreeMap<>();
		TransUtil.transBean2Map(reqObj, reqMap, true);
		postWithSign(mockMvc, url, reqMap);
	}
	
	/**
	 * 发送携带签名的POST请求
	 * @param mockMvc
	 * @param url
	 * @param reqMap
	 * @throws Exception
	 */
	public static void postWithSign(MockMvc mockMvc, String url, TreeMap<String, Object> reqMap) throws Exception {
		RequestBuilder requestBuilder = post(url).contentType(MediaType.APPLICATION_JSON).content(addSignForReqParam(reqMap));
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	/**
	 * 为请求参数添加签名-使用默认secret
	 * @param reqMap
	 * @return
	 */
	private static String addSignForReqParam(TreeMap<String, Object> reqMap) {
		return addSignForReqParam(reqMap, DEFAULT_SECRET);
	}
	
	/**
	 * 为请求参数添加签名
	 * @param reqMap
	 * @param secret
	 * @return
	 */
	private static String addSignForReqParam(TreeMap<String, Object> reqMap, String secret) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : reqMap.entrySet()) {
			if(entry.getValue() instanceof Collection) {
				// 集合类要特殊处理一下-AbstractCollecion.toString()会自动添加空格
				sb.append(entry.getKey()).append("=").append(entry.getValue().toString().replace(" ", "")).append("&");				
			} else {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		String linkString = sb.substring(0, sb.length() - 1);// 去除最后一个'&'
		String clientSign = DigestUtils.md5Hex(linkString + secret);
		System.out.println("clinet linkString:" + linkString + ", sign:" + clientSign);
		reqMap.put("sign", clientSign);
		return JSON.toJSONString(reqMap);
	}

}

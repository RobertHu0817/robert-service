package com.ll.platform.robertService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title IgnoreUrlsConfig.java
 * @Description 开放资源路径-由配置文件读取
 * @author Robert
 * @date 2019年11月28日
 */
@Component
@ConfigurationProperties(prefix = "props")
@Getter
@Setter
public class YmlConfig {

	private List<String> ignoredUrls;

}

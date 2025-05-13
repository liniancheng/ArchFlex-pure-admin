package com.adtec.rdc.base.common.base.service.feign;

import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dgmp-common-service", contextId = "base-cloud-common-dgmp-common")
public interface DgmpCommonService {
	@PostMapping("/search/delete")
	Future<Boolean> deleteSearchIndex(@RequestBody Map<String,String> map);
}

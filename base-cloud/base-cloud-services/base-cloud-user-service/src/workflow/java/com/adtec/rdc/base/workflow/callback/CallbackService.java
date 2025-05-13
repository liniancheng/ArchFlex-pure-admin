package com.adtec.rdc.base.workflow.callback;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQuery;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;

/**
 * 回调服务类
 * @author JTao
 *
 */
@Component
@Import(FeignClientsConfiguration.class)
public class CallbackService {
	private FeignClientProxy feignClientProxy;
    private Feign.Builder builder;

    @Autowired
    public CallbackService(Decoder decoder, Encoder encoder, Client client, Contract contract) {
    	this.builder = Feign.builder()
				.client(client)
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract);
    }

    public ApiResult<Boolean> callback(String uri, WorkflowQuery workflow) throws URISyntaxException {
    	this.feignClientProxy = this.builder.target(FeignClientProxy.class, uri);
        return this.feignClientProxy.callback(workflow);
    }
}

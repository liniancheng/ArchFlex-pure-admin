package com.adtec.rdc.base.auth.serializer;

import java.io.IOException;
import java.util.Map;

import com.adtec.rdc.base.auth.exception.CustomOauth2Exception;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author: JTao
 * @date: 2018/10/12 10:19
 * @description: 异常json序列化方式
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauth2Exception> {

	private static final long serialVersionUID = 1L;

	protected CustomOauthExceptionSerializer() {
        super(CustomOauth2Exception.class);
    }

    @Override
    public  void serialize(CustomOauth2Exception value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        //HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        gen.writeStartObject();
        gen.writeStringField("code", String.valueOf(value.getHttpErrorCode()));
        if ("FIRST".equals(value.getMessage())|| "LOCKED".equals(value.getMessage())) {
        	gen.writeStringField("data", value.getMessage());
        	gen.writeStringField("message", value.getMessage());
        } else {
        	gen.writeStringField("message", "用户名或密码错误");
        	gen.writeStringField("data", null);
        }
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}

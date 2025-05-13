package com.adtec.rdc.base.gateway.filter;

import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.model.bo.StatisticalInfo;
import com.adtec.rdc.base.common.model.bo.StatisticalStrategy;
import com.adtec.rdc.base.common.util.UrlUtil;
import com.adtec.rdc.base.common.util.UserUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名：StatisticsFilter</br>
 * 模块：</br>
 * 说明：</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author 何伟/hewei@adtec.com.cn
 * @version V1.0
 * @copyright ADTEC
 * @since 2020/1/15
 */
@Component
public class StatisticsFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MessageQueueService messageQueueService;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUrl = request.getRequestURI();
        Cursor<Object> cursor = redisTemplate.opsForSet().scan(CommonConstants.BASE_REDIS_LIST_LEY_STATISTICAL_STRATEGY, ScanOptions.NONE);
        while (cursor.hasNext()) {
            StatisticalStrategy statisticalStrategy = (StatisticalStrategy)cursor.next();
            if (matchStar(statisticalStrategy.getServiceUrl(), requestUrl)) {
                StatisticalInfo statisticalInfo = new StatisticalInfo();
                statisticalInfo.setRequestHost(UrlUtil.getRemoteHost(request));
                statisticalInfo.setRequestMethod(request.getMethod());
                statisticalInfo.setRequestTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                statisticalInfo.setRequestUrl(requestUrl);
                statisticalInfo.setRequestUserId(UserUtil.getUserId(request));
                statisticalInfo.setResponseStatus(String.valueOf(ctx.getResponse().getStatus()));
                statisticalInfo.setUserAgent(request.getHeader("user-agent"));
                statisticalInfo.setStrategyId(statisticalStrategy.getStrategyId());
                try {
                    statisticalInfo.setResponseDataSize(IOUtils.toByteArray(ctx.getResponseDataStream()).length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messageQueueService.convertAndSend(MqQueueNameConstant.REQUEST_STATISTICS_QUEUE, statisticalInfo);
                break;
            }
        }
        return null;
    }

    private boolean matchStar(String regUrl, String requestUrl) {
        String[] regRoutes = regUrl.split("/");
        String[] requestRoutes = requestUrl.split("/");
        int j = 0;
        boolean skipMore = false;
        List<String> matchRoutes = new ArrayList<>();
        for (int i = 0; i < regRoutes.length; i++) {
            if (j >= requestRoutes.length) {
                break;
            }
            String reg = regRoutes[i];
            if (reg.equals("*")) {
                if (!matchStar(matchRoutes, requestRoutes, j)) {
                    return false;
                } else {
                    j += matchRoutes.size();
                }
                matchRoutes.clear();
                skipMore = false;
                j++;
                continue;
            }
            if (reg.equals("**")) {
                if (!matchStar(matchRoutes, requestRoutes, j)) {
                    return false;
                } else {
                    j += matchRoutes.size();
                }
                matchRoutes.clear();
                j++;
                skipMore = true;
                continue;
            }
            if (skipMore) {
                matchRoutes.add(reg);
            } else if (!reg.equals(requestRoutes[j++])) {
                return false;
            }
        }
        if (j < requestRoutes.length && !matchStar(matchRoutes, requestRoutes, j)) {
            return false;
        }
        return true;
    }

    private boolean matchStar(List<String> matchRoutes, String[] requestRoutes, int start) {
        int len = matchRoutes.size();
        if (len == 0) {
            return true;
        }
        for (int i = start; i < requestRoutes.length; i ++) {
            if (i > requestRoutes.length - len) {
                return false;
            }
            boolean match = true;
            for (int j = 0; j < len; j ++) {
                if (!requestRoutes[i+j].equals(matchRoutes.get(j))) {
                    match = false;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }
}

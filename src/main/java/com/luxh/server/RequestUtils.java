package com.luxh.server;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author luxiaohua
 * @Date 2019/6/5
 */
public class RequestUtils {

    /**
     * 获取请求参数
     * @param request
     */
    public static Map<String, Object> getParams(FullHttpRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        HttpMethod httpMethod = request.method();
        if(HttpMethod.GET == httpMethod) {
            // get
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            decoder.parameters().entrySet().forEach( entry ->
                    params.put(entry.getKey(), entry.getValue().get(0))
            );
        }else if(HttpMethod.POST == httpMethod) {
            // post
            try {
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
                decoder.offer(request);
                List<InterfaceHttpData> interfaceHttpDataList = decoder.getBodyHttpDatas();
                for(InterfaceHttpData interfaceHttpData : interfaceHttpDataList) {
                    Attribute data = (Attribute)interfaceHttpData;
                    params.put(data.getName(), data.getValue());
                }
            }catch(Exception e) {
                e.printStackTrace();
            }

        }else {
            // 暂不支持

        }
        return params;
    }
}

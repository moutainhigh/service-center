package com.baidubce.services.client;

import com.baidubce.AbstractBceClient;
import com.baidubce.BceClientConfiguration;
import com.baidubce.BceClientException;
import com.baidubce.auth.SignOptions;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.BceMetadataResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceRequest;
import com.baidubce.util.HttpUtils;
import com.baidubce.util.JsonUtils;
import com.shengsu.helper.entity.AxBindRequest;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.BindResponse;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created on 2019/12/3
 *
 * PnsClient这个类目前必须要在com.baidubce.services这个包下，不然会报错
 * 目前只写了一个请求示例：createAxbBindRequest
 * 其他请求参考接口文档，修改对应的请求参数，请求方法以及请求URI即可
 */
public class PnsClient extends AbstractBceClient {

    private static final String[] HEADERS_TO_SIGN = new String[]{"host", "x-bce-date"};

    private static final HttpResponseHandler[] handlers = new HttpResponseHandler[]{new BceMetadataResponseHandler(),
            new BceErrorResponseHandler(), new BceJsonResponseHandler()};

    public PnsClient(BceClientConfiguration clientConfiguration) {
        super(clientConfiguration, handlers);
    }

    /**
     * 填充Request Body
     * @param internalRequest
     * @param bceRequest
     */
    private void fillPayload(InternalRequest internalRequest, AbstractBceRequest bceRequest) {
        if (internalRequest.getHttpMethod() == HttpMethodName.POST || internalRequest.getHttpMethod() == HttpMethodName.PUT) {
            String strJson = JsonUtils.toJsonString(bceRequest);
            Object var4 = null;

            byte[] requestJson;
            try {
                requestJson = strJson.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var6) {
                throw new BceClientException("Unsupported encode.", var6);
            }

            internalRequest.addHeader("Content-Length", String.valueOf(requestJson.length));
            internalRequest.addHeader("Content-Type", "application/json; charset=utf-8");
            internalRequest.setContent(RestartableInputStream.wrap(requestJson));
        }

    }

    /**
     * 构造请求
     * @param bceRequest
     * @param httpMethod
     * @param pathVariables
     * @return
     */
    private InternalRequest createRequest(AbstractBceRequest bceRequest, HttpMethodName httpMethod, String... pathVariables) {
        List<String> path = new ArrayList();
        if (pathVariables != null) {
            String[] paths = pathVariables;
            int pathLen = pathVariables.length;

            for(Integer pathIdx = 0; pathIdx < pathLen; ++pathIdx) {
                String pathVariable = paths[pathIdx];
                path.add(pathVariable);
            }
        }

        URI uri = HttpUtils.appendUri(this.getEndpoint(), (String[])path.toArray(new String[path.size()]));
        InternalRequest request = new InternalRequest(httpMethod, uri);
        SignOptions signOptions = new SignOptions();
        signOptions.setHeadersToSign(new HashSet(Arrays.asList(HEADERS_TO_SIGN)));
        request.setSignOptions(signOptions);
        request.setCredentials(bceRequest.getRequestCredentials());
        return request;
    }

    /**
     * AXB绑定接口
     *
     * @param axbBindRequest
     * @return
     */
    public BindResponse createAxbBindRequest(AxbBindRequest axbBindRequest) {
        InternalRequest internalRequest = this.createRequest(axbBindRequest, HttpMethodName.POST, "/cloud/api/v1/axb/binding");
        fillPayload(internalRequest, axbBindRequest);
        return (BindResponse)this.invokeHttpClient(internalRequest, BindResponse.class);
    }

    public BindResponse createAxBindRequest(AxBindRequest axBindRequest) {
        InternalRequest internalRequest = this.createRequest(axBindRequest, HttpMethodName.POST, "/cloud/api/v1/ax/binding");
        fillPayload(internalRequest, axBindRequest);
        return (BindResponse)this.invokeHttpClient(internalRequest, BindResponse.class);
    }
}

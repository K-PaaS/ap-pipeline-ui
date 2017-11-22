package paasta.delivery.pipeline.ui.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import paasta.delivery.pipeline.ui.exception.CustomException;
import paasta.delivery.pipeline.ui.job.Job;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.ui.common
 *
 * @author REX
 * @version 1.0
 * @since 5 /10/2017
 */
@Service
public class RestTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateService.class);
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private final String commonApiBase64Authorization;
    private final String deliveryPipelineApiBase64Authorization;
    private final String inspectionApiBase64Authorization;
    private final String binaryStorageApiBase64Authorization;
    private String base64Authorization;
    private String baseUrl;

    private final RestTemplate restTemplate;


    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;

    // DELIVERY PIPELINE API
    @Value("${deliveryPipelineApi.url}")
    private String deliveryPipelineApiUrl;

    //  INSPECTION API
    @Value("${inspectionApi.url}")
    private String inspectionApiUrl;

    //  BINARY STORAGE API
    @Value("${binaryStorageApi.url}")
    private String binaryStorageApiUrl;


    /**
     * Instantiates a new Rest template service.
     *
     * @param restTemplate                             the rest template
     * @param commonApiAuthorizationId                 the common api authorization id
     * @param commonApiAuthorizationPassword           the common api authorization password
     * @param deliveryPipelineApiAuthorizationId       the delivery pipeline api authorization id
     * @param deliveryPipelineApiAuthorizationPassword the delivery pipeline api authorization password
     * @param inspectionApiAuthorizationId             the inspection api authorization id
     * @param inspectionApiAuthorizationPassword       the inspection api authorization password
     * @param binaryStorageApiAuthorizationId          the binary storage api authorization id
     * @param binaryStorageApiAuthorizationPassword    the binary storage api authorization password
     */
    @Autowired
    public RestTemplateService(RestTemplate restTemplate,
                               @Value("${commonApi.authorization.id}") String commonApiAuthorizationId,
                               @Value("${commonApi.authorization.password}") String commonApiAuthorizationPassword,
                               @Value("${deliveryPipelineApi.authorization.id}") String deliveryPipelineApiAuthorizationId,
                               @Value("${deliveryPipelineApi.authorization.password}") String deliveryPipelineApiAuthorizationPassword,
                               @Value("${inspectionApi.authorization.id}") String inspectionApiAuthorizationId,
                               @Value("${inspectionApi.authorization.password}") String inspectionApiAuthorizationPassword,
                               @Value("${binaryStorageApi.authorization.id}") String binaryStorageApiAuthorizationId,
                               @Value("${binaryStorageApi.authorization.password}") String binaryStorageApiAuthorizationPassword) {
        this.restTemplate = restTemplate;

        this.commonApiBase64Authorization = "Basic "
                + Base64Utils.encodeToString(
                (commonApiAuthorizationId + ":" + commonApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));
        this.deliveryPipelineApiBase64Authorization = "Basic "
                + Base64Utils.encodeToString(
                (deliveryPipelineApiAuthorizationId + ":" + deliveryPipelineApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));
        this.inspectionApiBase64Authorization = "Basic "
                + Base64Utils.encodeToString(
                (inspectionApiAuthorizationId + ":" + inspectionApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));
        this.binaryStorageApiBase64Authorization = "Basic "
                + Base64Utils.encodeToString(
                (binaryStorageApiAuthorizationId + ":" + binaryStorageApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));

    }


    /**
     * Send map.
     *
     * @param reqApi     the req api
     * @param reqUrl     the req url
     * @param httpMethod the http method
     * @param bodyObject the body object
     * @return the map
     */
    public Map send(String reqApi, String reqUrl, HttpMethod httpMethod, Object bodyObject) {

        setApiUrlAuthorization(reqApi);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, base64Authorization);
        reqHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);

        LOGGER.info("POST >> Request: {}, {baseUrl} : {}, Content-Type: {}", HttpMethod.POST, reqUrl, reqHeaders.get(CONTENT_TYPE));
        ResponseEntity<Map> resEntity = restTemplate.exchange(baseUrl + reqUrl, httpMethod, reqEntity, Map.class);
        LOGGER.info("Map send :: Response Type: {}", resEntity.getBody().getClass());

        return resEntity.getBody();
    }


    /**
     * Send t.
     *
     * @param <T>          the type parameter
     * @param reqApi       the req api
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @return the t
     */
    public <T> T send(String reqApi, String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {

        setApiUrlAuthorization(reqApi);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, base64Authorization);
        reqHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);

        LOGGER.info("<T> T send :: Request : {} {baseUrl} : {}, Content-Type: {}", httpMethod, reqUrl, reqHeaders.get(CONTENT_TYPE));
        ResponseEntity<T> resEntity = restTemplate.exchange(baseUrl + reqUrl, httpMethod, reqEntity, responseType);
        if (resEntity.getBody() != null) {
            LOGGER.info("Response Type: {}", resEntity.getBody().getClass());
        } else {
            LOGGER.info("Response Type: {}", "response body is null");
        }


        return resEntity.getBody();
    }


    /**
     * Cf send t.
     *
     * @param <T>          the type parameter
     * @param reqToken     the req token
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @return the t
     */
    public <T> T cfSend(String reqToken, String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, "bearer " + reqToken);
        reqHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);

        LOGGER.info("<T> T send :: Request : {} {baseUrl} : {}, Content-Type: {}", httpMethod, reqUrl, reqHeaders.get(CONTENT_TYPE));
        ResponseEntity<T> resEntity = restTemplate.exchange(reqUrl, httpMethod, reqEntity, responseType);
        if (resEntity.getBody() != null) {
            LOGGER.info("Response Type: {}", resEntity.getBody().getClass());
        } else {
            LOGGER.info("Response Type: {}", "response body is null");
        }


        return resEntity.getBody();
    }


    /**
     * Send to scm t.
     *
     * @param <T>          the type parameter
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @param job          the custom job
     * @return the t
     */
    public <T> T customSend(String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType, Job job) {

        String authorization = job.getRepositoryAccountId() + ":" + job.getRepositoryAccountPassword();

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, "Basic " + Base64Utils.encodeToString(authorization.getBytes(StandardCharsets.UTF_8)));
        reqHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);

        LOGGER.info("<T> T send :: Request : {} {baseUrl} : {}, Content-Type: {}", httpMethod, reqUrl, reqHeaders.get(CONTENT_TYPE));
        ResponseEntity<T> resEntity = restTemplate.exchange(reqUrl, httpMethod, reqEntity, responseType);
        if (resEntity.getBody() != null) {
            LOGGER.info("Response Type: {}", resEntity.getBody().getClass());
        } else {
            LOGGER.info("Response Type: {}", "response body is null");
        }

        return resEntity.getBody();
    }


    /**
     * Send multipart map.
     *
     * @param <T>          the type parameter
     * @param reqApi       the req api
     * @param reqUrl       the req url
     * @param file         the file
     * @param responseType the response type
     * @return the map
     * @throws Exception the exception
     */
    public <T> T sendMultipart(String reqApi, String reqUrl, MultipartFile file, Class<T> responseType) throws Exception {

        setApiUrlAuthorization(reqApi);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, base64Authorization);
        reqHeaders.add(CONTENT_TYPE, "multipart/form-data");

        final MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();

        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() throws IllegalStateException {
                return file.getOriginalFilename();
            }
        };

        data.add("file", resource);
        data.add("fileName", file.getOriginalFilename());
        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(data, reqHeaders);

        LOGGER.info("POST >> Request: {} {baseUrl} : {}, Content-Type: {}", HttpMethod.POST, reqUrl, reqHeaders.get(CONTENT_TYPE));
        final ResponseEntity<T> resEntity = restTemplate.exchange(baseUrl + reqUrl, HttpMethod.POST, requestEntity, responseType);
        if (resEntity.getBody() != null) {
            LOGGER.info("Map sendMultipart :: Response Type: {}", reqUrl, resEntity.getBody().getClass());
        } else {
            LOGGER.info("Response Type: {}", "response body is null");
        }

        return resEntity.getBody();
    }


    private void setApiUrlAuthorization(String reqApi) {

        String apiUrl = "";
        String authorization = "";

        // COMMON API
        if (Constants.TARGET_COMMON_API.equals(reqApi)) {
            apiUrl = commonApiUrl;
            authorization = commonApiBase64Authorization;
        }

        // DELIVERY PIPELINE API
        if (Constants.TARGET_DELIVERY_PIPELINE_API.equals(reqApi)) {
            apiUrl = deliveryPipelineApiUrl;
            authorization = deliveryPipelineApiBase64Authorization;
        }

        //  INSPECTION API
        if (Constants.TARGET_INSPECTION_API.equals(reqApi)) {
            apiUrl = inspectionApiUrl;
            authorization = inspectionApiBase64Authorization;
        }

        //  BINARY STORAGE API
        if (Constants.TARGET_BINARY_STORAGE_API.equals(reqApi)) {
            apiUrl = binaryStorageApiUrl;
            authorization = binaryStorageApiBase64Authorization;
        }

        if ("".equals(apiUrl) || "".equals(authorization)) {
            try {
                throw new CustomException("ERROR :: setApiUrlAuthorization");
            } catch (Exception e) {
                LOGGER.error("");
                throw new CustomException("ERROR :: setApiUrlAuthorization", e);
            }
        }

        this.base64Authorization = authorization;
        this.baseUrl = apiUrl;
    }

    /**
     * Make query param string.
     *
     * @param reqUrl the req url
     * @param obj    the obj
     * @return the string
     */
    public String makeQueryParam(String reqUrl, Object obj) {
        ObjectMapper om = new ObjectMapper();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(reqUrl);
        Map<String, Object> codingRulesMap = om.convertValue(obj, Map.class);

        for(String key : codingRulesMap.keySet()) {

            if (codingRulesMap.get(key) != null) {
                builder.queryParam( key, codingRulesMap.get(key) );
            }
        }
        return builder.build().encode().toUriString();

    }

}

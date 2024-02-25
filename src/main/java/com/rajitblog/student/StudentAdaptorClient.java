package com.rajitblog.student;

import com.rajitblog.student.dto.ErrorMsg;
import com.rajitblog.utils.ErrorMsgMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.Optional;
@Slf4j
public class StudentAdaptorClient {
    public <H, K> H doPost(String url, K body, Class<H> responseType, Map<String, String> headers) {
        HttpEntity httpEntity = getHttpEntity(body, headers);
        try {
            RestTemplate restTemplate=new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            throw getException(url, exception);
        } catch (RestClientException exception) {
            throw getException(url, exception);
        }
    }
    private RuntimeException getException(String url, HttpStatusCodeException exception) {
        String message = String.format("Error occurred when invoking student API url %s ", url);
        Optional<ErrorMsg> errorMsgOptional = new ErrorMsgMaker().makeFrom(exception.getResponseBodyAsString());
        if (errorMsgOptional.isPresent()) {
            log.info("user id:{} , message:{}, status:{}",errorMsgOptional.get().getUser_id(),errorMsgOptional.get().getMessage(),errorMsgOptional.get().getStatus());
            return new StudentAdaptorApiException(errorMsgOptional.get().getMessage(), exception, url, errorMsgOptional.get().getMessage(),
                    errorMsgOptional.get().getStatus(), errorMsgOptional.get().getUser_id());
        } else {
            return new StudentAdaptorApiException(message, exception, url, exception.getMessage(), exception.getStatusText(),
                    exception.getMessage());
        }
    }

    private RuntimeException getException(String url, RestClientException exception) {
        String message = String.format("Error occured when invoking student API url %s with error %s", url, exception.getMessage());
        return new StudentAdaptorApiException(message, exception, url);
    }

    public <H> H doGet(String url, Class<H> responsetype, Map<String, String> headers) {
        try {
            HttpEntity httpEntity = getHttpEntity(null, headers);
            RestTemplate restTemplate=new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responsetype).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            throw getException(url, exception);
        } catch (RestClientException exception) {
            throw getException(url, exception);
        }
    }

    public <H> H doPost(String url, HttpEntity requestBody, Class<H> responsetype) {
        try {
            RestTemplate restTemplate=new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.POST, requestBody, responsetype).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            throw getException(url, exception);
        } catch (RestClientException exception) {
            throw getException(url, exception);
        }
    }

    private <K> HttpEntity getHttpEntity(K body, Map<String, String> headers) {
        HttpHeaders httpHeaders = getHttpHeaders(headers);
        if (body == null) {
            return new HttpEntity<K>(httpHeaders);
        } else {
            return new HttpEntity<K>(body, httpHeaders);
        }
    }

    private HttpHeaders getHttpHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!headers.isEmpty()) {
            headers.forEach(httpHeaders::set);
        }
        return httpHeaders;
    }
}

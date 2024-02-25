package com.rajitblog.api;

import com.rajitblog.BusinessException;
import com.rajitblog.error.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Slf4j
public class AppExceptionCreator extends RuntimeException{
    public AppException createException(String url, BusinessException exception) {

        log.error("Error in executing API for url : " + url, exception);
        HttpStatus statusCode = getStatusCode(exception);
        log.info("exception status code {}", statusCode.value());
        AppException build = AppException.builder().status(exception.getStatusCode().name()).statusCode(statusCode)
                .message(getErrorMessage(exception.getStatusCode(), exception)).information(exception.getInformation())
                .build();
        log.info("error code {}", build.getStatusCode());
        log.info("information {}", exception.getInformation());
        return build;
    }

    public AppException createException(String url, Exception exception) {
        log.info(String.valueOf(exception instanceof BusinessException));
        log.info("exception occured of type {}", exception.getClass(), exception);
        if (exception instanceof BusinessException) {
            return createException(url, (BusinessException) exception);
        }
        return AppException.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).status(BusinessError.GENERIC_ERROR.name())
                .message(getErrorMessage(BusinessError.GENERIC_ERROR, exception)).build();
    }

    public String getErrorMessage(BusinessError error, Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            if (!businessException.getInformation().isEmpty()) {
                return getErrorMessage(businessException);
            }
        }
        switch (error) {
            case GENERIC_ERROR:
                return "Sorry, Something Went wrong, please try again in sometime";
            default:
                return exception.getMessage();
        }
    }

    /**
     * if exception.information is not empty call this method and get the message
     * this function populates the message based on the error code give by the {@link in.dreamplug.orden.datascience.StudentAdaptorClient}
     */
    public String getErrorMessage(BusinessException exception) {
        String error_code = String.valueOf(exception.getInformation().get("error_code"));
        switch (error_code) {
            case "unexpected_error":
                return "Unexpected error";
            default:
                return error_code;
        }
    }

    //GENERIC_ERROR is treated as INTERNAL_SERVER_ERROR, other errors are either BAD_REQUEST or UNAUTHORIZED
    public HttpStatus getStatusCode(BusinessError error) {
        log.warn("exception came with error code :{}", error);
        switch (error) {
            case GENERIC_ERROR:
            case CREATE_SYBILL_AFFLUENCE_SCOR_ERROR:
                return HttpStatus.BAD_REQUEST;
            case GET_APOLLO_REWARDS_RANKING_ERROR:
            case AUTHENTICATION_ERROR:
            default:
                log.error("default triggered");
                return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus getStatusCode(BusinessException businessException) {
        Map<String, Object> information = businessException.getInformation();
        if (information.isEmpty()) {
            log.info("error code:{}",information);
            return getStatusCode(businessException.getStatusCode());
        } else {
            String errorCode = String.valueOf(information.get("message"));
            log.info("error code:{}",errorCode);
            switch (errorCode) {
                case "unexpected_error":
                    return HttpStatus.INTERNAL_SERVER_ERROR;
                case "authorisation failed":
                    return HttpStatus.UNAUTHORIZED;
                case "invalid_uri":
                    return HttpStatus.NOT_FOUND;
                case "invalid_path":
                    return HttpStatus.BAD_REQUEST;
                default:
                    return getStatusCode(businessException.getStatusCode());
            }
        }
    }
}

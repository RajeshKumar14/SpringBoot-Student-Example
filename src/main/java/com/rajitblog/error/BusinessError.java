package com.rajitblog.error;
/**
 * A class to hold error codes for all business services, this will be used to communicate to client through
 * AppException errorCode field. Clients can build logic / display messages according to this error code.
 */
public enum BusinessError {
    GENERIC_ERROR,
    CREATE_SYBILL_AFFLUENCE_SCOR_ERROR,
    GET_APOLLO_REWARDS_RANKING_ERROR,
    AUTHENTICATION_ERROR,
    CONTRACT_REFRESH_ERROR
}

package com.newsapp.app.data.enums;

import javax.net.ssl.HttpsURLConnection;

public enum HttpExceptionType {

    DEFAULT("", -1),
    UNAUTHORIZED("Unauthorised User", HttpsURLConnection.HTTP_UNAUTHORIZED),
    FORBIDDEN("Forbidden", HttpsURLConnection.HTTP_FORBIDDEN),
    INTERNAL_SERVER_ERROR("Internal Server Error", HttpsURLConnection.HTTP_INTERNAL_ERROR),
    BAD_REQUEST("Bad Request", HttpsURLConnection.HTTP_BAD_REQUEST),
    NO_INTERNET_CONNECTION("No Internet Connection", 0),
    API_ERROR("Something Went Wrong API is not responding properly!", 1);

    public String errType;
    public int errCode;

    HttpExceptionType(String type, int resourceId) {
        errType = type;
        errCode = resourceId;
    }

    public static HttpExceptionType getExceptionsType(String type) {
        HttpExceptionType[] values = HttpExceptionType.values();
        for (HttpExceptionType value : values) {
            if (value.errType.equalsIgnoreCase(type)) {
                return value;
            }
        }
        return DEFAULT;
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String mType) {
        this.errType = mType;
    }
}
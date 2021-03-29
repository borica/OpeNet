package com.br.opet.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class RestUtil {
	
	public static final String HTTPS = "https://";
	public static final String SEPARATOR = "&";
	public static final String EQUALS = "=";
	public static final String PARAM = "?";
	
	public static final String SCOPE = "scope";
	public static final String GRANT_TYPE = "grant_type";
	public static final String OFFLINE = "offline";
	public static final String INCLUDE_GRANTED_SCOPES = "include_granted_scopes";
	public static final String RESPONSE_TYPE = "response_type";
	public static final String STATE = "state";
	public static final String TRUE = "true";
	
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CLIENT_ID = "client_id";
	public static final String CODE = "code";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String CONTENT_TYPE = "Content-Type";
	
	public static final String APPLICATION_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
	public static final String APPLICATION_JSON = "application/json";
	
	public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append(EQUALS);
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append(SEPARATOR);
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
	
}

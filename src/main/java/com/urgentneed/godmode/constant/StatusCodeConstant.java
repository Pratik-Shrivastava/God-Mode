package com.urgentneed.godmode.constant;

public class StatusCodeConstant {
    // 2xx: Success
    public static final int HTTP_OK = 200;                                 // Standard success
    public static final int HTTP_CREATED = 201;                            // Resource created
    public static final int HTTP_ACCEPTED = 202;                           // Request accepted, processing not complete
    public static final int HTTP_NO_CONTENT = 204;                         // Success but no content returned

    // 3xx: Redirection
    public static final int HTTP_MOVED_PERMANENTLY = 301;
    public static final int HTTP_FOUND = 302;
    public static final int HTTP_NOT_MODIFIED = 304;

    // 4xx: Client Errors
    public static final int HTTP_BAD_REQUEST = 400;                        // Generic client-side error
    public static final int HTTP_UNAUTHORIZED = 401;                       // Missing/invalid authentication
    public static final int HTTP_FORBIDDEN = 403;                          // Authenticated but access denied
    public static final int HTTP_NOT_FOUND = 404;                          // Resource not found
    public static final int HTTP_METHOD_NOT_ALLOWED = 405;
    public static final int HTTP_NOT_ACCEPTABLE = 406;                     // Payload/format not acceptable
    public static final int HTTP_CONFLICT = 409;                           // Duplicate resource or version conflict
    public static final int HTTP_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int HTTP_UNPROCESSABLE_ENTITY = 422;

    // 5xx: Server Errors
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
    public static final int HTTP_NOT_IMPLEMENTED = 501;
    public static final int HTTP_BAD_GATEWAY = 502;
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;

    // Custom Application-Specific Codes (non-standard)
    public static final int APP_USER_ID_HEADER_MISSING = 290;
    public static final int APP_ADMIN_USER_ID_HEADER_MISSING = 291;
    public static final int APP_PROJECT_ROLES_HEADER_MISSING = 292;
    public static final int APP_SERVER_KEY_HEADER_MISSING = 293;
    public static final int APP_SERVER_KEY_AUTH_FAILED = 294;
    public static final int APP_PROJECT_ID_HEADER_MISSING = 295;
    public static final int APP_CATEGORY_HEADER_MISSING = 296;
    public static final int APP_PUBLIC_KEY_HEADER_MISSING = 297;
    public static final int APP_AUTHENTICATION_FAILED = 298;
    public static final int APP_EXCEPTION_OCCURRED = 599;                  // Reserved for unknown application error

}

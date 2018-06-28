package server.http;

public enum HttpPacketStatus {
	
	CON("100", "Continue"),
    SWPRO("101", "Switching Protocols"),
    OK("200", "OK"),
    CREATED("201", "Created"),
    ACCPT("202", "Accepted"),
    NOAUTHINFO("203", "Non-Authoritative Information"),
    NOCONTENT("204", "No Content"),
    RSTCONTENT("205", "Reset Content"),
    PARCONTENT("206", "Partial Content"),
    MULCHOICE("300", "Multiple Choices"),
    MOVPERM("301", "Moved Permanently"),
    FOUND("302", "Found"),
    SEEOTHER("303", "See Other"),
    NOTMOD("304", "Not Modified"),
    USEPROXY("305", "Use Proxy"),
    UNUSED("306", "(Unused)"),
    TEMPREDIRECT("307", "Temporary Redirect"),
    BADREQUEST("400", "Bad Request"),
    UNAUTH("401", "Unauthorized"),
    PAYREQ("402", "Payment Required"),
    FORBIDDEN("403", "Forbidden"),
    NOTFOUND("404", "Not Found"),
    NOTALLOWED("405", "Method Not Allowed"),
    NOTACCEPTABLE("406", "Not Acceptable"),
    PROXYAUTHREQ("407", "Proxy Authentication Required"),
    REQTMOUT("408", "Request Timeout"),
    CONFLICT("409", "Conflict"),
    GONE("410", "Gone"),
    LENREQ("411", "Length Required"),
    PRECONFAILED("412", "Precondition Failed"),
    REQTOOLARGE("413", "Request Entity Too Large"),
    URITOOLONG("414", "Request-URI Too Long"),
    UNSUPPORTEDMIME("415", "Unsupported Media Type"),
    REQRANGEUNS("416", "Requested Range Not Satisfiable"),
    EXPFAILED("417", "Expectation Failed"),
    SERVERERROR("500", "Internal Server Error"),
    NOTIMPLEMENTED("501", "Not Implemented"),
    BADGATEWAY("502", "Bad Gateway"),
    SERVICEUNAVAILABLE("503", "Service Unavailable"),
    GTWAYTMOUT("504", "Gateway Timeout"),
    HTTPVNOTSUPPORTED("505", "HTTP Version Not Supported");
	
	private final String key;
    private final String value;

    HttpPacketStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }

}

package intern.server.common.constant;

public final class MappingConstants {

    public static final String API_VERSION_PREFIX = "/api/v1";

    public static final String API_AUTH_PREFIX = API_VERSION_PREFIX + "/auth";

    public static final String ADMIN = "/admin";

    public static final String USER = "/user";

    public static final String API_ADMIN_PREFIX = API_VERSION_PREFIX + ADMIN;

    public static final String API_USER_PREFIX = API_VERSION_PREFIX + USER;

    public static final String API_ADMIN_USER_MANAGEMENT = API_ADMIN_PREFIX + "/user";

    public static final String API_USER_USER_MANAGEMENT = API_USER_PREFIX + "/user";

}

package intern.server.common.constant;

public final class MappingConstants {

    /* API VERSION PREFIX */
    public static final String API_VERSION_PREFIX = "/api/v1";

    /* API COMMON */
    public static final String API_COMMON = API_VERSION_PREFIX + "/common";

    /* AUTHENTICATION */
    public static final String API_AUTH_PREFIX = API_VERSION_PREFIX + "/auth";

    // Constants representing the base paths for various resources
    public static final String ADMIN = "/admin";

    public static final String USER = "/user"; // Changed from "/manage" to "/user"

    public static final String PROJECT_DETAILS = "/project-details";

    // Constants representing the full paths for various resources
    public static final String API_ADMIN_PREFIX = API_VERSION_PREFIX + ADMIN;
    public static final String API_USER_PREFIX = API_VERSION_PREFIX + USER; // Changed from "/manage" to "/user"
    public static final String API_PROJECT_DETAILS_PREFIX = API_VERSION_PREFIX + PROJECT_DETAILS;

    public static final String API_ADMIN_PROJECT = API_ADMIN_PREFIX + "/project";

    public static final String API_ADMIN_ROLE = API_ADMIN_PREFIX + "/role";

    public static final String API_ADMIN_ACCOUNT_MANAGE = API_ADMIN_PREFIX + "/account-manage";

    // User-related resources
    public static final String API_USER_INTERN = API_USER_PREFIX + "/intern"; // Changed from "/manage" to "/user"
    public static final String API_USER_MEETINGS = API_USER_PREFIX + "/meetings"; // Changed from "/manage" to "/user"
    public static final String API_USER_EVALUATION = API_USER_PREFIX + "/evaluation"; // Changed from "/manage" to "/user"
}

package intern.server.utils;

import intern.server.common.base.PageableRequest;
import intern.server.common.base.ResponseObject;
import intern.server.common.constant.PaginationConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static String appendWildcard(String url) {
        return url + "/**";
    }

    public static Pageable createPageable(PageableRequest request, String defaultSortBy) {
        return PageRequest.of(
                request.getPage() - 1,
                request.getSize() == 0 ? PaginationConstant.DEFAULT_SIZE : request.getSize(),
                Sort.by(
                        (Sort.Direction.fromString(
                                request.getOrderBy()) == Sort.Direction.DESC ||
                                request.getOrderBy() == null
                        ) ? Sort.Direction.DESC : Sort.Direction.ASC,
                        (request.getSortBy() == null
                                || request.getSortBy().isEmpty()
                        ) ? defaultSortBy : request.getSortBy()
                ));
    }

    public static Pageable createPageable(PageableRequest request, String defaultSortBy, String defaultOrderBy) {
        int page = request.getPage() - 1;
        int size = request.getSize() == 0 ? PaginationConstant.DEFAULT_SIZE : request.getSize();
        Sort.Direction direction = request.getOrderBy() == null || request.getOrderBy().isEmpty()
                ? Sort.Direction.fromString(defaultOrderBy)
                : Sort.Direction.fromString(request.getOrderBy());
        String sortBy = request.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = defaultSortBy;
        }
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }

    public static Pageable createPageable(PageableRequest request) {
        return createPageable(request, "created_date", "DESC");
    }

    public static ResponseEntity<?> createResponseEntity(ResponseObject<?> responseObject) {
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^0[0-9]{9,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return !matcher.matches();
    }

    public static String replaceManySpaceToOneSpace(String name) {
        // Thay thế tất cả khoảng trắng liên tiếp bằng dấu gạch dưới
        return name.replaceAll("\\s+", " ");
    }

    public static String replaceSpaceToEmpty(String name) {
        // Thay thế tất cả khoảng trắng liên tiếp bằng dấu gạch dưới
        return name.replaceAll("\\s+", "");
    }

    private static final Map<Character, Character> SPECIAL_CHAR_MAP = new HashMap<>();

    public static String generateCodeFromName(String name) {
        // Chuyển role name chuỗi thành chữ hoa
        String upperCaseString = name.toUpperCase();

        // Thay thế các ký tự đặc biệt
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : upperCaseString.toCharArray()) {
            if (SPECIAL_CHAR_MAP.containsKey(c)) {
                stringBuilder.append(SPECIAL_CHAR_MAP.get(c));
            } else {
                stringBuilder.append(c);
            }
        }
        String replacedString = stringBuilder.toString();

        // Loại bỏ dấu
        String normalizedString = Normalizer.normalize(replacedString, Normalizer.Form.NFD);
        String withoutAccentString = normalizedString.replaceAll("\\p{M}", "");

        // Thay thế tất cả khoảng trắng liên tiếp bằng dấu gạch dưới
        return withoutAccentString.replaceAll("\\s+", "_");
    }

}

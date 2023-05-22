package com.dogoo.SystemWeighingSas.unitity;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Các helper method để làm việc với String
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class Strings {

    /**
     * Phiên bản ngắn gọn của method String.format() với Locale luôn là US
     *
     * @param format chuỗi mô tả format
     * @param params các tham số
     * @return A formatted string
     */
    public static String format(String format, Object... params) {
        return String.format(Locale.US, format, params);
    }

    /**
     * Nối các string và phân cách bởi một chuỗi quy định trước
     *
     * @param separator chuỗi dùng để phân cách các string
     * @param strs      các string cần nối
     * @return chuỗi chứa các string được nối
     */
    public static String join(String separator, String... strs) {
        return join(Arrays.asList(strs), separator);
    }

    /**
     * Nối các string và phân cách bởi một chuỗi quy định trước
     *
     * @param separator chuỗi dùng để phân cách các string
     * @param strs      các string cần nối
     * @return chuỗi chứa các string được nối
     */
    public static <T> String join(T[] strs, String separator) {
        return join(Arrays.asList(strs), separator);
    }

    /**
     * Convert một tập các đối tượng về String và nối chúng thành một
     * chuỗi được phân cách bởi một chuỗi quy định trước
     *
     * @param iterable  chứa tập các đối tượng cần nối
     * @param separator chuỗi dùng để phân cách các string
     * @return chuỗi chứa các string được nối
     */
    public static String join(Iterable<?> iterable, String separator) {
        return join(iterable.iterator(), separator, "", "");
    }

    /**
     * Convert một tập các đối tượng về String và nối chúng thành một
     * chuỗi được phân cách bởi một chuỗi quy định trước
     *
     * @param iterator  iterator trỏ tới tập các đối tượng cần nối
     * @param separator chuỗi dùng để phân cách các string
     * @return chuỗi chứa các string được nối
     */
    public static String join(Iterator<?> iterator, String separator) {
        return join(iterator, separator, "", "");
    }

    /**
     * Convert một tập các đối tượng về String và nối chúng thành một
     * chuỗi được phân cách bởi một chuỗi quy định trước
     *
     * @param iterable  chứa tập các đối tượng cần nối
     * @param separator chuỗi dùng để phân cách các string
     * @param prefix    chuỗi bổ sung vào đầu kết quả sau khi nối
     * @param suffix    chuỗi bổ sung vào cuối kết quả sau khi nối
     * @return chuỗi chứa các string được nối cùng với prefix và suffix
     */
    public static String join(Iterable<?> iterable, String separator, String prefix, String suffix) {
        return join(iterable.iterator(), separator, prefix, suffix);
    }

    /**
     * Convert một tập các đối tượng về String và nối chúng thành một
     * chuỗi được phân cách bởi một chuỗi quy định trước
     *
     * @param iterator  iterator trỏ tới tập các đối tượng cần nối
     * @param separator chuỗi dùng để phân cách các string
     * @param prefix    chuỗi bổ sung vào đầu kết quả sau khi nối
     * @param suffix    chuỗi bổ sung vào cuối kết quả sau khi nối
     * @return chuỗi chứa các string được nối cùng với prefix và suffix
     */
    public static String join(Iterator<?> iterator, String separator, String prefix, String suffix) {
        if (!iterator.hasNext()) return "";

        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(separator);
            }
        }

        String s = sb.toString();
        return s.isEmpty() ? s : prefix + sb.toString() + suffix;
    }

    public static boolean isNonEmpty(CharSequence source) {
        return source != null && source.length() > 0;
    }

    public static boolean isNullOrEmpty(CharSequence source) {
        return source == null || source.length() == 0;
    }

    public static boolean isNullOrStringEmpty(Object source) {
        return source == null || source.toString().isEmpty();
    }

    public static boolean isNullOrWhitespace(String source) {
        return source == null || source.trim().isEmpty();
    }

    /**
     * Kiểm tra một chuỗi có chứa ít nhất một chuỗi trong danh sách các
     * chuỗi con hay không
     *
     * @param source  chuỗi cần kiểm tra
     * @param strings danh sách các chuỗi con cần kiểm tra
     * @return true nếu chuỗi cần kiểm tra chứa ít nhất một chuỗi con
     * và false nếu ngược lại
     */
    public static boolean containsOnce(String source, String... strings) {
        for (String s : strings) {
            if (source.contains(s)) return true;
        }
        return false;
    }

    /**
     * Kiểm tra một chuỗi có chứa tất cả các chuỗi con hay không
     *
     * @param source  chuỗi cần kiểm tra
     * @param strings danh sách các chuỗi con cần kiểm tra
     * @return true nếu chuỗi chứa tất cả các chuỗi con cần kiểm tra
     * và false nếu ngược lại
     */
    public static boolean containsAll(String source, String... strings) {
        for (String s : strings) {
            if (!source.contains(s)) return false;
        }
        return true;
    }

    /**
     * Lấy về chuỗi con gồm một số các kí tự đầu tiên từ một chuỗi
     *
     * @param source       chuỗi ban đầu
     * @param numChars     số lượng kí tự đầu tiên cần lấy
     * @param withThreeDot có thêm dấu ba chấm (...) vào cuối chuỗi kết quả hay không
     * @param skip         số kí tự đầu tiên cần bỏ qua trước khi bắt đầu lấy
     * @return chuỗi kết quả
     */
    public static String firstCharacters(String source, int numChars, boolean withThreeDot, int skip) {
        try {
            return source.substring(skip, numChars) + (withThreeDot ? "..." : "");
        } catch (IndexOutOfBoundsException ignored) {
            return source;
        }
    }

    /**
     * Lấy về chuỗi con gồm một số các kí tự đầu tiên từ một chuỗi, có bổ sung
     * thêm dấu ba chấm (...) vào cuối chuỗi kết quả
     *
     * @param source   chuỗi ban đầu
     * @param numChars số lượng kí tự đầu tiên cần lấy
     * @return chuỗi được lấy ra từ chuỗi ban đầu chứa một số kí tự đầu tiên
     * từ chuỗi ban đầu và dấu ba chấm ở cuối (...)
     */
    public static String firstCharacters(String source, int numChars) {
        return firstCharacters(source, numChars, true, 0);
    }

    /**
     * Lấy về chuỗi con gồm một số các kí tự đầu tiên từ một chuỗi
     *
     * @param source   chuỗi ban đầu
     * @param numChars số lượng kí tự đầu tiên cần lấy
     * @param skip     số kí tự đầu tiên cần bỏ qua trước khi bắt đầu lấy
     * @return chuỗi được lấy ra từ chuỗi ban đầu chứa một số kí tự đầu tiên
     */
    public static String firstCharacters(String source, int numChars, int skip) {
        return firstCharacters(source, numChars, false, skip);
    }

    /**
     * Xóa các kí tự unicode sử dụng 4 bytes để lưu trữ
     *
     * @param source chuỗi ban đầu
     * @return chuỗi đã được xóa các kí tự unicode 4 bytes
     */
    public static String remove4bytesUnicodeSymbols(String source) {
        return source.replaceAll("[^\\u0000-\\uFFFF]", "");
    }

    /**
     * Lấy về chuỗi con gồm một số các kí tự cuối cùng từ một chuỗi
     *
     * @param source   chuỗi ban đầu
     * @param numChars số lượng kí tự cuối cùng cần lấy
     * @return chuỗi con chứa các kí tự cuối cùng từ chuỗi ban đầu
     */
    public static String lastCharacters(String source, int numChars) {
        try {
            return source.substring(source.length() - numChars);
        } catch (IndexOutOfBoundsException e) {
            return source;
        }
    }

    /**
     * Đơn giản hóa một chuỗi bằng cách bỏ đi các kí tự phân tách câu, từ
     *
     * @param source chuỗi cần xử lý
     * @return chuỗi đã được xử lý
     */
    public static String simplify(String source) {
        return source.toLowerCase().replaceAll("[.,:;?!\n\t]", "");
    }

    public static String utf8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static boolean isNullOrEmpty(String content) {
        return (content == null) || (content.isEmpty());
    }

    public static boolean isNotNullOrEmpty(String content) {
        return !isNullOrEmpty(content);
    }

    public static boolean isNotNullOrEmptyAll(String... contents) {
        for (String content : contents) {
            if (isNullOrEmpty(content)) return false;
        }
        return true;
    }

    public static String objectOrEmpty(String content) {
        return content == null ? "" : content;
    }

    public static boolean equalsStringNULL(String content) {
        return (content.equals("NULL")) || (content.equals("(NULL)"));
    }

    public static boolean notEqualsStringNULL(String content) {
        return !equalsStringNULL(content);
    }

    public static String removeAccent(String s) {
        String tmp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(tmp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
    }
}
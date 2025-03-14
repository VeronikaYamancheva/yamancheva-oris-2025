package constants;

public class PasswordTestCases {

    public static final String NULL_PASSWORD = null;

    public static final String EMPTY_PASSWORD = "";

    public static final String LONG_PASSWORD = "longPassword";

    public static final String SHORT_PASSWORD = "0";

    public static final String MIDDLE_PASSWORD = "12345";

    public static final String BLANK_PASSWORD = "          ";

    public static final String BLACKLIST_KNOWN_PASSWORD = "qwerty007";

    public static final String BLACKLIST_UNKNOWN_PASSWORD = "somePassword";

    public static final String UPPER_CORRECT_LOWER_CORRECT_PASSWORD = "AaBbCc";

    public static final String UPPER_MIDDLE_LOWER_MIDDLE_PASSWORD = "AaBb";

    public static final String UPPER_WRONG_LOWER_WRONG_PASSWORD = "Aa";

    public static final String ONLY_DIGITS_PASSWORD = "123";

    public static final String UPPER_CORRECT_LOWER_WRONG_PASSWORD = "ABCa";

    public static final String UPPER_CORRECT_LOWER_MIDDLE_PASSWORD = "ABCab";

    public static final String UPPER_MIDDLE_LOWER_CORRECT_PASSWORD = "ABabc";

    public static final String UPPER_MIDDLE_LOWER_WRONG_PASSWORD = "ABa";

    public static final String UPPER_WRONG_LOWER_CORRECT_PASSWORD = "Aabc";

    public static final String UPPER_WRONG_LOWER_MIDDLE_PASSWORD = "Aab";

    public static final String GOOD_PASSWORD = "goodPassword";

    public static final String BAD_PASSWORD = "badPassword";
}

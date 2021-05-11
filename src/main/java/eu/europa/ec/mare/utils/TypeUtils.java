package eu.europa.ec.mare.utils;

import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TypeUtils {
  /**
   * Return the Classpath / Classloader reference for the
   * provided class file.
   *
   * <p>
   * Convenience method for the code
   * </p>
   *
   * <pre>
   * String ref = myClassName.replace('.','/') + ".class";
   * </pre>
   *
   * @param className the class to reference
   * @return the classpath reference syntax for the class file
   */
  public static String toClassReference(String className)
  {
    return replace(className, '.', '/').concat(".class");
  }

  /**
   * Replace chars within string.
   * <p>
   * Fast replacement for {@code java.lang.String#}{@link String#replace(char, char)}
   * </p>
   *
   * @param str the input string
   * @param find the char to look for
   * @param with the char to replace with
   * @return the now replaced string
   */
  public static String replace(String str, char find, char with)
  {
    if (Objects.isNull(str))
      return null;

    if (find == with)
      return str;

    int c = 0;
    int idx = str.indexOf(find, c);
    if (idx == -1)
    {
      return str;
    }
    char[] chars = str.toCharArray();
    int len = chars.length;
    for (int i = idx; i < len; i++)
    {
      if (chars[i] == find)
        chars[i] = with;
    }
    return String.valueOf(chars);
  }

  public static String capitalize(String str) {
    return changeFirstCharacterCase(str, true);
  }

  private static String changeFirstCharacterCase(String str, boolean capitalize) {
    if (!hasLength(str)) {
      return str;
    } else {
      char baseChar = str.charAt(0);
      char updatedChar;
      if (capitalize) {
        updatedChar = Character.toUpperCase(baseChar);
      } else {
        updatedChar = Character.toLowerCase(baseChar);
      }

      if (baseChar == updatedChar) {
        return str;
      } else {
        char[] chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars, 0, chars.length);
      }
    }
  }

  public static boolean hasLength(String str) {
    return !Objects.isNull(str) && !str.isEmpty();
  }
}
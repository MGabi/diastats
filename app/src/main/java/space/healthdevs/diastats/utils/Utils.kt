package space.healthdevs.diastats.utils

import java.util.regex.Pattern

fun String.hasEmailPattern(): Boolean {
    val pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")
    return pattern.matcher(this).matches()
}

/**
 * Current AWS Cognito password requirements:
 *  - letters
 *  - digits
 *  - at least a length of 8
 */
fun String.hasPasswordPatternFromAws(): Boolean {
    val letterRegex = Regex("[a-zA-Z]+")
    val digitsRegex = Regex("[0-9]+")
    val lengthRegex = Regex("\\w{8,}")
    return this.contains(letterRegex) && this.contains(digitsRegex) && this.contains(lengthRegex)
}
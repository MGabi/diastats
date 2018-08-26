package space.healthdevs.diastats

import org.junit.Test

import org.junit.Assert.*
import space.healthdevs.diastats.utils.hasPasswordPatternFromAws
import space.healthdevs.diastats.utils.hasEmailPattern

class UtilsTest {
    @Test
    fun testHasEmailPattern() {
        var email = ""
        assertEquals(false, email.hasEmailPattern())
        email = "@."
        assertEquals(false, email.hasEmailPattern())
        email = "@"
        assertEquals(false, email.hasEmailPattern())
        email = "."
        assertEquals(false, email.hasEmailPattern())
        email = "abc@"
        assertEquals(false, email.hasEmailPattern())
        email = "abc@."
        assertEquals(false, email.hasEmailPattern())
        email = "abc@.com"
        assertEquals(false, email.hasEmailPattern())
        email = "@abc.com"
        assertEquals(false, email.hasEmailPattern())
        email = "@abc.com.com"
        assertEquals(false, email.hasEmailPattern())
        email = "abc@abc@abc.com.com"
        assertEquals(false, email.hasEmailPattern())
        email = "abc@abc.com"
        assertEquals(true, email.hasEmailPattern())
    }

    @Test
    fun testHasDigits() {
        var password = ""
        assertEquals(false, password.hasPasswordPatternFromAws())
        password = "abc"
        assertEquals(false, password.hasPasswordPatternFromAws())
        password = "123"
        assertEquals(false, password.hasPasswordPatternFromAws())
        password = "a1b2"
        assertEquals(false, password.hasPasswordPatternFromAws())
        password = "abcdefghksdk"
        assertEquals(false, password.hasPasswordPatternFromAws())
        password = "123145151256"
        assertEquals(false, password.hasPasswordPatternFromAws())
        password = "abcd1234"
        assertEquals(true, password.hasPasswordPatternFromAws())
    }
}

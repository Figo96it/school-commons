package pl.sda;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.credentials.Password;

import static junit.framework.TestCase.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class PasswordTest {

    private byte[][] encoded = new byte[][]{{59, 39, 69, 63, -88, 126, 58, 43, 33, -98, 36, 36},
            {-16, -39, -23, -125, 64, -31, -65, 18, 75, -46, 121, -25},
            {66, 58, 71, -106, 7, -61, 109, -32, -68, 79, 79, -24}};

    @Test
    @Parameters({
            "pass1",
            "dupa",
            "aaa"
    })
    public void checkRun(String param) {
        assertTrue(Password.checkPassword(param, Password.hashPassword(param)));
    }

    @Test
    @Parameters({
            "pass1, 0",
            "dupa, 1",
            "aaa, 2"
    })
    public void checkEncoded(String password, int index) {
        assertTrue(Password.checkPassword(password, encoded[index]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkEmptyString() {
        Password.hashPassword("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNullValue() {
        Password.hashPassword(null);
    }

}

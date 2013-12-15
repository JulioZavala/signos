package app.controller.auth;

import java.io.UnsupportedEncodingException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import app.zelper.misc.TypesUtil;

public class CryptMoodle implements PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object o) {
        String encodedPassword = null;
        try {
            encodedPassword = TypesUtil.getMD5(rawPass.trim());
            //encodedPassword = TypesUtil.getMD5(rawPass.trim() + GeneralStatic.getGeneralStatic().getSalt());
            return encodedPassword;
            

        } catch (UnsupportedEncodingException ex) {
        } finally {
            return encodedPassword;
        }


    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object o) {
        String pass2 = this.encodePassword(rawPass, null);
        return encPass.equals(pass2);
    }
}

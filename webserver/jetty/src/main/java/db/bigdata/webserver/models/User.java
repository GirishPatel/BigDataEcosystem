package db.bigdata.webserver.models;

import db.bigdata.webserver.commons.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.NoSuchAlgorithmException;

@Getter
@AllArgsConstructor
public class User {

    private String name;
    private String username;
    private String emailId;
    private String password;

    public void hashPassword() throws NoSuchAlgorithmException {
        this.password = Utility.getMD5(this.password);
    }

}

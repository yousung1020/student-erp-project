package common;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // 평문인 비밀번호를 BCrypt 해시로 변환
    public static String hashPassword(String password) {
        // gensalt() 메서드로 암호화에 사용할 salt 값 생성
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 입력된 비밀번호와 db에 있는 해시된 비밀번호가 일치하는지 확인
    public static boolean checkPassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }
}

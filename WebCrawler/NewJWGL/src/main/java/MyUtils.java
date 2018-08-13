import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class MyUtils {

    private static String codeStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxzy0123456789+/";

    public static String base64Str2Bin(String str) {
        int end = str.indexOf("="); // 去除末尾的'='
        if (end >= 0) {
            str = str.substring(0, end);
        }
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for(int i = 0; i < c.length; i++) {
            int index = codeStr.indexOf(c[i]); // 将每个字符转换为二进制
            String s = Integer.toBinaryString(index);
            int len = 6 - s.length(); // 将每个字符转换为二进制
            for(int j = 0; j < len; j++)
                s = "0" + s;
            sb.append(s);
        }
        return sb.toString();
    }

    public static PublicKey getPublicKey(String modulus, String exponent) throws Exception{
        // 将Base64转化为二进制
        String modulusBin = base64Str2Bin(modulus);
        String exponentBin = base64Str2Bin(exponent);
        BigInteger m = new BigInteger(modulusBin,2);
        BigInteger e = new BigInteger(exponentBin,2);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
        KeyFactory keyFactory = KeyFactory.getInstance("MyUtils");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static String EncryptByPublicKey(PublicKey publicKey , String data) throws Exception{
        Cipher cipher = Cipher.getInstance("MyUtils");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
}
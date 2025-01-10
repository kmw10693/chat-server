package chat.global.security.jwt;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RsaUtil {

    public PrivateKey getPrivateKey() throws GeneralSecurityException, URISyntaxException, IOException {
        var privateKeyContent = new String(Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("private_key_pkcs8.pem").toURI())));

        privateKeyContent = privateKeyContent.
                replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");

        var factory = KeyFactory.getInstance("RSA");
        var pkcs8EncodedKey = Base64.getDecoder().decode(privateKeyContent);

        return factory.generatePrivate(new PKCS8EncodedKeySpec(pkcs8EncodedKey));
    }

    public PublicKey getPublicKey() throws URISyntaxException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        var publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.pem").toURI())));

        publicKeyContent = publicKeyContent
                .replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");;

        var pkcs8EncodedKey = Base64.getDecoder().decode(publicKeyContent);
        var spec = new X509EncodedKeySpec(pkcs8EncodedKey);
        var factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }
}

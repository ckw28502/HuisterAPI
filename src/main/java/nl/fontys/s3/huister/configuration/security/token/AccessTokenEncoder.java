package nl.fontys.s3.huister.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}

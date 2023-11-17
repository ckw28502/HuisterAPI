package nl.fontys.s3.huister.configuration.security.token.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;


@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long id;
    private final UserRole role;
    private final String profilePictureUrl;

    public AccessTokenImpl(String subject, Long id, UserRole role,String profilePictureUrl) {
        this.subject = subject;
        this.id = id;
        this.role = role;
        this.profilePictureUrl=profilePictureUrl;
    }
}

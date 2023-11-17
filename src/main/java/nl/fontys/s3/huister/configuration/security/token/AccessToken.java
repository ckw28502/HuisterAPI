package nl.fontys.s3.huister.configuration.security.token;

import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;


public interface AccessToken {
    String getSubject();

    Long getId();

    UserRole getRole();

    String getProfilePictureUrl();

}

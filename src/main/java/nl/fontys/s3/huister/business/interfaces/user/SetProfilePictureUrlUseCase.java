package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest;

public interface SetProfilePictureUrlUseCase {
    void setProfilePictureUrl(SetProfilePictureUrlRequest request);
}

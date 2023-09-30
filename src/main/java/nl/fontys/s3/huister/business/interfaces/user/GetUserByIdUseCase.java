package nl.fontys.s3.huister.business.interfaces.user;


import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;

public interface GetUserByIdUseCase {
    GetUserByIdResponse getUserById(final int id);
}

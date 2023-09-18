package nl.fontys.s3.huister.business.user;


import nl.fontys.s3.huister.domain.response.user.GetUserByIdResponse;

public interface GetUserByIdUseCase {
    GetUserByIdResponse getUserById(final int id);
}

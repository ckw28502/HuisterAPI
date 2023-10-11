package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;

import java.util.List;

public interface GetAllOwnersUseCase {
    List<GetAllOwnersResponse>getAllOwners();
}

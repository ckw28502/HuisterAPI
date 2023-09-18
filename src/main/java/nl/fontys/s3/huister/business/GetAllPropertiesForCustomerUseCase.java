package nl.fontys.s3.huister.business;

import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesForCustomerResponse;

import java.util.List;

public interface GetAllPropertiesForCustomerUseCase {
    List<GetAllPropertiesForCustomerResponse>GetAllPropertiesForCustomer();
}

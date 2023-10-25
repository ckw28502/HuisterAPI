package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FakePropertyRepositoryImpl implements PropertyRepository {
    private final List<PropertyEntity>properties;
    private int NEXT_ID=1;

    public FakePropertyRepositoryImpl() {
        this.properties = new ArrayList<>();
    }

    @Override
    public Optional<PropertyEntity> getPropertyById(int id) {
        return properties.stream().filter(property ->
                property.getId()==id
                ).findFirst();
    }

    @Override
    public List<PropertyEntity> getAllNotRentedProperties() {
        List<PropertyEntity> notRentedProperties=this.properties.stream().filter(property ->
                !property.isRented()
        ).toList();
        return Collections.unmodifiableList(notRentedProperties);
    }

    @Override
    public List<PropertyEntity> getAllProperties() {
        return Collections.unmodifiableList(this.properties);
    }

    @Override
    public List<PropertyEntity> getPropertiesByOwner(int ownerId) {
        List<PropertyEntity>ownedProperties= this.properties.stream().filter(property ->
                    property.getOwnerId()==ownerId
                ).toList();
        return Collections.unmodifiableList(ownedProperties);
    }

    @Override
    public boolean isCityHasNoProperty(int cityId) {
        return properties.stream().noneMatch(property -> property.getCityId()==cityId);
    }

    @Override
    public int createProperty(CreatePropertyRequest request) {
        properties.add(PropertyEntity.builder()
                .area(request.getArea())
                .description(request.getDescription())
                .cityId(request.getCityId())
                .price(request.getPrice())
                .imageUrls(request.getImageUrls())
                .streetName(request.getStreetName())
                .postCode(request.getPostCode())
                .id(NEXT_ID)
                .isRented(false)
                .ownerId(request.getOwnerId())
                .build());
        NEXT_ID++;
        return NEXT_ID-1;
    }

    @Override
    public void updateProperty(UpdatePropertyRequest request) {
        Optional<PropertyEntity> optionalProperty=properties.stream().findFirst();
        if (optionalProperty.isPresent()){
            PropertyEntity updatedProperty=optionalProperty.get();
            updatedProperty.setDescription(request.getDescription());
            updatedProperty.setPrice(request.getPrice());
            updatedProperty.getImageUrls().addAll(request.getImageUrls());
            Collections.fill(properties, updatedProperty);
        }
    }

    @Override
    public void deleteProperty(int id) {
        properties.removeIf(property -> property.getId()==id);
    }

    @Override
    public int getPropertiesCount(int ownerId) {
        return (int) this.properties.stream().filter(property -> property.getOwnerId() == ownerId).count();
    }

    @Override
    public void rentProperty(OrderEntity order) {
        Optional<PropertyEntity>optionalProperty=this.getPropertyById(order.getPropertyId());
        if (optionalProperty.isPresent()){
            PropertyEntity property=optionalProperty.get();
            int index=this.properties.indexOf(property);
            property.setEndRent(LocalDate.now().plusMonths(order.getDuration()));
            property.setRented(true);
            this.properties.set(index,property);
        }
    }
}

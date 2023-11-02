DROP PROCEDURE IF EXISTS `delete_property`;


CREATE PROCEDURE `delete_property`(
    IN property_id BIGINT
)
BEGIN
    DECLARE city_id BIGINT;
    DECLARE count_property INT;
    SET count_property=0;

    SELECT p.city_id
    INTO city_id
    FROM property_table p
    WHERE p.id=property_id;

    DELETE FROM property_table WHERE id=property_id;

    SELECT COUNT(*)
    INTO count_property
    FROM property_table p
    WHERE p.city_id=city_id
    GROUP BY p.city_id;

    IF count_property<1 THEN
        DELETE FROM city_table WHERE id=city_id;
    END IF;

END
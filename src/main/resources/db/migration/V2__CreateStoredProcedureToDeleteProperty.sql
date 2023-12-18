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

    UPDATE property_table SET is_deleted=CURRENT_TIMESTAMP() WHERE id=property_id;

END
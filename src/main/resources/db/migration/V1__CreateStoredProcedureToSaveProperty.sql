DROP PROCEDURE IF EXISTS `save_property`;

CREATE PROCEDURE `save_property`(
    IN owner_id VARCHAR(255),
    IN city_name VARCHAR(255),
    IN street_name VARCHAR(255),
    IN post_code VARCHAR(255),
    IN description VARCHAR(255),
    IN image_url TEXT,
    IN area DOUBLE,
    IN price DOUBLE,
    IN house_number INT
)
BEGIN
    DECLARE city_id BIGINT;
    SET city_id=0;

    SELECT
        id
    INTO city_id
    FROM city_table
    WHERE name = city_name;

    IF city_id<1 THEN
        INSERT INTO city_table(name)
        VALUES(city_name);

        SELECT id
        INTO city_id
        FROM city_table
        WHERE name = city_name;
    END IF;

    INSERT INTO property_table(
        owner_id,
        city_id,
        street_name,
        post_code,
        description,
        image_url,
        area,
        price,
        end_rent,
        house_number
    ) VALUES (
        owner_id,
        city_id,
        street_name,
        post_code,
        description,
        image_url,
        area,
        price,
        NULL,
        house_number
    );

END
CREATE DATABASE pg_management_ms1;
USE pg_management_ms1;

CREATE TABLE users (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
email VARCHAR(150) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
role ENUM('ADMIN', 'RESIDENT') NOT NULL,
phone_number VARCHAR(15) NOT NULL,
room_number VARCHAR(10),
bed_number VARCHAR(5),
move_in_date DATE,
move_out_date DATE,
emergency_contact_name VARCHAR(100),
emergency_contact_phone VARCHAR(15),
is_active BOOLEAN DEFAULT true,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE resident_pins (
id BIGINT PRIMARY KEY AUTO_INCREMENT,

    resident_id BIGINT NOT NULL,
    pin_hash VARCHAR(255) NOT NULL,
    pin_type ENUM('GUEST', 'DELIVERY') NOT NULL,

    expires_at TIMESTAMP,
    max_uses INT DEFAULT 1,
    current_uses INT DEFAULT 0,

    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_pins_resident
        FOREIGN KEY (resident_id) REFERENCES users(id)
);

CREATE TABLE access_logs (
id BIGINT PRIMARY KEY AUTO_INCREMENT,

    resident_id BIGINT NOT NULL,
    pin_id BIGINT NOT NULL,

    access_type ENUM('GUEST', 'DELIVERY') NOT NULL,

    visitor_name VARCHAR(100),
    visitor_phone VARCHAR(15),
    purpose VARCHAR(255),
    vehicle_number VARCHAR(20),

    verified_by BIGINT NOT NULL,   -- ALWAYS the resident
    status ENUM('ACTIVE', 'EXITED', 'DENIED') NOT NULL,

    entry_time TIMESTAMP,
    exit_time TIMESTAMP,

    notes TEXT,

    CONSTRAINT fk_access_resident
        FOREIGN KEY (resident_id) REFERENCES users(id),

    CONSTRAINT fk_access_pin
        FOREIGN KEY (pin_id) REFERENCES resident_pins(id),

    CONSTRAINT fk_access_verified_by
        FOREIGN KEY (verified_by) REFERENCES users(id)
);

CREATE TABLE amenities (
id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,
    description TEXT,

    slot_duration_minutes INT DEFAULT 60,
    max_advance_booking_days INT DEFAULT 7,

    operating_start_time TIME NOT NULL,
    operating_end_time TIME NOT NULL,

    requires_approval BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE amenity_bookings (
id BIGINT PRIMARY KEY AUTO_INCREMENT,

    amenity_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    status ENUM(
        'UPCOMING',
        'ACTIVE',
        'COMPLETED',
        'CANCELLED',
        'NO_SHOW'
    ) NOT NULL,

    number_of_people INT DEFAULT 1,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancelled_at TIMESTAMP NULL,
    cancellation_reason VARCHAR(255),

    CONSTRAINT fk_booking_amenity
        FOREIGN KEY (amenity_id) REFERENCES amenities(id),

    CONSTRAINT fk_booking_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);
-- Users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_active ON users(is_active);

-- Pins
CREATE INDEX idx_pins_resident ON resident_pins(resident_id);
CREATE INDEX idx_pins_active ON resident_pins(is_active);

-- Access logs
CREATE INDEX idx_access_resident ON access_logs(resident_id);
CREATE INDEX idx_access_status ON access_logs(status);
CREATE INDEX idx_access_entry_time ON access_logs(entry_time);

-- Bookings
CREATE INDEX idx_booking_user_date
ON amenity_bookings(user_id, booking_date);

CREATE INDEX idx_booking_amenity_date
ON amenity_bookings(amenity_id, booking_date);


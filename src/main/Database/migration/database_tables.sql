
CREATE TABLE Person(
                      id SERIAL PRIMARY KEY,
                      fullname VARCHAR(100),
                      username VARCHAR(50) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      phone_number VARCHAR(20),
                      address VARCHAR(255),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Client (
                        id SERIAL PRIMARY KEY,
                        user_id INT REFERENCES Person(id) ON DELETE CASCADE

);

CREATE TABLE Employee (
                          id SERIAL PRIMARY KEY,
                          user_id INT REFERENCES Person(id) ON DELETE CASCADE,
                          position VARCHAR(50),
                          hire_date DATE,
                          salary DECIMAL(10, 2)
);

CREATE TABLE Hotel (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       location VARCHAR(100),
                       contact_info VARCHAR(100)
);
CREATE TYPE RoomType AS ENUM ('SINGLE', 'DOUBLE', 'SUITE');
CREATE TABLE Room (
                      id SERIAL PRIMARY KEY,
                      hotel_id INT REFERENCES Hotel(id) ON DELETE CASCADE,
                      room_number VARCHAR(10) NOT NULL,
                      room_type RoomType NOT NULL,
                      availability_status BOOLEAN DEFAULT TRUE
);

CREATE TABLE Season (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        start_date DATE NOT NULL,
                        end_date DATE NOT NULL
);

CREATE TABLE Event (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       event_date DATE NOT NULL
);

CREATE TABLE Pricing (
                             id SERIAL PRIMARY KEY,
                             room_type RoomType NOT NULL,
                             season_id INT REFERENCES Season(id) ON DELETE CASCADE NULL,
                             event_id INT REFERENCES Event(id) ON DELETE CASCADE NULL,
                             base_price DECIMAL(10, 2) NOT NULL,
                             price_multiplier DECIMAL(5, 2) DEFAULT 1.0,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL
);
CREATE TYPE ReservationStatus AS ENUM ('Canceled', 'Confirmed');
CREATE TABLE Reservation (
                             id SERIAL PRIMARY KEY,
                             client_id INT REFERENCES Client(id) ON DELETE CASCADE,
                             hotel_id INT REFERENCES Hotel(id) ON DELETE CASCADE,
                             room_id INT REFERENCES Room(id) ON DELETE CASCADE,
                             check_in_date DATE NOT NULL,
                             check_out_date DATE NOT NULL,
                             reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             total_price DECIMAL(10, 2)
);

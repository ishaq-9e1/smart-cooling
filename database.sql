CREATE DATABASE IF NOT EXISTS smart_cooling;
USE smart_cooling;
CREATE TABLE IF NOT EXISTS cooling_systems (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 building_name VARCHAR(120) NOT NULL,
 system_name VARCHAR(120) NOT NULL,
 cooling_demand DOUBLE NOT NULL,
 outdoor_temperature DOUBLE NOT NULL,
 storage_capacity DOUBLE NOT NULL,
 stored_energy DOUBLE NOT NULL,
 energy_consumption DOUBLE NOT NULL,
 electricity_rate DOUBLE NOT NULL
);
INSERT INTO cooling_systems(building_name,system_name,cooling_demand,outdoor_temperature,storage_capacity,stored_energy,energy_consumption,electricity_rate) VALUES
('Municipal Hospital','Central Chiller',180,34,1000,820,520,8.2),
('Public Library','Library Cooling',120,32,700,448,310,8.2),
('City Hall','Administrative HVAC',260,36,900,540,680,8.5);

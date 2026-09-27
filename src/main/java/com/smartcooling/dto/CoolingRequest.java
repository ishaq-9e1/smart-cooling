package com.smartcooling.dto;

import jakarta.validation.constraints.*;

public class CoolingRequest {
    @NotBlank private String buildingName;
    @NotBlank private String systemName;
    @PositiveOrZero private double coolingDemand;
    private double outdoorTemperature;
    @Positive private double storageCapacity;
    @PositiveOrZero private double storedEnergy;
    @PositiveOrZero private double energyConsumption;
    @PositiveOrZero private double electricityRate;
    public String getBuildingName(){return buildingName;} public void setBuildingName(String v){buildingName=v;}
    public String getSystemName(){return systemName;} public void setSystemName(String v){systemName=v;}
    public double getCoolingDemand(){return coolingDemand;} public void setCoolingDemand(double v){coolingDemand=v;}
    public double getOutdoorTemperature(){return outdoorTemperature;} public void setOutdoorTemperature(double v){outdoorTemperature=v;}
    public double getStorageCapacity(){return storageCapacity;} public void setStorageCapacity(double v){storageCapacity=v;}
    public double getStoredEnergy(){return storedEnergy;} public void setStoredEnergy(double v){storedEnergy=v;}
    public double getEnergyConsumption(){return energyConsumption;} public void setEnergyConsumption(double v){energyConsumption=v;}
    public double getElectricityRate(){return electricityRate;} public void setElectricityRate(double v){electricityRate=v;}
}

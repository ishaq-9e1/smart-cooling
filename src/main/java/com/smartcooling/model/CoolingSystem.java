package com.smartcooling.model;

import jakarta.persistence.*;

@Entity
@Table(name="cooling_systems")
public class CoolingSystem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String buildingName;
    private String systemName;
    private double coolingDemand;
    private double outdoorTemperature;
    private double storageCapacity;
    private double storedEnergy;
    private double energyConsumption;
    private double electricityRate;

    public CoolingSystem() {}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getBuildingName(){return buildingName;}
    public void setBuildingName(String v){buildingName=v;}
    public String getSystemName(){return systemName;}
    public void setSystemName(String v){systemName=v;}
    public double getCoolingDemand(){return coolingDemand;}
    public void setCoolingDemand(double v){coolingDemand=v;}
    public double getOutdoorTemperature(){return outdoorTemperature;}
    public void setOutdoorTemperature(double v){outdoorTemperature=v;}
    public double getStorageCapacity(){return storageCapacity;}
    public void setStorageCapacity(double v){storageCapacity=v;}
    public double getStoredEnergy(){return storedEnergy;}
    public void setStoredEnergy(double v){storedEnergy=v;}
    public double getEnergyConsumption(){return energyConsumption;}
    public void setEnergyConsumption(double v){energyConsumption=v;}
    public double getElectricityRate(){return electricityRate;}
    public void setElectricityRate(double v){electricityRate=v;}
}

package com.smartcooling.service;

import com.smartcooling.dto.CoolingRequest;
import com.smartcooling.model.CoolingSystem;
import com.smartcooling.repository.CoolingRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CoolingService {
    private final CoolingRepository repo;
    public CoolingService(CoolingRepository repo){this.repo=repo;}
    public List<CoolingSystem> all(){return repo.findAll();}
    public CoolingSystem get(Long id){return repo.findById(id).orElseThrow();}
    public CoolingSystem create(CoolingRequest r){return repo.save(apply(new CoolingSystem(),r));}
    public CoolingSystem update(Long id,CoolingRequest r){return repo.save(apply(get(id),r));}
    public void delete(Long id){repo.deleteById(id);}
    public CoolingSystem charge(Long id,double amount){CoolingSystem s=get(id); s.setStoredEnergy(Math.min(s.getStorageCapacity(),s.getStoredEnergy()+Math.max(0,amount))); return repo.save(s);}
    public CoolingSystem discharge(Long id,double amount){CoolingSystem s=get(id); s.setStoredEnergy(Math.max(0,s.getStoredEnergy()-Math.max(0,amount))); return repo.save(s);}
    public Map<String,Object> stats(){List<CoolingSystem> x=all(); double d=x.stream().mapToDouble(CoolingSystem::getCoolingDemand).sum(), e=x.stream().mapToDouble(CoolingSystem::getEnergyConsumption).sum(), c=x.stream().mapToDouble(s->s.getEnergyConsumption()*s.getElectricityRate()).sum(), p=x.stream().mapToDouble(s->s.getStoredEnergy()).sum(), cap=x.stream().mapToDouble(CoolingSystem::getStorageCapacity).sum(); Map<String,Object> m=new LinkedHashMap<>(); m.put("buildings",x.size());m.put("demand",d);m.put("energy",e);m.put("cost",c);m.put("storage",cap==0?0:p/cap*100);return m;}
    public String demandStatus(double d){return d<100?"Low":d<250?"Normal":"High";}
    public double storagePercent(CoolingSystem s){return s.getStorageCapacity()==0?0:s.getStoredEnergy()/s.getStorageCapacity()*100;}
    private CoolingSystem apply(CoolingSystem s,CoolingRequest r){s.setBuildingName(r.getBuildingName());s.setSystemName(r.getSystemName());s.setCoolingDemand(r.getCoolingDemand());s.setOutdoorTemperature(r.getOutdoorTemperature());s.setStorageCapacity(r.getStorageCapacity());s.setStoredEnergy(Math.min(r.getStorageCapacity(),r.getStoredEnergy()));s.setEnergyConsumption(r.getEnergyConsumption());s.setElectricityRate(r.getElectricityRate());return s;}
}

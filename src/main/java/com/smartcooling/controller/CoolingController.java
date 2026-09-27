package com.smartcooling.controller;

import com.smartcooling.dto.CoolingRequest;
import com.smartcooling.model.CoolingSystem;
import com.smartcooling.service.CoolingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cooling")
@CrossOrigin
public class CoolingController {
    private final CoolingService service;
    public CoolingController(CoolingService service){this.service=service;}
    @GetMapping public List<CoolingSystem> all(){return service.all();}
    @GetMapping("/{id}") public CoolingSystem get(@PathVariable Long id){return service.get(id);}
    @PostMapping public CoolingSystem create(@Valid @RequestBody CoolingRequest r){return service.create(r);}
    @PutMapping("/{id}") public CoolingSystem update(@PathVariable Long id,@Valid @RequestBody CoolingRequest r){return service.update(id,r);}
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){service.delete(id);}
    @PostMapping("/{id}/charge") public CoolingSystem charge(@PathVariable Long id,@RequestParam double amount){return service.charge(id,amount);}
    @PostMapping("/{id}/discharge") public CoolingSystem discharge(@PathVariable Long id,@RequestParam double amount){return service.discharge(id,amount);}
    @GetMapping("/dashboard/stats") public Map<String,Object> stats(){return service.stats();}
    @GetMapping("/dashboard/details") public List<Map<String,Object>> details(){return service.all().stream().map(s->{Map<String,Object> m=new LinkedHashMap<>();m.put("id",s.getId());m.put("buildingName",s.getBuildingName());m.put("systemName",s.getSystemName());m.put("demand",s.getCoolingDemand());m.put("demandStatus",service.demandStatus(s.getCoolingDemand()));m.put("storage",service.storagePercent(s));m.put("energy",s.getEnergyConsumption());m.put("cost",s.getEnergyConsumption()*s.getElectricityRate());return m;}).toList();}
}

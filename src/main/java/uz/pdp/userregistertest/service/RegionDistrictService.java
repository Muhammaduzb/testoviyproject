package uz.pdp.userregistertest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.userregistertest.entity.District;
import uz.pdp.userregistertest.entity.Region;
import uz.pdp.userregistertest.repository.DistrictRepo;
import uz.pdp.userregistertest.repository.RegionRepo;

import java.util.List;

/**
 * created by Muhammad
 * on 13.08.2020
 */

@Service
public class RegionDistrictService {

    @Autowired
    RegionRepo regionRepo;

    @Autowired
    DistrictRepo districtRepo;

    public List<Region> getAllRegions(){
        return regionRepo.findAll();
    }

    public List<District> getAllDistricts(Integer id){
        return districtRepo.getByRegionId(id);
    }


}

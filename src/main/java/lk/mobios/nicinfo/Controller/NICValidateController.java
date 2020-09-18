package lk.mobios.nicinfo.Controller;

import lk.mobios.nicinfo.DTO.NICDataDTO;
import lk.mobios.nicinfo.Entity.NICData;
import lk.mobios.nicinfo.Service.NICDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/nic")
public class NICValidateController {

    @Autowired
    private NICDataService service;

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NICDataDTO getNICDetails(@PathVariable("id") String id){
        System.out.println(id);
        return service.validateNIC(id.replace("id=",""));
    }
    @GetMapping(value="/delete/{nic}",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteNIC(@PathVariable("nic") String nic){
        return service.deleteNICDetails(nic.replace("nic=",""));
    }
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateNicDetails(@RequestBody NICData data){
        return service.updateNICDetails(data);
    }
    @GetMapping(value="/view",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NICData> viewAllNicRecords(){
        return service.viewNICDetails();
    }
}

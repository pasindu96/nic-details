package lk.mobios.nicinfo.Controller;

import lk.mobios.nicinfo.DTO.NICDataDTO;
import lk.mobios.nicinfo.Service.NICDataService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "api/nic")
public class NICValidateController {

    NICDataService service=new NICDataService();

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NICDataDTO getNICDetails(@PathVariable("id") String id){
        System.out.println(id);
        return service.validateNIC(id.replace("id=",""));
//        return service.getDataFromNIC(id.replace("id=",""));
    }
}

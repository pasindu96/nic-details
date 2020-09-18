package lk.mobios.nicinfo.Service;

import lk.mobios.nicinfo.DTO.NICDataDTO;
import lk.mobios.nicinfo.Entity.NICData;

import java.util.List;

public interface NICDataService {

    NICDataDTO validateNIC(String nic);
    void saveNICDetails(NICData data);
    boolean updateNICDetails(NICData data);
    boolean deleteNICDetails(String nic);
    List<NICData> viewNICDetails();
}

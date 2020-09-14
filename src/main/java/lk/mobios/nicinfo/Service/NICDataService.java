package lk.mobios.nicinfo.Service;

import lk.mobios.nicinfo.DTO.NICDataDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NICDataService {
    String nicValidateRegex="^(?:19|20)?\\d{2}(?:[0-35-8]\\d\\d(?<!(?:000|500|36[7-9]|3[7-9]\\d|86[7-9]|8[7-9]\\d)))\\d{4}(?i:v|x)$";
    public NICDataDTO getDataFromNIC(String id){
        Pattern pattern= Pattern.compile(nicValidateRegex);
        Matcher matcher=pattern.matcher(id);
        boolean result=matcher.matches();
        System.out.println(result);
        return null;
    }
}

package lk.mobios.nicinfo.Service;

import lk.mobios.nicinfo.DTO.NICDataDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NICDataService {
    //stores whether the given nic is older or latest
    private boolean newNIC=false;
    private boolean leap=false;

    private int[] year = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int[] leapyear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //regex for validate nic
    private String nicValidateRegex="^(?:19|20)?\\d{2}(?:[0-35-8]\\d\\d(?<!(?:000|500|36[7-9]|3[7-9]\\d|86[7-9]|8[7-9]\\d)))\\d{4}(?i:v|x)$";

    public NICDataDTO getDataFromNIC(String id){

        Pattern pattern= Pattern.compile(nicValidateRegex);
        Matcher matcher=pattern.matcher(id);
        boolean result=matcher.matches();
        System.out.println(result);

        if(!result)
            return null;

        newNIC = id.length() == 12;

        NICDataDTO dto=new NICDataDTO();

        if(newNIC){
            leap=leapYear(Integer.parseInt(id.substring(0,4)));
            dto.setBirthDate(id.substring(0,4)+"-"+setBirthday(id));
            dto.setGender(Integer.parseInt(id.substring(4, 7))>500 ? "Female" : "Male");

        }else{
            leap=leapYear(1900+Integer.parseInt(id.substring(0,2)));
            dto.setBirthDate((Integer.parseInt(id.substring(0,2))+1900)+"-"+setBirthday(id));
            dto.setGender(Integer.parseInt(id.substring(2, 5))>500 ? "Female" : "Male");
        }
        return dto;
    }
    public boolean leapYear(int year){
        if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0))
            return true;
        else
            return false;
    }

    public String setBirthday(String id) {
        int d=0;
        int[] month;
        if (newNIC) {
            d = Integer.parseInt(id.substring(4, 7));
        } else {
            d = Integer.parseInt(id.substring(2, 5));
        }
        if (d > 500) {
            d= (d - 500);
        }

        int mo = 0, da = 0;
        int days = d;
        if(leap){
            month=leapyear;
        }else{
            month=year;
        }
        for (int i = 0; i < month.length; i++) {
            if (days < month[i]) {
                mo = i + 1;
                da = days;
                break;
            } else {
                days = days - month[i];
            }
        }
        return mo+"-"+da;
    }
}


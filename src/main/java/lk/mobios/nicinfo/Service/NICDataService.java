package lk.mobios.nicinfo.Service;

import lk.mobios.nicinfo.DTO.NICDataDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NICDataService {
    //Error codes
    /*
    1 - input is empty
    2 - 10 digits or 12 digits
    3 - first two digits should starts with 19 | 20
    4 - 10th digit should be V|X
    5 - <366 | < 866
    10 - success

     */

    //stores whether the given nic is older or latest
    private boolean newNICFormat =false;
    //check the year is leap or not
    private boolean leap=false;

    //define 2 arrays which contains the number of  days in normal and leap year
    private int[] normalYear = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int[] leapYear   = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public NICDataDTO validateNIC(String nic){

        //Check whether the input is empty
        if(nic.trim().isEmpty())
            return new NICDataDTO(1);

        //Check whether the input has 10 or 12 digits
        if(nic.length()==12 || nic.length()== 10){
            //check the nic whether the new format or old format
            newNICFormat = nic.length() == 12;

            if(newNICFormat){
                //new nic part
                if(nic.substring(0,2).equals("19") || nic.substring(0,2).equals("20")){
                    //New NIC is okay
                    leap=leapYear(Integer.parseInt(nic.substring(0,4)));
                    if((Integer.parseInt(nic.substring(4,7))>0 && Integer.parseInt(nic.substring(4,7)) <= (leap?366:365)) || Integer.parseInt(nic.substring(4,7))>500 || Integer.parseInt(nic.substring(4,7)) <= (leap?866:865)){
                        //return the results of new NIC
                        return new NICDataDTO(Integer.parseInt(nic.substring(4, 7))>500 ? "Female" : "Male" ,nic.substring(0,4)+"-"+setBirthday(nic),10);
                    }else{
                        return new NICDataDTO(5);
                    }
                }else{
                    return new NICDataDTO(3);
                }
            }else{
                //old nic part
                //Checks the 10th digit is V | X
                if(nic.substring(9).equalsIgnoreCase("v") || nic.substring(9).equalsIgnoreCase("x")){
                    leap=leapYear(1900+Integer.parseInt(nic.substring(0,2)));
                    if((Integer.parseInt(nic.substring(2,5))>0 && Integer.parseInt(nic.substring(2,5)) <= (leap?366:365)) || (Integer.parseInt(nic.substring(2,5))>500 && Integer.parseInt(nic.substring(2,5)) <= (leap?866:865))){
                        //return the results of old NIC
                        return new NICDataDTO(Integer.parseInt(nic.substring(2, 5))>500 ? "Female" : "Male" ,(Integer.parseInt(nic.substring(0,2))+1900)+"-"+setBirthday(nic),10);

                    }else{
                        return new NICDataDTO(5);
                    }
                }else{
                    return new NICDataDTO(4);
                }
            }
        }else{
            return new NICDataDTO(2);
        }

    }

    private boolean leapYear(int year){
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    private String setBirthday(String id) {
        int total_dates=0;
        int[] month;
        if (newNICFormat) {
            total_dates = Integer.parseInt(id.substring(4, 7));
        } else {
            total_dates = Integer.parseInt(id.substring(2, 5));
        }
        if (total_dates > 500) {
            total_dates= (total_dates - 500);
        }

        int birth_month = 0, birth_date = 0;
        int days = total_dates;
        if(leap){
            month= leapYear;
        }else{
            month= normalYear;
        }
        for (int i = 0; i < month.length; i++) {
            if (days <= month[i]) {
                birth_month = i + 1;
                birth_date = days;
                break;
            } else {
                days = days - month[i];
            }
        }
        return birth_month+"-"+birth_date;
    }

    //-----------------------> Validate with regex | without error codes <-----------------------
    //regex for validate nic
//    private String nicValidateRegex="^(?:19|20)?\\d{2}(?:[0-35-8]\\d\\d(?<!(?:000|500|36[7-9]|3[7-9]\\d|86[7-9]|8[7-9]\\d)))\\d{4}(?i:v|x|[0-9])$";
//
//    public NICDataDTO getDataFromNIC(String id){
//
//        Pattern pattern= Pattern.compile(nicValidateRegex);
//        Matcher matcher=pattern.matcher(id);
//        boolean result=matcher.matches();
//        System.out.println(result);
//
//        if(!result)
//            return new NICDataDTO();
//
//        newNICFormat = id.length() == 12;
//
//        NICDataDTO dto=new NICDataDTO();
//
//        if(newNICFormat){
//            leap=leapYear(Integer.parseInt(id.substring(0,4)));
//            dto.setBirthDate(id.substring(0,4)+"-"+setBirthday(id));
//            dto.setGender(Integer.parseInt(id.substring(4, 7))>500 ? "Female" : "Male");
//
//        }else{
//            leap=leapYear(1900+Integer.parseInt(id.substring(0,2)));
//            dto.setBirthDate((Integer.parseInt(id.substring(0,2))+1900)+"-"+setBirthday(id));
//            dto.setGender(Integer.parseInt(id.substring(2, 5))>500 ? "Female" : "Male");
//        }
//        return dto;
//    }
}


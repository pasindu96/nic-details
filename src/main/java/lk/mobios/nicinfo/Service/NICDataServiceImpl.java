package lk.mobios.nicinfo.Service;

import lk.mobios.nicinfo.DTO.NICDataDTO;
import lk.mobios.nicinfo.Entity.NICData;
import lk.mobios.nicinfo.Repository.NICDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly =  true)
public class NICDataServiceImpl implements NICDataService {
    @Autowired
    public NICDataRepository repository;
    //Error codes
    /*
    1 - input is empty
    2 - 10 digits or 12 digits
    3 - first two digits should starts with 19 | 20
    4 - 10th digit should be V|X
    5 - <366 | < 866
    6 - Only contain numbers
    10 - success
     */

    //Stores whether the given nic is older format or latest format
    private boolean newNICFormat = false;
    //Check the year is leap or not
    private boolean leap = false;

    //Define 2 arrays which contains the number of days and the months || Can use a 2D array
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private int[] normalYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public NICDataDTO validateNIC(String nic) {

        String gender = "";
        String dob = "";

        //Check whether the input is empty
        if (nic.trim().isEmpty())
            //Empty input error
            return new NICDataDTO(1);

        //Check whether the input has 10 or 12 digits
        if (nic.length() == 12 || nic.length() == 10) {
            //Check the nic whether the new format or old format
            newNICFormat = nic.length() == 12;
            if (newNICFormat) {
                try {
                    //Check whether the input characters are numbers
                    Long.parseLong(nic.substring(0, 12));
                } catch (NumberFormatException e) {
                    //Doesn't contain only numbers error
                    return new NICDataDTO(6);
                }
                //New NIC format
                //Check the NIC starts with 19 or 20
                if (nic.substring(0, 2).equals("19") || nic.substring(0, 2).equals("20")) {
                    //Check the middle numbers are in valid range (character 5,6 and 7)
                    if ((Integer.parseInt(nic.substring(4, 7)) > 0 && Integer.parseInt(nic.substring(4, 7)) <= 366) || Integer.parseInt(nic.substring(4, 7)) > 500 || Integer.parseInt(nic.substring(4, 7)) <= 866) {
                        gender = Integer.parseInt(nic.substring(4, 7)) > 500 ? "Female" : "Male";
                        dob = nic.substring(0, 4) + "-" + setBirthday(nic);
                        //Save the search result to the database
                        saveNICDetails(new NICData(nic, gender, dob));
                        //return the results of new NIC
                        return new NICDataDTO(gender, dob, 10);
                    } else {
                        //Invalid number range error (4,5,6 characters)
                        return new NICDataDTO(5);
                    }
                } else {
                    //First two digits not started with 19 | 20 error
                    return new NICDataDTO(3);
                }
            } else {
                //Old NIC format
                try {
                    //Check whether the first nine characters are numbers
                    Long.parseLong(nic.substring(0, 9));
                } catch (NumberFormatException e) {
                    //Doesn't contain only numbers error (0 to 9 digits in the NIC)
                    return new NICDataDTO(6);
                }
                //Checks the 10th digit is V | X
                if (nic.substring(9).equalsIgnoreCase("v") || nic.substring(9).equalsIgnoreCase("x")) {
                    int birthYear = 1900 + Integer.parseInt(nic.substring(0, 2));
                    //Check the middle numbers are in valid range (character 3,4 and 5)
                    if ((Integer.parseInt(nic.substring(2, 5)) > 0 && Integer.parseInt(nic.substring(2, 5)) <= 366) || (Integer.parseInt(nic.substring(2, 5)) > 500 && Integer.parseInt(nic.substring(2, 5)) <= 866)) {
                        gender = Integer.parseInt(nic.substring(2, 5)) > 500 ? "Female" : "Male";
                        dob = (birthYear) + "-" + setBirthday(nic);
                        //Save the search result to the database
                        saveNICDetails(new NICData(nic, gender, dob));

                        //Return the results of old NIC format
                        return new NICDataDTO(gender, dob, 10);
                    } else {
                        //Invalid number range error (4,5,6 characters)
                        return new NICDataDTO(5);
                    }
                } else {
                    //Last digit not equals V | X error
                    return new NICDataDTO(4);
                }
            }
        } else {
            //Does not contains 10 digits
            return new NICDataDTO(2);
        }
    }

    //A method to find the birth month and the date
    private String setBirthday(String id) {
        int total_dates = 0;
        if (newNICFormat) {
            total_dates = Integer.parseInt(id.substring(4, 7));
        } else {
            total_dates = Integer.parseInt(id.substring(2, 5));
        }
        if (total_dates > 500) {
            total_dates = (total_dates - 500);
        }

        int birth_month = 0, birth_date = 0;
        int days = total_dates;

        for (int i = 0; i < normalYear.length; i++) {
            if (days <= normalYear[i]) {
                birth_month = i + 1;
                birth_date = days;
                break;
            } else {
                days = days - normalYear[i];
            }
        }
        //Concat and return the birthday as MM-dd
        return months[birth_month - 1] + "-" + (birth_date < 10 ? "0" + birth_date : birth_date);
    }

    //Method to find the given is a leap year or not
    private boolean leapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    //A method to save the data to the database
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNICDetails(NICData data) {
        //Before save into the database, check the current NIC is already exists or not
        if (!repository.existsById(data.getNic()))
            repository.save(data);
    }

    //A method to update the data in the database
    @Override
    public boolean updateNICDetails(NICData data) {
        if (!repository.existsById(data.getNic()))
            return false;
        NICData toUpdate = repository.findById(data.getNic()).get();
        toUpdate.setBirthDay(data.getBirthDay());
        toUpdate.setGender(data.getGender());
        return repository.save(toUpdate) != null;
    }

    //A method to delete the records in the database
    @Override
    public boolean deleteNICDetails(String nic) {
        if (repository.existsById(nic)) {
            repository.deleteById(nic);
            return true;
        }
        return false;
    }

    //A method to list all the data in the database
    @Override
    public List<NICData> viewNICDetails() {
        return repository.findAll();
    }

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


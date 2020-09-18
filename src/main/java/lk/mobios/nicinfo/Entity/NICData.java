package lk.mobios.nicinfo.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NICData {
    @Id
    private String nic;
    private String gender;
    private String birthDay;


    public NICData() {

    }

    public NICData(String nic, String gender, String birthDay) {
        this.nic = nic;
        this.gender = gender;
        this.birthDay = birthDay;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "NICData{" +
                "nic='" + nic + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDay='" + birthDay + '\'' +
                '}';
    }
}

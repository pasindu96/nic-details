package lk.mobios.nicinfo.DTO;

import java.util.Date;

public class NICDataDTO {
    private String gender;
    private Date birthDate;

    public NICDataDTO(String gender, Date birthDate) {
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public NICDataDTO() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "NICDataDTO{" +
                "gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

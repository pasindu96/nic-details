package lk.mobios.nicinfo.DTO;

import java.util.Date;

public class NICDataDTO {
    private String gender;
    private String birthDate;

    public NICDataDTO(String gender, String birthDate) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

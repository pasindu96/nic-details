package lk.mobios.nicinfo.DTO;


public class NICDataDTO {
    private String gender;
    private String birthDate;
    private int errorCode;

    public NICDataDTO(String gender, String birthDate, int errorCode) {
        this.gender = gender;
        this.birthDate = birthDate;
        this.errorCode = errorCode;
    }

    public NICDataDTO() {
    }

    public NICDataDTO(int errorCode) {
        this.errorCode = errorCode;
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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "NICDataDTO{" +
                "gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}

package domain;

public class User {
    private String studentnumber = null;
    private String username = null;
    private String password = null;
    private String email = null;
    private String acode = null;
    private String islocked = null;

    public User() {
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }

    public String getIslocked() {
        return islocked;
    }

    public void setIslocked(String islocked) {
        this.islocked = islocked;
    }

    @Override
    public String toString() {
        return "user{" +
                "studentnumber='" + studentnumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", acode='" + acode + '\'' +
                ", islocked='" + islocked + '\'' +
                '}';
    }
}

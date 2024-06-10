package beans;

import java.time.LocalDate;
import java.util.Date;

public class Actor {
    private int id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;

    public Actor(){

    }
    public Actor(int id, String firstname, String lastname, LocalDate birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return String.format("Actor[%d, %s, %s, %s]", id, firstname, lastname, birthdate);
    }
}

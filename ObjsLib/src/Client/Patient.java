/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Patient implements Serializable {

    private static final long serialVersionUID = -6291904286218553733L;

    private String name;
    private String lastname;
    private Integer id;
    private String gender;
    private String email;
    private String phone;
    private String dob;
    private String username;
    private String password;
    private String MAC;

    public Patient() {
    }

    public Patient(Integer id, String name, String lastname, String gender, String email, String phone, String dob, String username, String password, String MAC) {
        this.name = name;
        this.lastname = lastname;
        this.id = id;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.MAC = MAC;
    }

    public Patient(String name, String lastname, String gender, String email, String phone, String dob, String username, String password, String MAC) {
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.MAC = MAC;
    }

    public Patient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String address) {
        this.email = address;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getMAC() {
        return MAC;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    @Override
    public String toString() {
        return "Patient{" + "name=" + name + ", lastname=" + lastname + ", id=" + id + ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", dob=" + dob + ", username=" + username + ", password=" + password + ", MAC=" + MAC + '}';
    }
}

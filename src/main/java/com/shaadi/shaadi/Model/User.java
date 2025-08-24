package com.shaadi.shaadi.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private LocalDate dob;
    private String birthplace;
    private String kuldevat;
    private String gotra;
    private String height;
    private String bloodGroup;
    private String education;
    private String profession;
    private String fatherName;
    private String fatherProfession;
    private String motherName;
    private String motherProfession;
    private String siblings;
    private String mama;
    private String kaka;
    private String address;
    private String mobile;

    private String profilePhotoPath;
    private String aadhaarPath;

    
    public User() {
    }

    public User(Long id, String name, String gender, LocalDate dob, String birthplace, String kuldevat, String gotra,
            String height, String bloodGroup, String education, String profession, String fatherName,
            String fatherProfession, String motherName, String motherProfession, String siblings, String mama,
            String kaka, String address, String mobile, String profilePhotoPath, String aadhaarPath) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.birthplace = birthplace;
        this.kuldevat = kuldevat;
        this.gotra = gotra;
        this.height = height;
        this.bloodGroup = bloodGroup;
        this.education = education;
        this.profession = profession;
        this.fatherName = fatherName;
        this.fatherProfession = fatherProfession;
        this.motherName = motherName;
        this.motherProfession = motherProfession;
        this.siblings = siblings;
        this.mama = mama;
        this.kaka = kaka;
        this.address = address;
        this.mobile = mobile;
        this.profilePhotoPath = profilePhotoPath;
        this.aadhaarPath = aadhaarPath;
    }
    
    //* Setter and Getter 
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    public String getBirthplace() {
        return birthplace;
    }
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }
    public String getKuldevat() {
        return kuldevat;
    }
    public void setKuldevat(String kuldevat) {
        this.kuldevat = kuldevat;
    }
    public String getGotra() {
        return gotra;
    }
    public void setGotra(String gotra) {
        this.gotra = gotra;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getFatherName() {
        return fatherName;
    }
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    public String getFatherProfession() {
        return fatherProfession;
    }
    public void setFatherProfession(String fatherProfession) {
        this.fatherProfession = fatherProfession;
    }
    public String getMotherName() {
        return motherName;
    }
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
    public String getMotherProfession() {
        return motherProfession;
    }
    public void setMotherProfession(String motherProfession) {
        this.motherProfession = motherProfession;
    }
    public String getSiblings() {
        return siblings;
    }
    public void setSiblings(String siblings) {
        this.siblings = siblings;
    }
    public String getMama() {
        return mama;
    }
    public void setMama(String mama) {
        this.mama = mama;
    }
    public String getKaka() {
        return kaka;
    }
    public void setKaka(String kaka) {
        this.kaka = kaka;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
    public String getAadhaarPath() {
        return aadhaarPath;
    }

    public void setAadhaarPath(String aadhaarPath) {
        this.aadhaarPath = aadhaarPath;
    }

    
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", birthplace="
                + birthplace + ", kuldevat=" + kuldevat + ", gotra=" + gotra + ", height=" + height + ", bloodGroup="
                + bloodGroup + ", education=" + education + ", profession=" + profession + ", fatherName=" + fatherName
                + ", fatherProfession=" + fatherProfession + ", motherName=" + motherName + ", motherProfession="
                + motherProfession + ", siblings=" + siblings + ", mama=" + mama + ", kaka=" + kaka + ", address="
                + address + ", mobile=" + mobile + ", profilePhotoPath=" + profilePhotoPath + ", aadhaarPath="
                + aadhaarPath + "]";
    }


    
}

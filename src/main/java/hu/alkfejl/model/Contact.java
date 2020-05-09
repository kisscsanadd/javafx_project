package hu.alkfejl.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.*;
import java.sql.Timestamp;

public class Contact{

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty modifiedOn= new SimpleStringProperty();
    private StringProperty birth= new SimpleStringProperty();
    private StringProperty email= new SimpleStringProperty();
    private StringProperty workEmail= new SimpleStringProperty();
    private StringProperty phone= new SimpleStringProperty();
    private StringProperty workPhone= new SimpleStringProperty();
    private StringProperty address= new SimpleStringProperty();
    private StringProperty workAddress= new SimpleStringProperty();
    private StringProperty position= new SimpleStringProperty();
    private StringProperty organization= new SimpleStringProperty();
    private String profilePictureString;

    private Image profilePicture;

    public Contact() {
    }

    public Contact(IntegerProperty id, StringProperty name, StringProperty modifiedOn, StringProperty birth, StringProperty email, StringProperty workEmail, StringProperty phone, StringProperty workPhone, StringProperty address, StringProperty workAddress, StringProperty position, StringProperty organization, String profilePictureString) {
        this.id = id;
        this.name = name;
        this.modifiedOn = modifiedOn;
        this.birth = birth;
        this.email = email;
        this.workEmail = workEmail;
        this.phone = phone;
        this.workPhone = workPhone;
        this.address = address;
        this.workAddress = workAddress;
        this.position = position;
        this.organization = organization;
        this.profilePictureString = profilePictureString;
    }


    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getModifiedOn() {
        return modifiedOn.get();
    }

    public StringProperty modifiedOnProperty() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn.set(modifiedOn);
    }

    public String getBirth() {
        return birth.get();
    }

    public StringProperty birthProperty() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth.set(birth);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getWorkEmail() {
        return workEmail.get();
    }

    public StringProperty workEmailProperty() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail.set(workEmail);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getWorkPhone() {
        return workPhone.get();
    }

    public StringProperty workPhoneProperty() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone.set(workPhone);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getWorkAddress() {
        return workAddress.get();
    }

    public StringProperty workAddressProperty() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress.set(workAddress);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getOrganization() {
        return organization.get();
    }

    public StringProperty organizationProperty() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization.set(organization);
    }

    public String getProfilePictureString() {
        return profilePictureString;
    }

    public void setProfilePictureString(String profilePictureString) {
        this.profilePictureString = profilePictureString;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name=" + name +
                ", modifiedOn=" + modifiedOn +
                ", birth=" + birth +
                ", email=" + email +
                ", workEmail=" + workEmail +
                ", phone=" + phone +
                ", workPhone=" + workPhone +
                ", address=" + address +
                ", workAddress=" + workAddress +
                ", position=" + position +
                ", organization=" + organization +
                ", profilePictureString=" + profilePictureString +
                ", profilePicture=" + profilePicture +
                '}';
    }

    public void copyTo(Contact target){
        target.setId(this.getId());
        target.setName(this.getName());
        target.setBirth(this.getBirth());
        target.setEmail(this.getEmail());
        target.setWorkEmail(this.getWorkEmail());
        target.setAddress(this.getAddress());
        target.setWorkAddress(this.getWorkAddress());
        target.setPhone(this.getPhone());
        target.setWorkPhone(this.getWorkPhone());
        target.setOrganization(this.getOrganization());
        target.setPosition(this.getPosition());
        target.setModifiedOn(this.getModifiedOn());
        target.setProfilePictureString(this.getProfilePictureString());
        target.setProfilePicture(this.getProfilePicture());
    }


}

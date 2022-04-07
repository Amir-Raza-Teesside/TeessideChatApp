package uk.ac.tees.aad.a0147662;

public class User {

    private String uid;
    private String name;
    private String phoneNumber;
    private String profilePic;

    public  User ()
    {

    }
    public User(String uid, String name, String phoneNumber, String profilePic) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

package uk.ac.tees.aad.a0147662;

public class Status {
    private String imageURL;
    private Long TimeStamp;


    public  Status ()
    {

    }


    public Status(String imageURL, Long timeStamp) {
        this.imageURL = imageURL;
        TimeStamp = timeStamp;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        TimeStamp = timeStamp;
    }



}

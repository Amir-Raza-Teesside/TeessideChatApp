package uk.ac.tees.aad.a0147662;

public class TweeterModel {

    private  String Tweet, Name;

    public  TweeterModel()
    {

    }
    public TweeterModel(String tweet, String name) {
        Tweet = tweet;
        Name = name;
    }



    public String getTweet() {
        return Tweet;
    }

    public void setTweet(String tweet) {
        Tweet = tweet;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }




}

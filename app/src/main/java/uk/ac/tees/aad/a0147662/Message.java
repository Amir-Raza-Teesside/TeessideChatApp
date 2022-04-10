package uk.ac.tees.aad.a0147662;

public class Message {
    private  String messageId, message, senderid;
    private  long timestamp;


    public  Message()
    {

    }

    public Message(String message, String senderid, long timestamp) {
        this.message = message;
        this.senderid = senderid;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

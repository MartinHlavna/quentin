package sk.mizik.quentin.commands;

/**
 * @author Marian Mizik
 * @since 1.0.0
 */
public class Result {

    private int status;
    private String sender;
    private String value;

    public Result(int status, String sender, String value) {
        this.status = status;
        this.sender = sender;
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

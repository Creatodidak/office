package id.creatodidak.e_disposisi;

/**
 * Created by BRIPDA ANGGI PERIANTO on 08,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class EventModel {
    private String eventTag;
    private String message;

    public EventModel(String eventTag, String message) {
        this.eventTag = eventTag;
        this.message = message;
    }

    public boolean isTagMatchWith(String tag){
        return eventTag.equals(tag);
    }

    public String getMessage() {
        return message;
    }
}
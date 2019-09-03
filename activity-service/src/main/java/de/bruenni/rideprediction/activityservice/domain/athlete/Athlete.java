package de.bruenni.rideprediction.activityservice.domain.athlete;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.Identifiable;

import javax.json.bind.annotation.JsonbProperty;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Athlete of activity service domain.
 *
 * @author Oliver Brüntje
 */
public class Athlete extends Identifiable<String> {

    @JsonbProperty(value = "nick_name")
    private String nickName;

    /**
     * Jsonb ctor
     */
    protected Athlete() {
        super("");
    }

    /*    @JsonbProperty(value = "latest_activity_start_date")
    private LocalDateTime latestActivityStartDate*/

    public Athlete(String userId, String nickName) {
        super(userId);
        this.nickName = notEmpty(nickName, "nickname may not be empty");
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

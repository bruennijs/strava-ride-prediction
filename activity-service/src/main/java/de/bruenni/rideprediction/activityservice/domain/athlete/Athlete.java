package de.bruenni.rideprediction.activityservice.domain.athlete;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.Identifiable;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Athlete of activity service domain.
 *
 * @author Oliver Brüntje
 */
public class Athlete extends Identifiable<String> {

    private static final String FIELD_NICK_NAME = "nick_name";

    @JsonbProperty(value = FIELD_NICK_NAME)
    private String nickName;

    /*    @JsonbProperty(value = "latest_activity_start_date")
    private LocalDateTime latestActivityStartDate*/
    @JsonbCreator
    public Athlete(@JsonbProperty(value = FIELD_ID) String userId,
            @JsonbProperty(value = FIELD_NICK_NAME) String nickName) {
        super(userId);
        this.nickName = notEmpty(nickName, "nickname may not be empty");
    }

    public String getNickName() {
        return nickName;
    }
}

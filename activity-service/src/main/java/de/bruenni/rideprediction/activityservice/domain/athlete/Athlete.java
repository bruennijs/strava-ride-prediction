package de.bruenni.rideprediction.activityservice.domain.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bruenni.rideprediction.activityservice.infrastructure.domain.Identifiable;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Athlete of activity service domain.
 *
 * @author Oliver Brüntje
 */
public class Athlete extends Identifiable<String> {

    public static final String FIELD_NICK_NAME = "nick_name";

    public static final String FIELD_ACTIVITIES = "activities";

    @JsonbProperty(value = FIELD_NICK_NAME)
    private String nickName;

    @JsonbProperty(value = FIELD_ACTIVITIES)
    private List<String> activities;

    /*    @JsonbProperty(value = "latest_activity_start_date")
    private LocalDateTime latestActivityStartDate*/
    @JsonbCreator
    public Athlete(@JsonbProperty(value = FIELD_ID) String userId,
            @JsonbProperty(value = FIELD_NICK_NAME) String nickName,
            @JsonbProperty(value = FIELD_ACTIVITIES) List<String> activities) {
        super(userId);
        this.nickName = notEmpty(nickName, "nickname may not be empty");
        this.activities = activities;
    }

    public String getNickName() {
        return nickName;
    }

    public List<String> getActivities() {
        return activities;
    }
}

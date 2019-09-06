package de.bruenni.rideprediction.activityservice.domain.athlete;

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
public class Athlete extends Identifiable<String> implements Comparable<Athlete> {

    public static final String FIELD_NICK_NAME = "nick_name";

    public static final String FIELD_AUTH_IDS = "auth_ids";

    @JsonbProperty(value = FIELD_NICK_NAME)
    private String nickName;

    @JsonbProperty(value = FIELD_AUTH_IDS)
    private List<String> authIds;

    /*    @JsonbProperty(value = "latest_activity_start_date")
    private LocalDateTime latestActivityStartDate*/
    @JsonbCreator
    public Athlete(@JsonbProperty(value = FIELD_ID) String userId,
            @JsonbProperty(value = FIELD_NICK_NAME) String nickName,
            @JsonbProperty(value = FIELD_AUTH_IDS) List<String> authIds) {
        super(userId);
        this.nickName = notEmpty(nickName, "nickname may not be empty");
        this.authIds = notEmpty(authIds, "authIds may not be null or empty");
    }

    public String getNickName() {
        return nickName;
    }

    public List<String> getAuthIds() {
        return authIds;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Athlete))
            return false;
        if (!super.equals(o))
            return false;

        Athlete athlete = (Athlete)o;

        return nickName.equals(athlete.nickName);
    }

    @Override public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nickName.hashCode();
        return result;
    }

    @Override public int compareTo(Athlete o) {
        return this.equals(o) ? 0 : -1;
    }

    @Override public String toString() {
        return "Athlete{" +
                "nickName='" + nickName + '\'' +
                ", authIds=" + authIds +
                "} " + super.toString();
    }
}

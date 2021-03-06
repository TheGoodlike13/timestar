package com.superum.api.v2.lesson;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.MoreObjects;
import com.superum.api.core.DTOWithTimestamps;
import eu.goodlike.libraries.joda.time.Time;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.jooq.Record;

import java.util.Objects;

import static timestar_v2.Tables.LESSON;

/**
 * <pre>
 * Data Transport Object for lessons
 *
 * This object is used to de-serialize and serialize JSON that is coming in and out of the back end;
 * it should contain minimal logic, if any at all; a good example would be conversion between a choice of optional
 * JSON fields;
 *
 * When creating an instance of ValidLessonDTO with JSON, these fields are required:
 *      FIELD_NAME  : FIELD_DESCRIPTION                                         FIELD_CONSTRAINTS
 *      groupId     : id of the group which is having this lesson               1 <= groupId
 *      length      : duration of the lesson in minutes                         1 <= length
 * You can choose from one set of these fields:
 *      startTime   : timestamp, representing the time this lesson started      0 <= startTime
 * or:
 *      startDate   : date when the lesson started                              date String, "yyyy-MM-dd"
 *      startHour   : hour when the lesson started                              0 <= startHour <= 23
 *      startMinute : minute when the lesson started                            0 <= startMinute <= 59
 *      timeZone    : time zone for the above 3 values                          time zone String;
 *                    check /misc/time/zones (MiscController) for valid values
 * These fields are optional:
 *      comment     : comment, made by the app client                           any String, max 500 chars
 * These fields should only be specified if they are known:
 *      id          : number representation of this lesson in the system        1 <= id
 *
 * When building JSON, use format
 *      for single objects:  "FIELD_NAME":"VALUE"
 *      for lists:           "FIELD_NAME":["VALUE1", "VALUE2", ...]
 * If you omit a field, it will use null;
 *
 * Example of JSON to send:
 * {
 *      "id": 1,
 *      "groupId": 1,
 *      "startDate": "2015-08-21",
 *      "startHour": 15,
 *      "startMinute": 46,
 *      "timeZone" : "EET",
 *      "length" : 45,
 *      "comment": "What a lesson"
 * }
 * or
 * {
 *      "id": 1,
 *      "groupId": 1,
 *      "startTime": 1440161160000,
 *      "length" : 45,
 *      "comment": "What a lesson"
 * }
 *
 * When returning an instance of ValidLessonDTO with JSON, these additional fields will be present:
 *      FIELD_NAME  : FIELD_DESCRIPTION
 *      teacherId   : id of the teacher which is responsible for the group that is having this lesson
 *      endTime     : timestamp, representing the time this lesson ended
 *      createdAt   : timestamp, taken by the database at the time of creation
 *      updatedAt   : timestamp, taken by the database at the time of creation and updating
 *
 * Example of JSON to expect:
 * {
 *      "id": 1,
 *      "groupId": 1,
 *      "teacherId": 1,
 *      "startTime": 1440161160000,
 *      "endTime": 1440163860000,
 *      "length" : 45,
 *      "comment": "What a lesson",
 *      "createdAt":1440104400000,
 *      "updatedAt":1440161945223
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public final class ValidLessonDTO extends DTOWithTimestamps {

    @JsonProperty(ID_FIELD)
    public Long getId() {
        return id;
    }
    @JsonIgnore
    public ValidLessonDTO withId(Long id) {
        return new ValidLessonDTO(id, groupId, teacherId, startTime, endTime, length, comment, getCreatedAt(), getUpdatedAt());
    }
    @JsonIgnore
    public ValidLessonDTO withoutId() {
        return new ValidLessonDTO(null, groupId, teacherId, startTime, endTime, length, comment, getCreatedAt(), getUpdatedAt());
    }

    @JsonProperty(GROUP_ID_FIELD)
    public Integer getGroupId() {
        return groupId;
    }

    @JsonProperty(TEACHER_ID_FIELD)
    public Integer getTeacherId() {
        return teacherId;
    }

    @JsonProperty(START_TIME_FIELD)
    public Long getStartTime() {
        return startTime;
    }

    @JsonProperty(END_TIME_FIELD)
    public Long getEndTime() {
        return endTime;
    }

    @JsonProperty(LENGTH_FIELD)
    public Integer getLength() {
        return length;
    }

    @JsonProperty(COMMENT_FIELD)
    public String getComment() {
        return comment;
    }

    // CONSTRUCTORS

    @JsonCreator
    public static ValidLessonDTO jsonInstance(@JsonProperty(ID_FIELD) Long id,
                                              @JsonProperty(GROUP_ID_FIELD) Integer groupId,
                                              @JsonProperty(START_TIME_FIELD) Long startTime,
                                              @JsonProperty("timeZone") String timeZone,
                                              @JsonProperty("startDate") String startDate,
                                              @JsonProperty("startHour") Integer startHour,
                                              @JsonProperty("startMinute") Integer startMinute,
                                              @JsonProperty(LENGTH_FIELD) Integer length,
                                              @JsonProperty(COMMENT_FIELD) String comment) {
        if (startTime == null) {
            return fromTimeZone(id, groupId, null, timeZone, startDate == null ? null : LocalDate.parse(startDate),
                    startHour, startMinute, length, comment, null, null);
        }
        return new ValidLessonDTO(id, groupId, null, startTime, null, length, comment, null, null);
    }

    public static ValidLessonDTO fromTimeZone(Long id, Integer groupId, Integer teacherId, String timeZone,
                                      LocalDate startDate, Integer startHour, Integer startMinute, Integer length,
                                      String comment, Instant createdAt, Instant updatedAt) {
        if (timeZone == null || startDate  == null || startHour == null || startMinute == null)
            return new ValidLessonDTO(id, groupId, null, null, null, length, comment, null, null);

        Long startTime = Time.forZoneId(timeZone).from(startDate, startHour, startMinute).toEpochMillis();
        return new ValidLessonDTO(id, groupId, teacherId, startTime, null, length, comment, createdAt, updatedAt);
    }

    public ValidLessonDTO(Long id, Integer groupId, Integer teacherId, Long startTime, Long endTime, Integer length, String comment,
                  Instant createdAt, Instant updatedAt) {
        super(createdAt, updatedAt);

        this.id = id;
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
        this.comment = comment;
    }

    public static ValidLessonDTO valueOf(Record lessonRecord) {
        if (lessonRecord == null)
            return null;

        return new ValidLessonDTO(
                lessonRecord.getValue(LESSON.ID),
                lessonRecord.getValue(LESSON.GROUP_ID),
                lessonRecord.getValue(LESSON.TEACHER_ID),
                lessonRecord.getValue(LESSON.TIME_OF_START),
                lessonRecord.getValue(LESSON.TIME_OF_END),
                lessonRecord.getValue(LESSON.DURATION_IN_MINUTES),
                lessonRecord.getValue(LESSON.COMMENT),
                Time.getDefault().from(lessonRecord.getValue(LESSON.CREATED_AT)).toJodaInstant(),
                Time.getDefault().from(lessonRecord.getValue(LESSON.UPDATED_AT)).toJodaInstant()
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static GroupIdStep stepBuilder() {
        return new Builder();
    }

    // PRIVATE

    private final Long id;
    private final Integer groupId;
    private final Integer teacherId;
    private final Long startTime;
    private final Long endTime;
    private final Integer length;
    private final String comment;

    // FIELD NAMES

    private static final String ID_FIELD = "id";
    private static final String GROUP_ID_FIELD = "groupId";
    private static final String TEACHER_ID_FIELD = "teacherId";
    private static final String START_TIME_FIELD = "startTime";
    private static final String END_TIME_FIELD = "endTime";
    private static final String LENGTH_FIELD = "length";
    private static final String COMMENT_FIELD = "comment";

    // OBJECT OVERRIDES

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Lesson")
                .add(ID_FIELD, id)
                .add(GROUP_ID_FIELD, groupId)
                .add(TEACHER_ID_FIELD, teacherId)
                .add(START_TIME_FIELD, startTime)
                .add(END_TIME_FIELD, endTime)
                .add(LENGTH_FIELD, length)
                .add(COMMENT_FIELD, comment)
                .add(CREATED_AT_FIELD, getCreatedAt())
                .add(UPDATED_AT_FIELD, getUpdatedAt())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidLessonDTO)) return false;
        ValidLessonDTO that = (ValidLessonDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(length, that.length) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, startTime, length, comment);
    }

    // GENERATED

    public interface GroupIdStep {
        TimeStep groupIdWithTeacher(Integer groupId, Integer teacherId);
    }

    public interface TimeStep {
        StartDateStep timeZone(DateTimeZone timeZone);
        StartDateStep timeZone(String timeZone);
        LengthStep startTime(Long startTime);
    }

    public interface StartDateStep {
        StartHourStep startDate(LocalDate startDate);
        StartHourStep startDate(String startDate);
    }

    public interface StartHourStep {
        StartMinuteStep startHour(int startHour);
    }

    public interface StartMinuteStep {
        LengthStep startMinute(int startMinute);
    }

    public interface LengthStep {
        BuildStep length(Integer length);
    }

    public interface BuildStep {
        BuildStep id(Long id);
        BuildStep comment(String comment);
        BuildStep createdAt(Instant createdAt);
        BuildStep createdAt(long createdAt);
        BuildStep updatedAt(Instant updatedAt);
        BuildStep updatedAt(long updatedAt);
        ValidLessonDTO build();
    }

    public static class Builder implements GroupIdStep, TimeStep, StartDateStep, StartHourStep, StartMinuteStep, LengthStep, BuildStep {
        private Long id;
        private Integer groupId;
        private Integer teacherId;
        private Long startTime;
        private String timeZone;
        private LocalDate startDate;
        private Integer startHour;
        private Integer startMinute;
        private Integer length;
        private String comment;
        private Instant createdAt;
        private Instant updatedAt;

        private Builder() {}

        @Override
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder groupIdWithTeacher(Integer groupId, Integer teacherId) {
            this.groupId = groupId;
            this.teacherId = teacherId;
            return this;
        }

        @Override
        public Builder startTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        @Override
        public Builder timeZone(DateTimeZone timeZone) {
            this.timeZone = timeZone.toString();
            return this;
        }

        @Override
        public Builder timeZone(String timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        @Override
        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        @Override
        public Builder startDate(String startDate) {
            this.startDate = LocalDate.parse(startDate);
            return this;
        }

        @Override
        public Builder startHour(int startHour) {
            this.startHour = startHour;
            return this;
        }

        @Override
        public Builder startMinute(int startMinute) {
            this.startMinute = startMinute;
            return this;
        }

        @Override
        public Builder length(Integer length) {
            this.length = length;
            return this;
        }

        @Override
        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        @Override
        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @Override
        public Builder createdAt(long createdAt) {
            this.createdAt = new Instant(createdAt);
            return this;
        }

        @Override
        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @Override
        public Builder updatedAt(long updatedAt) {
            this.updatedAt = new Instant(updatedAt);
            return this;
        }

        @Override
        public ValidLessonDTO build() {
            return startTime == null
                    ? fromTimeZone(id, groupId, teacherId, timeZone, startDate, startHour, startMinute, length,
                    comment, createdAt, updatedAt)
                    : new ValidLessonDTO(id, groupId, teacherId, startTime, null, length, comment, createdAt, updatedAt);
        }

    }

}

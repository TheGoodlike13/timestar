package com.superum.api.v2.attendance;

import com.google.common.base.MoreObjects;
import com.superum.helper.field.ManyDefined;
import eu.goodlike.v2.validate.Validate;
import org.jooq.lambda.Seq;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;

import static com.superum.api.core.CommonValidators.MANDATORY_JSON_ID;
import static com.superum.api.core.CommonValidators.MANDATORY_JSON_LONG_ID;

/**
 * <pre>
 * Domain object for lesson attendance
 *
 * This object should be used to validate DTO data and use it in a meaningful manner;
 * </pre>
 */
public class ValidLessonAttendance implements ManyDefined<Long, Integer> {

    @Override
    public Long primaryValue() {
        return lessonId;
    }

    @Override
    public Seq<Integer> secondaryValues() {
        return Seq.seq(studentIds);
    }

    public boolean areStudentsFromGroupThisLessonWasFor(BiPredicate<Long, Set<Integer>> checker) {
        return checker.test(lessonId, studentIds);
    }

    // CONSTRUCTORS

    public ValidLessonAttendance(ValidLessonAttendanceDTO validLessonAttendanceDTO) {
        this.lessonId = validLessonAttendanceDTO.getLessonId();
        this.studentIds = validLessonAttendanceDTO.getStudentIds();

        MANDATORY_JSON_LONG_ID.ifInvalid(lessonId)
                .thenThrow(() -> new InvalidLessonAttendanceException("Lesson id for attendance must be set and positive, not " + lessonId));

        Validate.collectionOf(Integer.class).not().isNull().not().isEmpty()
                .forEachIfNot(MANDATORY_JSON_ID)
                .Throw(id -> new InvalidLessonAttendanceException("Student ids for attendance must be set and positive, not " + id))
                .ifInvalid(studentIds).thenThrow(() -> new InvalidLessonAttendanceException("Attendance must have at least a single student!"));
    }

    // PRIVATE

    private final Long lessonId;
    private final Set<Integer> studentIds;

    // OBJECT OVERRIDES

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("ValidLessonAttendance")
                .add("lessonId", lessonId)
                .add("studentIds", studentIds)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidLessonAttendance)) return false;
        ValidLessonAttendance that = (ValidLessonAttendance) o;
        return Objects.equals(lessonId, that.lessonId) &&
                Objects.equals(studentIds, that.studentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, studentIds);
    }

}

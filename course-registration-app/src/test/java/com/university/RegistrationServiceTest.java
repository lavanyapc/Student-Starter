package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits.
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
        assertEquals(20, student.getTotalCredits());
        assertEquals(0, course.getCurrentEnrollment());
    }

    // Capacity boundary tests

    @Test
    void shouldRejectRegistrationWhenCourseIsAtCapacity() {

        Student student = new Student("S201", "Rahul");

        Course course = new Course(
                "CS501",
                "Operating Systems",
                4,
                2,
                true,
                null
        );

        course.incrementEnrollment();
        course.incrementEnrollment();

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS501"));
        assertEquals(0, student.getTotalCredits());
        assertEquals(2, course.getCurrentEnrollment());
    }

    @Test
    void shouldAcceptRegistrationWhenExactlyOneSeatRemains() {

        Student student = new Student("S202", "Priya");

        Course course = new Course(
                "CS502",
                "Computer Networks",
                4,
                2,
                true,
                null
        );

        course.incrementEnrollment();

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS502"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(2, course.getCurrentEnrollment());
    }

    // Credit validation boundary tests

    @Test
    void shouldAcceptCourseWithOneCredit() {
        assertCreditRegistrationResult(1, true);
    }

    @Test
    void shouldAcceptCourseWithSixCredits() {
        assertCreditRegistrationResult(6, true);
    }

    @Test
    void shouldRejectCourseWithZeroCreditsWithoutChangingState() {
        assertCreditRegistrationResult(0, false);
    }

    @Test
    void shouldRejectCourseWithSevenCreditsWithoutChangingState() {
        assertCreditRegistrationResult(7, false);
    }

    @Test
    void shouldRejectCourseWithNegativeCreditsWithoutChangingState() {
        assertCreditRegistrationResult(-1, false);
    }

    private void assertCreditRegistrationResult(
            int credits, boolean expectedResult) {

        Student student = new Student("S203", "Meera");

        Course course = new Course(
                "CS503",
                "Test Course",
                credits,
                10,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertEquals(expectedResult, result);
        assertEquals(expectedResult, student.isRegistered("CS503"));
        assertEquals(
                expectedResult ? credits : 0,
                student.getTotalCredits()
        );
        assertEquals(
                expectedResult ? 1 : 0,
                course.getCurrentEnrollment()
        );
    }
}
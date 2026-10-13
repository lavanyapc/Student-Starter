package com.university;

public class RegistrationService {

    private static final int MAX_CREDITS = 20;
    private static final int MIN_COURSE_CREDITS = 1;
    private static final int MAX_COURSE_CREDITS = 6;

    public boolean register(Student student, Course course) {

        if (course.getCredits() < MIN_COURSE_CREDITS
                || course.getCredits() > MAX_COURSE_CREDITS) {//added
            return false;
        }

        if (!course.isActive()) {
            return false;
        }

        if (course.getCurrentEnrollment() >= course.getCapacity()) {//changed
            return false;
        }

  
        if (student.isRegistered(course.getCourseCode())) {
            return false;
        }

        String prerequisite = course.getPrerequisite();

        if (prerequisite != null
                && !prerequisite.isBlank()
                && !student.hasCompletedPrerequisite(prerequisite)) {
            return false;
        }

        if (student.getTotalCredits() + course.getCredits() > MAX_CREDITS) {
            return false;
        }

        student.addRegistration(
                course.getCourseCode(),
                course.getCredits()
        );

        course.incrementEnrollment();

        return true;
    }
}
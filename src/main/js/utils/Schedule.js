import TimeSlot from './Time';
import { getInstructorLastName } from './grade';

class Schedule extends Set {
  /**
   * Either a copy constructor or normal constructor
   * The data parameter will be of type Schedule to copy or
   * type Integer to specify the gap time
   */
  constructor(data = 15) {
    if (data instanceof Schedule) {
      super(data);
      this.gap = data.gap;
      this.credits = data.credits;
    } else {
      super();
      this.gap = data;
      this.credits = 0;
    }
  }

  /**
   * Obtains the estimated GPA of a schedule
   * @param {Object} gradeMap is the mapping of grades to course-instructor
   * @param {boolean} useCourseAvg use course average gpa inplace of non-existant instructor GPA
   * @returns Double.toFixed(2)
   */
  calculateGPA(gradeMap, useCourseAvg) {
    let qualityCredits = 0.00;
    let totalCredits = 0;

    this.forEach((course) => {
      const name = `${course.subject}${course.courseNumber}`;
      const instructor = getInstructorLastName(course.instructor);

      if (gradeMap[name]) {
        if (gradeMap[name][instructor]) {
          qualityCredits += gradeMap[name][instructor] * course.credits;
          totalCredits += course.credits;
        } else if (useCourseAvg) {
          qualityCredits += gradeMap[name].AVERAGE * course.credits;
          totalCredits += course.credits;
        }
      }
    });

    const scheduleGPA = (totalCredits === 0) ? 0.00 : qualityCredits / totalCredits;
    return scheduleGPA.toFixed(2);
  }

  /**
   * Checks if the given course conflicts with the current schedule taking gap time into account.
   * A course conflicts if a single of its meetings is the same day and its time overlaps
   * with another already scheduled meeting
   * @param {Object} course the course to add
   * @returns true if the course conflicts, false if not
   */
  conflictsWith(course) {
    course.meetings.forEach((meeting) => {
      if (!meeting.timeSlot) {
        meeting.timeSlot = new TimeSlot(meeting.startTime, meeting.endTime);
      }
    });
    return [...this].some(scheduled => (
      scheduled.meetings.some(scheduledMeeting => (
        course.meetings.some(meeting => (
          scheduledMeeting.timeSlot.isValid && scheduledMeeting.days.find(scheduledDay => (
            meeting.timeSlot.isValid && meeting.days.find(day => day === scheduledDay)
          )) && scheduledMeeting.timeSlot.conflictsWith(meeting.timeSlot, this.gap)
        ))
      ))
    ));
  }

  /* eslint no-param-reassign: ["error", { "props": false }] */
  /**
   * If the course doesn't conflict, adds it to the schedule, increments the credits,
   * and creates a TimeSlot object for each of its meetings
   * @param {Object} course the course to add
   * @returns true if the course was added, false if not
   */
  add(course) {
    if (!course) {
      return false;
    }
    const prevSize = this.size;
    if (!this.conflictsWith(course) && super.add(course).size > prevSize) {
      this.credits += course.credits;
      return true;
    }
    return false;
  }

  delete(course) {
    if (super.delete(course)) {
      this.credits -= course.credits;
      return true;
    }
    return false;
  }

  clear() {
    super.clear();
    this.credits = 0;
  }
}

export default Schedule;

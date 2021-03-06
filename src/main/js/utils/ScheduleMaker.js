/* eslint-disable no-underscore-dangle */
import Schedule from './Schedule';

class ScheduleMaker {
  /**
   * Constructor
   * @param {Object} courseListings list of courses to make schedules from
   * @param {int} gap minimum gap time for a schedule
   * @param {Object} gradeMap optional, used to sort outputted schedules by gpa
   * @param {boolean} useCourseAvg optional, used to sort outputted schedules by gpa
   */
  constructor(courseListings, gap, gradeMap, useCourseAvg) {
    this.courseListings = courseListings;
    this.schedule = new Schedule(gap);
    this.stack = [];
    this.complete = false;
    this._stackAdd(0);
    this.gradeMap = gradeMap;
    this.useCourseAvg = useCourseAvg;
    this.schedules = [];
  }

  /*
    Adding to stack in reverse order to preserve GPA ordering if there is one
    Not using a queue with Array.shift because it is O(n)
  */
  _stackAdd(listIndex) {
    for (let i = this.courseListings[listIndex].length; i > 0; i -= 1) {
      this.stack.push([listIndex, i - 1]);
    }
  }

  makeSchedules() {
    const {
      courseListings,
      schedules,
      stack,
      schedule,
      gradeMap,
      useCourseAvg,
    } = this;
    let schedulesAdded = 0;

    if (schedules.length >= ScheduleMaker.MAX_TOTAL_SCHEDULES) {
      return schedules;
    }

    while (stack.length !== 0 && schedulesAdded < ScheduleMaker.MAX_SCHEDULES) {
      const [listIndex, courseIndex] = stack.pop();

      if (schedule.size > listIndex) {
        const scheduleArray = [...schedule];
        while (schedule.size > listIndex) {
          schedule.delete(scheduleArray.pop());
        }
      }

      const course = courseListings[listIndex][courseIndex];
      if (schedule.add(course)) {
        if (listIndex === courseListings.length - 1) {
          schedules.push(new Schedule(schedule));
          schedulesAdded += 1;
        } else {
          this._stackAdd(listIndex + 1);
        }
      }
    }

    if (stack.length === 0) {
      this.complete = true;
    }

    if (gradeMap) {
      schedules.sort((schedule1, schedule2) => (
        schedule2.calculateGPA(gradeMap, useCourseAvg)
        - schedule1.calculateGPA(gradeMap, useCourseAvg)
      ));
    }

    return schedules;
  }
}

ScheduleMaker.MAX_SCHEDULES = 1000;
ScheduleMaker.MAX_TOTAL_SCHEDULES = 100000;

export default ScheduleMaker;

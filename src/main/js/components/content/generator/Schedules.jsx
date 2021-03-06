import React from 'react';
import ScheduleVisualTable from '../../ScheduleVisualTable';
import CourseTable from '../../../containers/content/generator/CourseTable';

class Schedules extends React.Component {
  constructor(props) {
    super(props);
    this.changeScheduleIndex = this.changeScheduleIndex.bind(this);
    this.toggleTextTable = this.toggleTextTable.bind(this);
    this.toggleVisualTable = this.toggleVisualTable.bind(this);
    this.toggleCarousel = this.toggleCarousel.bind(this);
    this.leftArrowListener = this.leftArrowListener.bind(this);
    this.rightArrowListener = this.rightArrowListener.bind(this);
    this.state = {
      scheduleIndex: 0,
      showTextTable: true,
      showVisualTable: true,
      showCarousel: true,
    };
  }

  componentDidMount() {
    window.addEventListener('keydown', this.leftArrowListener);
    window.addEventListener('keydown', this.rightArrowListener);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.leftArrowListener);
    window.removeEventListener('keydown', this.rightArrowListener);
  }

  leftArrowListener(e) {
    if (e.keyCode === 37 || e.keyCode === 65) {
      this.incrementSchedule(-1);
    }
  }

  rightArrowListener(e) {
    if (e.keyCode === 39 || e.keyCode === 68) {
      this.incrementSchedule(1);
    }
  }

  changeScheduleIndex(index) {
    let newIndex;
    const { schedules } = this.props;
    if (index < 0) {
      newIndex = 0;
    } else if (index > schedules.length - 1) {
      newIndex = schedules.length - 1;
    } else {
      newIndex = Math.floor(index);
    }
    this.setState({ scheduleIndex: newIndex });
  }

  toggleTextTable() {
    this.setState(prevState => ({ showTextTable: !prevState.showTextTable }));
  }

  toggleVisualTable() {
    this.setState(prevState => ({ showVisualTable: !prevState.showVisualTable }));
  }

  toggleCarousel() {
    this.setState(prevState => ({ showCarousel: !prevState.showCarousel }));
  }

  incrementSchedule(increment) {
    const { scheduleIndex } = this.state;
    this.changeScheduleIndex(scheduleIndex + increment);
  }

  render() {
    const {
      schedules,
      doneGenerating,
      generateMoreSchedules,
    } = this.props;
    const {
      scheduleIndex,
      showTextTable,
      showVisualTable,
      showCarousel,
    } = this.state;
    const schedule = schedules[scheduleIndex] ? schedules[scheduleIndex] : {};

    return (
      <div>
        <div id="schedules-bar" className="flex-container-spaced no-print">
          <div>
            <input
              type="number"
              className="form-control"
              onInput={e => this.changeScheduleIndex(e.target.value - 1)}
              onChange={() => {}}
              placeholder="Schedule #"
              min={schedules.length === 0 ? 0 : 1}
              max={schedules.length}
              value={schedules.length > 0 ? scheduleIndex + 1 : 0}
              style={{ width: '75px' }}
            />
          </div>
          <h4>
            {`Schedule ${schedules.length > 0 ? scheduleIndex + 1 : 0} of ${schedules.length}`}
          </h4>
          <div>
            { !doneGenerating && (
              <button type="button" className="btn btn-default" onClick={generateMoreSchedules}>
                Load More
              </button>
            )}
            <div className="btn-group">
              <button
                type="button"
                className="btn btn-default dropdown-toggle"
                data-toggle="dropdown"
              >
                <span className="glyphicon glyphicon-menu-hamburger" />
              </button>
              <ul className="dropdown-menu dropdown-menu-right">
                <li>
                  <a onClick={this.toggleCarousel}>
                    Toggle Nav
                  </a>
                </li>
                <li>
                  <a onClick={this.toggleTextTable}>
                    Toggle Text
                  </a>
                </li>
                <li>
                  <a onClick={this.toggleVisualTable}>
                    Toggle Visual
                  </a>
                </li>
                <li role="separator" className="divider" />
                <li>
                  <a
                    onClick={() => {
                      const text = document.createElement('textarea');
                      text.value = window.location.href;
                      text.style.left = '-9999px';
                      document.body.appendChild(text);
                      text.select();
                      document.execCommand('copy');
                      document.body.removeChild(text);
                    }}
                  >
                    <span className="glyphicon glyphicon-copy" />
                    {' Copy Link'}
                  </a>
                </li>
                <li>
                  <a onClick={window.print}>
                    <span className="glyphicon glyphicon-print" />
                    {' Print'}
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div className="carousel">
          <div className="carousel-inner">
            {showTextTable && <CourseTable courses={schedule} colored header />}
            {showVisualTable && <ScheduleVisualTable schedule={schedule} />}
          </div>
          {showCarousel && (
            <div
              role="button"
              className="left carousel-control no-print"
              onClick={() => this.incrementSchedule(-1)}
              onKeyPress={() => {}}
              tabIndex={-1}
            >
              <span className="glyphicon glyphicon-chevron-left" />
            </div>
          )}
          {showCarousel && (
            <div
              role="button"
              className="right carousel-control no-print"
              onClick={() => this.incrementSchedule(1)}
              onKeyPress={() => {}}
              tabIndex={-1}
            >
              <span className="glyphicon glyphicon-chevron-right" />
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default Schedules;

package scheduler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ScheduleGen
 */
@WebServlet("/ScheduleGen")
public class ScheduleGen extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String[] colors = new String[]{"orange", "lightseagreen", "lightgrey", "gold", "lightskyblue", 
                                                  "lightsalmon", "lightgreen", "lightblue", "lightyellow", 
                                                  "silver", "peachpuff", "pink"};;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleGen() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter printer = response.getWriter();
        StringBuilder html = new StringBuilder();
        LinkedList<Schedule> schedules;
        
        try {
            schedules = getSchedules(request);
        }
        catch (Exception e) {
            schedules = null;
        }
        
        // -- HEAD START -- ///
        html.append("<!DOCTYPE html>");
        html.append("<html lang='en'>");
        html.append("<head>");
        html.append("<title>PScheduler</title>");
        html.append("<meta charset='utf-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
        html.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>");
        html.append("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
        html.append("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/css/bootstrap-select.min.css'>");
        html.append("<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/bootstrap-select.min.js'></script>");
        html.append("<link rel='stylesheet' href='stylesheets/styles.css'>");
        html.append("<script type='text/javascript' src='javascripts/Schedules.js'");
        html.append("<script>");
        html.append("(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){");
        html.append("(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),");
        html.append("m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)");
        html.append("})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');");
        html.append("ga('create', 'UA-86032292-1', 'auto');");
        html.append("ga('send', 'pageview');");
        html.append("</script>");
        html.append("</head>");
        // -- HEAD END -- //

        
        // -- HEADER START -- //
        html.append("<body style='background-color: #eceeef'>");
        html.append("<div style='background-color: DarkSlateGray; border-bottom: 1px solid darkorange;'>");
        html.append("<div class='container-fluid'>");
        html.append("<h1>");
        html.append("<a href='http://www.pscheduler.com' style='text-decoration:none'>");
        html.append("<span style='color: darkorange'>P</span><span style='color: white'>Scheduler</span></a>");
        html.append("<small style='color: darkorange'><i> VT Schedule Creation</i></small>");
        html.append("</h1>");
        html.append("</div>");
        html.append("</div>");
        // -- HEADER END -- //
        
        // -- BODY START -- //
        html.append("<div class='container-fluid'>");
        html.append("<div style='padding-top: 10px;' class='row'>");
        html.append("<div style='padding-left: 12.5px; padding-right: 12.5px;' class='col-sm-12'>");
        html.append("<div class=' panel panel-default outline'>");
        html.append("<div style='background-color: white;' class='panel-heading center'>");
        html.append("<div class='row'>");
        html.append("<div class='col-sm-2'>");
        html.append("<div class='input-group'>");
        html.append("<div class='row'>");
        html.append("<div style='padding:0 0 0 5px;' class='col-sm-4'>");
        html.append("<span class='input-group-btn'>");
        html.append("<button type='button' class='btn btn-default glyphicon glyphicon-chevron-left form-control' onclick='changeSchedule(-1)'></button>");
        html.append("</span>");
        html.append("</div>");
        html.append("<div style='padding:1px 0 0 0;' class='col-sm-4'>");
        html.append("<input id='changet' class='form-control' type='text' value='1'> ");
        html.append("</div>");
        html.append("<div style='padding:0 3px 0 2px;' class='col-sm-4'>");
        html.append("<span class='input-group-btn'>");
        html.append("<button type='button' class='btn btn-default glyphicon glyphicon-chevron-right form-control' onclick='changeSchedule(1)'></button>");
        html.append("</span>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("<div class='col-sm-8'>");
        
        html.append("<h4><b>");
        if (schedules == null) {
            html.append("0 Schedules");
        }
        else {
            html.append(schedules.size() + " Schedules");
            if (schedules.size() != 0) {
                html.append(" | " + schedules.get(0).totalCredits() + " Credit Hours");
            }
        }
        html.append("</b></h4>");
        
        html.append("</div>");
        html.append("<div style='text-align:right' class='col-sm-1'>");
        html.append("<form action='modify'>");
        html.append("<button type='submit' class='btn btn-default'>Modify Search</button>");
        html.append("<select style='display: none;' name='classes'><option value='" + request.getParameter("schedule") + "'></option></select>");
        html.append("<select style='display: none;' name='term'><option value='" + request.getParameter("term") + "'></option></select>");
        html.append("<select style='display: none;' name='h1'><option value='" + request.getParameter("h1") + "'></option></select>");
        html.append("<select style='display: none;' name='m1'><option value='" + request.getParameter("m1") + "'></option></select>");
        html.append("<select style='display: none;' name='start'><option value='" + request.getParameter("start") + "'></option></select>");
        html.append("<select style='display: none;' name='h2'><option value='" + request.getParameter("h2") + "'></option></select>");
        html.append("<select style='display: none;' name='m2'><option value='" + request.getParameter("m2") + "'></option></select>");
        html.append("<select style='display: none;' name='end'><option value='" + request.getParameter("end") + "'></option></select>");
        String[] freeDays = request.getParameterValues("free");
        if (freeDays != null) {
            for (String d : freeDays) {
                html.append("<select style='display: none;' name='free'><option value='" + d + "'></option></select>");
            }
        }
        html.append("</form>");
        html.append("</div>");
        html.append("<div style='text-ling:right' class='col-sm-1'>");
        html.append("<button type='button' class='btn btn-default hidetb'>Hide Table</button>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("<div style='padding-top:0px' class='panel-body'>");
        
        try {
            if (schedules == null || schedules.size() == 0) {
                html.append("No Schedules Matched Your Parameters");
            }
            else if (schedules.size() < 1000) {
                html.append("<ul style='padding-left:0' id='textschedules' name='0'>");
                appendTextSchedules(html, schedules);
                html.append("</ul>");
           
                html.append("<ul style='padding-top:5px; padding-left:0' id='tableschedules' name='0'>");
                appendTableSchedules(html, schedules);
                html.append("</ul>");
            }
            else {
                html.append("There were over 999 Schedules, please narrow your parameters");
            }
        }
        catch (Exception e) {
            html.append("Error in Displaying Schedules<br>Please email me the url of this page!<br>");
        }
            
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        // -- BODY END -- //
        
        // -- FOOTER START -- //
        html.append("<div class='footer'>");
        html.append("<div style='padding: 2vh 15vw 2vh 15vw;' class='container-fluid'>");
        html.append("<div style='color:white;' class='row'>");
        html.append("<div class='col-sm-4 text-right'>");
        html.append("<a href='mailto:ngophill@vt.edu' style='color:white'>ngophill@vt.edu</a>");
        html.append("</div>");
        html.append("<div class='col-sm-4 text-center'>");
        html.append("<a style='color:darkorange;' href='https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_DispRequest'>Virginia Tech Class Time Table</a>");
        html.append("</div>");
        html.append("<div class='col-sm-4 text-left'>");
        html.append("<a style='color:white;' href='https://github.com/PhillipNgo/Scheduler-Website'>View the Project on GitHub</a>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");
        // -- FOOTER END -- //
        
        printer.print(html.toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    
    private LinkedList<Schedule> getSchedules(HttpServletRequest request) throws Exception {
        String startTime = request.getParameter("h1")+ ":";
        if ((Integer.parseInt(request.getParameter("m1"))-1)*5 == 0 || (Integer.parseInt(request.getParameter("m1"))-1)*5 == 5) {
            startTime += "0";
        }
        startTime += ((Integer.parseInt(request.getParameter("m1"))-1)*5) + request.getParameter("start");

        String endTime = request.getParameter("h2")+ ":";
        if ((Integer.parseInt(request.getParameter("m2"))-1)*5 == 0 || (Integer.parseInt(request.getParameter("m2"))-1)*5 == 5) {
            endTime += "0";
        }
        endTime += ((Integer.parseInt(request.getParameter("m2"))-1)*5) + request.getParameter("end");

        String[] freeDays = request.getParameterValues("free");  
        String term = request.getParameter("term");

        String[] classes = request.getParameter("schedule").split("xx");
        LinkedList<String> subjects = new LinkedList<>();
        LinkedList<String> numbers = new LinkedList<>();
        LinkedList<String> types = new LinkedList<>();
        LinkedList<String> crns = new LinkedList<>();
        LinkedList<String> profs = new LinkedList<>();

        for (int i = 0; i < classes.length; i++) {
            if (classes[i].length() == 5) {
                crns.add(classes[i]);
            }
            else {
                String[] split = classes[i].split("ZXD");
                int index = 0;
                if (split[0].charAt(split[0].length()-1) == 'H') {
                    index = 1;
                }
                types.add(split[0].substring(0, 1));
                subjects.add(split[0].substring(1, split[0].length()-4-index));
                numbers.add(split[0].substring(split[0].length()-4-index, split[0].length()));
                profs.add(split[1].replace("11", " "));
            }
        }
        
        return ScheduleMaker.generateSchedule(term, subjects, numbers, types, startTime, endTime, freeDays, crns, profs);
    }
    

    private void appendTextSchedules(StringBuilder html, LinkedList<Schedule> schedules) {
        int i = 0;
        for (Schedule schedule : schedules) {
            if (i != 0) {
                html.append("<table id='" + (i++) + "' style='display: none;' class='text table'>");
            }
            else {
                html.append("<table id='" + (i++) + "' class='text table'>");
            }
            html.append("<tr style='font-weight:bold'><td class='text'>CRN</td> <td  class='text'>Course</td> <td  class='text'>Title</td>"
                    + "<td class='text'>Type</td><td  class='text'>Credits</td><td  class='text'>Instructor</td>"
                    + "<td class='text'>Days</td> <td  class='text'>Time</td> <td  class='text'>Location</td></tr>");
            int j = 0;
            for (VTCourse c : schedule) {
                html.append("<tr class='left'"); //background color goes here
                if (c.getTimeSlot() != null) {
                    html.append(" style='background-color:" + colors[j] + "'");
                }
                html.append("><td class='text'>" + c.getCRN() + "</td>");
                html.append("<td class='text'>" + c.getSubject() + " " + c.getNum() + "</td>");
                html.append("<td class='text pad'>" + c.getName() + "</td>");
                String classType = c.getClassType();
                switch (classType) {
                    case "L": classType = "Lecture";
                              break;
                    case "B": classType = "Lab";
                              break;
                    case "C": classType = "Recitation";
                              break;
                    case "H": classType = "Hybrid";
                              break;
                    case "E": classType = "Emporium";
                              break;
                    case "O": classType = "Online";
                              break;
                    case "I": classType = "Independent Study";
                              break;
                    case "R": classType = "Research";
                              break;
                    default:  classType = "bug";
                              break;
                }
                html.append("<td class='text'>" + classType + "</td>");
                html.append("<td class='text'>" + c.getCredits() + "</td>");
                html.append("<td class='text'>" + c.getProf() + "</td>");             
                Time t = c.getTimeSlot();
                if (t != null) {
                    String[] days = c.getDays();
                    String day = "";
                    for (int k = 0; k < days.length; k++) {
                        day += days[k];
                    }
                    html.append("<td  class='text'>" + day + "</td>");
                    html.append("<td  class='text'>" + t.getStart() + " - " + t.getEnd() + "</td>");
                    html.append("<td  class='text pad'>" + c.getLocation() + "</td>");
                    html.append("</tr>");
                    if (c.getAdditionalDays() != null && c.getAdditionalTime() != null && c.getAdditionalLocation() != null) {
                        html.append("<tr " + "style='background-color:" + colors[j] + "'><td></td><td></td><td></td><td></td><td></td><td></td>");
                        days = c.getAdditionalDays();
                        String addedDays = "";
                        for (int k = 0; k < days.length; k++) {
                            addedDays += days[k];
                        }
                        html.append("<td  class='text'>" + addedDays + "</td>");
                        html.append("<td  class='text'>" + c.getAdditionalTime().getStart() + " - " + c.getAdditionalTime().getEnd() + "</td>");
                        html.append("<td  class='text pad'>" + c.getAdditionalLocation() + "</td>");
                        html.append("</tr>");
                    }
                }
                j++;
            }
            html.append("</table>");
        }      
    }
    
    private void appendTableSchedules(StringBuilder html, LinkedList<Schedule> schedules) throws TimeException {
        int i = 0;
        for (Schedule schedule : schedules) {
            if (i != 0) {
                html.append("<table class='outline' id='" + (i++) + "' style='display:none;text-align:center;'>");
            }
            else {
                html.append("<table class='outline' id='" + (i++) + "' style='text-align:center;'>");
            }
            
            html.append("<tr>");
            html.append("<td class='center' style='width:100pt; height:35pt;'>" + i + " of " + schedules.size() + "</td>");
            html.append("<td class='outline center' style='width:200pt; height:35pt;'>Monday</td>");
            html.append("<td class='outline center' style='width:200pt; height:35pt;'>Tuesday</td>");
            html.append("<td class='outline center' style='width:200pt; height:35pt;'>Wednesday</td>");
            html.append("<td class='outline center' style='width:200pt; height:35pt;'>Thursday</td>");
            html.append("<td class='outline center' style='width:200pt; height:35pt;'>Friday</td>");
            html.append("</tr>");
            
            int early = schedule.earliestTime()/60;
            int late = schedule.lastestTime()/60;
            for (int time = 0; time < late - early + 1; time++) {
                for (int row = 0; row < 12; row++) {
                    if (row == 0) {
                        html.append("<tr class='small'><td class='outline time' rowspan='12'>" + Time.timeString(early*60 + time*60) + "</td>");
                    }
                    else {
                        html.append("<tr class='small'>");
                    }
                    for (int col = 0; col < 5; col++) {
                        String c = getHTMLSchedule(schedule, col, Time.timeString(early*60 + time*60 + row*5));
                        if (c != null) {
                            String[] s = c.split("--");
                            html.append("<td " + "class='outline fill center' rowspan='" + s[1] + "' style='" +
                                        "background-color:" + s[2] + "'>");
                            html.append(s[0]);
                            html.append("</td>");
                        }
                        else if (!schedule.isBusy(col, (early*60 + time*60 + row*5))) {
                            html.append("<td></td>");
                        }
                    }
                    html.append("</tr>");
                }
            }
            html.append("</table>");
        }
    }
    
    /**
     * The corresponding class that has the given day and start time
     * 
     * @param day the day specified
     * @param startTime the time specified
     * @return the class if it exists
     */
    private String getHTMLSchedule(Schedule schedule, int day, String startTime) {
        StringBuilder html = new StringBuilder();
        for (VTCourse c : schedule) {
            String[] days = c.getDays();
            if (days != null) {
                for (String d : days) {
                    if (Schedule.DAYS.indexOf(d) == day) {
                        Time t = c.getTimeSlot();
                        if (t.getStart().equals(startTime)) {
                            html.append(c.getSubject() + " " + c.getNum() + "<br>");
                            html.append("CRN: " + c.getCRN() + "<br>");
                            html.append(t.getStart() + " - " + t.getEnd() + "<br>");
                            html.append(c.getLocation() + "<br>");
                            html.append("Prof: " + c.getProf());
                            int start = Time.timeNumber(t.getStart())/5;
                            int end = Time.timeNumber(t.getEnd())/5;
                            html.append("--" + (end-start));
                            html.append("--" + colors[schedule.indexOf(c)]);
                            return html.toString();
                        }
                        break;
                    }
                }
            }
            if (c.getAdditionalDays() != null) {
                days = c.getAdditionalDays();
                for (String d : days) {
                    if (Schedule.DAYS.indexOf(d) == day) {
                        Time t = c.getAdditionalTime();
                        if (c.getAdditionalTime().getStart().equals(startTime)) {
                            html.append(c.getSubject() + " " + c.getNum() + "<br>");
                            html.append("CRN: " + c.getCRN() + "<br>");
                            html.append(t.getStart() + " - " + t.getEnd() + "<br>");
                            html.append(c.getAdditionalLocation() + "<br>");
                            html.append("Prof: " + c.getProf());
                            int start = Time.timeNumber(t.getStart())/5;
                            int end = Time.timeNumber(t.getEnd())/5;
                            html.append("--" + (end-start));
                            html.append("--" + colors[schedule.indexOf(c)]);
                            return html.toString();
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }
}

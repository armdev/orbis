package io.project.app.profile;

import io.project.app.security.SessionContext;
import io.project.app.unicorn.DriverTimelineClient;
import io.project.app.web.dto.DriverTimeline;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

@Named
@javax.faces.view.ViewScoped
public class ScheduleView implements Serializable {

    @Inject
    private SessionContext sessionContext = null;
    
    private ScheduleModel eventModel;
    @Setter
    @Getter
    private DriverTimeline event = new DriverTimeline();
    @Inject
    private DriverTimelineClient driverTimelineClient;
    @Setter
    private List<DriverTimeline> timeLineList = new ArrayList<>();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
//        timeLineList = driverTimelineClient.findTimelineByDriverId(sessionContext.getUser().getId());
//        for (DriverTimeline r : timeLineList) {
//            eventModel.addEvent(r);
//        }

    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1);	//set random day of month

        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            System.out.println("New Event save ");
            // event.setLocation(sessionContext.getLocationModel());
            // event.setUserId(sessionContext.getUser().getUserId());
            // restRouteClient.save(event);
            eventModel.addEvent(event);
        } else {
            System.out.println("EEEEvent update id is :==> " + event.getId());
            //  event.setLocation(sessionContext.getLocationModel());
            // event.setUserId(sessionContext.getUser().getUserId());
            // event.setId(event.getId());
            //restRouteClient.update(event);
            eventModel.updateEvent(event);
        }
        event = new DriverTimeline();
    }

    public void removeEvent(ActionEvent actionEvent) {
        //restRouteClient.delete(event.getRouteId());
        eventModel.deleteEvent(event);
    }

    public void onEventSelect(SelectEvent selectEvent) {
        System.out.println("current event: onEventSelect called ");

        event = (DriverTimeline) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        System.out.println("onDateSelect ### ");
        //event = new CourierRouteModel((String) selectEvent.getObject(), (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        event = new DriverTimeline();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
    }

    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);

        return t.getTime();
    }

    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}

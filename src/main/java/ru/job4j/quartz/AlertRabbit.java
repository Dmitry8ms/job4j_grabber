package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        String propsFile = AlertRabbit.class.getClassLoader().getResource("rabbit.properties")
                .getFile();
        Properties prs = LoadProps.getLoadedPropsFrom(propsFile);
        String interval = prs.getProperty("rabbit.interval");
        int intervalInt = 0;
        try {
            intervalInt = Integer.parseInt(interval);
        } catch (NumberFormatException e) {
            System.out.println("Wrong interval parameter in rabbit.properties");
        }
        if (intervalInt > 0) {
            try {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDetail job = newJob(Rabbit.class).build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(intervalInt)
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException se) {
                se.printStackTrace();
            }
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}

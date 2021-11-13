package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private final Properties prs;

    public AlertRabbit(String propsFile) {
        this.prs = LoadProps.getLoadedPropsFrom(propsFile);
    }

    public static void main(String[] args) {
        var alertRabbit = new AlertRabbit("rabbit.properties");
        try (Connection dbConnect = alertRabbit.connect()) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", dbConnect);
            JobDetail job = newJob(Rabbit.class).usingJobData(data).build();
            String interval = alertRabbit.prs.getProperty("rabbit.interval");
            int intervalInt = Integer.parseInt(interval);
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(intervalInt)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }

    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        Connection cn;
        Class.forName(prs.getProperty("driver-class-name"));
        cn = DriverManager.getConnection(
                prs.getProperty("url"),
                prs.getProperty("username"),
                prs.getProperty("password")
        );
        return cn;
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("store");
            if (cn != null) {
                try (PreparedStatement ps = cn.prepareStatement(
                        "insert into rabbit (created_date) values(?);")) {
                    ps.setDate(1, new Date(System.currentTimeMillis()));
                    ps.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}

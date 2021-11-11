package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private final String propsFile;
    private Properties prs;

    public AlertRabbit(String propsFile) {
        this.propsFile = propsFile;
        this.prs = LoadProps.getLoadedPropsFrom(propsFile);
    }

    public static void main(String[] args) {
        var rabbit = new AlertRabbit("rabbit.properties");
        try (Connection dbConnect = rabbit.connect()) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", dbConnect);
            JobDetail job = newJob(Rabbit.class).usingJobData(data).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(rabbit.getInterval())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException | SQLException se) {
            se.printStackTrace();
        }

    }

    private Connection connect() {
        Connection cn = null;
        try {
            Class.forName(prs.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    prs.getProperty("url"),
                    prs.getProperty("username"),
                    prs.getProperty("password")
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cn;
    }

    private int getInterval() {
        String interval = prs.getProperty("rabbit.interval");
        int intervalInt = 0;
        try {
            intervalInt = Integer.parseInt(interval);
            if (intervalInt <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong interval parameter in rabbit.properties");
        }
        return intervalInt;
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("store");
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

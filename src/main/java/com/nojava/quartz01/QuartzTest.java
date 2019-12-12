package com.nojava.quartz01;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * quartz定时任务测试
 * 官网文档地址：
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/quick-start.html
 * 参考文档
 * https://www.w3cschool.cn/quartz_doc/quartz_doc-kixe2cq3.html
 * quartz 默认配置文件 quartz.properties org/quartz/quartz.properties
 * 自定义配置文件
 * StdSchedulerFactory先从当前工作目录中加载quartz.properties文件 如果失败则加载jar包中的配置文件。
 */
public class QuartzTest {
    /**
     * quartz之helloworld
     * StdSchedulerFactory 工厂类
     * 调度程序一旦关闭，便无法重新启动，除非重新实例化。
     * Scheduler生命周期从SchedulerFactory创建它时开始，到Scheduler调用shutdown()方法时结束
     *
     * 注意点1：不要用junit进行测试，junit测试跟线程有关的可能会有问题。。
     *
     */
    public static void main(String[] args) {
        try {
            //第一步 获得Scheduler实例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //第二步 启动
            scheduler.start();

            //第三步 定义job实现Job接口 这个接口只有一个execute方法

            //withIdentity(String name, String group)同一个分组下的Job或Trigger的名称必须唯一，即一个Job或Trigger的key由名称（name）和分组（group）组成。
            //第四步 创建JobDetail 包含job的各种属性设置，以及用于存储job实例状态信息的JobDataMap
            /**
             * JobDataMap
             * 中可以包含不限量的（序列化的）数据对象，在job实例执行的时候，可以使用其中的数据；JobDataMap是Java Map接口的一个实现，
             * 额外增加了一些便于存取基本类型的数据的方法。
             * usingJobData(JobDataMap newJobDataMap) 存放数据的地方
             */
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("a","aaa");
            jobDataMap.put("b","bbb");
            jobDataMap.put("c","ccc");
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData(jobDataMap)
                    .usingJobData("jobSays", "Hello World!")
                    .build();

            //第五步 创建Trigger  调用job 必须创建一个trigger去触发job Quartz自带了各种不同类型的Trigger，最常用的主要是SimpleTrigger和CronTrigger。
            /**
             * startNow()   立即执行
             * withIntervalInSeconds(int intervalInSeconds) 隔intervalInSeconds执行一次
             * repeatForever() 重复到永远
             * endAt(Date triggerEndTime)  停止时间
             */
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date parse = simpleDateFormat.parse("2019-10-28 11:03:00");
//            Trigger trigger1 = TriggerBuilder.newTrigger()
//                    .withIdentity("myTrigger", "group1")
//                    .startNow()
//                    .withSchedule(simpleSchedule()
//                            .withIntervalInSeconds(4)
//                            .repeatForever())
//                    .endAt(parse)
//                    .build();



            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger1", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                    .build();

            //第六步 告诉quartz job使用定义的Trigger 将Job和Trigger注册到Scheduler
//            scheduler.scheduleJob(job, trigger1);

            scheduler.scheduleJob(job, trigger2);

            //第七步 停止应用程序（非必须）
//            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

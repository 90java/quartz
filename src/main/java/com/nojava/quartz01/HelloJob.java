package com.nojava.quartz01;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 表明该job需要完成什么类型的任务 在execute方法实现
 */
public class HelloJob implements Job {
    /**
     * 获取JobDataMap存放的数据
     * 1.jobExecutionContext.getJobDetail().getJobDataMap()
     * 2.jobExecutionContext.getMergedJobDataMap();
     *
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println(new Date()+":Hello!  HelloJob is executing.");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String a = (String)jobDataMap.get("a");
        String b = (String)jobDataMap.get("b");
        String c = (String)jobDataMap.get("c");
        String jobSays = (String)jobDataMap.get("jobSays");

        System.err.println(a+"--"+b+"--"+c+"--"+jobSays);

        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        String a1 = (String)mergedJobDataMap.get("a");
        String b1 = (String)mergedJobDataMap.get("b");

        System.err.println(a1+"--"+b1);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

package com.openschool.aop.template;

public class NotificationTemplate {

    public static final String taskStatusChangedSubject = "Task status changed";
    public static final String taskStatusChangedBody = """
                                                       Task id:
                                                       %d
                                                       New status:
                                                       %s
                                                       """;



}

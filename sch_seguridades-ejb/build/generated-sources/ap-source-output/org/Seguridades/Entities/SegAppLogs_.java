package org.Seguridades.Entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-11T11:43:25")
@StaticMetamodel(SegAppLogs.class)
public class SegAppLogs_ { 

    public static volatile SingularAttribute<SegAppLogs, Date> dateLog;
    public static volatile SingularAttribute<SegAppLogs, String> locLog;
    public static volatile SingularAttribute<SegAppLogs, String> locationLog;
    public static volatile SingularAttribute<SegAppLogs, String> messageLog;
    public static volatile SingularAttribute<SegAppLogs, String> appLog;
    public static volatile SingularAttribute<SegAppLogs, String> throwableLog;
    public static volatile SingularAttribute<SegAppLogs, String> logLevelLog;
    public static volatile SingularAttribute<SegAppLogs, String> stacktraceLog;
    public static volatile SingularAttribute<SegAppLogs, Integer> idLog;

}
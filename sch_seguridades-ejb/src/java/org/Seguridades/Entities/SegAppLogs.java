/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nelson
 */
@Entity
@Table(name = "seg_app_logs",schema = "sch_seguridades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAppLogs.findAll", query = "SELECT s FROM SegAppLogs s"),
    @NamedQuery(name = "SegAppLogs.findByIdLog", query = "SELECT s FROM SegAppLogs s WHERE s.idLog = :idLog"),
    @NamedQuery(name = "SegAppLogs.findByAppLog", query = "SELECT s FROM SegAppLogs s WHERE s.appLog = :appLog"),
    @NamedQuery(name = "SegAppLogs.findByLogLevelLog", query = "SELECT s FROM SegAppLogs s WHERE s.logLevelLog = :logLevelLog"),
    @NamedQuery(name = "SegAppLogs.findByLocationLog", query = "SELECT s FROM SegAppLogs s WHERE s.locationLog = :locationLog"),
    @NamedQuery(name = "SegAppLogs.findByLocLog", query = "SELECT s FROM SegAppLogs s WHERE s.locLog = :locLog"),
    @NamedQuery(name = "SegAppLogs.findByMessageLog", query = "SELECT s FROM SegAppLogs s WHERE s.messageLog = :messageLog"),
    @NamedQuery(name = "SegAppLogs.findByThrowableLog", query = "SELECT s FROM SegAppLogs s WHERE s.throwableLog = :throwableLog"),
    @NamedQuery(name = "SegAppLogs.findByStacktraceLog", query = "SELECT s FROM SegAppLogs s WHERE s.stacktraceLog = :stacktraceLog"),
    @NamedQuery(name = "SegAppLogs.findByDateLog", query = "SELECT s FROM SegAppLogs s WHERE s.dateLog = :dateLog")})
public class SegAppLogs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_log")
    private Integer idLog;
    @Size(max = 100)
    @Column(name = "app_log")
    private String appLog;
    @Size(max = 100)
    @Column(name = "log_level_log")
    private String logLevelLog;
    @Size(max = 300)
    @Column(name = "location_log")
    private String locationLog;
    @Size(max = 2147483647)
    @Column(name = "loc_log")
    private String locLog;
    @Size(max = 2147483647)
    @Column(name = "message_log")
    private String messageLog;
    @Size(max = 2147483647)
    @Column(name = "throwable_log")
    private String throwableLog;
    @Size(max = 2147483647)
    @Column(name = "stacktrace_log")
    private String stacktraceLog;
    @Column(name = "date_log")
    @Temporal(TemporalType.DATE)
    private Date dateLog;

    public SegAppLogs() {
    }

    public SegAppLogs(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getAppLog() {
        return appLog;
    }

    public void setAppLog(String appLog) {
        this.appLog = appLog;
    }

    public String getLogLevelLog() {
        return logLevelLog;
    }

    public void setLogLevelLog(String logLevelLog) {
        this.logLevelLog = logLevelLog;
    }

    public String getLocationLog() {
        return locationLog;
    }

    public void setLocationLog(String locationLog) {
        this.locationLog = locationLog;
    }

    public String getLocLog() {
        return locLog;
    }

    public void setLocLog(String locLog) {
        this.locLog = locLog;
    }

    public String getMessageLog() {
        return messageLog;
    }

    public void setMessageLog(String messageLog) {
        this.messageLog = messageLog;
    }

    public String getThrowableLog() {
        return throwableLog;
    }

    public void setThrowableLog(String throwableLog) {
        this.throwableLog = throwableLog;
    }

    public String getStacktraceLog() {
        return stacktraceLog;
    }

    public void setStacktraceLog(String stacktraceLog) {
        this.stacktraceLog = stacktraceLog;
    }

    public Date getDateLog() {
        return dateLog;
    }

    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAppLogs)) {
            return false;
        }
        SegAppLogs other = (SegAppLogs) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Seguridades.SegAppLogs[ idLog=" + idLog + " ]";
    }
    
}

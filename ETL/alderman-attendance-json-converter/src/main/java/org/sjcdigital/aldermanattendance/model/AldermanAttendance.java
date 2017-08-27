package org.sjcdigital.aldermanattendance.model;

import java.util.Date;
import java.util.Map;

public class AldermanAttendance {

	private String session;
	private Date date;
	private Map<String, String> attendance;
	
	public AldermanAttendance() {
	}

	public AldermanAttendance(String session, Date date, Map<String, String> attendance) {
		this.session = session;
		this.date = date;
		this.attendance = attendance;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<String, String> getAttendance() {
		return attendance;
	}

	public void setAttendance(Map<String, String> attendance) {
		this.attendance = attendance;
	}

	@Override
	public String toString() {
		return "AldermanAttendance [session=" + session + ", date=" + date + ", attendance=" + attendance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attendance == null) ? 0 : attendance.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AldermanAttendance other = (AldermanAttendance) obj;
		if (attendance == null) {
			if (other.attendance != null)
				return false;
		} else if (!attendance.equals(other.attendance))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		return true;
	}

}

package com.backinfile.core;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.backinfile.support.Time2;

public abstract class Service implements Delayed {
	protected long m_time = 0;
	protected int m_dispatchHertz = 50;

	private long last_pulsePerSec_time = 0;

	public abstract void startup();

	public abstract void pulse();

	public abstract void pulsePerSec();

	public final void pulseService() {
		m_time = Time2.getCurrentTimestamp();

		try {
			pulse();
		} catch (Exception e) {
			Log.core.error("service pulse error", e);
		}
		if (last_pulsePerSec_time == 0 || m_time - last_pulsePerSec_time >= Time2.SECOND) {
			try {
				pulsePerSec();
			} catch (Exception e) {
				Log.core.error("service pulsePerSec error", e);
			}
			last_pulsePerSec_time = m_time;
		}

	}

	public final long getTime() {
		return m_time;
	}

	public void setDispatchHertz(int dispatchHertz) {
		this.m_dispatchHertz = dispatchHertz;
	}

	@Override
	public final long getDelay(TimeUnit unit) {
		return m_time + 1000 / m_dispatchHertz - Time2.getCurrentTimestamp();
	}

	@Override
	public final int compareTo(Delayed o) {
		Service service = (Service) o;
		return Long.compare(m_time, service.m_time);
	}
}

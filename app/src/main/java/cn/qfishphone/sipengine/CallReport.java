package cn.qfishphone.sipengine;

public interface CallReport {

	public String getFrom();

	public String getTo();

	public CallDirection getDirection();

	public int getDuration();

	public CallStatus getCallStatus();

	public String getRecordFile();

	public String getDateTime();

	public boolean getCallRecord();
}

package cn.qfishphone.sipengine;

public class CallDirection {
	public static CallDirection Outgoing = new CallDirection("CallOutgoing");
	public static CallDirection Incoming = new CallDirection("Callincoming");
	private String mStringValue;

	private CallDirection(String aStringValue) {
		mStringValue = aStringValue;
	}

	public String toString() {
		return mStringValue;
	}
}

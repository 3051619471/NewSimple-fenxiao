package cn.qfishphone.sipengine;

import android.content.Context;
import android.util.Log;

public class SipEngineFactoryImpl extends SipEngineFactory {
	private native static boolean NativeInit();

	static {
		Log.d("*ASTGO*", "Loading astgo_client-jni...");
		System.loadLibrary("astgo_client-jni");
	}

	@Override
	public SipEngineCore createPhoneCore(SipEngineEventListener alistener,
			Context context) {
		return new SipEngineCoreImpl(alistener, context);
	}

}

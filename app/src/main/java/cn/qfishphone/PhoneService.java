//package cn.qfishphone;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.IBinder;
//import android.util.Log;
//
//import com.astgo.fenxiao.MainActivity;
//import com.astgo.fenxiao.MyApplication;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import cn.qfishphone.sipengine.SipEngineCore;
//import cn.qfishphone.sipengine.SipEngineEventListener;
//import cn.qfishphone.sipengine.SipEngineFactory;
//
//
//public class PhoneService extends Service implements SipEngineEventListener {
//	private static PhoneService the_service_instance_ = null;
//	static private SipEngineCore the_sipengine_ = null;
//	static private SipEngineEventListener the_ui_event_linstener_ = null;
//	public Handler mHandler = new Handler();
//	Timer mTimer = new Timer("Astgo scheduler");
//
//	public static boolean isready() {
//		return (the_service_instance_ != null);
//	}
//
//	static public SipEngineCore getSipEngine() {
//		return the_sipengine_;
//	}
//
//	static public void RegisterUIEventListener(SipEngineEventListener aLinstener) {
//		the_ui_event_linstener_ = aLinstener;
//	}
//
//	static public void DeRegisterUIEventListener() {
//		the_ui_event_linstener_ = null;
//	}
//
//	public static PhoneService instance() {
//		if (the_service_instance_ == null) {
//			throw new RuntimeException(
//					"the_service_instance_ not instanciated yet");
//		} else {
//			return the_service_instance_;
//		}
//	}
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		the_service_instance_ = this;
//
//
//		if (the_sipengine_ == null) {
//
//			Log.e("*ASTGO*", "PhoneService  runing create PhoneService");
//
//			the_sipengine_ = SipEngineFactory.instance().createPhoneCore(
//					(SipEngineEventListener) this, this);
//			the_sipengine_.CoreInit();
//			the_sipengine_.EnableDebug(false);
//			//Log.d("*SipEngine*", "Run CoreEventProgress timer");
//			TimerTask lTask = new TimerTask() {
//				@Override
//				public void run() {
//					the_sipengine_.CoreEventProgress();
//				}
//			};
//
//			mTimer.scheduleAtFixedRate(lTask, 0, 100);
//		} else {
//			ResetCore();
//		}
//
//		Log.e("*ASTGO*", "PhoneService  runing create PhoneService");
//
//		MyApplication.setsipengine(the_sipengine_);
//
//
//		if (MainActivity.context != null) {
//
//			//Log.e("*ASTGO*", "PhoneService  runing create PhoneService getMainUI");
//
//			RegisterUIEventListener(MainActivity.context);
//		}
//
//
//
//
//		PhoneService.instance().getSipEngine().SetNS(true);
//		PhoneService.instance().getSipEngine().SetAGC(true);
//		PhoneService.instance().getSipEngine().SetAEC(true);
//
//	}
//
//	public void ResetCore() {
//
//		if (mTimer != null) {
//			mTimer.cancel();
//			mTimer = null;
//		}
//
//		the_sipengine_ = SipEngineFactory.instance().createPhoneCore(
//				(SipEngineEventListener) this, this);
//		the_sipengine_.CoreInit();
//
//		//Log.d("*SipEngine*", "Reset CoreEventProgress timer");
//
//		TimerTask lTask = new TimerTask() {
//			@Override
//			public void run() {
//				the_sipengine_.CoreEventProgress();
//			}
//		};
//
//		mTimer = new Timer();
//		mTimer.scheduleAtFixedRate(lTask, 0, 100);
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		mTimer.cancel();
//		mTimer = null;
//		if (the_sipengine_ != null)
//			the_sipengine_.DeRegisterSipAccount();
//		the_service_instance_ = null;
//	}
//
//	public void OnSipEngineState(int code) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnSipEngineState(code);
//		}
//	}
//
//	public void OnRegistrationState(int code, int error_code) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnRegistrationState(code, error_code);
//		}
//	}
//
//	public void OnNewCall(int CallDir, String peer_caller, boolean is_video_call) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnNewCall(CallDir, peer_caller,
//					is_video_call);
//		}
//
//		if (CallDir == 1) {
//			/* new incoming call */
//
//			final String f_peer_caller  = peer_caller;
//
//			mHandler.post(new Runnable() {
//				public void run() {
//
//					/*
//					Intent intent = new Intent(MyApplication.context,SipCallActivity.class);
//					intent.putExtra("number", f_peer_caller);
//					intent.putExtra("name", f_peer_caller);
//					intent.putExtra("calltype", "in");
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					MyApplication.context.startActivity(intent);
//					*/
//
//					/*
//					 * Intent intent = new Intent();
//					 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					 * intent.setClass(the_service_instance_,
//					 * SipEngineTest.class); startActivity(intent);
//					 */
//
//				}
//			});
//		}
//	}
//
//	public void OnCallProcessing() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallProcessing();
//		}
//	}
//
//	public void OnCallRinging() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallProcessing();
//		}
//	}
//
//	public void OnCallConnected() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallConnected();
//		}
//	}
//
//	public void OnCallStreamsRunning(boolean is_video_call) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallStreamsRunning(is_video_call);
//		}
//	}
//
//	public void OnCallPaused() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallPaused();
//		}
//	}
//
//	public void OnCallResuming() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallResuming();
//		}
//	}
//
//	public void OnCallEnded() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallEnded();
//		}
//	}
//
//	public void OnCallFailed(int status) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallFailed(status);
//		}
//	}
//
//	public void OnRemoteDtmfClicked(int dtmf) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnRemoteDtmfClicked(dtmf);
//		}
//	}
//
//	public void OnCallReport(long nativePtr) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallReport(nativePtr);
//		}
//	}
//
//	public void OnCallMediaStreamConnected(int mode) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallMediaStreamConnected(mode);
//		}
//	}
//
//	@Override
//	public void OnCallPausedByRemote() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallPausedByRemote();
//		}
//
//	}
//
//	@Override
//	public void OnCallResumingByRemote() {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnCallPausedByRemote();
//		}
//	}
//
//	@Override
//	public void OnNetworkQuality(int ms, String vos_balance) {
//		if (the_ui_event_linstener_ != null) {
//			the_ui_event_linstener_.OnNetworkQuality(ms, vos_balance);
//		}
//	}
//}

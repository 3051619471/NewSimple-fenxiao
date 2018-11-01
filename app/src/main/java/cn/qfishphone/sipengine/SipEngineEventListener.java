package cn.qfishphone.sipengine;

public interface SipEngineEventListener {
	/**
	 * < ���������¼�
	 *
	 * @return
	 */
	public void OnSipEngineState(int code);

	/**
	 * < SIP �˺�ע���¼�
	 *
	 * @return
	 */
	public void OnRegistrationState(int code, int error_code);

	/**
	 * < ������
	 *
	 * @return
	 */
	public void OnNewCall(int CallDir, String peer_caller, boolean is_video_call);

	/**
	 * < ���д�����
	 *
	 * @return
	 */
	public void OnCallProcessing();

	/**
	 * < �Զ�����
	 *
	 * @return
	 */
	public void OnCallRinging();

	/**
	 * < ���н�ͨ
	 *
	 * @return
	 */
	public void OnCallConnected();

	/**
	 * < ý���ͨ
	 *
	 * @return
	 */
	public void OnCallStreamsRunning(boolean is_video_call);

	/**
	 * < ý���ͨ typedef enum { TRANS_RTP = 0, TRANS_ICE } MediaTransMode;
	 *
	 * @return
	 */
	public void OnCallMediaStreamConnected(int mode);

	/* ���ر��ֺ��� */
	public void OnCallPaused();

	/* ���غ��лָ� */
	public void OnCallResuming();

	/* Զ�˱��ֺ��� */
	public void OnCallPausedByRemote();

	/* Զ�˻ָ����� */
	public void OnCallResumingByRemote();

	/* ���н��� */
	public void OnCallEnded();

	/**
	 * < ����ʧ��
	 *
	 * @return
	 */
	public void OnCallFailed(/* CallErrorCode */int status);

	/**
	 * < �����ӳ٣���VOS����ֵ
	 *
	 * @return
	 */
	public void OnNetworkQuality(int ms, String vos_balance);

	/**
	 * < �Է�����dtmf
	 *
	 * @return
	 */
	public void OnRemoteDtmfClicked(int dtmf);

	/**
	 * < ��������
	 * 
	 * @return
	 */
	public void OnCallReport(long nativePtr);
}

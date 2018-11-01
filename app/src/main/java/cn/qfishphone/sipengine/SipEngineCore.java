package cn.qfishphone.sipengine;

public interface SipEngineCore {
	/* ��ʼ��Sip���� */
	public boolean CoreInit();

	/* ����Sip���� */
	public boolean CoreTerminate();

	/* ����Ƿ��ʼ���ɹ� */
	public boolean IsInitialized();

	/* �����¼������� */
	public boolean CoreEventProgress();

	/* ����һ��sip url ���� sip:alice@sip.domain.com */
	public boolean MakeUrlCall(String url);

	/* ����һ������ */
	public boolean MakeCall(String num);

	/* �ܾ����磬��Ҷϵ�ǰͨ�� */
	public boolean Hangup();

	/* ע��sip�ʺ� */
	public boolean RegisterSipAccount(String username, String password,
									  String server, int port);

	/* ����˺��Ƿ���ע�� */
	public boolean AccountIsRegistered();

	/* ǿ������ע�� */
	public boolean ForceReRegster();

	/* ע���ʺ� */
	public boolean DeRegisterSipAccount();

	/* �������� */
	public boolean SetAEC(boolean yesno);

	/* �Զ�������� */
	public boolean SetAGC(boolean yesno);

	/* ���� */
	public boolean SetNS(boolean yesno);

	/* ����dtmf�ź� mode 0=rfc2833, 1=sip info */
	public boolean SendDtmf(int mode, String dtmf);

	/* �л����� */
	public boolean SetLoudspeakerStatus(boolean yesno);

	/* RC4 �������� */
	public boolean SetRC4Crypt(boolean yesno, String key);

	/* ����Ƿ������� */
	public boolean HaveIncomingCall();

	/* �������� */
	public boolean AnswerCall();

	/* �������������� */
	public boolean SetSpeakerVolume(int vol);

	/* ���������Ƿ���ã��Ա�֪ͨ�ں��Ƿ�ע�ᵽ������ */
	public boolean SetNetworkStateReachable(boolean yesno);

	/* ¼��ͨ�� path=/sdcard/xxxx/recoding_path */
	public boolean StartRecoding(String path);

	/* ֹͣ¼�� */
	public boolean StopRecoding();

	/* �ж��Ƿ�����¼�� */
	public boolean CallIsInRecording();

	/* ��ӡDebug��־ */
	public boolean EnableDebug(boolean yesno);

	/* for p2p mode */
	/* P2P ����ģʽ����Ҫ��������֧��,��������Ҫ����Stun��Turn ������ */
	public boolean SetUseICE(boolean yesno);

	/* ��ǿ������ģʽ */
	public boolean SetVoFEC(boolean yesno);

	/* ����Transport */
	public boolean ResetTransport();

	/* ����VOS����ѯ���� */
	public boolean SendVOSBalanceQuery();

	/* ���ֺ��� */
	public boolean SetHold();

	/* �ָ����� */
	public boolean SetUnHold();

	/* Mic���� */
	public boolean MuteMic(boolean yesno);

	/* Spk���� */
	public boolean MuteSpk(boolean yesno);

	/* ����Stun ��������ַ */
	public boolean SetStunServer(String stun_server);

	/* ����Turn ��������Ϣ */
	public boolean SetTurnServer(String user, String passwd, String turn_server);

	/* ���µ�ǰAudioCodecs List */
	public boolean SetAudioCodecs(String codec_list);

	/*
	 * ���ô���˿�����: ����1 0 = UDP, 1 = TCP, 2 = TLS ����2 1-65535 ָ���� -1 or
	 * 0 ����˿�
	 */
	public boolean SetTransport(int type, int bind_port);
}

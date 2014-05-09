package gov.polisen.ainappen.ipTelephony;

import gov.polisen.ainappen.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;
import android.util.Log;

/**
 * Listens for incoming SIP calls and hands them off to the Call class in the
 * main activity.
 */
public class IncomingCallReceiver extends BroadcastReceiver {

	private Call	sipCall;

	/**
	 * Creates an incoming call receiver that is connected to the given Call
	 * instance.
	 * 
	 * @param sipCall The Call that this receiver is connected to.
	 */
	public IncomingCallReceiver(Call sipCall) {
		this.sipCall = sipCall;
	}

	/**
	 * Processes the incoming call, answers it and sends it to the Call class.
	 * It also opens the fragment that handles calls.
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		SipAudioCall incomingCall = null;
		Log.d("recieving call", "recieving call");
		try {

			SipAudioCall.Listener listener = new SipAudioCall.Listener() {
				@Override
				public void onRinging(SipAudioCall call, SipProfile caller) {
					try {
						call.answerCall(30);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			MainActivity mainActivity = (MainActivity) context;

			incomingCall = mainActivity.getSipCall().getManager()
					.takeAudioCall(intent, listener);
			incomingCall.answerCall(30);
			incomingCall.startAudio();
			if (incomingCall.isMuted()) {
				incomingCall.toggleMute();
			}

			sipCall.setCall(incomingCall);
			mainActivity.gotoCall();

		} catch (Exception e) {
			if (incomingCall != null) {
				incomingCall.close();
			}
		}
	}
}

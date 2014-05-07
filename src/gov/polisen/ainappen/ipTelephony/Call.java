package gov.polisen.ainappen.ipTelephony;

import java.text.ParseException;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.util.Log;

/**
 * A Call object that handles SIP registration and SIP calls.
 * 
 * @author samuel
 * 
 */
public class Call {

	private SipManager				manager;
	private SipProfile				myUser;
	private SipAudioCall			call	= null;
	private IncomingCallReceiver	callReceiver;
	private Context					context;

	private static final String		DOMAIN	= "itkand-1.ida.liu.se";

	/**
	 * Instantiates a Call object that handles SIP registration and SIP calls.
	 * 
	 * @param context The context that the SIP manager is connected to
	 */
	public Call(Context context) {
		IntentFilter filter = createIntentFilter();
		callReceiver = new IncomingCallReceiver(this);
		this.context = context;
		context.registerReceiver(callReceiver, filter);

	}

	/**
	 * Creates an intent filter that fires the incoming call receiver when
	 * someone calls this applications SIP address.
	 * 
	 * @return the created filter.
	 */
	private IntentFilter createIntentFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.AinAppen.INCOMING_CALL");
		return filter;
	}

	/**
	 * Creates a new instance of the SIP manager that handles calls and
	 * registrations.
	 */
	public void initializeManager(String userName, String password) {
		if (manager == null) {
			manager = SipManager.newInstance(context);
		}
		initializeLocalProfile(userName, password);
	}

	/**
	 * Initializes a SIP profile and tries to register with the SIP server.
	 * 
	 * @param userName The user name to be registered.
	 * @param password The password for the user.
	 */
	private void initializeLocalProfile(String userName, String password) {
		if (manager == null) {
			return;
		}

		if (myUser != null) {
			closeLocalProfile();
		}

		try {
			SipProfile.Builder builder = new SipProfile.Builder(userName,
					DOMAIN);
			builder.setPassword(password);

			myUser = builder.build();

			Intent i = new Intent();
			i.setAction("android.AinAppen.INCOMING_CALL");
			PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
					Intent.FILL_IN_DATA);
			manager.open(myUser, pi, null);

			manager.setRegistrationListener(myUser.getUriString(),
					new SipRegistrationListener() {
						@Override
						public void onRegistering(String localProfileUri) {
							Log.d("registering on sip server",
									"registering on sip server");
						}

						@Override
						public void onRegistrationDone(String localProfileUri,
								long expiryTime) {
							Log.d("registration on sip server done",
									"registration on sip server done");
						}

						@Override
						public void onRegistrationFailed(
								String localProfileUri, int errorCode,
								String errorMessage) {
							Log.d("registration on sip server failed",
									"registration on sip server failed");
						}
					});
		} catch (ParseException pe) {
			Log.w("peerException", pe);
		} catch (SipException se) {
			Log.w("sipException", se);
		}
	}

	/**
	 * Closes and unregisters your user profile.
	 */
	public void closeLocalProfile() {
		if (manager == null) {
			return;
		}
		try {
			if (myUser != null) {
				if (!call.isInCall())
					manager.close(myUser.getUriString());
				context.unregisterReceiver(callReceiver);
			}
		} catch (Exception ee) {
			Log.d("onDestroy", "Failed to close local profile.", ee);
		}
	}

	/**
	 * Starts a new call.
	 * 
	 * @param userName The SIP user name of the user you want to call.
	 */
	public void initiateCall(String userName) {

		String sipAdress = userName + "@" + DOMAIN;

		try {
			SipAudioCall.Listener callListener = new SipAudioCall.Listener() {
				@Override
				public void onCallEstablished(SipAudioCall call) {
					call.startAudio();
					if (call.isMuted()) {
						call.toggleMute();
					}

				}

				@Override
				public void onCallEnded(SipAudioCall call) {

				}
			};

			call = manager.makeAudioCall(myUser.getUriString(), sipAdress,
					callListener, 30);

		} catch (Exception e) {
			Log.i("InitiateCall", "Error when trying to close manager.", e);
			if (myUser != null) {
				try {
					manager.close(myUser.getUriString());
				} catch (Exception ee) {
					Log.i("InitiateCall",
							"Error when trying to close manager.", ee);
					ee.printStackTrace();
				}
			}
			if (call != null) {
				call.close();
			}
		}
	}

	public SipManager getManager() {
		return manager;
	}

	public SipAudioCall getCall() {
		return call;
	}

	public void setCall(SipAudioCall call) {
		this.call = call;
	}

}

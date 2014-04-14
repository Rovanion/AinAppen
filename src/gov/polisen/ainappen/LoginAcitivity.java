package gov.polisen.ainappen;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAcitivity extends Activity {

	public LoginAcitivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_acitivity);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	@SuppressLint("ValidFragment")
	public class PlaceholderFragment extends Fragment {

		EditText		userNameText;
		EditText		passwordText;
		private Button	quickLogButton;
		private Button	databaseLoginButton;
		View			rootView;
		LoginDBHandler	ldh;

		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_login_acitivity,
					container, false);
			setupLoginToDatabaseButtonListener();
			setupQuickLoginButtonListener();
			ldh = new LoginDBHandler(getActivity());
			return rootView;
		}

		public void setupQuickLoginButtonListener() {
			quickLogButton = (Button) rootView
					.findViewById(R.id.quickLoginButton);
			quickLogButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					cheatLogin();
				}
			});
		}

		public void checkLogin() {
			Intent intent = new Intent(getActivity(), MainActivity.class);
			makeGlobal();
			ldh.release();
			startActivity(intent);
		}

		public void cheatLogin() {
			Intent intent = new Intent(getActivity(), MainActivity.class);
			final GlobalData appData = ((GlobalData) getApplicationContext());
			appData.setUserID("fuskLog");
			ldh.release();
			startActivity(intent);
			finish();
		}

		public void makeGlobal() {
			final GlobalData appData = ((GlobalData) getApplicationContext());
			Random rnd = new Random();
			int dId = rnd.nextInt(1000000);
			appData.setUserID(userNameText.getText().toString());
			appData.setDeviceID(dId);
			appData.setPassword(passwordText.getText().toString());
		}

		public void setupLoginToDatabaseButtonListener() {
			databaseLoginButton = (Button) rootView
					.findViewById(R.id.database_login_button);
			databaseLoginButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					textFieldSetter();
					boolean access;
					access = checkIfCorrect();
					if (access) {
						checkLogin();
					} else {
						makeFailedToast();
					}
				}
			});
		}

		public void makeFailedToast() {
			String toastMessage = "Fel användarnamn eller lösenord!";
			Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG)
					.show();
		}

		public void textFieldSetter() {
			userNameText = (EditText) rootView.findViewById(R.id.userIdInput);
			passwordText = (EditText) rootView.findViewById(R.id.userPassword);
		}

		public boolean checkIfCorrect() {
			Boolean isCorrect = false;
			LoginDBHandler ldh = new LoginDBHandler(getActivity());
			Hasher hs = new Hasher();

			/*
			 * This part is temporary for creating our (for now) only existing
			 * saved user in the login database. If such a user already exists,
			 * nothing is changed in the database.
			 */
			LoginData tempLogin1 = new LoginData("7001");
			LoginData tempLogin2 = new LoginData("7002");
			String tempSalt1 = "henning";
			String tempSalt2 = "henning2";
			tempLogin1.setSalt(tempSalt1);
			tempLogin2.setSalt(tempSalt2);
			String tempPassword1 = "polisen1";
			String tempPassword2 = "polisen2";

			// Generate and set hashed password from salt+password
			String tempHashedPw1 = null;
			String tempHashedPw2 = null;
			String noAlgorithmTxt = "Can't login due to no algorithm";
			try {
				tempHashedPw1 = hs.getSHA256Hash(tempSalt1 + tempPassword1);
				tempHashedPw2 = hs.getSHA256Hash(tempSalt2 + tempPassword2);
			} catch (NoSuchAlgorithmException e1) {
				Toast.makeText(getActivity(), noAlgorithmTxt, Toast.LENGTH_LONG)
						.show();
			}
			tempLogin1.setHashedPassword(tempHashedPw1);
			tempLogin2.setHashedPassword(tempHashedPw2);
			ldh.makeTempLogin(tempLogin1);
			ldh.makeTempLogin(tempLogin2);

			/*
			 * Check if the username and password are correct! First a LoginData
			 * object with this information needs to be created.
			 */
			LoginData userData = new LoginData(userNameText.getText()
					.toString());
			String userPw = passwordText.getText().toString();

			// Get the SALT used for this user from database!
			String userSalt = ldh.getSalt(userData);

			/**
			 * TODO: Här vill vi egentligen ansluta till serversidan och se om
			 * användaren finns där, just nu säger vi bara att det är fel!
			 */
			if (userSalt == null) {
				Thread.yield();
				return isCorrect;
			} else {
				// Set the salt in our user object
				userData.setSalt(userSalt);
				// generate the hash from salt + the password the user entered
				String userHashedPw = null;
				try {
					userHashedPw = hs.getSHA256Hash(userSalt + userPw);
				} catch (NoSuchAlgorithmException e) {
					Toast.makeText(getActivity(), noAlgorithmTxt,
							Toast.LENGTH_LONG).show();
				}

				userData.setHashedPassword(userHashedPw);

				// Compare if username and hashed password matches what's in the
				// database
				isCorrect = ldh.loginAuthenticity(userData);
				return isCorrect;
			}
		}
	}
}

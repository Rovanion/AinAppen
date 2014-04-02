package gov.polisen.ainappen;

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


public class LoginAcitivity extends Activity{

	public LoginAcitivity(){

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
	public static class PlaceholderFragment extends Fragment {

		EditText userNameText;
		EditText passwordText;
		private Button quickLogButton;
		private Button databaseLoginButton;
		View rootView;
		LoginDBHandler ldh;

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

		public void setupQuickLoginButtonListener(){
			quickLogButton = (Button) rootView.findViewById(R.id.quickLoginButton);
			quickLogButton.setOnClickListener( new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					checkLogin();
				}
			});
		}

		public void checkLogin(){
			Intent intent = new Intent(getActivity(), MainActivity.class);
			ldh.release();
			startActivity(intent);
		}

		public void setupLoginToDatabaseButtonListener(){
			databaseLoginButton = (Button) rootView.findViewById(R.id.database_login_button);
			databaseLoginButton.setOnClickListener( new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					textFieldSetter();
					boolean access;
					access = checkIfCorrect();
					if(access){
						checkLogin();
					}
					else{
						makeFailedToast();
					}
				}
			});
		}
		public void makeFailedToast(){
			String toastMessage = "Fel användarnamn eller lösenord!";
			Toast.makeText(getActivity(), (CharSequence)toastMessage , Toast.LENGTH_LONG).show();
		}

		public void textFieldSetter(){
			userNameText = (EditText) rootView.findViewById(R.id.userIdInput);
			passwordText = (EditText) rootView.findViewById(R.id.userPassword);
		}

		public boolean checkIfCorrect(){
			Boolean isCorrect = false;
			LoginData userData = new LoginData(userNameText.getText().toString(),passwordText.getText().toString());
			LoginDBHandler ldh = new LoginDBHandler(getActivity());
			LoginData tempLogin = new LoginData("polisen", "aina");
			ldh.makeTempLogin(tempLogin);
			isCorrect = ldh.loginAuthenticity(userData);
			return isCorrect;
		}
	}
}

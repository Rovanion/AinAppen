package gov.polisen.ainappen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginAcitivity extends Activity {

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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_login_acitivity,
					container, false);
			setupLoginToDatabaseButtonListener();
			setupQuickLoginButtonListener();
			return rootView;
		}

		public void checkLogin(View view){
			Intent intent = new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
		}

		public void setupQuickLoginButtonListener(){
			quickLogButton = (Button) rootView.findViewById(R.id.quickLoginButton);
			quickLogButton.setOnClickListener( new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					checkLogin(v);
				}
			});
		}

		public void setupLoginToDatabaseButtonListener(){
			databaseLoginButton = (Button) rootView.findViewById(R.id.database_login_button);
			databaseLoginButton.setOnClickListener( new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					textFieldSetter();
					boolean access = checkIfCorrect();
					if(access){
						checkLogin(v);
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
			LoginData userData = new LoginData(userNameText.toString(),passwordText.toString());
			LoginDBHandler ldh = new LoginDBHandler();
			isCorrect = ldh.loginAuthenticity(userData, getActivity());
			return isCorrect;
		}
	}
}

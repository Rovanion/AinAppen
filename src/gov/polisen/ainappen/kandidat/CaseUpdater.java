package gov.polisen.ainappen.kandidat;

import java.util.TimerTask;

public class CaseUpdater extends TimerTask {
	@Override
	public void run() {
		EnergySavingPolicy policy = EnergySavingPolicy.getPolicy();
		policy.getAlgorithm().uploadNewCase(policy.getDummyCase());
	}
}

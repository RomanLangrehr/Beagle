package de.uka.ipd.sdq.beagle.judge;

import de.uka.ipd.sdq.beagle.core.Blackboard;
import de.uka.ipd.sdq.beagle.core.ResourceDemandingInternalAction;
import de.uka.ipd.sdq.beagle.core.SEFFBranch;
import de.uka.ipd.sdq.beagle.core.SEFFLoop;

import java.util.Collection;

/*
 * ATTENTION: Checkstyle turned off! remove this comment block when implementing this
 * class! CHECKSTYLE:OFF TODO
 */

/**
 * This view of the Blackboard is designed to be used by
 * {@link de.uka.ipd.sdq.beagle.judge.FinalJudge}, therefore allowing remeasuring of
 * {@link ResourceDemandingInternalAction}s, {@link SEFFBranch}es, and {@link SEFFLoop}s.
 * 
 * @author Christoph Michelbach
 */
public class FinalJudgeBlackboardView {

	/**
	 * Creates a new FinalJudgeBlackboardView for {@param blackboard}.
	 *
	 * @param blackboard The {@link Blackboard} to be accessed through the
	 *            FinalJudgeBlackboardView.
	 */
	public FinalJudgeBlackboardView(Blackboard blackboard) {
		// TODO: Implement method
	}

	/**
	 * Returns the {@link ResourceDemandingInternalAction}s to be measured.
	 * 
	 * @return the {@link ResourceDemandingInternalAction}s to be measured
	 */
	public Collection<ResourceDemandingInternalAction> getResourceDemandingInternalActionsToBeMeasured() {
		return null; // TODO Implement method
	}

	/**
	 * Returns the {@link SEFFBranch}es to be measured.
	 * 
	 * @return the {@link SEFFBranch}es to be measured
	 */
	public Collection<SEFFBranch> getSeffBranchesToBeMeasured() {
		return null; // TODO Implement method
	}

	/**
	 * Returns the {@link SEFFLoop}s to be measured.
	 * 
	 * @return the {@link SEFFLoop}s to be measured
	 */
	public Collection<SEFFLoop> getSeffLoopsToBeMeasured() {
		return null; // TODO Implement method
	}

	/**
	 * Sets the final {@link ResourceDemandingInternalAction}s on the {@link Blackboard}.
	 *
	 * @param finalRdias A collection of the final {@link ResourceDemandingInternalAction}
	 *            s to be set on the {@link Blackboard}
	 */
	public void setFinalResourceDemandingInternalAction(Collection<ResourceDemandingInternalAction> finalRdias) {
		// TODO Implement method
	}

	/**
	 * Sets the final {@link SEFFBranch}es on the {@link Blackboard}.
	 *
	 * @param finalRdias A collection of the final {@link SEFFBranch}es to be set on the
	 *            {@link Blackboard}
	 */
	public void setFinalSeffBranches(Collection<SEFFBranch> finalSeffBranches) {
		// TODO Implement method
	}

	/**
	 * Sets the final {@link SEFFLoop}s on the {@link Blackboard}.
	 *
	 * @param finalRdias A collection of the final {@link SEFFLoop}s to be set on the
	 *            {@link Blackboard}
	 */
	public void setFinalSeffLoops(Collection<SEFFLoop> finalSeffLoops) {
		// TODO Implement method
	}

	/**
	 * Remeasures the {@link ResourceDemandingInternalAction}s in {@code remeasure}.
	 *
	 * @param remeasure The {@link ResourceDemandingInternalAction}s to be remeasured.
	 */
	public void remeasureResourceDemandingInternalActions(Collection<ResourceDemandingInternalAction> remeasure) {
		// TODO Implement method
	}

	/**
	 * Remeasures the {@link SEFFBranch}es in {@code remeasure}.
	 *
	 * @param remeasure The {@link SEFFBranch}es to be remeasured.
	 */
	public void remeasureSeffBranches(Collection<SEFFBranch> remeasure) {
		// TODO Implement method
	}

	/**
	 * Remeasures the {@link SEFFLoop}s in {@code remeasure}.
	 *
	 * @param remeasure The {@link SEFFLoop}s to be remeasured.
	 */
	public void remeasureSeffLoops(Collection<SEFFLoop> remeasure) {
		// TODO Implement method
	}

}

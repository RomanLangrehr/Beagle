package de.uka.ipd.sdq.beagle.core.measurement;
/**
 * ATTENTION: Test coverage check turned off. Remove this comments block when implementing
 * this class!
 * 
 * <p>COVERAGE:OFF
 */

import de.uka.ipd.sdq.beagle.core.AnalysisController;
import de.uka.ipd.sdq.beagle.core.Blackboard;
import de.uka.ipd.sdq.beagle.core.CodeSection;
import de.uka.ipd.sdq.beagle.core.ResourceDemandingInternalAction;
import de.uka.ipd.sdq.beagle.core.SeffBranch;
import de.uka.ipd.sdq.beagle.core.SeffLoop;
import de.uka.ipd.sdq.beagle.core.measurement.order.LaunchConfiguration;
import de.uka.ipd.sdq.beagle.core.measurement.order.MeasurementOrder;
import de.uka.ipd.sdq.beagle.core.measurement.order.ParameterCharacteriser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controls which measurement tool is working.
 *
 * <p>There is always at most one measurement tool working.
 *
 * @author Roman Langrehr
 * @author Christoph Michelbach
 * @see AnalysisController
 */
public class MeasurementController {

	/**
	 * All {@link MeasurementTool}s this {@link MeasurementController} knows.
	 */
	private Set<MeasurementTool> measurementTools;

	/**
	 * Constructs a new {@link MeasurementController}.
	 *
	 * @param measurementTools The {@link MeasurementTool}s to use.
	 */
	public MeasurementController(final Set<MeasurementTool> measurementTools) {
		this.measurementTools = measurementTools;
	}

	/**
	 * Determines whether a {@link MeasurementTool} can contribute to the
	 * {@link Blackboard}.
	 *
	 * @param blackboard The blackboard.
	 * @return Whether a {@link MeasurementTool} can measure something which is marked as
	 *         'to be measured'. When {@code true} is returned, this is no guarantee that
	 *         at least one new measurement result will be added.
	 */
	public boolean canMeasure(final ReadOnlyMeasurementControllerBlackboardView blackboard) {
		for (MeasurementTool measurementTool : this.measurementTools) {
			if (measurementTool.canMeasure(blackboard)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Instructs all available {@link MeasurementTool MeasurementTools} to measure all
	 * items marked as “to be measured”. {@link MeasurementTool MeasurementTools} may not
	 * produce results for every item but will report results for all items they were able
	 * to measure.
	 *
	 * <p>This method may only be called, when {@link #canMeasure} returned {@code true}
	 * before and the {@link Blackboard} wasn't changed between this call. Otherwise the
	 * behaviour of this method is undefined.
	 *
	 * @param blackboard The blackboard.
	 */
	public void measure(final MeasurementControllerBlackboardView blackboard) {
		// Read from the blackboard.
		final Set<SeffBranch> seffBranches = blackboard.getSeffBranchesToBeMeasured();
		final Set<SeffLoop> seffLoops = blackboard.getSeffLoopsToBeMeasured();
		final Set<ResourceDemandingInternalAction> rdias = blackboard.getRdiasToBeMeasured();

		// Initialise everything needed to create a measurement order.
		final Set<CodeSection> parameterValueSection = new HashSet<CodeSection>();
		final Set<CodeSection> resourceDemandSections = new HashSet<CodeSection>();
		final Set<CodeSection> executionSections = new HashSet<CodeSection>();
		final Set<LaunchConfiguration> launchConfigurations = new HashSet<LaunchConfiguration>();
		final ParameterCharacteriser parameterCharacteriser = new ParameterCharacteriser();

		// Fill {@code executionSections}.
		for (SeffBranch seffBranch : seffBranches) {
			final List<CodeSection> codeSections = seffBranch.getBranches();

			for (CodeSection codeSection : codeSections) {
				executionSections.add(codeSection);
			}
		}

		// Fill {@code parameterValueSection}.
		for (SeffLoop seffLoop : seffLoops) {
			final CodeSection codeSection = seffLoop.getLoopBody();
			parameterValueSection.add(codeSection);
		}

		// Fill {@code resourceDemandSections}.
		for (ResourceDemandingInternalAction rdia : rdias) {
			final CodeSection codeSection = rdia.getAction();
			resourceDemandSections.add(codeSection);
		}

		// Give every measurement tool a measurement order.
		for (MeasurementTool measurementTool : this.measurementTools) {
			final MeasurementOrder measurementOrder = new MeasurementOrder(parameterValueSection,
				resourceDemandSections, executionSections, launchConfigurations, parameterCharacteriser);

			measurementTool.measure(measurementOrder);
		}
	}
}

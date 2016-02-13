package de.uka.ipd.sdq.beagle.core.facade;

import de.uka.ipd.sdq.beagle.core.AnalysisController;
import de.uka.ipd.sdq.beagle.core.BlackboardFactory;
import de.uka.ipd.sdq.beagle.core.FailureHandler;
import de.uka.ipd.sdq.beagle.core.FailureReport;
import de.uka.ipd.sdq.beagle.core.pcmconnection.PcmRepositoryBlackboardFactoryAdder;

import org.palladiosimulator.pcm.core.entity.Entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls the execution of the Beagle Analysis. {@code BeagleController} can start,
 * pause, continue, and abort an Analysis.
 *
 * @author Christoph Michelbach
 * @author Roman Langrehr
 */
public class BeagleController {

	/**
	 * The analysis controller used for this project.
	 */
	private AnalysisController analysisController;

	/**
	 * Constructs a new {@code BeagleController} with the given
	 * {@link BeagleConfiguration}.
	 *
	 * @param beagleConfiguration The {@link BeagleConfiguration} this BeagleController
	 *            has permanently. It cannot be changed.
	 */
	public BeagleController(final BeagleConfiguration beagleConfiguration) {
		final BlackboardFactory blackboardFactory = new BlackboardFactory();
		if (beagleConfiguration.getElements() == null) {
			try {
				new PcmRepositoryBlackboardFactoryAdder(beagleConfiguration.getRepositoryFile())
					.getBlackboardForAllElements(blackboardFactory);
			} catch (final FileNotFoundException fileNotFoundException) {
				FailureHandler.getHandler(this.getClass()).handle(new FailureReport<>().cause(fileNotFoundException));
			}
		} else {
			try {
				new PcmRepositoryBlackboardFactoryAdder(beagleConfiguration.getRepositoryFile())
					.getBlackboardForIds(this.entitysToStrings(beagleConfiguration.getElements()), blackboardFactory);
			} catch (final FileNotFoundException fileNotFoundException) {
				FailureHandler.getHandler(this.getClass()).handle(new FailureReport<>().cause(fileNotFoundException));
			}
		}
		this.analysisController = new AnalysisController(blackboardFactory.createBlackboard());
	}

	/**
	 * Starts the analysis. This method can only be used once per {@link BeagleController}
	 * . Further calls will be ignored.
	 *
	 */
	public void startAnalysis() {
		this.analysisController.performAnalysis();
	}

	/**
	 * Pauses the analysis. If the analysis is already paused, calls to this method are
	 * ignored.
	 *
	 */
	public void pauseAnalysis() {

	}

	/**
	 * Continues the analysis if it is paused. If it's running, calls to this method are
	 * ignored.
	 *
	 */
	public void continueAnalysis() {

	}

	/**
	 * Aborts the analysis. If it already is aborted, calls to this method are ignored.
	 * Aborting the analysis is also possible if it wasn't started yet. In this case it
	 * will never be possible to start it.
	 *
	 */
	public void abortAnalysis() {

	}

	/**
	 * Converts {@linkplain Entity Entities} to {@linkplain String Strings}.
	 *
	 * @param entities The {@linkplain Entity Entities} to convert.
	 * @return The ids of the {@code entities}.
	 */
	private List<String> entitysToStrings(final List<Entity> entities) {
		final List<String> strings = new ArrayList<>();
		for (final Entity entity : entities) {
			strings.add(entity.getId());
		}
		return strings;
	}
}

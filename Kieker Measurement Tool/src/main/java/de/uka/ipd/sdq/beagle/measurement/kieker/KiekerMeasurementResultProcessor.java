package de.uka.ipd.sdq.beagle.measurement.kieker;

import de.uka.ipd.sdq.beagle.core.CodeSection;
import de.uka.ipd.sdq.beagle.core.ResourceDemandType;
import de.uka.ipd.sdq.beagle.core.failurehandling.FailureHandler;
import de.uka.ipd.sdq.beagle.core.failurehandling.FailureReport;
import de.uka.ipd.sdq.beagle.core.measurement.order.MeasurementEvent;
import de.uka.ipd.sdq.beagle.core.measurement.order.ResourceDemandCapturedEvent;

import kieker.analysis.AnalysisController;
import kieker.analysis.IProjectContext;
import kieker.analysis.exception.AnalysisConfigurationException;
import kieker.analysis.plugin.annotation.InputPort;
import kieker.analysis.plugin.filter.AbstractFilterPlugin;
import kieker.analysis.plugin.reader.filesystem.FSReader;
import kieker.common.configuration.Configuration;
import kieker.common.record.controlflow.OperationExecutionRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Processes the results generated by Kieker and transforms them into corresponding
 * {@linkplain MeasurementEvent measurement events}. To do so, it needs one or multible
 * {@link CodeSectionIdentifier CodeSectionIdentifiers}. No events will be created for
 * code sections that cannot be identified.
 *
 * @author Joshua Gleitze
 */
public class KiekerMeasurementResultProcessor {

	/**
	 * Handler of Failures.
	 */
	private static final FailureHandler FAILURE_HANDLER = FailureHandler.getHandler("Kieker Result Processor");

	/**
	 * The folder Kieker wrote its results to.
	 */
	private final Path kiekerOutputFolder;

	/**
	 * The identifier of resource demand code sections.
	 */
	private CodeSectionIdentifier resourceDemandIdentifier;

	/**
	 * Creates a processor that will process the results generated by Kieker from the
	 * provided {@code kiekerOutputFolder}.
	 *
	 * @param kiekerOutputFolder The folder Kieker wrote its results to.
	 */
	public KiekerMeasurementResultProcessor(final Path kiekerOutputFolder) {
		this.kiekerOutputFolder = kiekerOutputFolder;
	}

	/**
	 * Makes this processor use the given {@code identifier} to identify code sections
	 * resource demands were to be measured for.
	 *
	 * @param identifier The identifier of resource demand code sections.
	 * @return {@code this}.
	 */
	public KiekerMeasurementResultProcessor useResourceDemandIdentifier(final CodeSectionIdentifier identifier) {
		this.resourceDemandIdentifier = identifier;
		return this;
	}

	/**
	 * Processes Kieker’s results and generates the matching measurement events.
	 *
	 * @return The measurement events representing Kieker’s results.
	 * @throws IOException If reading from Kieker’s result folder fails.
	 */
	public List<MeasurementEvent> process() throws IOException {
		final List<MeasurementEvent> resultEvents = new LinkedList<>();

		// in the moment, we’re only looking at resource demand results. If we can’t
		// identify those, we’ve got nothing to do.
		if (this.resourceDemandIdentifier == null) {
			return new ArrayList<>();
		}

		for (final File resultFolder : this.kiekerOutputFolder.toFile().listFiles()) {
			resultEvents.addAll(this.processResultFolder(resultFolder.toPath()));
		}

		return resultEvents;
	}

	/**
	 * Processes Kieker’s results for a specific result folder generated by Kieker.
	 *
	 * @param resultFolder The specific result folder Kieker produced that shall be
	 *            examined.
	 * @return The measurement events representing Kieker’s results for the specific
	 *         {@code resultFolder}.
	 */
	private List<MeasurementEvent> processResultFolder(final Path resultFolder) {
		final AnalysisController kiekerAnalysisController = new AnalysisController();

		final Configuration readerConfiguration = new Configuration();
		readerConfiguration.setProperty(FSReader.CONFIG_PROPERTY_NAME_INPUTDIRS, resultFolder.toString());
		final FSReader reader = new FSReader(readerConfiguration, kiekerAnalysisController);

		final KiekerProbeToMeasurementResultFilter resultFilter =
			new KiekerProbeToMeasurementResultFilter(new Configuration(), kiekerAnalysisController);
		try {
			kiekerAnalysisController.connect(reader, FSReader.OUTPUT_PORT_NAME_RECORDS, resultFilter,
				KiekerProbeToMeasurementResultFilter.INPUT_PORT_NAME);
			kiekerAnalysisController.run();
		} catch (final AnalysisConfigurationException analysisError) {
			final FailureReport<List<MeasurementEvent>> failure =
				new FailureReport<List<MeasurementEvent>>().cause(analysisError)
					.continueWith(() -> new ArrayList<>())
					.retryWith(() -> this.processResultFolder(resultFolder));
			return FAILURE_HANDLER.handle(failure);
		}

		return resultFilter.eventList;
	}

	/**
	 * Scans Kieker’s results and creates measurement results for them.
	 *
	 * @author Joshua Gleitze
	 */
	private final class KiekerProbeToMeasurementResultFilter extends AbstractFilterPlugin {

		/**
		 * Name of this filter’s input port, at which it receives probes.
		 */
		private static final String INPUT_PORT_NAME = "Probe Receiver Input Port";

		/**
		 * The events that were read from the results.
		 */
		private final List<MeasurementEvent> eventList = new LinkedList<>();

		/**
		 * Creates this filter. Does nothing.
		 *
		 * @param configuration A configuration that will not be used.
		 * @param projectContext A projectContext that will not be used.
		 */
		private KiekerProbeToMeasurementResultFilter(final Configuration configuration,
			final IProjectContext projectContext) {
			super(configuration, projectContext);
			// this class cannot be configured
		}

		@Override
		public Configuration getCurrentConfiguration() {
			// this class cannot be configured
			return new Configuration();
		}

		/**
		 * Receives all {@link OperationExecutionRecord}s created while measuring.
		 *
		 * @param record One recorded record.
		 */
		@InputPort(name = INPUT_PORT_NAME, eventTypes = OperationExecutionRecord.class)
		public void receive(final OperationExecutionRecord record) {
			final CodeSection recordedSection =
				KiekerMeasurementResultProcessor.this.resourceDemandIdentifier.getSectionFor((int) record.getTraceId());

			if (recordedSection != null) {
				final long duration = record.getTout() - record.getTin();
				final MeasurementEvent event =
					new ResourceDemandCapturedEvent(recordedSection, ResourceDemandType.RESOURCE_TYPE_CPU_NS, duration);
				this.eventList.add(event);
			}
		}
	}
}
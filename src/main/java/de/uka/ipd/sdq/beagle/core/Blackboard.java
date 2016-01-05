package de.uka.ipd.sdq.beagle.core;

import de.uka.ipd.sdq.beagle.core.expressions.EvaluableExpression;
import de.uka.ipd.sdq.beagle.measurement.BranchDecisionMeasurementResult;
import de.uka.ipd.sdq.beagle.measurement.LoopRepetitionCountMeasurementResult;
import de.uka.ipd.sdq.beagle.measurement.ResourceDemandMeasurementResult;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Central and only storage of all knowledge gained by Beagle. Implements, together with
 * {@link BeagleController}, the Blackboard pattern from POSA I. The Blackboard’s
 * vocabularies are: {@link ResourceDemandingInternalAction}, {@link SEFFBranch},
 * {@link SEFFLoop}, {@link ResourceDemandMeasurementResult},
 * {@link BranchDecisionMeasurementResult} , {@link LoopRepetitionCountMeasurementResult}
 * and {@link EvaluableExpression}. It further allows classes to store custom data.
 *
 * <p>The Blackboard is typically not accessed directly by its using classes, but through
 * <em>blackboard views</em> (recognisable by having the {@code BlackboardView} suffix).
 * These are surrogates for the blackboard. They don’t modify its contents but only
 * restrict access to it.
 *
 * @author Joshua Gleitze
 * @author Roman Langrehr
 * @see BeagleController
 */
public class Blackboard implements Serializable {

	/**
	 * Serialisation version UID, see {@link java.io.Serializable}.
	 */
	private static final long serialVersionUID = 6382577321150787599L;

	/**
	 * All {@linkplain SEFFLoop SEFF loops} known to Beagle.
	 *
	 * @return all {@linkplain SEFFLoop SEFF loops} known to Beagle. Changes to the
	 *         returned set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<SEFFLoop> getAllSEFFLoops() {
		return null;
	}

	/**
	 * All {@linkplain SEFFBranch SEFF branches} known to Beagle.
	 *
	 * @return all {@linkplain SEFFBranch SEFF branches} known to Beagle. Changes to the
	 *         returned set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<SEFFBranch> getAllSEFFBranches() {
		return null;
	}

	/**
	 * All {@linkplain ResourceDemandingInternalAction resource demanding internal
	 * actions} known to Beagle.
	 *
	 * @return all {@linkplain ResourceDemandingInternalAction resource demanding internal
	 *         actions} known to Beagle. Changes to the returned set will not modify the
	 *         blackboard content. Is never {@code null}.
	 */
	public Set<ResourceDemandingInternalAction> getAllRDIAs() {
		return null;
	}

	/**
	 * Reports that {@code rdias} shall be measured for its resource demands.
	 *
	 * @param rdias Resource demanding internal actions that shall be measured. Must not
	 *            be {@code null} and must be known to this blackboard.
	 * @see #remeasureRDIAs(Collection)
	 */
	public void remeasureRDIAs(final ResourceDemandingInternalAction... rdias) {
	}

	/**
	 * Reports that {@code rdias} shall be measured for its resource demands.
	 *
	 * @param rdias Resource demanding internal actions that shall be measured. Must not
	 *            be {@code null} and must be known to this blackboard.
	 * @see #remeasureRDIAs(ResourceDemandingInternalAction...)
	 */
	public void remeasureRDIAs(final Collection<ResourceDemandingInternalAction> rdias) {
	}

	/**
	 * Reports that {@code branches} shall be measured for its branch decisions.
	 *
	 * @param branches SEFF branches that shall be measured. Must not be {@code null} and
	 *            must be known to this blackboard.
	 * @see #remeasureSEFFBranches(Collection)
	 */
	public void remeasureSEFFBranches(final SEFFBranch... branches) {
	}

	/**
	 * Reports that {@code branches} shall be measured for its branch decisions.
	 *
	 * @param branches SEFF branches that shall be measured. Must not be {@code null} and
	 *            must be known to this blackboard.
	 * @see #remeasureSEFFBranches(SEFFBranch...)
	 */
	public void remeasureSEFFBranches(final Collection<SEFFBranch> branches) {
	}

	/**
	 * Reports that {@code loops} shall be measured for its repetitions.
	 *
	 * @param loops SEFF Loops that shall be measured. Must not be {@code null} and must
	 *            be known to this blackboard.
	 * @see #remeasureSEFFLoops(Collection)
	 */
	public void remeasureSEFFLoops(final SEFFLoop... loops) {
	}

	/**
	 * Reports that {@code loops} shall be measured for its repetitions.
	 *
	 * @param loops SEFF Loops that shall be measured. Must not be {@code null} and must
	 *            be known to this blackboard.
	 * @see #remeasureSEFFLoops(SEFFLoop...)
	 */
	public void remeasureSEFFLoops(final Collection<SEFFLoop> loops) {
	}

	/**
	 * {@linkplain SEFFLoop SEFF loops} that shall be measured for their repetitions.
	 *
	 * @return All {@linkplain SEFFLoop SEFF loops} to be measured. Changes to the
	 *         returned set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<SEFFLoop> getSEFFLoopsToBeMeasured() {
		return null;
	}

	/**
	 * {@linkplain SEFFBranch SEFF branches} that shall be measured for their branch
	 * decisions.
	 *
	 * @return All {@linkplain SEFFBranch SEFF branches} to be measured. Changes to the
	 *         returned set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<SEFFBranch> getSEFFBranchesToBeMeasured() {
		return null;
	}

	/**
	 * {@linkplain ResourceDemandingInternalAction RDIAs} that shall be measured for their
	 * resource demands.
	 *
	 * @return All {@linkplain ResourceDemandingInternalAction resource demanding internal
	 *         actions} to be measured. Changes to the returned set will not modify the
	 *         blackboard content. Is never {@code null}.
	 */
	public Set<ResourceDemandingInternalAction> getRDIAsToBeMeasured() {
		return null;
	}

	/**
	 * Writes data for {@code writer} to the blackboard. This method serves as a type safe
	 * mean for tools to store data that is not part of their results. Values stored here
	 * will never contribute to Beagle’s results. Any class calling this method should
	 * always pass its own {@linkplain Class} instance as {@code writer}. {@code writer}
	 * has to implement {@link BlackboardStorer} and thereby declare the type of values
	 * they write. Calling this method will override any data potentially stored
	 * previously for the given {@code writer}.
	 *
	 * @param writer The class the data should be written for. Must not be {@code null}.
	 * @param written The data to write.
	 * @param <WRITTEN_TYPE> {@code written}’s type.
	 */
	public <WRITTEN_TYPE extends Serializable> void writeFor(
		final Class<? extends BlackboardStorer<WRITTEN_TYPE>> writer, final WRITTEN_TYPE written) {
	}

	/**
	 * Reads data previously written for {@code writer} through
	 * {@link #writeFor(Class, Serializable)}.
	 *
	 * @param writer The class the desired data was written for. Must not be {@code null}.
	 * @param <WRITTEN_TYPE> The type of the data to be read.
	 * @return The data written in the last call to {@linkplain #writeFor} for
	 *         {@code writer}. {@code null} if no data has been written for {@code writer}
	 *         yet.
	 * @see #writeFor(Class, Serializable)
	 */
	public <WRITTEN_TYPE extends Serializable> WRITTEN_TYPE readFor(
		final Class<? extends BlackboardStorer<WRITTEN_TYPE>> writer) {
		return null;
	}

	/**
	 * Adds a measurement result for the provided {@code rdia}.
	 *
	 * @param rdia A resource demanding internal action that was measured. Must not be
	 *            {@code null} .
	 * @param result The result of that measurement. Must not be {@code null}.
	 */
	public void reportMeasurementResultForRDIA(final ResourceDemandingInternalAction rdia,
		final ResourceDemandMeasurementResult result) {
	}

	/**
	 * Adds measurement results for {@linkplain ResourceDemandingInternalAction
	 * ResourceDemandingInternalActions}.
	 *
	 * @param results The result of some measurements associated with it's Resource
	 *            Demanding Internal Action. Must not be {@code null} and no key or value
	 *            of this map may be {@code null}. Not each Resource Demanding Internal
	 *            Action of this blackboard needs to be a key in this map.
	 */
	public void reportMeasurementResultsForRDIAs(
		final Map<ResourceDemandingInternalAction, Set<ResourceDemandMeasurementResult>> results) {
	}

	/**
	 * Adds a measurement result for the provided {@code loop}.
	 *
	 * @param loop A SEFF Loop was measured. Must not be {@code null}.
	 * @param result The result of that measurement. Must not be {@code null}.
	 */
	public void reportMeasurementResultForSEFFLoop(final SEFFLoop loop,
		final LoopRepetitionCountMeasurementResult result) {
	}

	/**
	 * Adds measurement results for {@linkplain SEFFLoop SEFFLoops}.
	 *
	 * @param results The result of some measurements associated with it's SEFF Loops.
	 *            Must not be {@code null} and no key or value of this map may be
	 *            {@code null}. Not each Resource Demanding Internal Action of this
	 *            blackboard needs to be a key in this map.
	 */
	public void reportMeasurementResultsForSEFFLoops(
		final Map<ResourceDemandingInternalAction, Set<LoopRepetitionCountMeasurementResult>> results) {
	}

	/**
	 * Adds a measurement result for the provided {@code branch}.
	 *
	 * @param branch A SEFF Branch that was measured. Must not be {@code null}.
	 * @param result The result of that measurement. Must not be {@code null}.
	 */
	public void reportMeasurementResultForSEFFBranch(final SEFFBranch branch,
		final BranchDecisionMeasurementResult result) {
	}

	/**
	 * Adds measurement results for {@linkplain SEFFBranch SEFFBranches}.
	 *
	 * @param results The result of some measurements associated with it's SEFF Branches.
	 *            Must not be {@code null} and no key or value of this map may be
	 *            {@code null}. Not each Resource Demanding Internal Action of this
	 *            blackboard needs to be a key in this map.
	 */
	public void reportMeasurementResultsForSEFFBranches(
		final Map<ResourceDemandingInternalAction, Set<BranchDecisionMeasurementResult>> results) {
	}

	/**
	 * Gets all results yet measured for the loop repetitions of {@code loop}.
	 *
	 * @param loop A SEFF Loop to get the measurement results of. Must not be {@code null}
	 *            .
	 * @return All measurement results reported for {@code loop}. Changes to the returned
	 *         set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<ResourceDemandMeasurementResult> getMeasurementResultsFor(final SEFFLoop loop) {
		return null;
	}

	/**
	 * Gets all results yet measured for branch decisions of {@code branch}.
	 *
	 * @param branch A SEFF Branch to get the measurement results of. Must not be
	 *            {@code null}.
	 * @return All measurement results reported for {@code branch}. Changes to the
	 *         returned set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<BranchDecisionMeasurementResult> getMeasurementResultsFor(final SEFFBranch branch) {
		return null;
	}

	/**
	 * Gets all results yet measured for the resource demands of {@code rdia}.
	 *
	 * @param rdia An resource demanding internal action to get the measurement results
	 *            of. Must not be {@code null}.
	 * @return All measurement results reported for {@code rdia}. Changes to the returned
	 *         set will not modify the blackboard content. Is never {@code null}.
	 */
	public Set<ResourceDemandMeasurementResult> getMeasurementResultsFor(final ResourceDemandingInternalAction rdia) {
		return null;
	}

	/**
	 * Adds {@code expression} as a proposal to describe {@code rdia}’s measurement
	 * results’ parametric dependencies.
	 *
	 * @param rdia A resource demanding internal action. Must not be {@code null}.
	 * @param expression An evaluable expression proposed to describe {@code rdia}’s
	 *            measurement results. Must not be {@code null}.
	 */
	public void proposeExpressionForRDIA(final ResourceDemandingInternalAction rdia,
		final EvaluableExpression expression) {
	}

	/**
	 * Adds {@code expression} as a proposal to describe {@code branch}’s measurement
	 * results’ parametric dependencies.
	 *
	 * @param branch A SEFF Branch. Must not be {@code null}.
	 * @param expression An evaluable expression proposed to describe {@code branch}’s
	 *            measurement results. Must not be {@code null}.
	 */
	public void proposeExpressionForSEFFBranch(final SEFFBranch branch, final EvaluableExpression expression) {
	}

	/**
	 * Adds {@code expression} as a proposal to describe {@code loop}’s measurement
	 * results’ parametric dependencies.
	 *
	 * @param loop A SEFF Loop. Must not be {@code null}.
	 * @param expression An evaluable expression proposed to describe {@code loop}’s
	 *            measurement results. Must not be {@code null}.
	 */
	public void proposeExpressionForSEFFLoop(final SEFFLoop loop, final EvaluableExpression expression) {
	}

	/**
	 * Adds {@code expressions} as a proposal to describe {@code rdia}’s measurement
	 * results’ parametric dependencies.
	 *
	 * @param expressions A map which lists for each Resource Demanding Internal Action
	 *            some evaluable expression which are proposed to describe the resource
	 *            demanding internal action's measurement results. Must not be
	 *            {@code null} and no key or value of this map may be {@code null}. Not
	 *            each Resource Demanding Internal Action of this blackboard needs to be a
	 *            key in this map.
	 */
	public void proposeExpressionsForRDIAs(
		final Map<ResourceDemandingInternalAction, Set<EvaluableExpression>> expressions) {
	}

	/**
	 * Adds {@linkplain EvaluableExpression EvaluableExpressions} as a proposal to
	 * describe {@link SEFFBranch}’s measurement results’ parametric dependencies.
	 *
	 * @param expressions A map which lists for each SEFFBranch some evaluable expression
	 *            which are proposed to describe the SEFF Branch's measurement results.
	 *            Must not be {@code null} and no key or value of this map may be
	 *            {@code null}. Not each SEFFBranch of this blackboard needs to be a key
	 *            in this map.
	 */
	public void proposeExpressionsForSEFFBranches(final Map<SEFFBranch, Set<EvaluableExpression>> expressions) {
	}

	/**
	 * Adds {@linkplain EvaluableExpression EvaluableExpressions} as a proposal to
	 * describe {@link SEFFLoop}’s measurement results’ parametric dependencies.
	 *
	 * @param expressions A map which lists for each SEFF Loop some evaluable expression
	 *            which are proposed to describe the SEFF Loop's measurement results. Must
	 *            not be {@code null} and no key or value of this map may be {@code null}.
	 *            Not each SEFFLoop of this blackboard needs to be a key in this map.
	 */
	public void proposeExpressionsForSEFFLoops(final Map<SEFFLoop, Set<EvaluableExpression>> expressions) {
	}

	/**
	 * Sets {@code expression} as the final description of {@code rdia}’s measurement
	 * results’ parametric dependencies. Consecutive calls to this method with the same
	 * {@code rdia} will override previous settings.
	 *
	 * @param rdia A resource demanding internal action. Must not be {@code null}.
	 * @param expression An evaluable expression describing {@code rdia}’s measurement
	 *            results. May be {@code null} to describe that no suitable expression was
	 *            found.
	 */
	public void setFinalExpressionForRDIA(final ResourceDemandingInternalAction rdia,
		final EvaluableExpression expression) {
	}

	/**
	 * Sets {@code expression} as the final description of {@code loop}’s measurement
	 * results’ parametric dependencies. Consecutive calls to this method with the same
	 * {@code loop} will override previous settings.
	 *
	 * @param loop A SEFF Loop. Must not be {@code null}.
	 * @param expression An evaluable expression describing {@code loop}’s measurement
	 *            results. May be {@code null} to describe that no suitable expression was
	 *            found.
	 */
	public void setFinalExpressionForSEFFLoop(final SEFFLoop loop, final EvaluableExpression expression) {
	}

	/**
	 * Sets {@code expression} as the final description of {@code branch}’s measurement
	 * results’ parametric dependencies. Consecutive calls to this method with the same
	 * {@code branch} will override previous settings.
	 *
	 * @param branch A SEFF Branch. Must not be {@code null}.
	 * @param expression An evaluable expression describing {@code branch}’s measurement
	 *            results. May be {@code null} to describe that no suitable expression was
	 *            found.
	 */
	public void setFinalExpressionForSEFFBranch(final SEFFBranch branch, final EvaluableExpression expression) {
	}

	/**
	 * Sets the final {@link EvaluableExpression} of some
	 * {@link ResourceDemandingInternalAction}s’ measurement results’ parametric
	 * dependencies. Consecutive calls to this method will override previous settings for
	 * each Resource Demanding Internal Action.
	 *
	 * @param expressions An evaluable expression for some Resource Demanding Internal
	 *            Actions describing their measurement results. The evaluable expressions
	 *            may be {@code null} to describe that no suitable expression was found.
	 *            Must not be {@code null} and no key of this map may be {@code null}. Not
	 *            each Resource Demanding Internal Action of this blackboard needs to be a
	 *            key in this map.
	 */
	public void setFinalExpressionsForRDIAs(
		final Map<ResourceDemandingInternalAction, EvaluableExpression> expressions) {
	}

	/**
	 * Sets the final {@link EvaluableExpression} of some {@link SEFFLoop}s’ measurement
	 * results’ parametric dependencies. Consecutive calls to this method will override
	 * previous settings for each Resource Demanding Internal Action.
	 *
	 * @param expressions An evaluable expression for some SEFF Loops describing their
	 *            measurement results. The evaluable expressions may be {@code null} to
	 *            describe that no suitable expression was found. Must not be {@code null}
	 *            and no key of this map may be {@code null}. Not each SEFFLoop of this
	 *            blackboard needs to be a key in this map.
	 */
	public void setFinalExpressionsForSEFFLoops(final Map<SEFFLoop, EvaluableExpression> expressions) {
	}

	/**
	 * Sets the final {@link EvaluableExpression} of some {@link SEFFBranch}s’ measurement
	 * results’ parametric dependencies. Consecutive calls to this method will override
	 * previous settings for each Resource Demanding Internal Action.
	 *
	 * @param expressions An evaluable expression for some SEFF Branches describing their
	 *            measurement results. The evaluable expressions may be {@code null} to
	 *            describe that no suitable expression was found. Must not be {@code null}
	 *            and no key of this map may be {@code null}. Not each SEFFLoop of this
	 *            blackboard needs to be a key in this map.
	 */
	public void setFinalExpressionsForSEFFBranches(final Map<SEFFBranch, EvaluableExpression> expressions) {
	}
}

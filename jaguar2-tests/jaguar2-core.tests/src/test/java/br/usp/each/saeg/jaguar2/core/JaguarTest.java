/**
 * Copyright (c) 2021, 2021 University of Sao Paulo and Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Roberto Araujo - initial API and implementation and/or initial documentation
 */
package br.usp.each.saeg.jaguar2.core;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.usp.each.saeg.jaguar2.api.Heuristic;
import br.usp.each.saeg.jaguar2.api.IBundleSpectrum;
import br.usp.each.saeg.jaguar2.spi.CoverageController;
import br.usp.each.saeg.jaguar2.spi.SpectrumExporter;

@RunWith(MockitoJUnitRunner.class)
public class JaguarTest {

    @Mock
    private CoverageController controllerMock;

    @Mock
    private SpectrumExporter exporterMock;

    @Mock
    private Heuristic heuristicMock;

    private Jaguar jaguar;

    @Before
    public void setUp() {
        jaguar = new Jaguar(controllerMock, exporterMock, heuristicMock);
    }

    @Test
    public void testRunStartedCallControllerInit() {
        // When
        jaguar.testRunStarted();

        // Then
        verify(controllerMock, times(1)).init();
    }

    @Test
    public void testStartedCallControllerDump() throws IOException {
        // When
        jaguar.testStarted("name");

        // Then
        verify(controllerMock, times(1)).dump(true);
    }

    @Test
    public void testStartedCallControllerResetWhenNoDump() throws IOException {
        // Given
        jaguar = new Jaguar(controllerMock, exporterMock, heuristicMock, true);

        // When
        jaguar.testStarted("name");

        // Then
        verify(controllerMock, times(1)).reset();
    }

    @Test
    public void testFinishSuccessCallControllerSaveWithFalse() {
        // When
        jaguar.testFinished("name", false);

        // Then
        verify(controllerMock, times(1)).save("name", false);
    }

    @Test
    public void testFinishFailCallControllerSaveWithTrue() {
        // When
        jaguar.testFinished("name", true);

        // Then
        verify(controllerMock, times(1)).save("name", true);
    }

    @Test
    public void testRunFinishedCallControllerAnalyze() throws Exception {
        // When
        jaguar.testRunFinished();

        // Then
        verify(controllerMock, times(1)).analyze();
    }

    @Test
    public void testRunFinishedCallExporterInit() throws Exception {
        // When
        jaguar.testRunFinished();

        // Then
        verify(exporterMock, times(1)).init();
    }

    @Test
    public void testRunFinishedCallExporterShutdown() throws Exception {
        // When
        jaguar.testRunFinished();

        // Then
        verify(exporterMock, times(1)).shutdown();
    }

    @Test
    public void testRunFinishedCallExporterWrite() throws Exception {
        // Given
        final IBundleSpectrum spectrum = mock(IBundleSpectrum.class);
        doReturn(spectrum).when(controllerMock).analyze();

        // When
        jaguar.testRunFinished();

        // Then
        verify(exporterMock, times(1)).write(same(spectrum), same(jaguar));
    }

    @Test
    public void testFailedPassedCounterInitialState() {
        Assert.assertEquals(0, jaguar.getFailedTests());
        Assert.assertEquals(0, jaguar.getPassedTests());
    }

    @Test
    public void testFinishSuccessIncrementsPassedTests() {
        // When
        jaguar.testFinished("name", false);

        // Then
        Assert.assertEquals(0, jaguar.getFailedTests());
        Assert.assertEquals(1, jaguar.getPassedTests());
    }

    @Test
    public void testFinishFailIncrementsFailedTests() {
        // When
        jaguar.testFinished("name", true);

        // Then
        Assert.assertEquals(1, jaguar.getFailedTests());
        Assert.assertEquals(0, jaguar.getPassedTests());
    }

    @Test
    public void testFinishSuccessSuccessIncrementsPassedTestsTwoTimes() {
        // When
        jaguar.testFinished("name", false);
        jaguar.testFinished("name", false);

        // Then
        Assert.assertEquals(0, jaguar.getFailedTests());
        Assert.assertEquals(2, jaguar.getPassedTests());
    }

    @Test
    public void testFinishFailFailIncrementsFailedTestsTwoTimes() {
        // When
        jaguar.testFinished("name", true);
        jaguar.testFinished("name", true);

        // Then
        Assert.assertEquals(2, jaguar.getFailedTests());
        Assert.assertEquals(0, jaguar.getPassedTests());
    }

    @Test
    public void testFinishSuccessFailIncrementsCountersCorrectly() {
        // When
        jaguar.testFinished("name", false);
        jaguar.testFinished("name", true);

        // Then
        Assert.assertEquals(1, jaguar.getFailedTests());
        Assert.assertEquals(1, jaguar.getPassedTests());
    }

    @Test
    public void testFinishFailSuccessIncrementsCountersCorrectly() {
        // When
        jaguar.testFinished("name", true);
        jaguar.testFinished("name", false);

        // Then
        Assert.assertEquals(1, jaguar.getFailedTests());
        Assert.assertEquals(1, jaguar.getPassedTests());
    }

}

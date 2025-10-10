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
package br.usp.each.saeg.jaguar2.junit;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.usp.each.saeg.jaguar2.core.Jaguar;

@RunWith(MockitoJUnitRunner.class)
public class JaguarJUnitRunListenerTest {

    @Mock
    private Jaguar jaguarMock;

    private JaguarJUnitRunListener listener;

    private InOrder inOrder;

    @Before
    public void setUp() {
        listener = new JaguarJUnitRunListener(jaguarMock);
        inOrder = inOrder(jaguarMock);
    }

    // ---

    @Test
    public void testSuccess() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                successTest("Class", "test1");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifySuccessTest("Class", "test1");
            }

        });
    }

    @Test
    public void testFail() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                failTest("Class", "test1");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyFailTest("Class", "test1");
            }

        });
    }

    @Test
    public void testAssumptionFailure() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                assumptionFailureTest("Class", "test1");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyAssumptionFailure("Class", "test1");
            }

        });
    }

    // ---

    @Test
    public void testSuccessAndSuccess() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                successTest("Class", "test1");
                successTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifySuccessTest("Class", "test1");
                verifySuccessTest("Class", "test2");
            }

        });
    }

    @Test
    public void testSuccessAndFail() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                successTest("Class", "test1");
                failTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifySuccessTest("Class", "test1");
                verifyFailTest("Class", "test2");
            }

        });
    }

    @Test
    public void testSuccessAndAssumptionFailure() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                successTest("Class", "test1");
                assumptionFailureTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifySuccessTest("Class", "test1");
                verifyAssumptionFailure("Class", "test2");
            }

        });
    }

    // ---

    @Test
    public void testFailAndSuccess() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                failTest("Class", "test1");
                successTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyFailTest("Class", "test1");
                verifySuccessTest("Class", "test2");
            }

        });
    }

    @Test
    public void testFailAndFail() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                failTest("Class", "test1");
                failTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyFailTest("Class", "test1");
                verifyFailTest("Class", "test2");
            }

        });
    }

    @Test
    public void testFailAndAssumptionFailure() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                failTest("Class", "test1");
                assumptionFailureTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyFailTest("Class", "test1");
                verifyAssumptionFailure("Class", "test2");
            }

        });
    }

    // ---

    @Test
    public void testAssumptionFailureAndSuccess() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                assumptionFailureTest("Class", "test1");
                successTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyAssumptionFailure("Class", "test1");
                verifySuccessTest("Class", "test2");
            }

        });
    }

    @Test
    public void testAssumptionFailureAndFail() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                assumptionFailureTest("Class", "test1");
                failTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyAssumptionFailure("Class", "test1");
                verifyFailTest("Class", "test2");
            }

        });
    }

    @Test
    public void testAssumptionFailureAndAssumptionFailure() throws Exception {
        // When
        runTests(new Runnable() {

            @Override
            public void run() {
                assumptionFailureTest("Class", "test1");
                assumptionFailureTest("Class", "test2");
            }

        });

        // Then
        verifyTests(new Runnable() {

            @Override
            public void run() {
                verifyAssumptionFailure("Class", "test1");
                verifyAssumptionFailure("Class", "test2");
            }

        });
    }

    // ---

    private void runTests(final Runnable runnable) throws Exception {
        listener.testRunStarted(mock(Description.class));
        runnable.run();
        listener.testRunFinished(mock(Result.class));
    }

    private void successTest(final String className, final String name) {
        try {
            final Description desc = Description.createTestDescription(className, name);
            listener.testStarted(desc);
            listener.testFinished(desc);
        } catch (final IOException e) {
            fail();
        }
    }

    private void failTest(final String className, final String name) {
        try {
            final Description desc = Description.createTestDescription(className, name);
            final Failure failure = mock(Failure.class);
            listener.testStarted(desc);
            listener.testFailure(failure);
            listener.testFinished(desc);
        } catch (final IOException e) {
            fail();
        }
    }

    private void assumptionFailureTest(final String className, final String name) {
        try {
            final Description desc = Description.createTestDescription(className, name);
            final Failure failure = mock(Failure.class);
            listener.testStarted(desc);
            listener.testAssumptionFailure(failure);
            listener.testFinished(desc);
        } catch (final IOException e) {
            fail();
        }
    }

    private void verifyTests(final Runnable runnable) throws Exception {
        inOrder.verify(jaguarMock).testRunStarted();
        runnable.run();
        inOrder.verify(jaguarMock).testRunFinished();
        inOrder.verifyNoMoreInteractions();
    }

    private void verifySuccessTest(final String className, final String name) {
        try {
            final String formattedName = String.format("%s(%s)", name, className);
            inOrder.verify(jaguarMock).testStarted(formattedName);
            inOrder.verify(jaguarMock).testFinished(formattedName, false);
        } catch (final IOException e) {
            fail();
        }
    }

    private void verifyFailTest(final String className, final String name) {
        try {
            final String formattedName = String.format("%s(%s)", name, className);
            inOrder.verify(jaguarMock).testStarted(formattedName);
            inOrder.verify(jaguarMock).testFinished(formattedName, true);
        } catch (final IOException e) {
            fail();
        }
    }

    private void verifyAssumptionFailure(final String className, final String name) {
        try {
            final String formattedName = String.format("%s(%s)", name, className);
            inOrder.verify(jaguarMock).testStarted(formattedName);
        } catch (final IOException e) {
            fail();
        }
    }

}

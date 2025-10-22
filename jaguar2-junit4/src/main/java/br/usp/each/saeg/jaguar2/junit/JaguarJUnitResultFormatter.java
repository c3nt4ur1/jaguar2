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

import java.io.IOException;
import java.io.OutputStream;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import br.usp.each.saeg.jaguar2.CoverageControllerLoader;
import br.usp.each.saeg.jaguar2.SpectrumExporterLoader;
import br.usp.each.saeg.jaguar2.core.Jaguar;
import br.usp.each.saeg.jaguar2.core.heuristic.Ochiai;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class JaguarJUnitResultFormatter implements JUnitResultFormatter {

    public static final String JAGUAR2_NO_DUMP = "jaguar2.noDump";

    private final Jaguar jaguar;

    private boolean fail;

    public JaguarJUnitResultFormatter(final Jaguar jaguar) {
        this.jaguar = jaguar;
    }

    public JaguarJUnitResultFormatter() {
        this(new Jaguar(
                new CoverageControllerLoader().load(),
                new SpectrumExporterLoader().load(),
                new Ochiai(),
                Boolean.getBoolean(JAGUAR2_NO_DUMP)));
    }

    @Override
    public void startTestSuite(final JUnitTest suite) {
        jaguar.testRunStarted();
    }

    @Override
    public void startTest(final Test test) {
        fail = false;
        try {
            jaguar.testStarted(test.toString());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endTest(final Test test) {
        jaguar.testFinished(test.toString(), fail);
    }

    @Override
    public void addFailure(final Test test, final AssertionFailedError e) {
        fail = true;
    }

    @Override
    public void addError(final Test test, final Throwable e) {
        fail = true;
    }

    @Override
    public void endTestSuite(final JUnitTest suite) {
        try {
            jaguar.testRunFinished();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setOutput(final OutputStream out) {
    }

    @Override
    public void setSystemOutput(final String out) {
    }

    @Override
    public void setSystemError(final String err) {
    }

}

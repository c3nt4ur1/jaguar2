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

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class JaguarTestRunner {

    public static void main(final String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: JaguarTestRunner <test-class-1> [test-class-2] ...");
            System.exit(1);
        }

        final JUnitCore core = new JUnitCore();
        core.addListener(new TextListener(System.out));
        core.addListener(new JaguarJUnitRunListener());

        try {

            // Load classes
            final Class<?>[] testClasses = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                testClasses[i] = Class.forName(args[i]);
            }

            // Run tests and exit with proper status code
            System.exit(core.run(testClasses).wasSuccessful() ? 0 : 1);

        } catch (final ClassNotFoundException e) {
            System.err.println("Error loading test class: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

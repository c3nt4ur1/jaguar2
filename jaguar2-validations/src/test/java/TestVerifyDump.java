
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.junit.Assert;
import org.junit.Test;

public class TestVerifyDump {

    @Test
    public void verify() throws XMLStreamException, FileNotFoundException {
        final XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(
            new FileInputStream(
                "../jaguar2-examples/jaguar2-example-junit4-jacoco/target/site/jacoco/jacoco.xml"
            )
        );

        final List<String> sessions = new ArrayList<String>();
        final QName id = QName.valueOf("id");
        while (reader.hasNext()) {
            final XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                final StartElement startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("sessioninfo")) {
                    sessions.add(startElement.getAttributeByName(id).getValue());
                }
            }
        }

        Assert.assertEquals(6, sessions.size());
        Assert.assertTrue(sessions.contains("bootstrap"));
        Assert.assertTrue(sessions.contains("passed:test1(br.usp.each.saeg.jaguar2.MaxTest)"));
        Assert.assertTrue(sessions.contains("passed:test1(br.usp.each.saeg.jaguar2.MaxTest)"));
        Assert.assertTrue(sessions.contains("passed:test3(br.usp.each.saeg.jaguar2.MaxTest)"));
        Assert.assertTrue(sessions.contains("failed:test4(br.usp.each.saeg.jaguar2.MaxTest)"));
        Assert.assertTrue(sessions.contains("failed:test5(br.usp.each.saeg.jaguar2.MaxTest)"));
    }

}

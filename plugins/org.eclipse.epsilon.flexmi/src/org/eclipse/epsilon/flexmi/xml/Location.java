/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.xml;
public class Location {

    public static final String ID = "location";

    private final int startLine;
    private final int startColumn;
    private final int endLine;
    private final int endColumn;

    public Location(int startLine,
            int startColumn, int endLine, int endColumn) {
        super();
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    @Override
    public String toString() {
        return "[line " + startLine + ":"
                + startColumn + " to line " + endLine + ":"
                + endColumn + "]";
    }
}
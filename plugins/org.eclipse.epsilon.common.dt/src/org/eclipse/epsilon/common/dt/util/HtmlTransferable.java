/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class HtmlTransferable implements Transferable {
        private DataFlavor _htmlDataFlavor;
        private String _htmlText;

        public HtmlTransferable(String htmlText) {
            _htmlText = htmlText;
            try {
				_htmlDataFlavor = new DataFlavor("text/html") {
					@Override
					public Class<?> getRepresentationClass() {
						return String.class;
					}
				};
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        @Override
		public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{_htmlDataFlavor};
        }

        @Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
            return "text/html".equalsIgnoreCase(flavor.getMimeType());
        }

        @Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            //InputStream stringStream = new ByteArrayInputStream(_htmlText.getBytes("utf-8"));
            //return stringStream;
        	return _htmlText;
        }
    }

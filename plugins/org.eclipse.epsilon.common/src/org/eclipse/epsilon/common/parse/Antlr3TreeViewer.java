/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.parse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.antlr.runtime.tree.Tree;
import org.eclipse.epsilon.common.util.StringUtil;

public class Antlr3TreeViewer extends JFrame {

	private static final long serialVersionUID = -5944491471447325678L;

	//StaticFieldNameResolver v2Resolver;
	StaticFieldNameResolver v3Resolver;
	
	//JTree v2Tree = new JTree();
	JTree v3Tree = new JTree();
	
	public Antlr3TreeViewer(Tree tree, Class<?> v3Parser) {
		
		//this.v2Resolver = new StaticFieldNameResolver(v2Parser);
		this.v3Resolver = new StaticFieldNameResolver(v3Parser);
		
		//v2Tree.setModel(new V2TreeModel(ast));
		//v2Tree.setCellRenderer(new V2TreeCellRenderer());
		
		v3Tree.setModel(new V3TreeModel(tree));
		v3Tree.setCellRenderer(new V3TreeCellRenderer());
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane();
		//splitPane.setLeftComponent(new JScrollPane(v2Tree));
		splitPane.setRightComponent(new JScrollPane(v3Tree));
		splitPane.setDividerLocation(0.5);
	
		this.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		//TODO: See how to expand tree
		//TODO: Highlight changes
		
		this.setTitle("AST Viewer");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(100,100,800,800);
		//expandAll(v2Tree);
		//expandAll(v3Tree);
		
		this.setVisible(true);
		
	} 
	
	  public void expandAll(JTree tree) {
		    int row = 0;
		    while (row < tree.getRowCount()) {
		      tree.expandRow(row);
		      row++;
		      }
		    }
	/*
	class V2TreeModel implements TreeModel{
		
		private AST ast = null;
		
		public V2TreeModel(AST ast){
			this.ast = ast;
		}
		
		public AST toAST(Object o){
			return (AST) o;
		}
		
		public Object getRoot() {
			return ast;
		}

		public Object getChild(Object arg0, int arg1) {
			AST child = toAST(arg0).getFirstChild();
			for (int i=0;i<arg1;i++){
				child = child.getNextSibling();
				if (child == null) return ((EolAst) arg0).getAnnotationsAst();
			}
			return child;
		}

		public int getChildCount(Object arg0) {
			if (((EolAst) arg0).getAnnotationsAst() != null) {
				return toAST(arg0).getNumberOfChildren() + 1;
			}
			else {
				return toAST(arg0).getNumberOfChildren();
			}
		}

		public boolean isLeaf(Object arg0) {
			return toAST(arg0).getNumberOfChildren() == 0;
		}

		public void valueForPathChanged(TreePath arg0, Object arg1) {
		}

		public int getIndexOfChild(Object arg0, Object arg1) {
			return 0;
		}

		public void addTreeModelListener(TreeModelListener arg0) {
			
		}

		public void removeTreeModelListener(TreeModelListener arg0) {
			
		}
		
	}
*/
	class V3TreeModel implements TreeModel{
		
		private Tree tree = null;
		
		public V3TreeModel(Tree tree){
			this.tree = tree;
		}
		
		public Tree toTree(Object o){
			return (Tree) o;
		}
		
		public Object getRoot() {
			return tree;
		}

		public Object getChild(Object arg0, int arg1) {
			Tree child = toTree(arg0);
			return child.getChild(arg1);
		}

		public int getChildCount(Object arg0) {
			return toTree(arg0).getChildCount();
			/*
			if (((EolAst) arg0).getAnnotationsAst() != null) {
				return toAST(arg0).getNumberOfChildren() + 1;
			}
			else {
				return toAST(arg0).getNumberOfChildren();
			}
			*/
		}

		public boolean isLeaf(Object arg0) {
			return toTree(arg0).getChildCount() == 0;
		}

		public void valueForPathChanged(TreePath arg0, Object arg1) {
		}

		public int getIndexOfChild(Object arg0, Object arg1) {
			return 0;
		}

		public void addTreeModelListener(TreeModelListener arg0) {
			
		}

		public void removeTreeModelListener(TreeModelListener arg0) {
			
		}
		
	}
	/*
	class V2TreeCellRenderer implements TreeCellRenderer{

		public Component getTreeCellRendererComponent(JTree arg0, Object arg1, boolean selected, boolean expanded, boolean leaf, int row, boolean arg6) {
			JLabel label = new JLabel();
			
			AST ast = (AST) arg1;
			
			if (selected){
				label.setOpaque(true);
				label.setBackground(SystemColor.activeCaption);
				label.setForeground(SystemColor.activeCaptionText);
			}
			
			label.setIcon(new ImageIcon(V2TreeCellRenderer.class.getResource("node.gif")));
			
			label.setText("<html>" +
					getText(ast) 
					+ " (" + v2Resolver.getField(ast.getType()) + ")"
					+ "<font color='#C0C0C0'>"
					+ " (Line:" + ast.getLine() + ",Col:" 
					+ ast.getColumn() //+ ", Props: "
					//+ toString(((EolAst) ast).getProperties())
					//+ (ast instanceof FileAst ? ((FileAst) ast).getFile().getAbsolutePath() : "")
					+ ")"
					+ "</font>"
					+ "</html>");
			
			return label;
		}
		
		private String getText(AST ast){
			String str = StringUtil.escapeHtml(ast.getText());
			str = str.replaceAll("\r", "<font color='#336699'>\\\\r</font>");
			str = str.replaceAll("\n", "<font color='#336699'>\\\\n</font>");
			str = str.replaceAll("\t", "<font color='#336699'>\\\\t</font>");
			return str;
		}
	}
	*/
	class V3TreeCellRenderer implements TreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree arg0, Object arg1, boolean selected, boolean expanded, boolean leaf, int row, boolean arg6) {
			JLabel label = new JLabel();
			
			Tree ast = (Tree) arg1;

			if (selected){
				label.setOpaque(true);
				label.setBackground(SystemColor.activeCaption);
				label.setForeground(SystemColor.activeCaptionText);
			}
			
			label.setIcon(new ImageIcon(V3TreeCellRenderer.class.getResource("node.gif")));
			
			label.setText("<html>" +
					getText(ast) 
					+ " (" + v3Resolver.getField(ast.getType()) + "-" + ast.getType() + ")"
					+ "<font color='#C0C0C0'>"
					+ " (Line:" + ast.getLine() + ",Col:" 
					+ ast.getCharPositionInLine() //+ ", Props: "
					//+ toString(((EolAst) ast).getProperties())
					//+ (ast instanceof FileAst ? ((FileAst) ast).getFile().getAbsolutePath() : "")
					+ ")"
					//+ StringUtil.toString(ast.getNextSibling())
					+ "</font>"
					+ "</html>");
			
			return label;
		}
		
		private String getText(Tree ast) {
			String str = StringUtil.escapeHtml(ast.getText());
			str = str.replaceAll("\r", "<font color='#336699'>\\\\r</font>");
			str = str.replaceAll("\n", "<font color='#336699'>\\\\n</font>");
			str = str.replaceAll("\t", "<font color='#336699'>\\\\t</font>");
			return str;
		}
	}
}

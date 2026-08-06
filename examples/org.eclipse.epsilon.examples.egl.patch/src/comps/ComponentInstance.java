/**
 */
package comps;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link comps.ComponentInstance#getPrototype <em>Prototype</em>}</li>
 * </ul>
 *
 * @see comps.CompsPackage#getComponentInstance()
 * @model annotation="instance of='Component'"
 * @generated
 */
public interface ComponentInstance extends Component {
	/**
	 * Returns the value of the '<em><b>Prototype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prototype</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prototype</em>' reference.
	 * @see #setPrototype(Component)
	 * @see comps.CompsPackage#getComponentInstance_Prototype()
	 * @model
	 * @generated
	 */
	Component getPrototype();

	/**
	 * Sets the value of the '{@link comps.ComponentInstance#getPrototype <em>Prototype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prototype</em>' reference.
	 * @see #getPrototype()
	 * @generated
	 */
	void setPrototype(Component value);

} // ComponentInstance

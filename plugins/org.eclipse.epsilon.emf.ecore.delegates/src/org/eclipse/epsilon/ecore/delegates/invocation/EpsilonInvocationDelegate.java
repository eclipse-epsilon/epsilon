package org.eclipse.epsilon.ecore.delegates.invocation;

import org.eclipse.emf.ecore.EOperation;

/**
 * Add support for resetting the Invocation Delegate
 * 
 * @since 2.5
 */
public interface EpsilonInvocationDelegate extends EOperation.Internal.InvocationDelegate {

	interface Factory extends EOperation.Internal.InvocationDelegate.Factory {

		EpsilonInvocationDelegate createInvocationDelegate(EOperation operation);

		interface Descriptor {
			Factory getFactory();
		}

		interface Registry extends EOperation.Internal.InvocationDelegate.Factory.Registry {
			Factory getFactory(String uri);

			class Smart
					extends EOperation.Internal.InvocationDelegate.Factory.Registry.Impl
					implements Registry {

				public Factory getFactory(String uri) {
					return (Factory) get(uri);
				}

				private static final long serialVersionUID = 3930135039118088799L;

			}
		}

	}

	void reset();

}

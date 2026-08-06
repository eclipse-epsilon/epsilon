package org.eclipse.epsilon.eol.dt.uris;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.execute.context.uris.AbstractUpURIResolver;

public class PlatformResourceUpURIResolver extends AbstractUpURIResolver {

	private static final String PATH_PREFIX = "/resource/";

	@Override
	public Optional<URI> resolve(URI uri, IModule parentModule, URI rel) throws URISyntaxException, IOException {
		if (!"up".equals(uri.getScheme()) || !"platform".equals(rel.getScheme()) || !rel.getPath().startsWith(PATH_PREFIX)) {
			// This resolver only handles up:// URIs from platform:/resource/ 
			return Optional.empty();
		}
		validateUpUri(uri);

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String[] parts = rel.getPath().substring(PATH_PREFIX.length()).split("/", 2);
		if (parts.length != 2) {
			return Optional.empty();
		}

		IProject project = root.getProject(parts[0]);
		IResource relFile = project.findMember(parts[1]);
		if (relFile != null) {
			List<URI> candidates = new ArrayList<>();
			for (IContainer parent = relFile.getParent(); parent != null; parent = parent.getParent()) {
				if (uri.getAuthority().equals(parent.getName())) {
					findRecursively(parent, uri.getPath(), candidates);
					break;
				}
			}

			if (candidates.isEmpty()) {
				return Optional.empty();
			} else if (candidates.size() > 1) {
				throw new URISyntaxException(uri.toString(), String.format(
						"Ambiguous - possible options:\n%s",
						String.join(
							System.lineSeparator(),
							candidates.stream()
								.map(c -> "- " + c.toString())
								.collect(Collectors.toList())
						)
					));
			} else {
				return Optional.of(candidates.get(0));
			}
		}

		return Optional.empty();
	}

	protected void findRecursively(IContainer parent, String path, List<URI> candidates) {
		IResource member = parent.findMember(path);
		if (member != null) {
			candidates.add(member.getLocationURI());
		}

		try {
			for (IResource child : parent.members()) {
				if (child instanceof IContainer) {
					findRecursively((IContainer) child, path, candidates);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}

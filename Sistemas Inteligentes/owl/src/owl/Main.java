package owl;

import java.io.File;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Main {
	public static void main(String[] args)
			throws IOException, OWLOntologyStorageException, OWLOntologyCreationException {
		File f = new File("animauxPlantes.owl");
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.loadOntologyFromOntologyDocument(f);

		OWLController owlController = new OWLController("animauxPlantes.owl");
		try {
			owlController.exibirHierarquia();
		} catch (OWLException e) {
			e.printStackTrace();
		}
	}
}

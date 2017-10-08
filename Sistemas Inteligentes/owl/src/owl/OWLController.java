package owl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;

public class OWLController {
	private String ontologiaPath;
	private OWLDataFactory owlDataFactory;
	private OWLOntologyManager owlManager;
	private OWLOntology ontologia;
	private LabelExtractor le = new LabelExtractor();

	public OWLController(String nomeOntologia) throws OWLOntologyCreationException {
		this.ontologiaPath = nomeOntologia;
		this.owlDataFactory = OWLManager.getOWLDataFactory();
		this.owlManager = OWLManager.createOWLOntologyManager();
		File f = new File("animauxPlantes.owl");
		this.ontologia = this.owlManager.loadOntologyFromOntologyDocument(f);
		sinalizarListaIndividuos(true);

	}

	private void sinalizarListaIndividuos(boolean b) {
		// TODO Auto-generated method stub

	}

	public OWLReasoner obtemRaciocinador(OWLOntology ontologia) {
		StructuralReasonerFactory rf = new StructuralReasonerFactory();
		OWLReasoner raciocinador = rf.createReasoner(ontologia);
		return raciocinador;
	}

	public void exibirHierarquia() throws OWLException {
		OWLClass clazz = this.owlDataFactory.getOWLThing();
		// System.out.println("Class : " + clazz);
		// Print the hierarchy below thing
		OWLReasoner raciocinador = obtemRaciocinador(this.ontologia);
		printHierarchy(raciocinador, clazz, 1, new HashSet<OWLClass>());
	}

	public void printHierarchy(OWLReasoner r, OWLClass clazz, int level, Set<OWLClass> visited) throws OWLException {
		// Only print satisfiable classes to skip Nothing
		if (!visited.contains(clazz) && r.isSatisfiable(clazz)) {
			visited.add(clazz);
			// for (int i = 0; i < level * 4; i++) {
			// System.out.print(" ");
			// }
			// System.out.println(labelFor(clazz, r.getRootOntology()));
			boolean response = QuestionHelper.handleQuestion(clazz.toString());
			NodeSet<OWLClass> classes = r.getSubClasses(clazz, true);

			if (!response) {
				return;
			}

			if (response && classes.getFlattened().iterator().next().isOWLNothing()) {
				System.out.println("Found it, its a " + clazz.toString().split("#")[1].split(">")[0] + "!");
				System.exit(0);
			}

			// Find the children and recurse
			for (OWLClass child : classes.getFlattened()) {
				printHierarchy(r, child, level + 1, visited);
			}
			System.out.println("Can't find it! :(");
		}
	}

	private String labelFor(OWLClass clazz, OWLOntology o) {
		Set<OWLAnnotation> annotations = clazz.getAnnotations(o);
		for (OWLAnnotation anno : annotations) {
			String result = anno.accept(this.le);
			if (result != null) {
				return result;
			}
		}
		return clazz.getIRI().toString();
	}

	// trouve les instances et les prop avec un reasoner
	public void jesaisPas() {
		OWLReasoner r = obtemRaciocinador(this.ontologia);
		for (OWLClass c : this.ontologia.getClassesInSignature()) {
			// the boolean argument specifies direct subclasses
			for (OWLNamedIndividual i : r.getInstances(c, true).getFlattened()) {
				System.out.println(labelFor(i, this.ontologia) + ":" + labelFor(c, this.ontologia));
				// look up all property assertions
				for (OWLObjectProperty op : this.ontologia.getObjectPropertiesInSignature()) {
					NodeSet<OWLNamedIndividual> petValuesNodeSet = r.getObjectPropertyValues(i, op);
					for (OWLNamedIndividual value : petValuesNodeSet.getFlattened()) {
						System.out.println(labelFor(i, this.ontologia) + " " + labelFor(op, this.ontologia) + " "
								+ labelFor(value, this.ontologia));
					}
				}
			}
		}
	}

	private String labelFor(OWLObjectProperty op, OWLOntology ontologia2) {
		// TODO changer
		return "property";
	}

	private String labelFor(OWLNamedIndividual i, OWLOntology ontologia2) {
		// TODO changer
		return "named individual";
	}

	// permet de recuperer les sous classes mais il faut un reasoner
	public void sousclasse() {
		OWLReasoner r = obtemRaciocinador(this.ontologia);
		r.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		// Look up and print all direct subclasses for all classes
		// a NodeSet represents a set of Nodes.
		// a Node represents a set of equivalent classes
		for (OWLClass c : this.ontologia.getClassesInSignature()) {
			// the boolean argument specifies direct subclasses
			NodeSet<OWLClass> subClasses = r.getSubClasses(c, true);
			for (OWLClass subClass : subClasses.getFlattened()) {
				System.out.println(labelFor(subClass, this.ontologia) + " subclass of " + labelFor(c, this.ontologia));
			}
		}

	}

	public List<String> obtemIndividuos(String propriedade, String id) throws Exception {
		List<String> lista = new ArrayList<>();
		if (this.ontologia == null || propriedade == null || id == null) {
			throw new Exception("Dados n�o informados.");
		}
		Set<OWLNamedIndividual> individuos = this.ontologia.getIndividualsInSignature();
		for (OWLNamedIndividual owlNamedIndividual : individuos) {
			// System.out.println("Individuo: " + owlNamedIndividual);
			Set<OWLDataPropertyAssertionAxiom> dataProperties = this.ontologia
					.getDataPropertyAssertionAxioms(owlNamedIndividual);
			for (OWLDataPropertyAssertionAxiom owlDataPropertyAssertionAxiom : dataProperties) {
				OWLDataPropertyAssertionAxiom axiomaSemAnotacao = owlDataPropertyAssertionAxiom
						.getAxiomWithoutAnnotations();
				OWLDataPropertyImpl dataProperty = (OWLDataPropertyImpl) axiomaSemAnotacao.getProperty();
				if (dataProperty != null) {
					String sufixoPropriedade = dataProperty.getIRI().getFragment();
					if (propriedade.equals(sufixoPropriedade)) {
						OWLLiteral predicado = axiomaSemAnotacao.getObject();
						String valorPredicado = predicado.getLiteral();
						if (propriedade.equals("valor")) {
							// Valor m�ximo
							if (Double.parseDouble(valorPredicado) <= Double.parseDouble(id)) {
								lista.add(owlNamedIndividual.toString());
							}
						} else {
							if (propriedade.equals("ano")) {
								// Ano m�nimo
								if (Integer.parseInt(valorPredicado) >= Integer.parseInt(id)) {
									lista.add(owlNamedIndividual.toString());
								}
							} else {
								// Para as demais caracter�sticas, valores
								// devem
								// ser exatamente iguais
								if (valorPredicado.equals(id)) {
									lista.add(owlNamedIndividual.toString());
								}
							}
						}
					}
				}
			}
		}

		return lista;
	}

}

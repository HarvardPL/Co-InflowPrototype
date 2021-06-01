package lbs.harvard.coinflow.util.rifl;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.lattice.IFCLabelString;
import lbs.harvard.coinflow.lattice.IFCLattice;
import lbs.harvard.coinflow.lattice.impl.IFCLatticeGraphImpl;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * RIFLParser processes the rifl.xml files used in IFSPEC benchmark suites
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class RIFLParser {
	
	public static void parsing(String pathToRIFLFile) {
		String file = pathToRIFLFile;
//		IFCPolicyInternal policy = new IFCPolicyInternal();
		// key is 'handle', value is label
		Map<String, String> labelAssignments = new HashMap<>();
		try {
	         File inputFile = new File(file);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         // build lattice
	         NodeList domainsList = doc.getElementsByTagName("domain");
	         String[] leaves = new String[domainsList.getLength()];
	         for (int i = 0; i < domainsList.getLength(); i++) {
	        	 	Node nNode = domainsList.item(i);
	        	 	leaves[i] = nNode.getAttributes().getNamedItem("name").getNodeValue();
	         }
	         IFCLattice lattice =  null;
	         NodeList flowsList = doc.getElementsByTagName("flow");
	         for (int i = 0; i < flowsList.getLength(); i++) {
	        	 	Node nNode = flowsList.item(i);
	        	 	String p1 = nNode.getAttributes().getNamedItem("from").getNodeValue();
	        	 	String p2 = nNode.getAttributes().getNamedItem("to").getNodeValue();
	        	 	IFCLatticeGraphImpl imp = new IFCLatticeGraphImpl();
	        	 	imp.addFlowRelation(new IFCLabelString(p1), new IFCLabelString(p2));
	        	 	imp.setup();
	        	 	lattice = imp;
	         }
	         
	         // retrieve the assignments of labels to ports
	         NodeList assignmentList = doc.getElementsByTagName("assign");
	         for (int i = 0; i < assignmentList.getLength(); i++) {
	        	 	Node nNode = assignmentList.item(i);
	        	 	String handle = nNode.getAttributes().getNamedItem("handle").getNodeValue();
	        	 	String label = nNode.getAttributes().getNamedItem("domain").getNodeValue();
	        	 	labelAssignments.put(handle, label);
	         }
	         
	         // retrieve the sources and sinks
	         NodeList sinksList = doc.getElementsByTagName("sink");
	         for (int temp = 0; temp < sinksList.getLength(); temp++) {
	        	 	Node sinkTopNode = sinksList.item(temp);
	        	 	Node assignableNode = sinkTopNode.getParentNode();
	        	 	while(!assignableNode.getNodeName().equals("assignable")) {
	        	 		assignableNode = assignableNode.getParentNode();
	        	 	}
	        	 	String handleId = assignableNode.getAttributes().getNamedItem("handle").getNodeValue();
	        	 	String label = labelAssignments.get(handleId);
	        	 	// ports
	        	 	for (int i = 0; i < sinkTopNode.getChildNodes().getLength(); i++) {
	        	 		Node sinkNode = sinkTopNode.getChildNodes().item(i);
	        	 		if(sinkNode.getNodeName().equals("field")) {
		        	 		String className = sinkNode.getAttributes().getNamedItem("class").getNodeValue();
		        	 		String fieldName = sinkNode.getAttributes().getNamedItem("name").getNodeValue();
		        	 		IFCPolicyInternal.staticAddFieldAsSink(className, fieldName, label);
		        	 	}else if(sinkNode.getNodeName().equals("parameter")) {
		        	 		String className = sinkNode.getAttributes().getNamedItem("class").getNodeValue();
		        	 		String methodName = sinkNode.getAttributes().getNamedItem("method").getNodeValue();
		        	 		int pos = Integer.valueOf(sinkNode.getAttributes().getNamedItem("parameter").getNodeValue());
		        	 			IFCPolicyInternal.staticAddParameterAsSink(
		        	 					className, methodName, pos, label);
		        	 	}else if(sinkNode.getNodeName().equals("returnvalue")) {
		        	 		String className = sinkNode.getAttributes().getNamedItem("class").getNodeValue();
		        	 		String methodName = sinkNode.getAttributes().getNamedItem("method").getNodeValue();
		        	 			IFCPolicyInternal.staticAddReturnValAsSink(
		        	 					className, methodName, label);
		        	 	}
	        	 	}
	         }
	         NodeList sourcesList = doc.getElementsByTagName("source");
	         for (int temp = 0; temp < sourcesList.getLength(); temp++) {
	        	 	Node sourceTopNode = sourcesList.item(temp);
	        	 	Node assignableNode = sourceTopNode.getParentNode();
	        	 	while(!assignableNode.getNodeName().equals("assignable")) {
	        	 		assignableNode = assignableNode.getParentNode();
	        	 	}
	        	 	String handleId = assignableNode.getAttributes().getNamedItem("handle").getNodeValue();
	        	 	String label = labelAssignments.get(handleId);
	        	 	for (int i = 0; i < sourceTopNode.getChildNodes().getLength(); i++) {
	        	 		Node sourceNode = sourceTopNode.getChildNodes().item(i);
		        	 	// ports
		        	 	if(sourceNode.getNodeName().equals("field")) {
		        	 		String className = sourceNode.getAttributes().getNamedItem("class").getNodeValue();
		        	 		String fieldName = sourceNode.getAttributes().getNamedItem("name").getNodeValue();
		        	 		IFCPolicyInternal.staticAddFieldAsSource(className, fieldName, label);
		        	 	}else if(sourceNode.getNodeName().equals("parameter")) {
		        	 		String className = sourceNode.getAttributes().getNamedItem("class").getNodeValue();
		        	 		String methodName = sourceNode.getAttributes().getNamedItem("method").getNodeValue();
		        	 		int pos = Integer.valueOf(sourceNode.getAttributes().getNamedItem("parameter").getNodeValue());
		        	 			IFCPolicyInternal.staticAddParameterAsSource(
		        	 					className, methodName, pos, label);
		        	 	}else if(sourceNode.getNodeName().equals("returnvalue")) {
		        	 		String className = sourceNode.getAttributes().getNamedItem("class").getNodeValue();
		        	 		String methodName = sourceNode.getAttributes().getNamedItem("method").getNodeValue();
		        	 			IFCPolicyInternal.staticAddReturnValAsSource(
		        	 					className, methodName, label);
		        	 	}
	        	 	}
	         }
		}catch (Exception e) {
		}
	}
	
	public static IFCLattice buildLattice(String riflFile) {
		File inputFile = new File(riflFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        IFCLattice lattice =  null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
			// build lattice
	        NodeList domainsList = doc.getElementsByTagName("domain");
	        String[] leaves = new String[domainsList.getLength()];
	        for (int i = 0; i < domainsList.getLength(); i++) {
	       	 	Node nNode = domainsList.item(i);
	       	 	leaves[i] = nNode.getAttributes().getNamedItem("name").getNodeValue();
	        }
	        
	         NodeList flowsList = doc.getElementsByTagName("flow");
	         for (int i = 0; i < flowsList.getLength(); i++) {
	        	 	Node nNode = flowsList.item(i);
	        	 	String p1 = nNode.getAttributes().getNamedItem("from").getNodeValue();
//	        	 	lattice = new ConfidentialLatticeBVImpl(new String[] {p1});
	        	 	String p2 = nNode.getAttributes().getNamedItem("to").getNodeValue();
//	        	 	lattice.memoPrincipal(PrincipalFactory.makePrincipal(p2), lattice.lookup(PrincipalFactory.makePrincipal(p1)));
	        	 	IFCLatticeGraphImpl imp = new IFCLatticeGraphImpl();
	        	 	imp.addFlowRelation(new IFCLabelString(p1), new IFCLabelString(p2));
	        	 	//the setup needs to be called before using every lattice
	        	 	imp.setup();
	        	 	lattice = imp;
	         }
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return lattice;
	}
	
	public static void main(String[] args) {
		parsing("/Users/llama_jian/Develop/IFSPEC/webstore2/rifl.xml");
	}
}

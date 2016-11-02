package evaluation.dataset.sparse;

import java.io.IOException;

import evaluation.dataset.Dataset;
import evaluation.dataset.DatasetType;
import rdt.essentials.RDTException;
import rdt.essentials.RDTInstances;
import rdt.tree.collector.CollectorPreferences;
import rdt.tree.collector.MultilabelCollector;

public class Sparse_Rcv1 extends Dataset{

	private int[] rA;
	
	public Sparse_Rcv1(){
		rA = new int[45];
		for(int i=0; i<rA.length; i++){
			rA[i] = i + 1449;
		}
	}
	
	@Override
	public RDTInstances getTrainInstances() throws IOException, RDTException {
		return new RDTInstances("data/sparse/rcv1.arff", getRestrictedAttributeIds());
	}

	@Override
	public RDTInstances getTestInstances() throws IOException, RDTException {
		return new RDTInstances("data/sparse/rcv1.arff", getRestrictedAttributeIds());
	}

	@Override
	public RDTInstances getCVInstances() throws IOException, RDTException {
		return new RDTInstances("data/sparse/rcv1.arff", getRestrictedAttributeIds());
	}	

	@Override
	public int[] getRestrictedAttributeIds() {
		return rA;
	}

	@Override
	public CollectorPreferences getCollectorPreferences() {
		CollectorPreferences cp = new CollectorPreferences();
		cp.addCollector(new MultilabelCollector(getRestrictedAttributeIds()));
		return cp;
	}

	@Override
	public DatasetType getDatasetType() {
		return DatasetType.SPARSE_RCV1;
	}
	
	@Override
	public String getName() {
		return "rcv1";
	}
}

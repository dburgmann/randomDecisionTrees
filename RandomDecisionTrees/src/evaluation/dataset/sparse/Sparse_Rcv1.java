package evaluation.dataset.sparse;

import java.io.IOException;

import evaluation.dataset.Dataset;
import evaluation.dataset.DatasetType;
import rdt.essentials.RDTException;
import rdt.essentials.RDTInstances;
import rdt.tree.collector.ClassificationCollector;
import rdt.tree.collector.CollectorPreferences;
import rdt.tree.collector.MultilabelCollector;

public class Sparse_Rcv1 extends Dataset{

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
		return new int[]{47224};
	}

	 @Override
	public CollectorPreferences getCollectorPreferences() {
	    CollectorPreferences cp = new CollectorPreferences();
	    cp.addCollector(new ClassificationCollector(getRestrictedAttributeIds()[0]));
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

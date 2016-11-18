package evaluation.dataset.sparse;

import java.io.IOException;

import evaluation.dataset.Dataset;
import evaluation.dataset.DatasetType;
import rdt.essentials.RDTException;
import rdt.essentials.RDTInstances;
import rdt.tree.collector.ClassificationCollector;
import rdt.tree.collector.CollectorPreferences;
import rdt.tree.collector.MultilabelCollector;

public class Sparse_N20 extends Dataset{

	@Override
	public RDTInstances getTrainInstances() throws IOException, RDTException {
		return new RDTInstances("data/sparse/n20.arff", getRestrictedAttributeIds());
	}

	@Override
	public RDTInstances getTestInstances() throws IOException, RDTException {
		return new RDTInstances("data/sparse/n20.arff", getRestrictedAttributeIds());
	}

	@Override
	public RDTInstances getCVInstances() throws IOException, RDTException {
		return new RDTInstances("data/sparse/n20.arff", getRestrictedAttributeIds());
	}	

	@Override
	public int[] getRestrictedAttributeIds() {
		return new int[]{62058};
	}
	
	 @Override
	    public CollectorPreferences getCollectorPreferences() {
	        CollectorPreferences cp = new CollectorPreferences();
	        cp.addCollector(new ClassificationCollector(getRestrictedAttributeIds()[0]));
	        return cp;
	    }

	@Override
	public DatasetType getDatasetType() {
		return DatasetType.SPARSE_N20;
	}
	
	@Override
	public String getName() {
		return "n20";
	}
}

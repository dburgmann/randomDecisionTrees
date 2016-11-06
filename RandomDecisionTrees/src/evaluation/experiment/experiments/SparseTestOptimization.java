package evaluation.experiment.experiments;

import evaluation.dataset.DatasetType;
import evaluation.experiment.Optimization;
import rdt.model.ModelType;

public class SparseTestOptimization extends Optimization{

	@Override
	protected ModelType getModelType() {
		return ModelType.BATCH_ENSEMBLE;
	}

	@Override
	protected Object[][][] getParameters() {
		return new Object[][][]{
			//Model 1
			{
				new Integer[]{10},		//Trees
				new Integer[]{15},		//maxDeep
				new Integer[]{5},		//maxS
				new Long[]{(long) 1},	//randomSeed
				
			}
		};
	}

	@Override
	public DatasetType[] getDatasets() {
		return new DatasetType[]{
				DatasetType.SPARSE_N20,
				DatasetType.SPARSE_RCV1
		};
	}

	@Override
	protected int numFolds() {
		// TODO Auto-generated method stub
		return 0;
	}


}

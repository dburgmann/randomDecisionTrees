package evaluation.experiment.experiments;

import evaluation.dataset.DatasetType;
import evaluation.experiment.ExperimentType;
import evaluation.experiment.Optimization;
import rdt.model.ModelType;

public class SparseBatchOptimization extends Optimization{

	@Override
	protected ModelType getModelType() {
		return ModelType.SPARSE_BATCH_ENSEMBLE;
	}

	@Override
	protected Object[][] getParameters() {
		return new Object[][]{
				new Integer[]{10, 50, 100, 200},		//Trees
				new Integer[]{10, 20, 30},		//maxDeep
				new Integer[]{5},		//maxS
				new Long[]{(long) 1},	//randomSeed
				new Integer[]{1,5, 10}	//noSplitAttrs
		};
	}

	@Override
	public DatasetType[] getDatasets() {
		return new DatasetType[]{
				DatasetType.SPARSE_RCV1,
		};
	}


	@Override
	protected int getNumFolds() {
		return 10;
	}

	@Override
	protected int getRounds() {
		return 1;
	}

	@Override
	protected ExperimentType getExperimentType() {
		return ExperimentType.SPARSE_BATCH_EXPERIMENT;
	}


}

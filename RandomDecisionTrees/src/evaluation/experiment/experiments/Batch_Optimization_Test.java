package evaluation.experiment.experiments;

import evaluation.dataset.DatasetType;
import evaluation.experiment.ExperimentType;
import evaluation.experiment.Optimization;
import rdt.model.ModelType;

public class Batch_Optimization_Test extends Optimization{

	@Override
	protected ModelType getModelType() {
		return ModelType.BATCH_ENSEMBLE;
	}

	@Override
	protected Object[][] getParameters() {
		return new Object[][]{
				new Integer[]{50},		//Trees
				new Integer[]{10},		//maxDeep
				new Integer[]{5},		//maxS
				new Long[]{(long) 1},	//randomSeed 
		};
	}

	@Override
	public DatasetType[] getDatasets() {
		return new DatasetType[]{
				DatasetType.SPARSE_N20,
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
		return ExperimentType.BATCH_OPTIMIZATION_TEST;
	}


}

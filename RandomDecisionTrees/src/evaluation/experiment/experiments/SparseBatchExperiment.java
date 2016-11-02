package evaluation.experiment.experiments;

import evaluation.EvaluationType;
import evaluation.dataset.DatasetType;
import evaluation.experiment.CompareExperiment;
import evaluation.experiment.ExperimentType;
import rdt.model.ModelType;

public class SparseBatchExperiment extends CompareExperiment {
	
	@Override
	protected ModelType[] getModelTypes() {
		return new ModelType[]{
				ModelType.SPARSE_BATCH_ENSEMBLE
		};
	}

	@Override
	protected Object[][][] getParameters() {
		return new Object[][][]{
			//Model 1
			{
				new Integer[]{5},		//Trees
				new Integer[]{10},		//maxDeep
				new Integer[]{5},		//maxS
				new Long[]{(long) 1},	//randomSeed
				new Integer[]{1,5}	//noSplitAttrs
			}
		};
	}

	@Override
	public DatasetType[] getDatasets() {
		return new DatasetType[]{
				DatasetType.SPARSE_N20
		};
	}

	@Override
	protected int[] getFocuses() {
		return new int[]{0};
	}

	@Override
	protected EvaluationType getEvaluationType() {
		return EvaluationType.CROSS_VALIDATION;
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

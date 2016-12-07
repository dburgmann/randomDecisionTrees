package evaluation.experiment.experiments;

import evaluation.EvaluationType;
import evaluation.dataset.DatasetType;
import evaluation.experiment.CompareExperiment;
import evaluation.experiment.ExperimentType;
import rdt.model.ModelType;

public class StrictSparseBatchExperiment extends CompareExperiment {
	
	@Override
	protected ModelType[] getModelTypes() {
		return new ModelType[]{
				ModelType.STRICT_SPARSE_BATCH_ENSEMBLE
		};
	}

	@Override
	protected Object[][][] getParameters() {
		return new Object[][][]{
			//Model 1
			{
				new Integer[]{300},		//Trees
				new Integer[]{30},		//maxDeep
				new Integer[]{5},		//maxS
				new Long[]{(long) 1},	//randomSeed
				new Integer[]{1, 5, 10, 100}	//noSplitAttrs
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

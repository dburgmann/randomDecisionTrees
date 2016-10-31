package rdt.tree.builder;

import java.util.List;

import rdt.essentials.RDTAttribute;
import rdt.essentials.RDTException;
import rdt.tree.collector.CollectorPreferences;
import rdt.tree.splitter.SparseBatchSplitter;
import rdt.tree.splitter.Splitter;
import weka.core.Instance;

/**
 * @author DB
 */
public class SparseBatchTreeBuilder extends BatchTreeBuilder {
	int noSplitAttrs = 1;
	
	
	/**
	 * Create a new BatchTreeBuilder with the given maximal depth, the information about the learning
	 * tasks, the minimum number of instances to create an inner node and a random seed to initialize 
	 * the random number generator.
	 * 
	 * @param cp the information about the learning tasks
	 * @param maxDeep the maximal depth of the trees which will be built
	 * @param maxS the minimum number of instances to create an inner node
	 * @param randomSeed a seed to initialize the random number generator
	 */
	public SparseBatchTreeBuilder(CollectorPreferences cp, int maxDeep, int maxS, long randomSeed, int noSplitAttrs) throws RDTException{
		super(cp, maxDeep, maxS, randomSeed);
		this.noSplitAttrs = noSplitAttrs;
	}
	
	protected Splitter createSplitter(List<Instance> ions, int noSplitAttrs) throws RDTException{
		//helpSet = set of attributes already used in path to node
		//freeAttr = set of attributes available for selection
		
		Splitter splitter = new SparseBatchSplitter(ions, random, freeAttrs, helpSet, noSplitAttrs);
		//TODO: determine if null has to be returned in some cases (no attr chosen for splitting?!!?)
	
		return splitter;
	}
}
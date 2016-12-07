package rdt.tree.builder;

import java.util.List;

import rdt.essentials.RDTAttribute;
import rdt.essentials.RDTException;
import rdt.tree.collector.CollectorPreferences;
import rdt.tree.node.InnerNode;
import rdt.tree.node.Node;
import rdt.tree.splitter.SparseBatchSplitter;
import rdt.tree.splitter.Splitter;
import weka.core.Instance;

/**
 * @author DB
 */
public class SparseBatchTreeBuilder extends BatchTreeBuilder {
	int noSplitAttrs = 1;
	boolean strict = true;
	
	
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
	public SparseBatchTreeBuilder(CollectorPreferences cp, int maxDeep, int maxS, long randomSeed, int noSplitAttrs, boolean strict) throws RDTException{
		super(cp, maxDeep, maxS, randomSeed);
		this.noSplitAttrs = noSplitAttrs;
		this.strict = strict;
	}
	
	protected Splitter createSplitter(List<Instance> ions, int noSplitAttrs, boolean strict) throws RDTException{
		Splitter splitter = new SparseBatchSplitter(ions, random, freeAttrs, helpSet, noSplitAttrs, strict);
		
		if(splitter.getUsedAttributeIds().length == 0) 	return null;
		else 											return splitter;
	}
	
	
	
	
	/**
	 * Tries to build a new inner-node. For building the splitter a random attribute is
	 * chosen by calling the method getNotUsedAttribute(...). If this method returns null
	 * a leaf will be created. Otherwise a nominal or numeric splitter splitter will be
	 * created and the newly created inner-node with this splitter will be returned.
	 * 
	 * @param ions the list of instances which can be used to create the splitter
	 * @param parentNode the parent node
	 * @return the newly created node (leaf or inner-node)
	 */
	protected Node buildNode(List<Instance> ions, Node parentNode) throws RDTException{
		
		InnerNode in = new InnerNode();
		in.setParent(parentNode);
		Splitter splitter = createSplitter(ions, this.noSplitAttrs, this.strict);
		
		if(splitter == null){
			return createLeaf(ions);
		}
		
		in.setSplitter(splitter);
		return in;
	}
}
package rdt.tree.splitter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import rdt.essentials.RDTAttribute;
import rdt.essentials.RDTException;
import weka.core.Instance;

/**
 * Class for a SparseSplitter. A SparseSplitter can be used in trees for datasets which are sparse.
 * This splitter can only handle binary nominal attributes. This splitter chooses the attribute 
 * itself. If after the creation the splitter does not have any used-attribute-ids then the creation
 * of the splitter failed. This means the splitter was not able to find an attribute to split.
 * As a result of the sparse data the split-attribute is selected the following way. An instance is 
 * picked randomly and all attributes are checked. If the instance has the value 1 for an attribute
 * and the attribute is not tested in one of the splitters in the tree before then this attribute 
 * will be an candidate. After all attributes are checked we randomly select one of the candidates
 * to be the attribute which will be used in this splitter. If no candidate was found then the procedure
 * will be repeated another instance and so on. If no attribute can be found the used-attributes will be
 * an empty array.
 * 
 * @author MK
 */
public class SparseBatchSplitter implements Splitter{

	/**
	 * The attribute-id which will be used in this splitter. This array can contain 0 elements if
	 * the procedure to find an attribute to split failed.
	 */
	private int[] usedAttrIds;

	
	/**
	 * Creates a new SparseSplitter with the given parameters.
	 * 
	 * @param ions the instances which can be used to determine the attribute and the split-value
	 * @param random the random number generator to generate some random numbers
	 * @param freeAttrs the attributes which can be selected as the attribute for this splitter
	 * @param usedAttrs the attribute-ids which have been used in the tree before
	 * @param noSplitAttr the number of attributes used for splitting the trees 
	 */
	public SparseBatchSplitter(List<Instance> ions, Random random, RDTAttribute[] freeAttrs, Set<Integer> usedAttrs, int noSplitAttrs){
		//TODO: Modify according new method
		List<Integer> selectedIds = determineAttributes(ions, random, freeAttrs, usedAttrs, noSplitAttrs, 0);
		
		if(selectedIds == null){
			this.usedAttrIds = new int[0];
		}else{
			this.usedAttrIds = (int[]) selectedIds.toArray();
		}
	}
	
	/**
	 * Tries to find an attribute for the splitter recursively. This method returns -1 if no proper attribute can be
	 * found.
	 * 
	 * @param ions the instance which can be used to determine the attribute
	 * @param random the random number generator to generate some random numbers
	 * @param attr the attributes which can be selected
	 * @param usedAttrs the attribute-ids which have been used in the tree before
	 * @param round the current round of the recursive call
	 * @return the attribute-id of the attribute which will be tested in this splitter or -1 if no attribute was found
	 */
	private List<Integer> determineAttributes(List<Instance> ions, Random random, RDTAttribute[] attr, Set<Integer> usedAttrs, int no ,int round){
		int count					= 0;
		Instance inst 				= null; //selected instance
		List<Integer> eligibleAttr	= new LinkedList<Integer>(); //list of eligible attr in that instance
		List<Integer> selectedAttr 	= new LinkedList<Integer>(); //result list
				
		//select random instance
		inst = ions.get(random.nextInt(ions.size()));
		
		//determine eligible attributes
		for(int selected=0; selected < attr.length; selected++){
			if(
				inst.value(attr[selected].getAttributeId()) == 1 && //check if attr is set in instance
				!usedAttrs.contains(attr[selected].getAttributeId())//check if attr was used before
			){
				eligibleAttr.add(attr[selected].getAttributeId());  //add attr to selected attr if eligible
			}
		}
		
		
		//select attributes
		while(count < no && selectedAttr.size() < no && count < eligibleAttr.size()){
			selectedAttr.add(eligibleAttr.get(random.nextInt(eligibleAttr.size())));
		}
		
		
		//if no attr were found, repeat 		
		if(selectedAttr.size() == 0){
			round++;
			return determineAttributes(ions, random, attr, usedAttrs, no ,round);
		}
		
		return selectedAttr;
	}
	
	@Override
	public int determineChild(Instance inst) throws RDTException {
		//TODO: modify to use multiple Values / OR
		if(inst.value(usedAttrIds[0]) == 1){
			return 1;
		}
		return 0;
		
	}

	@Override
	public boolean canHandle(Instance inst) {	
		return !inst.isMissing(usedAttrIds[0]);
	} 

	@Override
	public int getNumberOfChilds() {
		return 2;
	}

	@Override
	public int[] getUsedAttributeIds() throws RDTException {
		return usedAttrIds;
	}

	@Override
	public SplitterType getType() {
		return SplitterType.SPARSE;
	}

	@Override
	public boolean isUpdateable() {
		return false;
	}

	@Override
	public boolean isConstructable() {
		return false;
	}

}

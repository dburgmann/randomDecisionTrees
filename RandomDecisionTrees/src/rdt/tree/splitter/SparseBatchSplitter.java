package rdt.tree.splitter;

import java.util.Arrays;
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
		List<Integer> selectedIds = determineAttributes(ions, random, freeAttrs, usedAttrs, noSplitAttrs);
			
		this.usedAttrIds = new int[selectedIds.size()];
		
		for (int i = 0; i < selectedIds.size(); i++) {
			this.usedAttrIds[i] = selectedIds.get(i);
		}
	}
	
	private List<Integer> determineAttributes(List<Instance> ions, Random random, RDTAttribute[] attr, Set<Integer> usedAttrs, int no){
		return determineAttributes(ions, random, attr, usedAttrs, no, true);
	}
	
	
	
	/**
	 * Tries to find an attribute for the splitter recursively. This method returns -1 if no proper attribute can be
	 * found.
	 * 
	 * @param ions the instance which can be used to determine the attribute
	 * @param random the random number generator to generate some random numbers
	 * @param attr the attributes which can be selected
	 * @param usedAttrs the attribute-ids which have been used in the tree before
	 * @param no number of attributes used in the splitter for decision
	 * @paran strict determines if no param should be enforced strictly or if less attributes are allowed if no instance fullfills the requirement
	 * @return the attribute-id of the attribute which will be tested in this splitter or -1 if no attribute was found
	 */
	private List<Integer> determineAttributes(List<Instance> ions, Random random, RDTAttribute[] attr, Set<Integer> usedAttrs, int no, boolean strict){
		
		Instance instance 			= null; //selected instance
		List<Integer> eligibleAttrs	= new LinkedList<Integer>(); //list of eligible attr in that instance
		List<Integer> selectedAttrs = new LinkedList<Integer>(); //result list
				
		if(ions.isEmpty()) return selectedAttrs;	//recursion anchor
		
		//check if no param should be enforced strictly
		if(strict){
			List<Instance> nonEmptyIons = new LinkedList<Instance>();
			
			//determine a random instance that has enough usable attributes
			while(eligibleAttrs.size() < no && !ions.isEmpty()){
				instance = ions.remove(random.nextInt(ions.size())); //select random instance & remove it from ions to prevent usage of one instance multiple times
				eligibleAttrs = getEligibleAttributesOfInstance(instance,attr, usedAttrs);
				
				//keep track of those instances that have attributes but not enough
				if(!eligibleAttrs.isEmpty()){	
					nonEmptyIons.add(instance);
				}
			}
			
			//check if strict mode found eligible Attributes
			if(eligibleAttrs.size() < no) return determineAttributes(nonEmptyIons, random, attr, usedAttrs, no, false); //if strict mode did not find enough attr try non strict variant.
			
		}else{
			//determine a random instance that has usable attributes
			while(eligibleAttrs.isEmpty() && !ions.isEmpty()){
				instance = ions.remove(random.nextInt(ions.size())); //select random instance & remove it from ions to prevent usage of one instance multiple times
				eligibleAttrs = getEligibleAttributesOfInstance(instance,attr, usedAttrs);				
			}
			
			if(eligibleAttrs.isEmpty()){
				return selectedAttrs;	//if even non strict mode did not find enough attr then there are none left
			}
		}		
		
		//select attributes
		int count = 0;
		int attribute = 0;
		while(count < no && count < eligibleAttrs.size()){
			attribute = eligibleAttrs.remove(random.nextInt(eligibleAttrs.size()));  //select a random attribute from all eligible attributes & remove it from eleigible list
			selectedAttrs.add(attribute);											 //add selected attribute to resultlist
			count++;
		}
				
		usedAttrs.addAll(selectedAttrs);
		return selectedAttrs;
	}
	
	private List<Integer> getEligibleAttributesOfInstance(Instance instance, RDTAttribute[] attr, Set<Integer> usedAttrs){
		List<Integer> eligibleAttrs	= new LinkedList<Integer>(); //list of eligible attr in that instance
		
		//determine eligible attributes
		for(int selected=0; selected < attr.length; selected++){
			if(
				instance.value(attr[selected].getAttributeId()) == 1 && //check if attr is set in instance
				!usedAttrs.contains(attr[selected].getAttributeId())//check if attr was used before
			){
				eligibleAttrs.add(attr[selected].getAttributeId());  //add attr to selected attr if eligible
			}
		}
		return eligibleAttrs;
	}
	
	
	@Override
	public int determineChild(Instance inst) throws RDTException {
		for(int i= 0; i < usedAttrIds.length; i++){
			if(inst.value(usedAttrIds[i]) == 1){
				return 1;
			}
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

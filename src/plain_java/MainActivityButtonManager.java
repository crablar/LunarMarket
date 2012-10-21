package plain_java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import android.widget.Button;

import com.jeffmeyerson.moonstocks.CompanyModel;

/**
 * The CompanyButtons singleton class.  Only used to wrap the company buttons in the MainActivity.
 * 
 * @author jeffreymeyerson
 *
 */
public class MainActivityButtonManager {

	
	
	private HashMap<String, Button> nameButtonPairs;
	
	public MainActivityButtonManager(LinkedList<CompanyModel> companyModels){
		
		// Fill the map with company
		for(CompanyModel companyModel : companyModels){
			String companyName = companyModel.getCompanyName();
			nameButtonPairs.put(companyName, null);
		}
	}
	
	public Set<String> getCompanyNames(){
		return nameButtonPairs.keySet();
	}
}

package plain_java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	
	public MainActivityButtonManager(List<CompanyModel> companyModels){
		
		// Fill the map with company data
		for(CompanyModel companyModel : companyModels){
			String companyName = companyModel.getCompanyName();
			nameButtonPairs.put(companyName, null);
		}
	}
	
	public Set<String> getCompanyNames(){
		return nameButtonPairs.keySet();
	}
	
	public void addCompanyButton(String company, Button button){
		nameButtonPairs.put(company, button);
	}
	
}

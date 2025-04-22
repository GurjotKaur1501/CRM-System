package se.yrgo.services.diary;

import java.util.List;
import java.util.ArrayList;

import se.yrgo.domain.Action;

public class DiaryManagementServiceMockImpl implements DiaryManagementService {
	
	private List<Action> actions = new ArrayList<>();

	@Override
	public void recordAction(Action action) {
		actions.add(action);
	}

	//Hint: 
	//Create a list<Action>
	//In the for each loop going through the list use this condition: "if(action.getOwningUser().equals(requiredUser) && !action.isComplete())" to add a new action to the list. 
	public List<Action> getAllIncompleteActions(String requiredUser) {
		return new ArrayList<>(actions);
	}

	public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}

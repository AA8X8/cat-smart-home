package edu.rutmiit.demo.demorest.graphql.types;

import edu.rutmiit.demo.catapicontract.dto.ActionRequest;
import java.util.Map;

public class ActionInput {
    private ActionRequest.ActionType type;
    private String catId;
    private Map<String, Object> parameters;

    public ActionRequest.ActionType getType() {
        return type;
    }

    public void setType(ActionRequest.ActionType type) {
        this.type = type;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}